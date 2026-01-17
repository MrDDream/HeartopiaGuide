package com.heartopia.timer.data

import android.R as AndroidR
import com.heartopia.timer.R

data class Bird(
    val nameResId: Int,
    val locationResId: Int,
    val levelResId: Int,
    val priceResId: Int,
    val weatherResId: Int,
    val timeResId: Int,
    val iconResId: Int
)

object BirdData {
    
    private fun getIconResId(nameResId: Int): Int {
        return when (nameResId) {
            R.string.bird_aegithalos_caudatus -> R.drawable.bird_aegithalos_caudatus
            R.string.bird_ara_bleu_et_jaune -> R.drawable.bird_ara_bleu_et_jaune
            R.string.bird_ara_rouge_et_vert -> R.drawable.bird_ara_rouge_et_vert
            R.string.bird_bergeronette_grise -> R.drawable.bird_bergeronette_grise
            R.string.bird_bouvreuil_ventre_rouge -> R.drawable.bird_bouvreuil_a_ventre_rouge
            R.string.bird_canard_colvert -> R.drawable.bird_canard_colvert
            R.string.bird_canard_siffleur -> R.drawable.bird_canard_siffleur
            R.string.bird_colombar_cou_rose -> R.drawable.bird_colombar_a_cou_rose
            R.string.bird_cormoran_vert_europeen -> R.drawable.bird_cormoran_vert_europeen
            R.string.bird_diamant_mandarin -> R.drawable.bird_diamant_mandarin
            R.string.bird_eider_royal -> R.drawable.bird_eider_royal
            R.string.bird_faisan_ventre_blanc -> R.drawable.bird_faisan_a_ventre_blanc
            R.string.bird_fringilla_coelebs -> R.drawable.bird_fringilla_coelebs
            R.string.bird_goeland_audouin -> R.drawable.bird_goeland_audouin
            R.string.bird_golden_oriole -> R.drawable.bird_golden_oriole
            R.string.bird_grand_flamant -> R.drawable.bird_grand_flamant
            R.string.bird_grand_pic -> R.drawable.bird_grand_pic
            R.string.bird_harle_piette -> R.drawable.bird_harle_piette
            R.string.bird_jardinier_tete_jaune -> R.drawable.bird_jardinier_a_tete_jaune
            R.string.bird_mesange_menton_argent -> R.drawable.bird_mesange_a_menton_argent
            R.string.bird_moucherolle_ventre_jaune -> R.drawable.bird_moucherolle_a_ventre_jaune
            R.string.bird_mouette -> R.drawable.bird_mouette
            R.string.bird_panure_moustaches -> R.drawable.bird_panure_a_moustaches
            R.string.bird_paon_bleu -> R.drawable.bird_paon_bleu
            R.string.bird_pie_greche_tete_rousse -> R.drawable.bird_pie_greche_tete_rousse
            R.string.bird_pigeon_yeux_jaunes -> R.drawable.bird_pigeon_a_yeux_jaunes
            R.string.bird_pigeon_fruit_or -> R.drawable.bird_pigeon_aux_fruit_dor
            R.string.bird_pigeon_colombin -> R.drawable.bird_pigeon_colombin
            R.string.bird_pigeon_wonga -> R.drawable.bird_pigeon_de_wonga
            R.string.bird_pinson -> R.drawable.bird_pinson
            R.string.bird_rouge_gorge -> R.drawable.bird_rouge_gorge
            R.string.bird_sittelle -> R.drawable.bird_sittelle
            R.string.bird_sterne_blanche -> R.drawable.bird_sterne_blanche
            R.string.bird_tadorne_casarca -> R.drawable.bird_tadorne_casarca
            R.string.bird_tourterelle_grise -> R.drawable.bird_tourterelle_grise
            R.string.bird_tourterelle_tigrine -> R.drawable.bird_tourterelle_tigrine
            R.string.bird_troglodyte -> R.drawable.bird_troglodyte
            else -> AndroidR.drawable.ic_menu_info_details
        }
    }
    
    private fun getWeatherResId(weather: String): Int {
        return when {
            weather.contains("Pluie", ignoreCase = true) && weather.contains("arc-en-ciel", ignoreCase = true) -> R.string.bird_weather_rain_rainbow
            weather.contains("Soleil", ignoreCase = true) && weather.contains("arc-en-ciel", ignoreCase = true) -> R.string.bird_weather_sun_rainbow
            weather.contains("Arc-en-ciel", ignoreCase = true) -> R.string.bird_weather_rainbow
            weather.contains("Toute", ignoreCase = true) -> R.string.bird_weather_all
            else -> R.string.bird_weather_all
        }
    }
    
    private fun getLevelResId(level: String?): Int {
        return when (level?.trim()) {
            "1" -> R.string.bird_level_1
            "2" -> R.string.bird_level_2
            "3" -> R.string.bird_level_3
            "4" -> R.string.bird_level_4
            "5" -> R.string.bird_level_5
            "6" -> R.string.bird_level_6
            "7" -> R.string.bird_level_7
            "8" -> R.string.bird_level_8
            "9" -> R.string.bird_level_9
            "10" -> R.string.bird_level_10
            "8 / 9" -> R.string.bird_level_8
            "?" -> R.string.bird_level_na
            null, "", " " -> R.string.bird_level_na
            else -> R.string.bird_level_na
        }
    }
    
    private fun getPriceResId(price: String?): Int {
        return when (price?.trim()) {
            "2" -> R.string.bird_price_2g
            "7" -> R.string.bird_price_7g
            "10" -> R.string.bird_price_10g
            "12" -> R.string.bird_price_12g
            "15" -> R.string.bird_price_15g
            "17" -> R.string.bird_price_17g
            "22" -> R.string.bird_price_22g
            "27" -> R.string.bird_price_27g
            "30" -> R.string.bird_price_30g
            "32" -> R.string.bird_price_32g
            "37" -> R.string.bird_price_37g
            "45" -> R.string.bird_price_45g
            "47" -> R.string.bird_price_47g
            "49" -> R.string.bird_price_49g
            "65" -> R.string.bird_price_65g
            "70" -> R.string.bird_price_70g
            null, "", " " -> R.string.bird_price_na
            else -> R.string.bird_price_na
        }
    }
    
    fun getAllBirds(): List<Bird> = listOf(
        Bird(R.string.bird_aegithalos_caudatus, R.string.bird_location_sommet_tete_blanc, R.string.bird_level_1, R.string.bird_price_2g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_aegithalos_caudatus)),
        Bird(R.string.bird_ara_bleu_et_jaune, R.string.bird_location_evenement_oiseau, R.string.bird_level_1, R.string.bird_price_7g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_ara_bleu_et_jaune)),
        Bird(R.string.bird_ara_indigo, R.string.bird_location_evenement_oiseau, R.string.bird_level_7, R.string.bird_price_22g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_ara_rouge_et_vert, R.string.bird_location_evenement_oiseau, R.string.bird_level_3, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_ara_rouge_et_vert)),
        Bird(R.string.bird_bergeronette_grise, R.string.bird_location_champ_fleurs_plage_violette, R.string.bird_level_4, R.string.bird_price_15g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_bergeronette_grise)),
        Bird(R.string.bird_bouvreuil, R.string.bird_location_na, R.string.bird_level_10, R.string.bird_price_na, R.string.bird_weather_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_bouvreuil_ventre_rouge, R.string.bird_location_banlieue, R.string.bird_level_1, R.string.bird_price_7g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_bouvreuil_ventre_rouge)),
        Bird(R.string.bird_calliste_septicolore, R.string.bird_location_na, R.string.bird_level_9, R.string.bird_price_na, R.string.bird_weather_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_canard_tete_blanche, R.string.bird_location_lac_montagne_thermale, R.string.bird_level_9, R.string.bird_price_45g, R.string.bird_weather_rain_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_canard_colvert, R.string.bird_location_lac, R.string.bird_level_1, R.string.bird_price_12g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_canard_colvert)),
        Bird(R.string.bird_canard_siffleur, R.string.bird_location_riviere, R.string.bird_level_2, R.string.bird_price_17g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_canard_siffleur)),
        Bird(R.string.bird_cardinal_couronne_grise, R.string.bird_location_village_pecheurs_place_village, R.string.bird_level_4, R.string.bird_price_15g, R.string.bird_weather_sun_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_chouette_lunette, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_chouette_neiges, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_cigogne_noire, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_colombar_cou_rose, R.string.bird_location_champ_fleurs, R.string.bird_level_2, R.string.bird_price_32g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_colombar_cou_rose)),
        Bird(R.string.bird_colombe_tete_bleue, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_colombe_jambu, R.string.bird_location_banlieue, R.string.bird_level_7, R.string.bird_price_27g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_cormoran_face_rouge, R.string.bird_location_ancienne_mer_mer_baleine, R.string.bird_level_6, R.string.bird_price_49g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_cormoran_imperial, R.string.bird_location_mer_est_mer_calme, R.string.bird_level_9, R.string.bird_price_45g, R.string.bird_weather_rain_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_cormoran_vert_europeen, R.string.bird_location_ocean, R.string.bird_level_3, R.string.bird_price_17g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_cormoran_vert_europeen)),
        Bird(R.string.bird_cygne_grand, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_cygne_noir, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_diamant_queue_feu, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_diamant_mandarin, R.string.bird_location_village_pecheurs_phare, R.string.bird_level_3, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_diamant_mandarin)),
        Bird(R.string.bird_eider_royal, R.string.bird_location_riviere, R.string.bird_level_3, R.string.bird_price_17g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_eider_royal)),
        Bird(R.string.bird_etourneau_dos_violet, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_faisan_ventre_blanc, R.string.bird_location_montagne_thermale_ruines, R.string.bird_level_4, R.string.bird_price_17g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_faisan_ventre_blanc)),
        Bird(R.string.bird_faucon_pattes_rouges, R.string.bird_location_montagne_thermale_ruines, R.string.bird_level_10, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_faucon_pelerin, R.string.bird_location_montagne_thermale_source_thermale, R.string.bird_level_8, R.string.bird_price_65g, R.string.bird_weather_rain_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_faucon_rouge, R.string.bird_location_foret_tour_faon_cime_arbres, R.string.bird_level_7, R.string.bird_price_47g, R.string.bird_weather_sun_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_flamant_rose_americain, R.string.bird_location_na, R.string.bird_level_9, R.string.bird_price_na, R.string.bird_weather_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_fou_pieds_bleus, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_fringilla_coelebs, R.string.bird_location_champ_fleurs, R.string.bird_level_1, R.string.bird_price_7g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_fringilla_coelebs)),
        Bird(R.string.bird_gobemouche_vert_de_gris, R.string.bird_location_na, R.string.bird_level_10, R.string.bird_price_na, R.string.bird_weather_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_goeland_audouin, R.string.bird_location_bord_mer_baleine, R.string.bird_level_3, R.string.bird_price_17g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_goeland_audouin)),
        Bird(R.string.bird_golden_oriole, R.string.bird_location_autour_foyers, R.string.bird_level_3, R.string.bird_price_15g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_golden_oriole)),
        Bird(R.string.bird_grand_ara_vert, R.string.bird_location_evenement_oiseau, R.string.bird_level_5, R.string.bird_price_15g, R.string.bird_weather_sun_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_grand_flamant, R.string.bird_location_bord_eau, R.string.bird_level_1, R.string.bird_price_15g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_grand_flamant)),
        Bird(R.string.bird_grand_pic, R.string.bird_location_montagne_thermale, R.string.bird_level_1, R.string.bird_price_7g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_grand_pic)),
        Bird(R.string.bird_grand_duc_europe, R.string.bird_location_na, R.string.bird_level_10, R.string.bird_price_na, R.string.bird_weather_rain_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_gros_bec, R.string.bird_location_montagne_thermale_lac_volcanique, R.string.bird_level_6, R.string.bird_price_15g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_guepier_europe, R.string.bird_location_bord_lac_montagne_thermale, R.string.bird_level_5, R.string.bird_price_22g, R.string.bird_weather_rain_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_harle_piette, R.string.bird_location_lac_foret, R.string.bird_level_4, R.string.bird_price_22g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_harle_piette)),
        Bird(R.string.bird_hibou_moyen_duc, R.string.bird_location_montagne_thermale_falaise_rocheuse, R.string.bird_level_6, R.string.bird_price_47g, R.string.bird_weather_sun_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_jardinier_tete_jaune, R.string.bird_location_foret_ile_chenes_spirituels, R.string.bird_level_4, R.string.bird_price_15g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_jardinier_tete_jaune)),
        Bird(R.string.bird_linotte_dos_blanc, R.string.bird_location_foret_foret_chenes_spirituel, R.string.bird_level_8, R.string.bird_price_22g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_macareux_moine, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_merle_bleu_est, R.string.bird_location_banlieue, R.string.bird_level_8, R.string.bird_price_30g, R.string.bird_weather_rain_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_mesange_menton_argent, R.string.bird_location_foret_tremplin, R.string.bird_level_3, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_mesange_menton_argent)),
        Bird(R.string.bird_mesange_bleue, R.string.bird_location_champ_fleurs_moulins_vent, R.string.bird_level_7, R.string.bird_price_22g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_moucherolle_ventre_jaune, R.string.bird_location_village_pecheurs_quai, R.string.bird_level_5, R.string.bird_price_15g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_moucherolle_ventre_jaune)),
        Bird(R.string.bird_mouette, R.string.bird_location_bord_mer, R.string.bird_level_2, R.string.bird_price_17g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_mouette)),
        Bird(R.string.bird_panure_moustaches, R.string.bird_location_montagne_thermale, R.string.bird_level_2, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_panure_moustaches)),
        Bird(R.string.bird_paon_blanc, R.string.bird_location_evenement_oiseau, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_paon_bleu, R.string.bird_location_evenement_oiseau, R.string.bird_level_1, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_paon_bleu)),
        Bird(R.string.bird_paon_noir, R.string.bird_location_evenement_oiseau, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_paon_vert, R.string.bird_location_evenement_oiseau, R.string.bird_level_8, R.string.bird_price_37g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_petit_flamant, R.string.bird_location_bord_lac_foret, R.string.bird_level_5, R.string.bird_price_27g, R.string.bird_weather_rain_rainbow, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_pie_greche_tete_rousse, R.string.bird_location_banlieue, R.string.bird_level_2, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_pie_greche_tete_rousse)),
        Bird(R.string.bird_pigeon_yeux_jaunes, R.string.bird_location_montagne_thermale_source_thermale, R.string.bird_level_5, R.string.bird_price_27g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_pigeon_yeux_jaunes)),
        Bird(R.string.bird_pigeon_fruit_or, R.string.bird_location_champs_fleurs_moulins_vent, R.string.bird_level_4, R.string.bird_price_17g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_pigeon_fruit_or)),
        Bird(R.string.bird_pigeon_colombin, R.string.bird_location_centre_ville, R.string.bird_level_1, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_pigeon_colombin)),
        Bird(R.string.bird_pigeon_wonga, R.string.bird_location_foret, R.string.bird_level_2, R.string.bird_price_15g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_pigeon_wonga)),
        Bird(R.string.bird_pigeon_rose, R.string.bird_location_champ_fleurs_montagne_baleine, R.string.bird_level_8, R.string.bird_price_27g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_pinson, R.string.bird_location_foret_ile_foret, R.string.bird_level_4, R.string.bird_price_15g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_pinson)),
        Bird(R.string.bird_rouge_gorge, R.string.bird_location_centre_ville, R.string.bird_level_1, R.string.bird_price_7g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_rouge_gorge)),
        Bird(R.string.bird_sarcelle_elegante, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_sittelle, R.string.bird_location_village_pecheurs, R.string.bird_level_2, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_sittelle)),
        Bird(R.string.bird_sterne, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_sterne_blanche, R.string.bird_location_bord_ancienne_mer, R.string.bird_level_5, R.string.bird_price_22g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_sterne_blanche)),
        Bird(R.string.bird_sterne_inca, R.string.bird_location_na, R.string.bird_level_na, R.string.bird_price_na, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_tadorne_casarca, R.string.bird_location_lac_banlieue, R.string.bird_level_3, R.string.bird_price_70g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_tadorne_casarca)),
        Bird(R.string.bird_tourterelle_tete_grise, R.string.bird_location_village_pecheurs_quai_oriental, R.string.bird_level_6, R.string.bird_price_27g, R.string.bird_weather_all, R.string.bird_time_all, android.R.drawable.ic_menu_info_details),
        Bird(R.string.bird_tourterelle_grise, R.string.bird_location_autour_foyers, R.string.bird_level_1, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_tourterelle_grise)),
        Bird(R.string.bird_tourterelle_tigrine, R.string.bird_location_village_pecheurs, R.string.bird_level_1, R.string.bird_price_10g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_tourterelle_tigrine)),
        Bird(R.string.bird_troglodyte, R.string.bird_location_foret, R.string.bird_level_1, R.string.bird_price_7g, R.string.bird_weather_all, R.string.bird_time_all, getIconResId(R.string.bird_troglodyte))
    )
}
