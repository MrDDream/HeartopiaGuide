package com.heartopia.timer.data

import android.R as AndroidR
import com.heartopia.timer.R

data class Bug(
    val nameResId: Int,
    val locationResId: Int,
    val levelResId: Int,
    val priceResId: Int,
    val weatherResId: Int,
    val timeResId: Int,
    val iconResId: Int
)

object BugData {
    
    private fun getIconResId(nameResId: Int): Int {
        return when (nameResId) {
            R.string.bug_abeille_queue_blanche -> R.drawable.bug_abeille_a_queue_blanche
            R.string.bug_abeille_charpentiere_bleue -> R.drawable.bug_abeille_charpenti_re_bleue
            R.string.bug_abeille_charpentiere_violette -> R.drawable.bug_abeille_charpenti_re_violette
            R.string.bug_actias_neidhoederi -> R.drawable.bug_actias_neidhoederi
            R.string.bug_agrion_virginie -> R.drawable.bug_agrion_de_virginie
            R.string.bug_azure_porcelaine -> R.drawable.bug_azure_de_porcelaine
            R.string.bug_bourdon_alt -> R.drawable.bug_bourdon
            R.string.bug_capricorne_montagnes -> R.drawable.bug_capricorne_des_montagnes
            R.string.bug_capricorne_tigre -> R.drawable.bug_capricorne_tigre
            R.string.bug_cetoine_etoilee_bleue -> R.drawable.bug_cetoine_etoilee_bleue
            R.string.bug_cetoine_etoilee -> R.drawable.bug_cetoine_etoilee
            R.string.bug_charaxes_jasius -> R.drawable.bug_charaxes_jasius
            R.string.bug_chenille_porte_case -> R.drawable.bug_chenille_porte_case
            R.string.bug_cicindele_verte -> R.drawable.bug_cicindele_verte
            R.string.bug_cigalle -> R.drawable.bug_cigalle
            R.string.bug_citron_aspasia -> R.drawable.bug_citron_aspasia
            R.string.bug_coccinelle_quatre_etoiles -> R.drawable.bug_coccinelle_a_quatre_etoiles
            R.string.bug_coccinelle_sept_points -> R.drawable.bug_coccinelle__sept_points
            R.string.bug_coccinelle_treize_points -> R.drawable.bug_coccinelle_a_treize_points
            R.string.bug_coccinelle_asiatique_alt -> R.drawable.bug_coccinelle_asiatique
            R.string.bug_comete_papillon_nuit -> R.drawable.bug_comete_papillon_de_nuit
            R.string.bug_criocere_asperge -> R.drawable.bug_criocasperge
            R.string.bug_criquet_ailes_reticulees -> R.drawable.bug_criquet_a_ailes_reticulees
            R.string.bug_criquet_pattes_massives -> R.drawable.bug_criquet_aux_pattes_massives
            R.string.bug_criquet_laitier -> R.drawable.bug_criquet_laitier
            R.string.bug_fourmi_alt -> AndroidR.drawable.ic_menu_info_details
            R.string.bug_grand_agrion -> R.drawable.bug_grand_agrion
            R.string.bug_grand_paon_nuit -> R.drawable.bug_grand_paon_de_nuit
            R.string.bug_grillon -> R.drawable.bug_grillon
            R.string.bug_hypolimnas_vert -> R.drawable.bug_hypolimnas_vert
            R.string.bug_isabelle -> R.drawable.bug_isabelle
            R.string.bug_libellule_queue_blanche -> R.drawable.bug_libellule_a_queue_blanche
            R.string.bug_libellule_feu -> R.drawable.bug_libellule_de_feu
            R.string.bug_libellule_rose -> R.drawable.bug_libellule_rose
            R.string.bug_libellule_tachetee -> R.drawable.bug_libellule_tachetee
            R.string.bug_lucane_dore_spectral -> R.drawable.bug_lucane_dore_spectral
            R.string.bug_lucanus -> R.drawable.bug_lucanus
            R.string.bug_mante_tete_conique -> R.drawable.bug_mante_a_tete_conique
            R.string.bug_mante_arc_en_ciel -> R.drawable.bug_mante_arc_en_ciel
            R.string.bug_mante_orchidee -> R.drawable.bug_mante_orchidee
            R.string.bug_mante_papoue -> AndroidR.drawable.ic_menu_info_details
            R.string.bug_melitee_leopard -> R.drawable.bug_melitee_leopard
            R.string.bug_morpho_bleu_alt -> R.drawable.bug_morpho_bleu
            R.string.bug_ornithoptere_vert -> R.drawable.bug_ornithoptere_vert
            R.string.bug_paon_jour -> R.drawable.bug_paon_de_jour
            R.string.bug_papillon_aile_bleu_vert -> R.drawable.bug_papillon_a_aile_bleu_vert
            R.string.bug_papillon_anneaux_rouges -> R.drawable.bug_papillon__anneaux_rouges
            R.string.bug_papillon_col_rouge -> R.drawable.bug_papillon_a_col_rouge
            R.string.bug_papillon_blanc -> R.drawable.bug_papillon_blanc
            R.string.bug_papillon_bleu_celeste -> R.drawable.bug_papillon_bleu_celeste
            R.string.bug_papillon_reine_alexandra -> R.drawable.bug_papillon_de_la_reine_alexandra
            R.string.bug_papillon_nuit_luminescent -> R.drawable.bug_papillon_de_nuit_luminescent
            R.string.bug_papillon_soleil -> R.drawable.bug_papillon_de_soleil
            R.string.bug_papillon_dore -> R.drawable.bug_papillon_dore
            R.string.bug_papillon_sorciere_blanche -> R.drawable.bug_papillon_sorci_re_blanche
            R.string.bug_parnassien -> R.drawable.bug_parnassien
            R.string.bug_pieride_cresson -> R.drawable.bug_pi_ride_du_cresson
            R.string.bug_pioche_arc_en_ciel -> R.drawable.bug_pioche_arc_en_ciel
            R.string.bug_punaise_bleue -> R.drawable.bug_punaise_bleue
            R.string.bug_punaise_picasso -> R.drawable.bug_punaise_picasso
            R.string.bug_pyrochroa -> R.drawable.bug_pyrochroa
            R.string.bug_pyrrhocoris_apterus -> R.drawable.bug_pyrrhocoris_apterus
            R.string.bug_sauterelle_oasis -> R.drawable.bug_sauterelle_de_l_oasis
            R.string.bug_sauterelle_rose -> R.drawable.bug_sauterelle_rose
            R.string.bug_scarabee_argente_gemmes -> R.drawable.bug_scarabee_argente_au_gemmes
            R.string.bug_scarabee_atlas -> R.drawable.bug_scarabee_atlas
            R.string.bug_scarabee_dore_gemmes -> R.drawable.bug_scarabee_dore_aux_gemmes
            R.string.bug_scarabee_goliath -> R.drawable.bug_scarabee_goliath
            R.string.bug_scarabee_hercule -> R.drawable.bug_scarab_e_hercule
            R.string.bug_scarabee_sacre -> AndroidR.drawable.ic_menu_info_details
            R.string.bug_scarabee_unicorne -> R.drawable.bug_scarabee_unicorne
            R.string.bug_vanesse_violette -> R.drawable.bug_vanesse_violette
            else -> AndroidR.drawable.ic_menu_info_details
        }
    }
    
    private fun getWeatherResId(weather: String?): Int {
        return when {
            weather == null || weather.isBlank() -> R.string.bug_weather_all
            weather.contains("Pluie", ignoreCase = true) && weather.contains("Arc-en-ciel", ignoreCase = true) -> R.string.bug_weather_rain_rainbow
            weather.contains("Soleil", ignoreCase = true) && weather.contains("Arc-en-ciel", ignoreCase = true) -> R.string.bug_weather_sun_rainbow
            weather.contains("Arc-en-ciel", ignoreCase = true) -> R.string.bug_weather_rainbow
            weather.contains("Toute", ignoreCase = true) -> R.string.bug_weather_all
            else -> R.string.bug_weather_all
        }
    }
    
    private fun getLevelResId(level: String?): Int {
        return when (level?.trim()) {
            "1" -> R.string.bug_level_1
            "2" -> R.string.bug_level_2
            "3" -> R.string.bug_level_3
            "4" -> R.string.bug_level_4
            "5" -> R.string.bug_level_5
            "6" -> R.string.bug_level_6
            "7" -> R.string.bug_level_7
            "8" -> R.string.bug_level_8
            "9" -> R.string.bug_level_9
            "10" -> R.string.bug_level_10
            "?" -> R.string.bug_level_na
            null, "", " " -> R.string.bug_level_na
            else -> R.string.bug_level_na
        }
    }
    
    private fun getPriceResId(price: String?): Int {
        return when (price?.trim()) {
            "30" -> R.string.bug_price_30g
            "35" -> R.string.bug_price_35g
            "45" -> R.string.bug_price_45g
            "55" -> R.string.bug_price_55g
            "60" -> R.string.bug_price_60g
            "75" -> R.string.bug_price_75g
            "90" -> R.string.bug_price_90g
            "105" -> R.string.bug_price_105g
            "110" -> R.string.bug_price_110g
            "140" -> R.string.bug_price_140g
            "150" -> R.string.bug_price_150g
            "165" -> R.string.bug_price_165g
            "180" -> R.string.bug_price_180g
            "185" -> R.string.bug_price_185g
            "195" -> R.string.bug_price_195g
            "220" -> R.string.bug_price_220g
            "240" -> R.string.bug_price_240g
            "275" -> R.string.bug_price_275g
            "390" -> R.string.bug_price_390g
            null, "", " " -> R.string.bug_price_na
            else -> R.string.bug_price_na
        }
    }
    
    private fun getTimeResId(time: String?): Int {
        return when {
            time == null || time.isBlank() -> R.string.bug_time_all_day
            time.contains("Toute", ignoreCase = true) -> R.string.bug_time_all_day
            time.contains("6h00") && time.contains("00h00", ignoreCase = true) -> R.string.bug_time_6h00_00h00
            time.contains("6H00") && time.contains("00H00", ignoreCase = true) -> R.string.bug_time_6h00_00h00
            time.contains("6H00") && time.contains("18H00", ignoreCase = true) -> R.string.bug_time_6h00_18h00
            time.contains("00H00") && time.contains("18H00", ignoreCase = true) -> R.string.bug_time_00h00_18h00
            time.contains("12H00") && time.contains("6H00", ignoreCase = true) -> R.string.bug_time_12h00_6h00
            time.contains("12H00") && time.contains("00H00", ignoreCase = true) -> R.string.bug_time_12h00_00h00
            time.contains("18H00") && time.contains("6H00", ignoreCase = true) -> R.string.bug_time_18h00_6h00
            time.contains("18H00") && time.contains("12H00", ignoreCase = true) -> R.string.bug_time_18h00_12h00
            else -> R.string.bug_time_all_day
        }
    }
    
    private fun getLocationResId(location: String?): Int {
        return when {
            location == null || location.isBlank() -> R.string.bug_location_na
            location.contains("Forêt", ignoreCase = true) && location.contains("Tour faon", ignoreCase = true) -> R.string.bug_location_foret_tour_faon
            location.contains("Champ de fleurs", ignoreCase = true) && location.contains("Montagne de baleine", ignoreCase = true) -> R.string.bug_location_champ_fleurs_montagne_baleine
            location.contains("Bord du lac de la forêt", ignoreCase = true) -> R.string.bug_location_bord_lac_foret
            location.contains("Centre Ville", ignoreCase = true) -> R.string.bug_location_centre_ville
            location.contains("Foyer", ignoreCase = true) -> R.string.bug_location_foyer
            location.contains("Montagne de baleine", ignoreCase = true) && location.contains("champ de fleurs", ignoreCase = true) -> R.string.bug_location_montagne_baleine_champ_fleurs
            location.contains("Montagne Thermale", ignoreCase = true) && location.contains("source thermale", ignoreCase = true) -> R.string.bug_location_montagne_thermale_source_thermale
            location.contains("Montagne Thermale", ignoreCase = true) && location.contains("Lac volcanique", ignoreCase = true) -> R.string.bug_location_montagne_thermale_lac_volcanique
            location.contains("Montagne Thermale", ignoreCase = true) && location.contains("Ruines", ignoreCase = true) -> R.string.bug_location_montagne_thermale_ruines
            location.contains("Montagne Thermale", ignoreCase = true) && location.contains("Falaise rocheuse", ignoreCase = true) -> R.string.bug_location_montagne_thermale_falaise_rocheuse
            location.contains("Montagne Thermale", ignoreCase = true) -> R.string.bug_location_montagne_thermale
            location.contains("Montagne thermale", ignoreCase = true) && location.contains("Ruines", ignoreCase = true) -> R.string.bug_location_montagne_thermale_ruines_alt
            location.contains("Montagne thermale", ignoreCase = true) && location.contains("falaise rocheuse", ignoreCase = true) -> R.string.bug_location_montagne_thermale_falaise_rocheuse
            location.contains("Montagne thermale", ignoreCase = true) -> R.string.bug_location_montagne_thermale
            location.contains("Forêt", ignoreCase = true) && location.contains("Ile de la forêt", ignoreCase = true) -> R.string.bug_location_foret_ile_foret
            location.contains("Forêt", ignoreCase = true) && location.contains("Forêt des chênes spirituel", ignoreCase = true) -> R.string.bug_location_foret_chenes_spirituel
            location.contains("Forêt", ignoreCase = true) && location.contains("Forêt de chêne spirituels", ignoreCase = true) -> R.string.bug_location_foret_chene_spirituels
            location.contains("Forêt", ignoreCase = true) && location.contains("Tremplin de la forêt", ignoreCase = true) -> R.string.bug_location_foret_tremplin
            location.contains("Forêt", ignoreCase = true) && location.contains("Tremplin", ignoreCase = true) -> R.string.bug_location_foret_tremplin_alt
            location.contains("Forêt", ignoreCase = true) -> R.string.bug_location_foret
            location.contains("Bord de l'eau", ignoreCase = true) -> R.string.bug_location_bord_eau
            location.contains("Bord de rivière", ignoreCase = true) -> R.string.bug_location_bord_riviere
            location.contains("Bord du lac de banlieue", ignoreCase = true) -> R.string.bug_location_bord_lac_banlieue
            location.contains("Lac de montagne thermale", ignoreCase = true) -> R.string.bug_location_lac_montagne_thermale
            location.contains("Plage violette", ignoreCase = true) && location.contains("Champ de fleur", ignoreCase = true) -> R.string.bug_location_plage_violette_champ_fleur
            location.contains("Champ de fleurs", ignoreCase = true) && location.contains("Des moulins à vent", ignoreCase = true) -> R.string.bug_location_champ_fleurs_moulins_vent
            location.contains("Champ de fleurs", ignoreCase = true) && location.contains("plage violette", ignoreCase = true) -> R.string.bug_location_champ_fleurs_plage_violette
            location.contains("Champ de fleurs", ignoreCase = true) -> R.string.bug_location_champ_fleurs
            location.contains("Evènement Insecte", ignoreCase = true) -> R.string.bug_location_evenement_insecte
            location.contains("Evènement Insectes", ignoreCase = true) -> R.string.bug_location_evenement_insectes
            location.contains("Spécialité attracteur d'abeilles gonflables", ignoreCase = true) -> R.string.bug_location_specialite_attracteur_abeilles
            location.contains("Village de Pêcheur", ignoreCase = true) && location.contains("Place du village de pêcheurs", ignoreCase = true) -> R.string.bug_location_village_pecheur_place
            location.contains("Village de Pêcheur", ignoreCase = true) && location.contains("Phare", ignoreCase = true) -> R.string.bug_location_village_pecheur_phare
            location.contains("Village de Pêcheur", ignoreCase = true) -> R.string.bug_location_village_pecheur
            location.contains("Village de pêcheur", ignoreCase = true) && location.contains("Quai orientale du village", ignoreCase = true) -> R.string.bug_location_village_pecheur_quai_orientale
            location.contains("Village de pêcheurs", ignoreCase = true) -> R.string.bug_location_village_pecheurs
            location.contains("Banlieue", ignoreCase = true) -> R.string.bug_location_banlieue
            else -> R.string.bug_location_na
        }
    }
    
    fun getAllBugs(): List<Bug> = listOf(
        Bug(R.string.bug_abeille_queue_blanche, R.string.bug_location_foret_tour_faon, R.string.bug_level_3, R.string.bug_price_165g, R.string.bug_weather_all, R.string.bug_time_6h00_00h00, getIconResId(R.string.bug_abeille_queue_blanche)),
        Bug(R.string.bug_abeille_charpentiere_bleue, R.string.bug_location_na, R.string.bug_level_9, R.string.bug_price_na, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_abeille_charpentiere_bleue)),
        Bug(R.string.bug_abeille_charpentiere_violette, R.string.bug_location_champ_fleurs_montagne_baleine, R.string.bug_level_5, R.string.bug_price_165g, R.string.bug_weather_sun_rainbow, R.string.bug_time_6h00_00h00, getIconResId(R.string.bug_abeille_charpentiere_violette)),
        Bug(R.string.bug_actias_neidhoederi, R.string.bug_location_banlieue, R.string.bug_level_3, R.string.bug_price_105g, R.string.bug_weather_sun_rainbow, R.string.bug_time_6h00_00h00, getIconResId(R.string.bug_actias_neidhoederi)),
        Bug(R.string.bug_agrion_virginie, R.string.bug_location_bord_lac_foret, R.string.bug_level_6, R.string.bug_price_110g, R.string.bug_weather_rain_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_agrion_virginie)),
        Bug(R.string.bug_azure_porcelaine, R.string.bug_location_centre_ville, R.string.bug_level_2, R.string.bug_price_105g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_azure_porcelaine)),
        Bug(R.string.bug_bourdon_alt, R.string.bug_location_champ_fleurs, R.string.bug_level_2, R.string.bug_price_110g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_bourdon_alt)),
        Bug(R.string.bug_capricorne_montagnes, R.string.bug_location_foret_ile_foret, R.string.bug_level_5, R.string.bug_price_165g, R.string.bug_weather_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_capricorne_montagnes)),
        Bug(R.string.bug_capricorne_tigre, R.string.bug_location_foret, R.string.bug_level_2, R.string.bug_price_110g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_capricorne_tigre)),
        Bug(R.string.bug_cetoine_etoilee_bleue, R.string.bug_location_foyer, R.string.bug_level_2, R.string.bug_price_165g, R.string.bug_weather_rain_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_cetoine_etoilee_bleue)),
        Bug(R.string.bug_cetoine_etoilee, R.string.bug_location_montagne_baleine_champ_fleurs, R.string.bug_level_7, R.string.bug_price_275g, R.string.bug_weather_rain_rainbow, R.string.bug_time_6h00_18h00, getIconResId(R.string.bug_cetoine_etoilee)),
        Bug(R.string.bug_charaxes_jasius, R.string.bug_location_montagne_thermale_source_thermale, R.string.bug_level_5, R.string.bug_price_90g, R.string.bug_weather_sun_rainbow, R.string.bug_time_00h00_18h00, getIconResId(R.string.bug_charaxes_jasius)),
        Bug(R.string.bug_cicindele_verte, R.string.bug_location_montagne_thermale, R.string.bug_level_2, R.string.bug_price_110g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_cicindele_verte)),
        Bug(R.string.bug_cigalle, R.string.bug_location_foret_chenes_spirituel, R.string.bug_level_4, R.string.bug_price_220g, R.string.bug_weather_all, R.string.bug_time_12h00_6h00, getIconResId(R.string.bug_cigalle)),
        Bug(R.string.bug_citron_aspasia, R.string.bug_location_banlieue, R.string.bug_level_1, R.string.bug_price_30g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_citron_aspasia)),
        Bug(R.string.bug_coccinelle_quatre_etoiles, R.string.bug_location_montagne_thermale_lac_volcanique, R.string.bug_level_4, R.string.bug_price_165g, R.string.bug_weather_rain_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_coccinelle_quatre_etoiles)),
        Bug(R.string.bug_coccinelle_sept_points, R.string.bug_location_banlieue, R.string.bug_level_2, R.string.bug_price_110g, R.string.bug_weather_rain_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_coccinelle_sept_points)),
        Bug(R.string.bug_coccinelle_asiatique_alt, R.string.bug_location_foret_tour_faon, R.string.bug_level_5, R.string.bug_price_165g, R.string.bug_weather_rain_rainbow, R.string.bug_time_12h00_6h00, getIconResId(R.string.bug_coccinelle_asiatique_alt)),
        Bug(R.string.bug_comete_papillon_nuit, R.string.bug_location_banlieue, R.string.bug_level_8, R.string.bug_price_240g, R.string.bug_weather_sun_rainbow, R.string.bug_time_18h00_6h00, getIconResId(R.string.bug_comete_papillon_nuit)),
        Bug(R.string.bug_criocere_asperge, R.string.bug_location_na, R.string.bug_level_1, R.string.bug_price_55g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_criocere_asperge)),
        Bug(R.string.bug_criquet_ailes_reticulees, R.string.bug_location_banlieue, R.string.bug_level_4, R.string.bug_price_140g, R.string.bug_weather_sun_rainbow, R.string.bug_time_12h00_6h00, getIconResId(R.string.bug_criquet_ailes_reticulees)),
        Bug(R.string.bug_criquet_pattes_massives, R.string.bug_location_village_pecheur, R.string.bug_level_2, R.string.bug_price_90g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_criquet_pattes_massives)),
        Bug(R.string.bug_fourmi_alt, R.string.bug_location_village_pecheur_place, R.string.bug_level_3, R.string.bug_price_220g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_fourmi_alt)),
        Bug(R.string.bug_grand_agrion, R.string.bug_location_bord_eau, R.string.bug_level_1, R.string.bug_price_35g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_grand_agrion)),
        Bug(R.string.bug_grand_paon_nuit, R.string.bug_location_evenement_insecte, R.string.bug_level_7, R.string.bug_price_150g, R.string.bug_weather_rain_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_grand_paon_nuit)),
        Bug(R.string.bug_grillon, R.string.bug_location_plage_violette_champ_fleur, R.string.bug_level_3, R.string.bug_price_180g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_grillon)),
        Bug(R.string.bug_isabelle, R.string.bug_location_foret_chene_spirituels, R.string.bug_level_8, R.string.bug_price_150g, R.string.bug_weather_sun_rainbow, R.string.bug_time_12h00_00h00, getIconResId(R.string.bug_isabelle)),
        Bug(R.string.bug_libellule_queue_blanche, R.string.bug_location_bord_riviere, R.string.bug_level_4, R.string.bug_price_75g, R.string.bug_weather_rain_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_libellule_queue_blanche)),
        Bug(R.string.bug_libellule_rose, R.string.bug_location_lac_montagne_thermale, R.string.bug_level_7, R.string.bug_price_na, R.string.bug_weather_rainbow, R.string.bug_time_12h00_6h00, getIconResId(R.string.bug_libellule_rose)),
        Bug(R.string.bug_libellule_tachetee, R.string.bug_location_bord_lac_banlieue, R.string.bug_level_2, R.string.bug_price_75g, R.string.bug_weather_rain_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_libellule_tachetee)),
        Bug(R.string.bug_lucanus, R.string.bug_location_foret_tremplin, R.string.bug_level_6, R.string.bug_price_275g, R.string.bug_weather_rain_rainbow, R.string.bug_time_6h00_00h00, getIconResId(R.string.bug_lucanus)),
        Bug(R.string.bug_mante_arc_en_ciel, R.string.bug_location_montagne_thermale_falaise_rocheuse, R.string.bug_level_4, R.string.bug_price_195g, R.string.bug_weather_sun_rainbow, R.string.bug_time_12h00_6h00, getIconResId(R.string.bug_mante_arc_en_ciel)),
        Bug(R.string.bug_mante_papoue, R.string.bug_location_montagne_thermale_ruines, R.string.bug_level_5, R.string.bug_price_390g, R.string.bug_weather_sun_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_mante_papoue)),
        Bug(R.string.bug_melitee_leopard, R.string.bug_location_village_pecheurs, R.string.bug_level_4, R.string.bug_price_90g, R.string.bug_weather_rain_rainbow, R.string.bug_time_18h00_12h00, getIconResId(R.string.bug_melitee_leopard)),
        Bug(R.string.bug_morpho_bleu_alt, R.string.bug_location_foret_chene_spirituels, R.string.bug_level_7, R.string.bug_price_150g, R.string.bug_weather_sun_rainbow, R.string.bug_time_6h00_00h00, getIconResId(R.string.bug_morpho_bleu_alt)),
        Bug(R.string.bug_ornithoptere_vert, R.string.bug_location_banlieue, R.string.bug_level_6, R.string.bug_price_150g, R.string.bug_weather_all, R.string.bug_time_18h00_6h00, getIconResId(R.string.bug_ornithoptere_vert)),
        Bug(R.string.bug_paon_jour, R.string.bug_location_champ_fleurs_moulins_vent, R.string.bug_level_na, R.string.bug_price_90g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_paon_jour)),
        Bug(R.string.bug_papillon_anneaux_rouges, R.string.bug_location_evenement_insectes, R.string.bug_level_na, R.string.bug_price_30g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_papillon_anneaux_rouges)),
        Bug(R.string.bug_papillon_col_rouge, R.string.bug_location_foyer, R.string.bug_level_3, R.string.bug_price_90g, R.string.bug_weather_all, R.string.bug_time_18h00_12h00, getIconResId(R.string.bug_papillon_col_rouge)),
        Bug(R.string.bug_papillon_blanc, R.string.bug_location_village_pecheurs, R.string.bug_level_1, R.string.bug_price_30g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_papillon_blanc)),
        Bug(R.string.bug_papillon_nuit_luminescent, R.string.bug_location_specialite_attracteur_abeilles, R.string.bug_level_na, R.string.bug_price_90g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_papillon_nuit_luminescent)),
        Bug(R.string.bug_papillon_dore, R.string.bug_location_foret, R.string.bug_level_na, R.string.bug_price_30g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_papillon_dore)),
        Bug(R.string.bug_papillon_sorciere_blanche, R.string.bug_location_evenement_insecte, R.string.bug_level_5, R.string.bug_price_90g, R.string.bug_weather_sun_rainbow, R.string.bug_time_all_day, getIconResId(R.string.bug_papillon_sorciere_blanche)),
        Bug(R.string.bug_parnassien, R.string.bug_location_evenement_insecte, R.string.bug_level_na, R.string.bug_price_30g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_parnassien)),
        Bug(R.string.bug_pieride_cresson, R.string.bug_location_centre_ville, R.string.bug_level_na, R.string.bug_price_60g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_pieride_cresson)),
        Bug(R.string.bug_punaise_bleue, R.string.bug_location_village_pecheur_phare, R.string.bug_level_6, R.string.bug_price_165g, R.string.bug_weather_all, R.string.bug_time_6h00_18h00, getIconResId(R.string.bug_punaise_bleue)),
        Bug(R.string.bug_punaise_picasso, R.string.bug_location_champ_fleurs_plage_violette, R.string.bug_level_8, R.string.bug_price_185g, R.string.bug_weather_sun_rainbow, R.string.bug_time_18h00_6h00, getIconResId(R.string.bug_punaise_picasso)),
        Bug(R.string.bug_pyrochroa, R.string.bug_location_montagne_thermale_source_thermale_alt, R.string.bug_level_3, R.string.bug_price_110g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_pyrochroa)),
        Bug(R.string.bug_pyrrhocoris_apterus, R.string.bug_location_foyer, R.string.bug_level_1, R.string.bug_price_35g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_pyrrhocoris_apterus)),
        Bug(R.string.bug_sauterelle_oasis, R.string.bug_location_montagne_thermale, R.string.bug_level_1, R.string.bug_price_45g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_sauterelle_oasis)),
        Bug(R.string.bug_sauterelle_rose, R.string.bug_location_evenement_insecte, R.string.bug_level_3, R.string.bug_price_90g, R.string.bug_weather_all, R.string.bug_time_all_day, getIconResId(R.string.bug_sauterelle_rose)),
        Bug(R.string.bug_scarabee_argente_gemmes, R.string.bug_location_montagne_thermale_falaise_rocheuse, R.string.bug_level_6, R.string.bug_price_165g, R.string.bug_weather_sun_rainbow, R.string.bug_time_00h00_18h00, getIconResId(R.string.bug_scarabee_argente_gemmes)),
        Bug(R.string.bug_scarabee_dore_gemmes, R.string.bug_location_foret_tremplin_alt, R.string.bug_level_7, R.string.bug_price_275g, R.string.bug_weather_sun_rainbow, R.string.bug_time_12h00_00h00, getIconResId(R.string.bug_scarabee_dore_gemmes)),
        Bug(R.string.bug_scarabee_sacre, R.string.bug_location_montagne_thermale_ruines_alt, R.string.bug_level_7, R.string.bug_price_275g, R.string.bug_weather_all, R.string.bug_time_00h00_18h00, getIconResId(R.string.bug_scarabee_sacre)),
        Bug(R.string.bug_scarabee_unicorne, R.string.bug_location_village_pecheur_quai_orientale, R.string.bug_level_8, R.string.bug_price_275g, R.string.bug_weather_sun_rainbow, R.string.bug_time_18h00_6h00, getIconResId(R.string.bug_scarabee_unicorne)),
        Bug(R.string.bug_vanesse_violette, R.string.bug_location_champ_fleurs_montagne_baleine, R.string.bug_level_na, R.string.bug_price_90g, R.string.bug_weather_sun_rainbow, R.string.bug_time_12h00_6h00, getIconResId(R.string.bug_vanesse_violette))
    )
}
