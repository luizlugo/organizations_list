package com.volcanolabs.githuborganizations.presentation.ui.organizationsList

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.volcanolabs.githuborganizations.R
import com.volcanolabs.githuborganizations.data.models.Organization
import com.volcanolabs.githuborganizations.databinding.ActivityOrganizationsListBinding
import com.volcanolabs.githuborganizations.presentation.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OrganizationsList : AppCompatActivity(), OrganizationsListAdapter.OrganizationsListListener {
    private var _binding: ActivityOrganizationsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrganizationsListViewModel by viewModels()
    private lateinit var adapter: OrganizationsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOrganizationsListBinding.inflate(layoutInflater)
        val view = binding.root
        setSupportActionBar(binding.topAppBar)

        adapter = OrganizationsListAdapter(this)
        binding.rvOrganizations.adapter = adapter

        viewModel.organizations.observe(this, {
            Timber.i("organizations retrieved")
            adapter.setData(it)
        })

        viewModel.screenStatus.observe(this, {
            when (it) {
                OrganizationsListViewModel.SCREEN_STATUS.LOADING_DATA -> displayLoading()
                OrganizationsListViewModel.SCREEN_STATUS.LOGGED_OUT -> performLogout()
                else -> hideLoading()
            }
        })

        setContentView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.organizations_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.btn_logout) {
            viewModel.performLogout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun performLogout() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(loginIntent)
    }

    private fun displayLoading() {
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingIndicator.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOrganizationClick(organization: Organization) {

    }

    override fun openGithub(url: String) {
        val githubUrl = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(githubUrl)
    }
}