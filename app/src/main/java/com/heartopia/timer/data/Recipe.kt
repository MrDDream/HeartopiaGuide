package com.heartopia.timer.data

import android.R as AndroidR
import com.heartopia.timer.R

data class Recipe(
    val nameResId: Int,
    val ingredientsResId: Int,
    val priceResId: Int,
    val energyResId: Int,
    val iconResId: Int
)

object RecipeData {
    
    private fun getIconResId(nameResId: Int): Int {
        return when (nameResId) {
            R.string.recipe_assortiment_fruit_mer_luxe -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_aubergines_gratinees_bolaignaise -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_bagel_poisson_fume -> R.drawable.recipe_bagel_au_poisson_fume
            R.string.recipe_boisson_etrange -> R.drawable.recipe_boisson_etrange
            R.string.recipe_burger_viande_fraiche -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_cafe_latte -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_coffee -> R.drawable.recipe_cafe
            R.string.recipe_cepes_bordeaux_rotis -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_champignons_rotis -> R.drawable.recipe_champignons_r_tis
            R.string.recipe_confiture_assortie -> R.drawable.recipe_confiture_assortie
            R.string.recipe_confiture_ananas -> R.drawable.recipe_confiture_d_ananas
            R.string.recipe_confiture_orange -> R.drawable.recipe_confiture_d_orange
            R.string.recipe_confiture_fraise -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_confiture_framboise -> R.drawable.recipe_confiture_de_framboise
            R.string.recipe_confiture_myrtille -> R.drawable.recipe_confiture_de_myrtille
            R.string.recipe_confiture_pomme -> R.drawable.recipe_confiture_de_pomme
            R.string.recipe_confiture_raisin -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_diner_chandelles -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_gateau_carotte -> R.drawable.recipe_gateau_a_la_carotte
            R.string.recipe_gateau_fromage -> R.drawable.recipe_gateau_au_fromage
            R.string.recipe_gouter_anglais -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_lentins_chene_rotis -> R.drawable.recipe_lentins_du_chtis
            R.string.recipe_menue_camping -> R.drawable.recipe_menue_camping
            R.string.recipe_mousserons_rotis -> R.drawable.recipe_mousserons_r_tis
            R.string.recipe_nourriture_etrange -> R.drawable.recipe_nourriture_trange
            R.string.recipe_pates_creme_truffes_noires -> R.drawable.recipe_p_la_cr_me_de_truffes_noires
            R.string.recipe_pizza_fruits_mer -> R.drawable.recipe_pizza_aux_fruits_de_mer
            R.string.recipe_plateau_ecrevisse -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_pleurotes_rotis -> R.drawable.recipe_pleurotes_r_tis
            R.string.recipe_poisson_frites -> R.drawable.recipe_poisson_et_frites
            R.string.recipe_ragout_rustique -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_risotto_fruits_mer -> R.drawable.recipe_risotto_aux_fruits_de_mer
            R.string.recipe_rouleau_nage_flottants_rouge -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_rouleau_nuage_flottants_saveur_originale -> R.drawable.recipe_rouleau_de_nuage_flottants_a_la_saveur_originale
            R.string.recipe_rouleau_nuages_flottants_orange -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_rouleau_nuages_flottants_bleu -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_rouleau_nuages_flottants_cyan -> R.drawable.recipe_rouleau_de_nuages_flottants_cyan
            R.string.recipe_rouleau_nuages_flottants_jaune -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_rouleau_nuages_flottants_vert -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_rouleau_nuages_flottants_violet -> R.drawable.recipe_rouleau_de_nuages_flottants_violet
            R.string.recipe_salade_campagne -> R.drawable.recipe_salade_de_campagne
            R.string.recipe_sauce_chocolat -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_tomato_sauce -> R.drawable.recipe_sauce_tomate
            R.string.recipe_soupe_epaisse_mais -> R.drawable.recipe_soupe_s
            R.string.recipe_spagehetti_polonaise -> R.drawable.recipe_spagehetti_polonaise
            R.string.recipe_tarte_pommes -> R.drawable.recipe_tarte_aux_pommes
            R.string.recipe_tarte_cepes_bordeaux -> R.drawable.recipe_tarte_de_cepes_de_bordeaux
            R.string.recipe_tarte_champignons -> R.drawable.recipe_tarte_de_champignons
            R.string.recipe_tarte_lentins_chene -> R.drawable.recipe_tarte_de_lentins_du_chene
            R.string.recipe_tarte_mousserons -> R.drawable.recipe_tarte_de_mousserons
            R.string.recipe_tarte_pleurotes -> R.drawable.recipe_tarte_de_pleurotes
            R.string.recipe_tarte_truffes_noires -> AndroidR.drawable.ic_menu_info_details
            R.string.recipe_tiramisu -> R.drawable.recipe_tiramisu
            else -> AndroidR.drawable.ic_menu_info_details
        }
    }
    
    private fun getPriceResId(price: String?): Int {
        val cleanPrice = price?.trim()?.replace(" ", "") ?: ""
        return when (cleanPrice) {
            "30" -> R.string.recipe_price_30
            "65" -> R.string.recipe_price_65
            "75" -> R.string.recipe_price_75
            "90" -> R.string.recipe_price_90
            "160" -> R.string.recipe_price_160
            "170" -> R.string.recipe_price_170
            "180" -> R.string.recipe_price_180
            "250" -> R.string.recipe_price_250
            "270" -> R.string.recipe_price_270
            "280" -> R.string.recipe_price_280
            "290" -> R.string.recipe_price_290
            "300" -> R.string.recipe_price_300
            "310" -> R.string.recipe_price_310
            "410" -> R.string.recipe_price_410
            "480" -> R.string.recipe_price_480
            "490" -> R.string.recipe_price_490
            "500" -> R.string.recipe_price_500
            "520" -> R.string.recipe_price_520
            "530" -> R.string.recipe_price_530
            "550" -> R.string.recipe_price_550
            "570" -> R.string.recipe_price_570
            "640" -> R.string.recipe_price_640
            "670" -> R.string.recipe_price_670
            "710" -> R.string.recipe_price_710
            "730" -> R.string.recipe_price_730
            "780" -> R.string.recipe_price_780
            "830" -> R.string.recipe_price_830
            "840" -> R.string.recipe_price_840
            "900" -> R.string.recipe_price_900
            "1230" -> R.string.recipe_price_1230
            "1340" -> R.string.recipe_price_1340
            "1760" -> R.string.recipe_price_1760
            "2260" -> R.string.recipe_price_2260
            "", " " -> R.string.recipe_price_na
            else -> R.string.recipe_price_na
        }
    }
    
    private fun getEnergyResId(energy: String?): Int {
        val cleanEnergy = energy?.trim()?.replace(" ", "")?.replace("x4", "")?.replace("x3", "")?.replace("x", "") ?: ""
        return when (cleanEnergy) {
            "10" -> R.string.recipe_energy_10
            "15" -> R.string.recipe_energy_15
            "22" -> R.string.recipe_energy_22
            "25" -> R.string.recipe_energy_25
            "30" -> R.string.recipe_energy_30
            "35" -> R.string.recipe_energy_35
            "40" -> R.string.recipe_energy_40
            "48" -> R.string.recipe_energy_48
            "50" -> R.string.recipe_energy_50
            "55" -> R.string.recipe_energy_55
            "60" -> R.string.recipe_energy_60
            "65" -> R.string.recipe_energy_65
            "70" -> R.string.recipe_energy_70
            "80" -> R.string.recipe_energy_80
            "90" -> R.string.recipe_energy_90
            "100" -> R.string.recipe_energy_100
            "", " " -> R.string.recipe_energy_na
            else -> R.string.recipe_energy_na
        }
    }
    
    fun getAllRecipes(): List<Recipe> = listOf(
        Recipe(R.string.recipe_assortiment_fruit_mer_luxe, R.string.recipe_assortiment_fruit_mer_luxe_ingredients, R.string.recipe_price_na, R.string.recipe_energy_65, getIconResId(R.string.recipe_assortiment_fruit_mer_luxe)),
        Recipe(R.string.recipe_aubergines_gratinees_bolaignaise, R.string.recipe_aubergines_gratinees_bolaignaise_ingredients, R.string.recipe_price_1230, R.string.recipe_energy_na, getIconResId(R.string.recipe_aubergines_gratinees_bolaignaise)),
        Recipe(R.string.recipe_bagel_poisson_fume, R.string.recipe_bagel_poisson_fume_ingredients, R.string.recipe_price_520, R.string.recipe_energy_50, getIconResId(R.string.recipe_bagel_poisson_fume)),
        Recipe(R.string.recipe_boisson_etrange, R.string.recipe_boisson_etrange_ingredients, R.string.recipe_price_30, R.string.recipe_energy_10, getIconResId(R.string.recipe_boisson_etrange)),
        Recipe(R.string.recipe_burger_viande_fraiche, R.string.recipe_burger_viande_fraiche_ingredients, R.string.recipe_price_na, R.string.recipe_energy_na, getIconResId(R.string.recipe_burger_viande_fraiche)),
        Recipe(R.string.recipe_cafe_latte, R.string.recipe_cafe_latte_ingredients, R.string.recipe_price_300, R.string.recipe_energy_40, getIconResId(R.string.recipe_cafe_latte)),
        Recipe(R.string.recipe_coffee, R.string.recipe_coffee_ingredients, R.string.recipe_price_290, R.string.recipe_energy_40, getIconResId(R.string.recipe_coffee)),
        Recipe(R.string.recipe_cepes_bordeaux_rotis, R.string.recipe_cepes_bordeaux_rotis_ingredients, R.string.recipe_price_180, R.string.recipe_energy_15, getIconResId(R.string.recipe_cepes_bordeaux_rotis)),
        Recipe(R.string.recipe_champignons_rotis, R.string.recipe_champignons_rotis_ingredients, R.string.recipe_price_180, R.string.recipe_energy_15, getIconResId(R.string.recipe_champignons_rotis)),
        Recipe(R.string.recipe_confiture_assortie, R.string.recipe_confiture_assortie_ingredients, R.string.recipe_price_160, R.string.recipe_energy_22, getIconResId(R.string.recipe_confiture_assortie)),
        Recipe(R.string.recipe_confiture_ananas, R.string.recipe_confiture_ananas_ingredients, R.string.recipe_price_280, R.string.recipe_energy_40, getIconResId(R.string.recipe_confiture_ananas)),
        Recipe(R.string.recipe_confiture_orange, R.string.recipe_confiture_orange_ingredients, R.string.recipe_price_270, R.string.recipe_energy_35, getIconResId(R.string.recipe_confiture_orange)),
        Recipe(R.string.recipe_confiture_fraise, R.string.recipe_confiture_fraise_ingredients, R.string.recipe_price_na, R.string.recipe_energy_na, getIconResId(R.string.recipe_confiture_fraise)),
        Recipe(R.string.recipe_confiture_framboise, R.string.recipe_confiture_framboise_ingredients, R.string.recipe_price_250, R.string.recipe_energy_30, getIconResId(R.string.recipe_confiture_framboise)),
        Recipe(R.string.recipe_confiture_myrtille, R.string.recipe_confiture_myrtille_ingredients, R.string.recipe_price_170, R.string.recipe_energy_22, getIconResId(R.string.recipe_confiture_myrtille)),
        Recipe(R.string.recipe_confiture_pomme, R.string.recipe_confiture_pomme_ingredients, R.string.recipe_price_270, R.string.recipe_energy_35, getIconResId(R.string.recipe_confiture_pomme)),
        Recipe(R.string.recipe_confiture_raisin, R.string.recipe_confiture_raisin_ingredients, R.string.recipe_price_na, R.string.recipe_energy_na, getIconResId(R.string.recipe_confiture_raisin)),
        Recipe(R.string.recipe_diner_chandelles, R.string.recipe_diner_chandelles_ingredients, R.string.recipe_price_1760, R.string.recipe_energy_na, getIconResId(R.string.recipe_diner_chandelles)),
        Recipe(R.string.recipe_gateau_carotte, R.string.recipe_gateau_carotte_ingredients, R.string.recipe_price_840, R.string.recipe_energy_55, getIconResId(R.string.recipe_gateau_carotte)),
        Recipe(R.string.recipe_gateau_fromage, R.string.recipe_gateau_fromage_ingredients, R.string.recipe_price_480, R.string.recipe_energy_50, getIconResId(R.string.recipe_gateau_fromage)),
        Recipe(R.string.recipe_gouter_anglais, R.string.recipe_gouter_anglais_ingredients, R.string.recipe_price_710, R.string.recipe_energy_25, getIconResId(R.string.recipe_gouter_anglais)),
        Recipe(R.string.recipe_lentins_chene_rotis, R.string.recipe_lentins_chene_rotis_ingredients, R.string.recipe_price_180, R.string.recipe_energy_15, getIconResId(R.string.recipe_lentins_chene_rotis)),
        Recipe(R.string.recipe_menue_camping, R.string.recipe_menue_camping_ingredients, R.string.recipe_price_2260, R.string.recipe_energy_100, getIconResId(R.string.recipe_menue_camping)),
        Recipe(R.string.recipe_mousserons_rotis, R.string.recipe_mousserons_rotis_ingredients, R.string.recipe_price_180, R.string.recipe_energy_15, getIconResId(R.string.recipe_mousserons_rotis)),
        Recipe(R.string.recipe_nourriture_etrange, R.string.recipe_nourriture_etrange_ingredients, R.string.recipe_price_30, R.string.recipe_energy_10, getIconResId(R.string.recipe_nourriture_etrange)),
        Recipe(R.string.recipe_pates_creme_truffes_noires, R.string.recipe_pates_creme_truffes_noires_ingredients, R.string.recipe_price_900, R.string.recipe_energy_90, getIconResId(R.string.recipe_pates_creme_truffes_noires)),
        Recipe(R.string.recipe_pizza_fruits_mer, R.string.recipe_pizza_fruits_mer_ingredients, R.string.recipe_price_780, R.string.recipe_energy_70, getIconResId(R.string.recipe_pizza_fruits_mer)),
        Recipe(R.string.recipe_plateau_ecrevisse, R.string.recipe_plateau_ecrevisse_ingredients, R.string.recipe_price_410, R.string.recipe_energy_30, getIconResId(R.string.recipe_plateau_ecrevisse)),
        Recipe(R.string.recipe_pleurotes_rotis, R.string.recipe_pleurotes_rotis_ingredients, R.string.recipe_price_180, R.string.recipe_energy_15, getIconResId(R.string.recipe_pleurotes_rotis)),
        Recipe(R.string.recipe_poisson_frites, R.string.recipe_poisson_frites_ingredients, R.string.recipe_price_310, R.string.recipe_energy_40, getIconResId(R.string.recipe_poisson_frites)),
        Recipe(R.string.recipe_ragout_rustique, R.string.recipe_ragout_rustique_ingredients, R.string.recipe_price_640, R.string.recipe_energy_60, getIconResId(R.string.recipe_ragout_rustique)),
        Recipe(R.string.recipe_risotto_fruits_mer, R.string.recipe_risotto_fruits_mer_ingredients, R.string.recipe_price_490, R.string.recipe_energy_40, getIconResId(R.string.recipe_risotto_fruits_mer)),
        Recipe(R.string.recipe_rouleau_nage_flottants_rouge, R.string.recipe_rouleau_nage_flottants_rouge_ingredients, R.string.recipe_price_670, R.string.recipe_energy_48, getIconResId(R.string.recipe_rouleau_nage_flottants_rouge)),
        Recipe(R.string.recipe_rouleau_nuage_flottants_saveur_originale, R.string.recipe_rouleau_nuage_flottants_saveur_originale_ingredients, R.string.recipe_price_550, R.string.recipe_energy_35, getIconResId(R.string.recipe_rouleau_nuage_flottants_saveur_originale)),
        Recipe(R.string.recipe_rouleau_nuages_flottants_orange, R.string.recipe_rouleau_nuages_flottants_orange_ingredients, R.string.recipe_price_na, R.string.recipe_energy_na, getIconResId(R.string.recipe_rouleau_nuages_flottants_orange)),
        Recipe(R.string.recipe_rouleau_nuages_flottants_bleu, R.string.recipe_rouleau_nuages_flottants_bleu_ingredients, R.string.recipe_price_570, R.string.recipe_energy_48, getIconResId(R.string.recipe_rouleau_nuages_flottants_bleu)),
        Recipe(R.string.recipe_rouleau_nuages_flottants_cyan, R.string.recipe_rouleau_nuages_flottants_cyan_ingredients, R.string.recipe_price_na, R.string.recipe_energy_na, getIconResId(R.string.recipe_rouleau_nuages_flottants_cyan)),
        Recipe(R.string.recipe_rouleau_nuages_flottants_jaune, R.string.recipe_rouleau_nuages_flottants_jaune_ingredients, R.string.recipe_price_na, R.string.recipe_energy_na, getIconResId(R.string.recipe_rouleau_nuages_flottants_jaune)),
        Recipe(R.string.recipe_rouleau_nuages_flottants_vert, R.string.recipe_rouleau_nuages_flottants_vert_ingredients, R.string.recipe_price_na, R.string.recipe_energy_na, getIconResId(R.string.recipe_rouleau_nuages_flottants_vert)),
        Recipe(R.string.recipe_rouleau_nuages_flottants_violet, R.string.recipe_rouleau_nuages_flottants_violet_ingredients, R.string.recipe_price_570, R.string.recipe_energy_48, getIconResId(R.string.recipe_rouleau_nuages_flottants_violet)),
        Recipe(R.string.recipe_salade_campagne, R.string.recipe_salade_campagne_ingredients, R.string.recipe_price_90, R.string.recipe_energy_15, getIconResId(R.string.recipe_salade_campagne)),
        Recipe(R.string.recipe_sauce_chocolat, R.string.recipe_sauce_chocolat_ingredients, R.string.recipe_price_na, R.string.recipe_energy_na, getIconResId(R.string.recipe_sauce_chocolat)),
        Recipe(R.string.recipe_tomato_sauce, R.string.recipe_tomato_sauce_ingredients, R.string.recipe_price_180, R.string.recipe_energy_35, getIconResId(R.string.recipe_tomato_sauce)),
        Recipe(R.string.recipe_soupe_epaisse_mais, R.string.recipe_soupe_epaisse_mais_ingredients, R.string.recipe_price_1340, R.string.recipe_energy_80, getIconResId(R.string.recipe_soupe_epaisse_mais)),
        Recipe(R.string.recipe_spagehetti_polonaise, R.string.recipe_spagehetti_polonaise_ingredients, R.string.recipe_price_670, R.string.recipe_energy_80, getIconResId(R.string.recipe_spagehetti_polonaise)),
        Recipe(R.string.recipe_tarte_pommes, R.string.recipe_tarte_pommes_ingredients, R.string.recipe_price_730, R.string.recipe_energy_70, getIconResId(R.string.recipe_tarte_pommes)),
        Recipe(R.string.recipe_tarte_cepes_bordeaux, R.string.recipe_tarte_cepes_bordeaux_ingredients, R.string.recipe_price_500, R.string.recipe_energy_35, getIconResId(R.string.recipe_tarte_cepes_bordeaux)),
        Recipe(R.string.recipe_tarte_champignons, R.string.recipe_tarte_champignons_ingredients, R.string.recipe_price_500, R.string.recipe_energy_35, getIconResId(R.string.recipe_tarte_champignons)),
        Recipe(R.string.recipe_tarte_lentins_chene, R.string.recipe_tarte_lentins_chene_ingredients, R.string.recipe_price_500, R.string.recipe_energy_35, getIconResId(R.string.recipe_tarte_lentins_chene)),
        Recipe(R.string.recipe_tarte_mousserons, R.string.recipe_tarte_mousserons_ingredients, R.string.recipe_price_500, R.string.recipe_energy_35, getIconResId(R.string.recipe_tarte_mousserons)),
        Recipe(R.string.recipe_tarte_pleurotes, R.string.recipe_tarte_pleurotes_ingredients, R.string.recipe_price_500, R.string.recipe_energy_35, getIconResId(R.string.recipe_tarte_pleurotes)),
        Recipe(R.string.recipe_tarte_truffes_noires, R.string.recipe_tarte_truffes_noires_ingredients, R.string.recipe_price_830, R.string.recipe_energy_80, getIconResId(R.string.recipe_tarte_truffes_noires)),
        Recipe(R.string.recipe_tiramisu, R.string.recipe_tiramisu_ingredients, R.string.recipe_price_530, R.string.recipe_energy_65, getIconResId(R.string.recipe_tiramisu))
    )
}
