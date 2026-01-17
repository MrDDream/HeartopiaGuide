package com.heartopia.timer.ui

import android.R as AndroidR
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.heartopia.timer.R
import com.heartopia.timer.data.Bug
import com.heartopia.timer.databinding.DialogBugImageBinding
import com.heartopia.timer.databinding.ItemBugBinding

class BugAdapter : ListAdapter<Bug, BugViewHolder>(BugDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BugViewHolder {
        val binding = ItemBugBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BugViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BugViewHolder, position: Int) {
        val bug = getItem(position)
        holder.bind(bug)
    }
}

class BugViewHolder(
    private val binding: ItemBugBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bug: Bug) {
        val context = binding.root.context
        
        binding.bugNameTextView.text = context.getString(bug.nameResId)
        binding.bugLocationTextView.text = context.getString(bug.locationResId)
        binding.bugLevelTextView.text = context.getString(bug.levelResId)
        
        val price = context.getString(bug.priceResId)
        if (price == "-") {
            binding.bugPriceNumberTextView.text = price
            binding.bugPriceNumberTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            binding.bugGoldIconImageView.visibility = android.view.View.GONE
        } else {
            // Extraire le nombre du prix (enlever le "G")
            val priceNumber = price.replace("G", "").trim()
            binding.bugPriceNumberTextView.text = priceNumber
            // La couleur est déjà définie dans le layout (?attr/colorOnSurfaceVariant), pas besoin de la changer
            binding.bugGoldIconImageView.visibility = android.view.View.VISIBLE
        }
        
        // Charger l'icône
        binding.bugIconImageView.setImageResource(bug.iconResId)
        
        // Activer le clic uniquement si ce n'est pas l'icône par défaut
        val isDefaultIcon = bug.iconResId == AndroidR.drawable.ic_menu_info_details
        if (isDefaultIcon) {
            binding.bugIconImageView.isClickable = false
            binding.bugIconImageView.isFocusable = false
            binding.bugIconImageView.setOnClickListener(null)
            binding.bugIconImageView.foreground = null
        } else {
            binding.bugIconImageView.isClickable = true
            binding.bugIconImageView.isFocusable = true
            binding.bugIconImageView.setOnClickListener {
                showEnlargedImageDialog(context, bug)
            }
        }
        
        // Afficher la météo
        binding.bugWeatherTextView.text = context.getString(bug.weatherResId)
    }
    
    private fun showEnlargedImageDialog(context: android.content.Context, bug: Bug) {
        val dialogBinding = DialogBugImageBinding.inflate(LayoutInflater.from(context))
        
        val isDefaultIcon = bug.iconResId == AndroidR.drawable.ic_menu_info_details
        
        if (isDefaultIcon) {
            // Afficher l'emoji dans un TextView pour les icônes par défaut
            dialogBinding.enlargedBugImageView.visibility = android.view.View.GONE
            dialogBinding.enlargedBugEmojiTextView.visibility = android.view.View.VISIBLE
            // L'emoji est déjà défini dans le layout, mais on pourrait le changer si nécessaire
            // Pour l'instant, on garde l'emoji par défaut
        } else {
            // Afficher l'image pour les vraies images
            dialogBinding.enlargedBugImageView.visibility = android.view.View.VISIBLE
            dialogBinding.enlargedBugEmojiTextView.visibility = android.view.View.GONE
            dialogBinding.enlargedBugImageView.setImageResource(bug.iconResId)
        }
        
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(dialogBinding.root)
            .create()
        
        // Permettre de fermer en cliquant en dehors
        dialogBinding.root.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}

class BugDiffCallback : DiffUtil.ItemCallback<Bug>() {
    override fun areItemsTheSame(oldItem: Bug, newItem: Bug): Boolean {
        return oldItem.nameResId == newItem.nameResId
    }

    override fun areContentsTheSame(oldItem: Bug, newItem: Bug): Boolean {
        return oldItem == newItem
    }
}
