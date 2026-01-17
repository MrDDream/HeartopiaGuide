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
import com.heartopia.timer.data.Bird
import com.heartopia.timer.databinding.DialogBirdImageBinding
import com.heartopia.timer.databinding.ItemBirdBinding

class BirdAdapter : ListAdapter<Bird, BirdViewHolder>(BirdDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirdViewHolder {
        val binding = ItemBirdBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BirdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BirdViewHolder, position: Int) {
        val bird = getItem(position)
        holder.bind(bird)
    }
}

class BirdViewHolder(
    private val binding: ItemBirdBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bird: Bird) {
        val context = binding.root.context
        
        binding.birdNameTextView.text = context.getString(bird.nameResId)
        binding.birdLocationTextView.text = context.getString(bird.locationResId)
        binding.birdLevelTextView.text = context.getString(bird.levelResId)
        
        val price = context.getString(bird.priceResId)
        if (price == "-") {
            binding.birdPriceTextView.text = price
            binding.birdPriceTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            binding.birdGoldIconImageView.visibility = android.view.View.GONE
        } else {
            // Extraire le nombre du prix (enlever le "G")
            val priceNumber = price.replace("G", "").trim()
            binding.birdPriceTextView.text = priceNumber
            // La couleur est déjà définie dans le layout (?attr/colorOnSurfaceVariant), pas besoin de la changer
            binding.birdGoldIconImageView.visibility = android.view.View.VISIBLE
        }
        
        // Charger l'icône
        binding.birdIconImageView.setImageResource(bird.iconResId)
        
        // Activer le clic uniquement si ce n'est pas l'icône par défaut
        val isDefaultIcon = bird.iconResId == AndroidR.drawable.ic_menu_info_details
        if (isDefaultIcon) {
            binding.birdIconImageView.isClickable = false
            binding.birdIconImageView.isFocusable = false
            binding.birdIconImageView.setOnClickListener(null)
            binding.birdIconImageView.foreground = null
        } else {
            binding.birdIconImageView.isClickable = true
            binding.birdIconImageView.isFocusable = true
            binding.birdIconImageView.setOnClickListener {
                showEnlargedImageDialog(context, bird)
            }
        }
        
        // Afficher la météo
        binding.birdWeatherTextView.text = context.getString(bird.weatherResId)
    }
    
    private fun showEnlargedImageDialog(context: android.content.Context, bird: Bird) {
        val dialogBinding = DialogBirdImageBinding.inflate(LayoutInflater.from(context))
        
        val isDefaultIcon = bird.iconResId == AndroidR.drawable.ic_menu_info_details
        
        if (isDefaultIcon) {
            // Afficher l'emoji dans un TextView pour les icônes par défaut
            dialogBinding.enlargedBirdImageView.visibility = android.view.View.GONE
            dialogBinding.enlargedBirdEmojiTextView.visibility = android.view.View.VISIBLE
            // L'emoji est déjà défini dans le layout, mais on pourrait le changer si nécessaire
            // Pour l'instant, on garde l'emoji par défaut
        } else {
            // Afficher l'image pour les vraies images
            dialogBinding.enlargedBirdImageView.visibility = android.view.View.VISIBLE
            dialogBinding.enlargedBirdEmojiTextView.visibility = android.view.View.GONE
            dialogBinding.enlargedBirdImageView.setImageResource(bird.iconResId)
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

class BirdDiffCallback : DiffUtil.ItemCallback<Bird>() {
    override fun areItemsTheSame(oldItem: Bird, newItem: Bird): Boolean {
        return oldItem.nameResId == newItem.nameResId
    }

    override fun areContentsTheSame(oldItem: Bird, newItem: Bird): Boolean {
        return oldItem == newItem
    }
}
