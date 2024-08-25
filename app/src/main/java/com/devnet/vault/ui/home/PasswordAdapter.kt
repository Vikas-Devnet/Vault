package com.devnet.vault.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devnet.vault.R
import com.devnet.vault.model.home.PasswordItem
import com.squareup.picasso.Picasso

class PasswordAdapter(
    private val items: List<PasswordItem>,
    private val onItemClicked: (PasswordItem) -> Unit
) : RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_password, parent, false)
        return PasswordViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClicked(item) }
    }

    override fun getItemCount(): Int = items.size

    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val urlTextView: TextView = itemView.findViewById(R.id.urlText)
        private val usernameTextView: TextView = itemView.findViewById(R.id.usernameText)
        private val imageView: ImageView = itemView.findViewById(R.id.urlLogo)

        fun bind(item: PasswordItem) {
            urlTextView.text = item.url
            usernameTextView.text = item.username
            // Use an image loading library like Glide or Picasso to load the image
            Picasso.get()
                .load(item.imageUrl)
//                .placeholder(R.drawable.placeholder_image) // Optional: show a placeholder while loading
//                .error(R.drawable.error_image) // Optional: show an error image if the load fails
                .into(imageView)        }
    }
}
