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
import com.heartopia.timer.data.Fish
import com.heartopia.timer.databinding.DialogFishImageBinding
import com.heartopia.timer.databinding.ItemFishBinding

class FishAdapter : ListAdapter<Fish, FishViewHolder>(FishDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FishViewHolder {
        val binding = ItemFishBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FishViewHolder, position: Int) {
        val fish = getItem(position)
        holder.bind(fish)
    }
}

class FishViewHolder(
    private val binding: ItemFishBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(fish: Fish) {
        val context = binding.root.context
        
        binding.fishNameTextView.text = context.getString(fish.nameResId)
        binding.fishHabitatTextView.text = context.getString(fish.habitatResId)
        binding.fishActivityTimeTextView.text = context.getString(fish.activityTimeResId)
        binding.fishLevelTextView.text = context.getString(fish.levelResId)
        
        val price = context.getString(fish.priceResId)
        if (price == "-") {
            binding.fishPriceNumberTextView.text = price
            binding.fishPriceNumberTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            binding.fishGoldIconImageView.visibility = android.view.View.GONE
        } else {
            // Extraire le nombre du prix (enlever le "G")
            val priceNumber = price.replace("G", "").trim()
            binding.fishPriceNumberTextView.text = priceNumber
            // La couleur est déjà définie dans le layout (?attr/colorOnSurfaceVariant), pas besoin de la changer
            binding.fishGoldIconImageView.visibility = android.view.View.VISIBLE
        }
        
        // Charger l'icône
        binding.fishIconImageView.setImageResource(fish.iconResId)
        
        // Activer le clic uniquement si ce n'est pas l'icône par défaut
        val isDefaultIcon = fish.iconResId == AndroidR.drawable.ic_menu_info_details
        if (isDefaultIcon) {
            binding.fishIconImageView.isClickable = false
            binding.fishIconImageView.isFocusable = false
            binding.fishIconImageView.setOnClickListener(null)
            binding.fishIconImageView.foreground = null
        } else {
            binding.fishIconImageView.isClickable = true
            binding.fishIconImageView.isFocusable = true
            binding.fishIconImageView.setOnClickListener {
                showEnlargedImageDialog(context, fish)
            }
        }
        
        // Afficher la météo
        binding.fishWeatherTextView.text = context.getString(fish.weatherResId)
    }
    
    private fun showEnlargedImageDialog(context: android.content.Context, fish: Fish) {
        val dialogBinding = DialogFishImageBinding.inflate(LayoutInflater.from(context))
        
        val isDefaultIcon = fish.iconResId == AndroidR.drawable.ic_menu_info_details
        
        if (isDefaultIcon) {
            // Afficher l'emoji dans un TextView pour les icônes par défaut
            dialogBinding.enlargedFishImageView.visibility = android.view.View.GONE
            dialogBinding.enlargedFishEmojiTextView.visibility = android.view.View.VISIBLE
            // L'emoji est déjà défini dans le layout, mais on pourrait le changer si nécessaire
            // Pour l'instant, on garde l'emoji par défaut
        } else {
            // Afficher l'image pour les vraies images
            dialogBinding.enlargedFishImageView.visibility = android.view.View.VISIBLE
            dialogBinding.enlargedFishEmojiTextView.visibility = android.view.View.GONE
            dialogBinding.enlargedFishImageView.setImageResource(fish.iconResId)
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

class FishDiffCallback : DiffUtil.ItemCallback<Fish>() {
    override fun areItemsTheSame(oldItem: Fish, newItem: Fish): Boolean {
        return oldItem.nameResId == newItem.nameResId
    }

    override fun areContentsTheSame(oldItem: Fish, newItem: Fish): Boolean {
        return oldItem == newItem
    }
}
