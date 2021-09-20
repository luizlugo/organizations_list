package com.volcanolabs.githuborganizations.presentation.ui.organizationsList

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.volcanolabs.githuborganizations.R
import com.volcanolabs.githuborganizations.data.models.Organization
import com.volcanolabs.githuborganizations.databinding.OrganizationListItemBinding

class OrganizationsListAdapter(private val listener: OrganizationsListListener) :
    RecyclerView.Adapter<OrganizationsListAdapter.ViewHolder>() {
    private var organizations: List<Organization>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(organizations: List<Organization>) {
        this.organizations = organizations
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OrganizationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Set Spannable for view on github
        val viewGithub = parent.context.getString(R.string.view_github)
        val viewGithubSpan = SpannableString(viewGithub)
        viewGithubSpan.setSpan(
            UnderlineSpan(),
            0,
            viewGithub.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        binding.btnViewGithub.text = viewGithubSpan
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val organization = organizations?.get(position)
        if (organization != null) {
            holder.bind(organization)
        }
    }

    override fun getItemCount() = organizations?.size ?: 0

    inner class ViewHolder(val binding: OrganizationListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var organization: Organization

        fun bind(organization: Organization) {
            this.organization = organization
            binding.tvName.text = organization.login

            if (organization.description.isNullOrEmpty()) {
                // hide description
                binding.tvDescription.visibility = View.GONE
            } else {
                binding.tvDescription.visibility = View.VISIBLE
                binding.tvDescription.text = organization.description
            }

            binding.btnViewGithub.setOnClickListener {
                organization.url?.let {
                    listener.openGithub(it)
                }
            }

            Picasso.get()
                .load(organization.avatarUrl)
                .into(binding.ivImage)
        }

        override fun onClick(p0: View?) {
            listener.onOrganizationClick(organization)
        }
    }

    interface OrganizationsListListener {
        fun onOrganizationClick(organization: Organization)
        fun openGithub(url: String)
    }
}