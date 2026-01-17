package com.heartopia.timer.data

import android.R as AndroidR
import com.heartopia.timer.R

data class Fish(
    val nameResId: Int,
    val habitatResId: Int,
    val activityTimeResId: Int,
    val weatherResId: Int,
    val levelResId: Int,
    val priceResId: Int,
    val iconResId: Int
)

object FishData {
    
    private fun getIconResId(nameResId: Int): Int {
        return when (nameResId) {
            R.string.fish_ablette -> R.drawable.fish_ablette
            R.string.fish_acantharchus_pomotis -> R.drawable.fish_acantharchus_pomotis
            R.string.fish_anguille_europeenne -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_aphyosemion_striatum -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_barbeau -> R.drawable.fish_barbeau
            R.string.fish_barbillon -> R.drawable.fish_barbillon
            R.string.fish_baudroie -> R.drawable.fish_baudroie
            R.string.fish_bernard_erminte -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_blennie_riviere -> R.drawable.fish_blennie_de_riviere
            R.string.fish_bonite -> R.drawable.fish_bonite
            R.string.fish_carpe_argente -> R.drawable.fish_carpe_argentee
            R.string.fish_carpe_europeenne -> R.drawable.fish_carpe_europeenne
            R.string.fish_carpe_noir -> R.drawable.fish_carpe_noir
            R.string.fish_carpe_papillon -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_chabot -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_chimere_argente -> R.drawable.fish_chimere_argentee
            R.string.fish_comete_coussut -> R.drawable.fish_comete_coussut
            R.string.fish_crabe_ruisseau -> R.drawable.fish_crabe_de_ruisseau
            R.string.fish_crevette_verte -> R.drawable.fish_crevette_verte
            R.string.fish_crevettes_mer -> R.drawable.fish_crevettes_de_mer
            R.string.fish_ecrevisse_noble -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_ecrevisse_noble_bleu -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_eglefin -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_eperlan_alt -> R.drawable.fish_eperlan
            R.string.fish_epinoche -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_fera -> R.drawable.fish_fera
            R.string.fish_grand_black_bass -> R.drawable.fish_grand_black_bass
            R.string.fish_grenouille_europeenne -> R.drawable.fish_grenouille_europeenne
            R.string.fish_hippocampe -> R.drawable.fish_hippocampe
            R.string.fish_langouste_europeenne -> R.drawable.fish_langouste_europeenne
            R.string.fish_loche_fleurs -> R.drawable.fish_loche_des_fleurs
            R.string.fish_loche_rochers -> R.drawable.fish_loche_des_rochers
            R.string.fish_lotte -> R.drawable.fish_lotte
            R.string.fish_maquereau_atlantique -> R.drawable.fish_maquereau_atlantique_
            R.string.fish_moule -> R.drawable.fish_moule
            R.string.fish_omble -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_omble_chevalier -> R.drawable.fish_omble_chevalier
            R.string.fish_oranda -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_pagellus_bogaraveo -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_perche_mer -> R.drawable.fish_perche_de_mer
            R.string.fish_perche_prunier -> R.drawable.fish_perche_de_prunier
            R.string.fish_perche_riviere -> R.drawable.fish_perche_de_riviere
            R.string.fish_pieuvre -> R.drawable.fish_pieuvre
            R.string.fish_pieuvre_naine_atlantique -> R.drawable.fish_pieuvre_naine_atlantique
            R.string.fish_plie_europeenne -> R.drawable.fish_plie_europeenne
            R.string.fish_poisson_yeux_rouge -> R.drawable.fish_poisson_a_yeux_rouge
            R.string.fish_poisson_epineux -> R.drawable.fish_poisson_epineux
            R.string.fish_poisson_lunaire -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_poisson_rouge -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_poisson_ruban -> R.drawable.fish_poisson_ruban
            R.string.fish_poisson_clown -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_poisson_globe_riviere -> R.drawable.fish_poisson_globe_de_riviere
            R.string.fish_poisson_tigre_ventre_rouge -> R.drawable.fish_poisson_tigre_a_ventre_rouge
            R.string.fish_regalec -> R.drawable.fish_regalec
            R.string.fish_rouget_barbet -> R.drawable.fish_rouget_barbet
            R.string.fish_roussette -> R.drawable.fish_roussette
            R.string.fish_sandre_blanc -> R.drawable.fish_sandre_blanc
            R.string.fish_sardine_alt -> R.drawable.fish_sardine
            R.string.fish_saumon_atlantique_alt -> R.drawable.fish_saumon_atlantique
            R.string.fish_saumon_keta -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_saurel -> R.drawable.fish_saurel
            R.string.fish_seche_europeenne -> R.drawable.fish_seche_europeenne_
            R.string.fish_tanche_doree -> R.drawable.fish_tanche_doree
            R.string.fish_thon_rouge -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_tilapia_alt -> R.drawable.fish_tilapia_
            R.string.fish_tortue -> AndroidR.drawable.ic_menu_info_details
            R.string.fish_truite -> R.drawable.fish_truite
            R.string.fish_vairon -> R.drawable.fish_vairon
            R.string.fish_zingel_streber -> R.drawable.fish_zingel_streber
            R.string.fish_gobie -> R.drawable.fish_gobie
            else -> AndroidR.drawable.ic_menu_info_details
        }
    }
    
    private fun getWeatherResId(weather: String?): Int {
        return when {
            weather == null || weather.isBlank() -> R.string.fish_weather_all
            weather.contains("Pluie", ignoreCase = true) && weather.contains("arc-en-ciel", ignoreCase = true) -> R.string.fish_weather_rain_rainbow
            weather.contains("Soleil", ignoreCase = true) && weather.contains("arc-en-ciel", ignoreCase = true) -> R.string.fish_weather_sun_rainbow
            weather.contains("Arc-en-ciel", ignoreCase = true) -> R.string.fish_weather_rainbow
            weather.contains("Toute", ignoreCase = true) -> R.string.fish_weather_all
            else -> R.string.fish_weather_all
        }
    }
    
    private fun getLevelResId(level: String?): Int {
        return when (level?.trim()) {
            "1" -> R.string.fish_level_1
            "2" -> R.string.fish_level_2
            "3" -> R.string.fish_level_3
            "4" -> R.string.fish_level_4
            "5" -> R.string.fish_level_5
            "6" -> R.string.fish_level_6
            "7" -> R.string.fish_level_7
            "8" -> R.string.fish_level_8
            "9" -> R.string.fish_level_9
            "10" -> R.string.fish_level_10
            "?" -> R.string.fish_level_na
            null, "", " " -> R.string.fish_level_na
            else -> R.string.fish_level_na
        }
    }
    
    private fun getPriceResId(price: String?): Int {
        return when (price?.trim()) {
            "50" -> R.string.fish_price_50g
            "75" -> R.string.fish_price_75g
            "100" -> R.string.fish_price_100g
            "105" -> R.string.fish_price_105g
            "150" -> R.string.fish_price_150g
            "155" -> R.string.fish_price_155g
            "210" -> R.string.fish_price_210g
            "230" -> R.string.fish_price_230g
            "250" -> R.string.fish_price_250g
            "320" -> R.string.fish_price_320g
            "535" -> R.string.fish_price_535g
            "850" -> R.string.fish_price_850g
            null, "", " " -> R.string.fish_price_na
            else -> R.string.fish_price_na
        }
    }
    
    private fun getTimeResId(time: String?): Int {
        return when {
            time == null || time.isBlank() -> R.string.fish_time_all_day
            time.contains("Toute", ignoreCase = true) -> R.string.fish_time_all_day
            time.contains("6h00") && time.contains("00H00", ignoreCase = true) -> R.string.fish_time_6h00_00h00
            time.contains("6H00") && time.contains("00H00", ignoreCase = true) -> R.string.fish_time_6h00_00h00
            time.contains("12h00") && time.contains("6h00", ignoreCase = true) -> R.string.fish_time_12h00_6h00
            time.contains("12h00") && time.contains("00h00", ignoreCase = true) -> R.string.fish_time_12h00_00h00
            time.contains("18h00") && time.contains("12h00", ignoreCase = true) -> R.string.fish_time_18h00_12h00
            time.contains("18h00") && time.contains("6h00", ignoreCase = true) -> R.string.fish_time_18h00_6h00
            time.contains("00h00") && time.contains("18H00", ignoreCase = true) -> R.string.fish_time_00h00_18h00
            time.contains("00h00") && time.contains("12h00", ignoreCase = true) -> R.string.fish_time_00h00_12h00
            else -> R.string.fish_time_all_day
        }
    }
    
    private fun getHabitatResId(habitat: String?): Int {
        return when {
            habitat == null || habitat.isBlank() -> R.string.fish_habitat_mer_ancienne
            habitat.contains("Lac", ignoreCase = true) && habitat.contains("forêt", ignoreCase = true) -> R.string.fish_habitat_lac_foret
            habitat.contains("Lac", ignoreCase = true) && habitat.contains("banlieue", ignoreCase = true) -> R.string.fish_habitat_lac_banlieue
            habitat.contains("Lac", ignoreCase = true) && habitat.contains("prairie", ignoreCase = true) -> R.string.fish_habitat_lac_prairie
            habitat.contains("Lac", ignoreCase = true) && habitat.contains("Montagne thermale", ignoreCase = true) -> R.string.fish_habitat_lac_montagne_thermale
            habitat.contains("Lac montagne thermale", ignoreCase = true) -> R.string.fish_habitat_lac_montagne_thermale_alt
            habitat.contains("Lac", ignoreCase = true) -> R.string.fish_habitat_lac
            habitat.contains("Rivière", ignoreCase = true) && habitat.contains("Aurore", ignoreCase = true) -> R.string.fish_habitat_riviere_aurore
            habitat.contains("Rivière", ignoreCase = true) && habitat.contains("calme", ignoreCase = true) -> R.string.fish_habitat_riviere_calme
            habitat.contains("Rivière", ignoreCase = true) && habitat.contains("peu profonde", ignoreCase = true) -> R.string.fish_habitat_riviere_peu_profonde_alt
            habitat.contains("Rivière", ignoreCase = true) -> R.string.fish_habitat_riviere
            habitat.contains("Fleuve", ignoreCase = true) && habitat.contains("géants", ignoreCase = true) -> R.string.fish_habitat_fleuve_arbres_geants
            habitat.contains("Ancienne mer", ignoreCase = true) -> R.string.fish_habitat_ancienne_mer
            habitat.contains("Mer de baleine", ignoreCase = true) -> R.string.fish_habitat_mer_baleine
            habitat.contains("Mer calme", ignoreCase = true) -> R.string.fish_habitat_mer_calme
            habitat.contains("Mer de l'Est", ignoreCase = true) -> R.string.fish_habitat_mer_est_alt
            habitat.contains("Océan", ignoreCase = true) -> R.string.fish_habitat_ocean_alt
            habitat.contains("Pêche en mer", ignoreCase = true) -> R.string.fish_habitat_peche_mer
            habitat.contains("Pêche en Mer", ignoreCase = true) -> R.string.fish_habitat_peche_mer_alt
            habitat.contains("Leurre sirène", ignoreCase = true) -> R.string.fish_habitat_leurre_sirene
            else -> R.string.fish_habitat_mer_ancienne
        }
    }
    
    fun getAllFish(): List<Fish> = listOf(
        Fish(R.string.fish_ablette, R.string.fish_habitat_lac, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_ablette)),
        Fish(R.string.fish_acantharchus_pomotis, R.string.fish_habitat_lac_foret, R.string.fish_time_6h00_00h00, R.string.fish_weather_all, R.string.fish_level_2, R.string.fish_price_150g, getIconResId(R.string.fish_acantharchus_pomotis)),
        Fish(R.string.fish_anguille_europeenne, R.string.fish_habitat_ancienne_mer, R.string.fish_time_all_day, R.string.fish_weather_rainbow, R.string.fish_level_7, R.string.fish_price_na, getIconResId(R.string.fish_anguille_europeenne)),
        Fish(R.string.fish_aphyosemion_striatum, R.string.fish_habitat_lac_banlieue, R.string.fish_time_12h00_6h00, R.string.fish_weather_sun_rainbow, R.string.fish_level_7, R.string.fish_price_150g, getIconResId(R.string.fish_aphyosemion_striatum)),
        Fish(R.string.fish_barbeau, R.string.fish_habitat_lac_banlieue, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_barbeau)),
        Fish(R.string.fish_barbillon, R.string.fish_habitat_riviere_peu_profonde_alt, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_75g, getIconResId(R.string.fish_barbillon)),
        Fish(R.string.fish_baudroie, R.string.fish_habitat_peche_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_2, R.string.fish_price_320g, getIconResId(R.string.fish_baudroie)),
        Fish(R.string.fish_bernard_erminte, R.string.fish_habitat_mer_est_alt, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_3, R.string.fish_price_100g, getIconResId(R.string.fish_bernard_erminte)),
        Fish(R.string.fish_blennie_riviere, R.string.fish_habitat_riviere_aurore, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_5, R.string.fish_price_150g, getIconResId(R.string.fish_blennie_riviere)),
        Fish(R.string.fish_bonite, R.string.fish_habitat_ocean_alt, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_210g, getIconResId(R.string.fish_bonite)),
        Fish(R.string.fish_carpe_argente, R.string.fish_habitat_lac, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_75g, getIconResId(R.string.fish_carpe_argente)),
        Fish(R.string.fish_carpe_europeenne, R.string.fish_habitat_riviere_aurore, R.string.fish_time_12h00_00h00, R.string.fish_weather_sun_rainbow, R.string.fish_level_4, R.string.fish_price_230g, getIconResId(R.string.fish_carpe_europeenne)),
        Fish(R.string.fish_carpe_noir, R.string.fish_habitat_lac_banlieue, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_75g, getIconResId(R.string.fish_carpe_noir)),
        Fish(R.string.fish_carpe_papillon, R.string.fish_habitat_lac_prairie, R.string.fish_time_all_day, R.string.fish_weather_rain_rainbow, R.string.fish_level_4, R.string.fish_price_320g, getIconResId(R.string.fish_carpe_papillon)),
        Fish(R.string.fish_chabot, R.string.fish_habitat_lac_montagne_thermale, R.string.fish_time_6h00_00h00, R.string.fish_weather_rain_rainbow, R.string.fish_level_7, R.string.fish_price_150g, getIconResId(R.string.fish_chabot)),
        Fish(R.string.fish_chimere_argente, R.string.fish_habitat_leurre_sirene, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_4, R.string.fish_price_320g, getIconResId(R.string.fish_chimere_argente)),
        Fish(R.string.fish_comete_coussut, R.string.fish_habitat_mer_calme, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_2, R.string.fish_price_155g, getIconResId(R.string.fish_comete_coussut)),
        Fish(R.string.fish_crabe_ruisseau, R.string.fish_habitat_lac_banlieue, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_5, R.string.fish_price_100g, getIconResId(R.string.fish_crabe_ruisseau)),
        Fish(R.string.fish_crabe_royal, R.string.fish_habitat_mer_baleine, R.string.fish_time_00h00_18h00, R.string.fish_weather_rainbow, R.string.fish_level_8, R.string.fish_price_na, getIconResId(R.string.fish_crabe_royal)),
        Fish(R.string.fish_crapet_soleil_bossu, R.string.fish_habitat_lac_montagne_thermale, R.string.fish_time_6h00_00h00, R.string.fish_weather_sun_rainbow, R.string.fish_level_9, R.string.fish_price_na, getIconResId(R.string.fish_crapet_soleil_bossu)),
        Fish(R.string.fish_crevette_verte, R.string.fish_habitat_riviere, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_crevette_verte)),
        Fish(R.string.fish_crevettes_mer, R.string.fish_habitat_mer_est_alt, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_crevettes_mer)),
        Fish(R.string.fish_ecrevisse_noble, R.string.fish_habitat_lac_foret, R.string.fish_time_18h00_12h00, R.string.fish_weather_all, R.string.fish_level_3, R.string.fish_price_100g, getIconResId(R.string.fish_ecrevisse_noble)),
        Fish(R.string.fish_ecrevisse_noble_bleu, R.string.fish_habitat_lac_foret, R.string.fish_time_18h00_6h00, R.string.fish_weather_all, R.string.fish_level_8, R.string.fish_price_250g, getIconResId(R.string.fish_ecrevisse_noble_bleu)),
        Fish(R.string.fish_eglefin, R.string.fish_habitat_mer_est_alt, R.string.fish_time_12h00_6h00, R.string.fish_weather_sun_rainbow, R.string.fish_level_8, R.string.fish_price_230g, getIconResId(R.string.fish_eglefin)),
        Fish(R.string.fish_eperlan_alt, R.string.fish_habitat_lac_prairie, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_2, R.string.fish_price_100g, getIconResId(R.string.fish_eperlan_alt)),
        Fish(R.string.fish_epinoche, R.string.fish_habitat_riviere_peu_profonde_alt, R.string.fish_time_all_day, R.string.fish_weather_rain_rainbow, R.string.fish_level_7, R.string.fish_price_150g, getIconResId(R.string.fish_epinoche)),
        Fish(R.string.fish_fera, R.string.fish_habitat_lac_montagne_thermale_alt, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_105g, getIconResId(R.string.fish_fera)),
        Fish(R.string.fish_gobie, R.string.fish_habitat_mer_est_alt, R.string.fish_time_6am_6pm, R.string.fish_weather_all, R.string.fish_level_4, R.string.fish_price_150g, getIconResId(R.string.fish_gobie)),
        Fish(R.string.fish_grand_black_bass, R.string.fish_habitat_lac_foret, R.string.fish_time_all_day, R.string.fish_weather_sun_rainbow, R.string.fish_level_4, R.string.fish_price_230g, getIconResId(R.string.fish_grand_black_bass)),
        Fish(R.string.fish_grande_huitre_perliere, R.string.fish_habitat_lac_foret, R.string.fish_time_all_day, R.string.fish_weather_rainbow, R.string.fish_level_na, R.string.fish_price_na, getIconResId(R.string.fish_grande_huitre_perliere)),
        Fish(R.string.fish_grenouille_europeenne, R.string.fish_habitat_leurre_sirene, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_4, R.string.fish_price_320g, getIconResId(R.string.fish_grenouille_europeenne)),
        Fish(R.string.fish_grodin_perlon, R.string.fish_habitat_mer_est_alt, R.string.fish_time_all_day, R.string.fish_weather_rainbow, R.string.fish_level_6, R.string.fish_price_na, getIconResId(R.string.fish_grodin_perlon)),
        Fish(R.string.fish_hippocampe, R.string.fish_habitat_mer_baleine, R.string.fish_time_00h00_18h00, R.string.fish_weather_all, R.string.fish_level_2, R.string.fish_price_100g, getIconResId(R.string.fish_hippocampe)),
        Fish(R.string.fish_langouste_europeenne, R.string.fish_habitat_mer_calme, R.string.fish_time_18h00_6h00, R.string.fish_weather_all, R.string.fish_level_5, R.string.fish_price_230g, getIconResId(R.string.fish_langouste_europeenne)),
        Fish(R.string.fish_loche_fleurs, R.string.fish_habitat_fleuve_arbres_geants, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_loche_fleurs)),
        Fish(R.string.fish_loche_rochers, R.string.fish_habitat_lac_banlieue, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_2, R.string.fish_price_100g, getIconResId(R.string.fish_loche_rochers)),
        Fish(R.string.fish_lotte, R.string.fish_habitat_riviere_calme, R.string.fish_time_12h00_00h00, R.string.fish_weather_all, R.string.fish_level_4, R.string.fish_price_230g, getIconResId(R.string.fish_lotte)),
        Fish(R.string.fish_maquereau_atlantique, R.string.fish_habitat_mer_baleine, R.string.fish_time_12h00_6h00, R.string.fish_weather_sun_rainbow, R.string.fish_level_5, R.string.fish_price_150g, getIconResId(R.string.fish_maquereau_atlantique)),
        Fish(R.string.fish_moule, R.string.fish_habitat_lac_foret, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_na, R.string.fish_price_na, getIconResId(R.string.fish_moule)),
        Fish(R.string.fish_omble, R.string.fish_habitat_lac_banlieue, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_6, R.string.fish_price_230g, getIconResId(R.string.fish_omble)),
        Fish(R.string.fish_omble_chevalier, R.string.fish_habitat_lac_banlieue, R.string.fish_time_00h00_12h00, R.string.fish_weather_sun_rainbow, R.string.fish_level_8, R.string.fish_price_na, getIconResId(R.string.fish_omble_chevalier)),
        Fish(R.string.fish_oranda, R.string.fish_habitat_mer_calme, R.string.fish_time_18h00_6h00, R.string.fish_weather_rain_rainbow, R.string.fish_level_7, R.string.fish_price_230g, getIconResId(R.string.fish_oranda)),
        Fish(R.string.fish_pagellus_bogaraveo, R.string.fish_habitat_ocean_alt, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_75g, getIconResId(R.string.fish_pagellus_bogaraveo)),
        Fish(R.string.fish_perche_mer, R.string.fish_habitat_lac_montagne_thermale_alt, R.string.fish_time_12h00_00h00, R.string.fish_weather_all, R.string.fish_level_2, R.string.fish_price_100g, getIconResId(R.string.fish_perche_mer)),
        Fish(R.string.fish_perche_prunier, R.string.fish_habitat_riviere, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_75g, getIconResId(R.string.fish_perche_prunier)),
        Fish(R.string.fish_perche_riviere, R.string.fish_habitat_peche_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_3, R.string.fish_price_320g, getIconResId(R.string.fish_perche_riviere)),
        Fish(R.string.fish_pieuvre, R.string.fish_habitat_mer_calme, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_3, R.string.fish_price_150g, getIconResId(R.string.fish_pieuvre)),
        Fish(R.string.fish_pieuvre_naine_atlantique, R.string.fish_habitat_ancienne_mer, R.string.fish_time_18h00_6h00, R.string.fish_weather_all, R.string.fish_level_4, R.string.fish_price_230g, getIconResId(R.string.fish_pieuvre_naine_atlantique)),
        Fish(R.string.fish_plie_europeenne, R.string.fish_habitat_lac_banlieue, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_5, R.string.fish_price_na, getIconResId(R.string.fish_plie_europeenne)),
        Fish(R.string.fish_poisson_divin, R.string.fish_habitat_ancienne_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_poisson_divin)),
        Fish(R.string.fish_poisson_epineux, R.string.fish_habitat_peche_mer_alt, R.string.fish_time_18h00_6h00, R.string.fish_weather_all, R.string.fish_level_9, R.string.fish_price_850g, getIconResId(R.string.fish_poisson_epineux)),
        Fish(R.string.fish_poisson_lunaire, R.string.fish_habitat_lac_prairie, R.string.fish_time_6h00_00h00, R.string.fish_weather_all, R.string.fish_level_8, R.string.fish_price_250g, getIconResId(R.string.fish_poisson_lunaire)),
        Fish(R.string.fish_poisson_rouge, R.string.fish_habitat_mer_calme, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_105g, getIconResId(R.string.fish_poisson_rouge)),
        Fish(R.string.fish_poisson_surspendu, R.string.fish_habitat_ancienne_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_3, R.string.fish_price_100g, getIconResId(R.string.fish_poisson_surspendu)),
        Fish(R.string.fish_poisson_clown, R.string.fish_habitat_ancienne_mer, R.string.fish_time_12h00_00h00, R.string.fish_weather_all, R.string.fish_level_6, R.string.fish_price_230g, getIconResId(R.string.fish_poisson_clown)),
        Fish(R.string.fish_poisson_lune, R.string.fish_habitat_fleuve_arbres_geants, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_3, R.string.fish_price_230g, getIconResId(R.string.fish_poisson_lune)),
        Fish(R.string.fish_poisson_tigre_ventre_rouge, R.string.fish_habitat_peche_mer, R.string.fish_time_6am_6pm, R.string.fish_weather_all, R.string.fish_level_7, R.string.fish_price_535g, getIconResId(R.string.fish_poisson_tigre_ventre_rouge)),
        Fish(R.string.fish_requin_taupe_bleu, R.string.fish_habitat_peche_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_320g, getIconResId(R.string.fish_requin_taupe_bleu)),
        Fish(R.string.fish_rouget_barbet, R.string.fish_habitat_peche_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_6, R.string.fish_price_535g, getIconResId(R.string.fish_rouget_barbet)),
        Fish(R.string.fish_roussette, R.string.fish_habitat_fleuve_arbres_geants, R.string.fish_time_all_day, R.string.fish_weather_sun_rainbow, R.string.fish_level_3, R.string.fish_price_230g, getIconResId(R.string.fish_roussette)),
        Fish(R.string.fish_sandre_blanc, R.string.fish_habitat_ocean_alt, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_sandre_blanc)),
        Fish(R.string.fish_sardine_alt, R.string.fish_habitat_mer_baleine, R.string.fish_time_12h00_6h00, R.string.fish_weather_all, R.string.fish_level_3, R.string.fish_price_155g, getIconResId(R.string.fish_sardine_alt)),
        Fish(R.string.fish_saumon_atlantique_alt, R.string.fish_habitat_riviere_calme, R.string.fish_time_all_day, R.string.fish_weather_rainbow, R.string.fish_level_7, R.string.fish_price_na, getIconResId(R.string.fish_saumon_atlantique_alt)),
        Fish(R.string.fish_saumon_keta, R.string.fish_habitat_mer_baleine, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_75g, getIconResId(R.string.fish_saumon_keta)),
        Fish(R.string.fish_saurel, R.string.fish_habitat_peche_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_5, R.string.fish_price_320g, getIconResId(R.string.fish_saurel)),
        Fish(R.string.fish_seche_europeenne, R.string.fish_habitat_peche_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_5, R.string.fish_price_320g, getIconResId(R.string.fish_seche_europeenne)),
        Fish(R.string.fish_seche_europeenne, R.string.fish_habitat_lac_foret, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_seche_europeenne)),
        Fish(R.string.fish_tanche_doree, R.string.fish_habitat_leurre_sirene, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_4, R.string.fish_price_320g, getIconResId(R.string.fish_tanche_doree)),
        Fish(R.string.fish_thon_rouge, R.string.fish_habitat_lac_prairie, R.string.fish_time_18h00_6h00, R.string.fish_weather_all, R.string.fish_level_5, R.string.fish_price_230g, getIconResId(R.string.fish_thon_rouge)),
        Fish(R.string.fish_tilapia_alt, R.string.fish_habitat_peche_mer, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_4, R.string.fish_price_230g, getIconResId(R.string.fish_tilapia_alt)),
        Fish(R.string.fish_tortue, R.string.fish_habitat_riviere_calme, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_tortue)),
        Fish(R.string.fish_truite, R.string.fish_habitat_riviere_aurore, R.string.fish_time_all_day, R.string.fish_weather_all, R.string.fish_level_1, R.string.fish_price_50g, getIconResId(R.string.fish_truite))
    )
}
