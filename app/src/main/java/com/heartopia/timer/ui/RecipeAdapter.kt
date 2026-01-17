package com.heartopia.timer.ui

import android.R as AndroidR
import android.app.Dialog
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.heartopia.timer.R
import com.heartopia.timer.data.Recipe
import com.heartopia.timer.databinding.DialogRecipeImageBinding
import com.heartopia.timer.databinding.ItemRecipeBinding

class RecipeAdapter(
    private val onFavoriteClick: (Recipe) -> Unit,
    private val isFavorite: (Int) -> Boolean
) : ListAdapter<Recipe, RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding, onFavoriteClick, isFavorite)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }
}

class RecipeViewHolder(
    private val binding: ItemRecipeBinding,
    private val onFavoriteClick: (Recipe) -> Unit,
    private val isFavorite: (Int) -> Boolean
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recipe: Recipe) {
        val context = binding.root.context
        
        binding.recipeNameTextView.text = context.getString(recipe.nameResId)
        
        // Parser les ingrédients et afficher avec les icônes
        val ingredientsText = context.getString(recipe.ingredientsResId)
        displayIngredientsWithIcons(ingredientsText)
        
        // Afficher le prix
        val price = context.getString(recipe.priceResId)
        if (price == "-") {
            binding.recipePriceNumberTextView.text = price
            binding.recipePriceNumberTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            binding.recipeGoldIconImageView.visibility = View.GONE
        } else {
            // Extraire le nombre du prix (enlever le "G")
            val priceNumber = price.replace("G", "").trim()
            binding.recipePriceNumberTextView.text = priceNumber
            binding.recipeGoldIconImageView.visibility = View.VISIBLE
        }
        
        // Afficher l'énergie avec l'icône éclair
        val energy = context.getString(recipe.energyResId)
        if (energy == "-") {
            binding.recipeEnergyTextView.text = energy
            binding.recipeEnergyTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            binding.recipeEnergyIconImageView.visibility = View.GONE
        } else {
            binding.recipeEnergyTextView.text = energy
            binding.recipeEnergyIconImageView.visibility = View.VISIBLE
        }
        
        // Charger l'icône
        binding.recipeIconImageView.setImageResource(recipe.iconResId)
        
        // Activer le clic uniquement si ce n'est pas l'icône par défaut
        val isDefaultIcon = recipe.iconResId == AndroidR.drawable.ic_menu_info_details
        if (isDefaultIcon) {
            binding.recipeIconImageView.isClickable = false
            binding.recipeIconImageView.isFocusable = false
            binding.recipeIconImageView.setOnClickListener(null)
            binding.recipeIconImageView.foreground = null
        } else {
            binding.recipeIconImageView.isClickable = true
            binding.recipeIconImageView.isFocusable = true
            binding.recipeIconImageView.setOnClickListener {
                showEnlargedImageDialog(context, recipe)
            }
        }
        
        // Gérer l'icône de favoris
        val favorite = isFavorite(recipe.nameResId)
        val heartColor = if (favorite) {
            ContextCompat.getColor(context, R.color.red)
        } else {
            ContextCompat.getColor(context, android.R.color.darker_gray)
        }
        binding.favoriteButton.imageTintList = ColorStateList.valueOf(heartColor)
        
        binding.favoriteButton.setOnClickListener {
            onFavoriteClick(recipe)
        }
    }
    
    private fun displayIngredientsWithIcons(ingredientsText: String) {
        binding.recipeIngredientsLayout.removeAllViews()
        
        // Si le texte contient "Tout type de" ou "n'importe quel", afficher tel quel
        if (ingredientsText.contains("Tout type", ignoreCase = true) || 
            ingredientsText.contains("n'importe", ignoreCase = true) ||
            ingredientsText.contains("Rater", ignoreCase = true)) {
            val ingredientRow = LayoutInflater.from(binding.root.context)
                .inflate(R.layout.item_ingredient, binding.recipeIngredientsLayout, false) as LinearLayout
            
            val iconImageView = ingredientRow.findViewById<ImageView>(R.id.ingredientIconImageView)
            val nameTextView = ingredientRow.findViewById<TextView>(R.id.ingredientNameTextView)
            
            nameTextView.text = ingredientsText.replaceFirstChar { 
                if (it.isLowerCase()) it.titlecase() else it.toString() 
            }
            iconImageView.setImageResource(AndroidR.drawable.ic_menu_info_details)
            binding.recipeIngredientsLayout.addView(ingredientRow)
            return
        }
        
        // Séparer les ingrédients par virgule, tiret, ou "x" suivi d'un nombre
        val parts = mutableListOf<String>()
        
        // Pattern pour détecter "x1 Blé x1 Salade" ou "x1 Blé x1 Salade x1 Viande"
        val xPattern = Regex("x(\\d+)\\s+([^x]+?)(?=x\\d+|$)", RegexOption.IGNORE_CASE)
        val xMatches = xPattern.findAll(ingredientsText)
        
        if (xMatches.any()) {
            xMatches.forEach { match ->
                val number = match.groupValues[1]
                val name = match.groupValues[2].trim()
                if (name.isNotBlank()) {
                    parts.add("x$number $name")
                }
            }
        } else {
            // Séparer par virgule ou tiret
            val separators = Regex("[,]|[-]")
            val splitParts = ingredientsText.split(separators).map { it.trim() }.filter { it.isNotBlank() }
            parts.addAll(splitParts)
        }
        
        parts.forEach { ingredient ->
            val ingredientRow = LayoutInflater.from(binding.root.context)
                .inflate(R.layout.item_ingredient, binding.recipeIngredientsLayout, false) as LinearLayout
            
            val iconImageView = ingredientRow.findViewById<ImageView>(R.id.ingredientIconImageView)
            val nameTextView = ingredientRow.findViewById<TextView>(R.id.ingredientNameTextView)
            
            // Extraire le nombre et le nom (format: "x2 Carotte" ou "2 carottes" ou "carotte")
            val xPattern = Regex("^x(\\d+)\\s+(.+)$", RegexOption.IGNORE_CASE)
            val numberPattern = Regex("^(\\d+)\\s+(.+)$")
            
            val xMatch = xPattern.find(ingredient)
            val numberMatch = if (xMatch == null) numberPattern.find(ingredient) else null
            
            when {
                xMatch != null -> {
                    val number = xMatch.groupValues[1]
                    val name = xMatch.groupValues[2]
                    val capitalizedName = name.replaceFirstChar { 
                        if (it.isLowerCase()) it.titlecase() else it.toString() 
                    }
                    nameTextView.text = "x$number $capitalizedName"
                    val iconResId = getIngredientIconResId(name.trim())
                    iconImageView.setImageResource(iconResId)
                }
                numberMatch != null -> {
                    val number = numberMatch.groupValues[1]
                    val name = numberMatch.groupValues[2]
                    val capitalizedName = name.replaceFirstChar { 
                        if (it.isLowerCase()) it.titlecase() else it.toString() 
                    }
                    nameTextView.text = "x$number $capitalizedName"
                    val iconResId = getIngredientIconResId(name.trim())
                    iconImageView.setImageResource(iconResId)
                }
                else -> {
                    // Pour les ingrédients sans nombre (ex: "Aubergine", "Sauce Tomate")
                    val capitalizedName = ingredient.replaceFirstChar { 
                        if (it.isLowerCase()) it.titlecase() else it.toString() 
                    }
                    nameTextView.text = capitalizedName
                    val iconResId = getIngredientIconResId(ingredient.trim())
                    iconImageView.setImageResource(iconResId)
                }
            }
            
            binding.recipeIngredientsLayout.addView(ingredientRow)
        }
    }
    
    private fun getIngredientIconResId(ingredientName: String): Int {
        val normalizedName = ingredientName.lowercase()
            .replace(" ", "_")
            .replace("é", "e")
            .replace("è", "e")
            .replace("ê", "e")
            .replace("ë", "e")
            .replace("à", "a")
            .replace("â", "a")
            .replace("ä", "a")
            .replace("ç", "c")
            .replace("ô", "o")
            .replace("ö", "o")
            .replace("ù", "u")
            .replace("û", "u")
            .replace("ü", "u")
            .replace("î", "i")
            .replace("ï", "i")
        
        return when {
            normalizedName.contains("aubergine") -> AndroidR.drawable.ic_menu_info_details
            normalizedName.contains("ananas") -> R.drawable.ingredient_ananas
            normalizedName.contains("bambou") -> R.drawable.ingredient_bambou
            normalizedName.contains("beurre") -> R.drawable.ingredient_beurre
            normalizedName.contains("blé") || normalizedName.contains("ble") -> R.drawable.ingredient_ble
            normalizedName.contains("carotte") -> R.drawable.ingredient_carotte
            normalizedName.contains("cèpe") || normalizedName.contains("cepe") -> R.drawable.ingredient_cepes_de_bordeaux
            normalizedName.contains("framboise") -> R.drawable.ingredient_framboise
            normalizedName.contains("fromage") -> R.drawable.ingredient_fromage
            normalizedName.contains("café") || normalizedName.contains("cafe") || normalizedName.contains("grain") -> R.drawable.ingredient_grains_de_caf_
            normalizedName.contains("haricot") -> R.drawable.ingredient_haricot_rouge
            normalizedName.contains("huile") -> R.drawable.ingredient_huile_de_cuisson
            normalizedName.contains("lait") -> R.drawable.ingredient_lait
            normalizedName.contains("laitue") || normalizedName.contains("salade") -> R.drawable.ingredient_laitue
            normalizedName.contains("lentin") -> R.drawable.ingredient_lentins_du_ch_ne
            normalizedName.contains("mousseron") -> R.drawable.ingredient_mousseron
            normalizedName.contains("myrtille") -> R.drawable.ingredient_myrtille
            normalizedName.contains("oeuf") || normalizedName.contains("œuf") -> R.drawable.ingredient_oeuf
            normalizedName.contains("orange") -> R.drawable.ingredient_orange
            normalizedName.contains("pleurote") -> R.drawable.ingredient_pleurote
            normalizedName.contains("pomme") && !normalizedName.contains("terre") -> R.drawable.ingredient_pomme
            normalizedName.contains("pomme_de_terre") || normalizedName.contains("patate") -> R.drawable.ingredient_pomme_de_terre
            normalizedName.contains("matcha") -> R.drawable.ingredient_poudre_de_matcha
            normalizedName.contains("vermicelle") -> R.drawable.ingredient_sachet_de_vermicelles
            normalizedName.contains("thé") || normalizedName.contains("the") -> R.drawable.ingredient_th__noir
            normalizedName.contains("tomate") || normalizedName.contains("sauce tomate") -> R.drawable.ingredient_tomate
            normalizedName.contains("viande") -> R.drawable.ingredient_viande
            normalizedName.contains("crustace") || normalizedName.contains("crustacé") -> AndroidR.drawable.ic_menu_info_details
            normalizedName.contains("poisson") -> AndroidR.drawable.ic_menu_info_details
            normalizedName.contains("fruit") -> AndroidR.drawable.ic_menu_info_details
            normalizedName.contains("legume") || normalizedName.contains("légume") -> AndroidR.drawable.ic_menu_info_details
            normalizedName.contains("sucre") -> AndroidR.drawable.ic_menu_info_details
            normalizedName.contains("truffe") -> AndroidR.drawable.ic_menu_info_details
            normalizedName.contains("champignon") -> AndroidR.drawable.ic_menu_info_details
            else -> AndroidR.drawable.ic_menu_info_details
        }
    }
    
    private fun showEnlargedImageDialog(context: android.content.Context, recipe: Recipe) {
        val dialogBinding = DialogRecipeImageBinding.inflate(LayoutInflater.from(context))
        
        val isDefaultIcon = recipe.iconResId == AndroidR.drawable.ic_menu_info_details
        
        if (isDefaultIcon) {
            // Afficher l'emoji dans un TextView pour les icônes par défaut
            dialogBinding.enlargedRecipeImageView.visibility = View.GONE
            dialogBinding.enlargedRecipeEmojiTextView.visibility = View.VISIBLE
        } else {
            // Afficher l'image pour les vraies images
            dialogBinding.enlargedRecipeImageView.visibility = View.VISIBLE
            dialogBinding.enlargedRecipeEmojiTextView.visibility = View.GONE
            dialogBinding.enlargedRecipeImageView.setImageResource(recipe.iconResId)
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

class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.nameResId == newItem.nameResId
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}
