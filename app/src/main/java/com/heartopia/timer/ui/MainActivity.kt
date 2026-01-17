package com.heartopia.timer.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.heartopia.timer.R
import com.heartopia.timer.data.SettingsManager
import com.heartopia.timer.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun attachBaseContext(newBase: Context) {
        val settingsManager = SettingsManager(newBase)
        val language = settingsManager.getLanguage()
        val targetLocale = when (language) {
            SettingsManager.Language.FRENCH -> Locale.forLanguageTag("fr")
            SettingsManager.Language.ENGLISH -> Locale.forLanguageTag("en")
        }
        
        // Vérifier la locale actuelle
        val currentLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newBase.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            newBase.resources.configuration.locale
        }
        
        // Ne changer la locale que si elle est différente
        if (currentLocale.language != targetLocale.language) {
            val config = Configuration(newBase.resources.configuration)
            config.setLocale(targetLocale)
            
            val context = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                newBase.createConfigurationContext(config)
            } else {
                @Suppress("DEPRECATION")
                newBase.resources.updateConfiguration(config, newBase.resources.displayMetrics)
                newBase
            }
            
            super.attachBaseContext(context)
        } else {
            // La locale est déjà correcte, pas besoin de changer
            super.attachBaseContext(newBase)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val settingsManager = SettingsManager(this)
        applyThemeIfNeeded(settingsManager)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSystemBars()
        setupNavigation()
        setupBackButton()
        requestNotificationPermission()
    }
    
    private fun setupBackButton() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
                val navController = navHostFragment?.navController
                
                // Vérifier si le clavier est ouvert en vérifiant si une vue a le focus
                val currentFocus = currentFocus
                val isKeyboardOpen = currentFocus != null && currentFocus.hasFocus()
                
                // Si le clavier est ouvert, fermer le clavier et ne pas ouvrir le drawer
                if (isKeyboardOpen) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                    currentFocus.clearFocus()
                    return
                }
                
                // Si on est sur une destination de niveau supérieur, ouvrir le drawer
                if (navController != null) {
                    val currentDestination = navController.currentDestination
                    if (currentDestination != null && appBarConfiguration.topLevelDestinations.contains(currentDestination.id)) {
                        if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                            binding.drawerLayout.closeDrawer(binding.navigationView)
                        } else {
                            binding.drawerLayout.openDrawer(binding.navigationView)
                        }
                        return
                    }
                }
                
                // Sinon, comportement par défaut (navigation back)
                if (navController != null && !navController.navigateUp()) {
                    finish()
                }
            }
        })
    }
    
    fun applyLanguage() {
        // La langue est déjà appliquée via attachBaseContext, on recrée juste l'activité
        recreate()
    }
    
    private fun applyThemeIfNeeded(settingsManager: SettingsManager) {
        val themeMode = settingsManager.getThemeMode()
        val targetNightMode = when (themeMode) {
            SettingsManager.ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            SettingsManager.ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        }
        
        // Vérifier le mode nuit actuel
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        
        // Ne changer que si différent
        if (currentNightMode != targetNightMode) {
            AppCompatDelegate.setDefaultNightMode(targetNightMode)
        }
    }
    
    fun applyTheme() {
        val settingsManager = SettingsManager(this)
        val themeMode = settingsManager.getThemeMode()
        val targetNightMode = when (themeMode) {
            SettingsManager.ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            SettingsManager.ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        }
        
        // AppCompatDelegate.setDefaultNightMode() déclenche automatiquement recreate()
        // Pas besoin d'appeler recreate() manuellement
        AppCompatDelegate.setDefaultNightMode(targetNightMode)
    }

    private fun setupSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            val insetsController = WindowInsetsControllerCompat(window, binding.root)
            insetsController.isAppearanceLightStatusBars = false
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        // Configuration de la toolbar et du drawer
        setSupportActionBar(binding.toolbar)
        
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.mapFragment, R.id.timerFragment, R.id.recipesFragment, R.id.codesFragment, R.id.bugsFragment, R.id.fishFragment, R.id.birdsFragment, R.id.settingsFragment),
            binding.drawerLayout
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
        
        // Observer les changements de destination pour afficher/masquer le bouton de recherche
        navController.addOnDestinationChangedListener { _, destination, _ ->
            invalidateOptionsMenu() // Rafraîchir le menu pour afficher/masquer le bouton de recherche
        }
    }
    
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    
    override fun onPrepareOptionsMenu(menu: android.view.Menu?): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
        val currentDestination = navHostFragment?.navController?.currentDestination
        
        // Afficher le bouton de recherche sur les pages Timer, Recipes, Bugs et Fish (pas Birds car la barre est toujours visible)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.isVisible = currentDestination?.id == R.id.timerFragment || 
                                currentDestination?.id == R.id.recipesFragment ||
                                currentDestination?.id == R.id.bugsFragment ||
                                currentDestination?.id == R.id.fishFragment
        
        // Afficher le bouton de rafraîchissement sur la page Codes
        val refreshItem = menu?.findItem(R.id.action_refresh)
        refreshItem?.isVisible = currentDestination?.id == R.id.codesFragment
        
        return super.onPrepareOptionsMenu(menu)
    }
    
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
                
                // Vérifier qu'on est bien sur TimerFragment ou RecipesFragment et que navHostFragment n'est pas null
                if (navHostFragment != null) {
                    val currentId = navHostFragment.navController.currentDestination?.id
                    val fragmentManager = navHostFragment.childFragmentManager
                    
                    if (currentId == R.id.timerFragment) {
                        // Chercher TimerFragment
                        var timerFragment: TimerFragment? = null
                        timerFragment = fragmentManager.fragments.find { 
                            it is TimerFragment && it.isAdded && it.view != null
                        } as? TimerFragment
                        
                        if (timerFragment == null) {
                            timerFragment = fragmentManager.primaryNavigationFragment as? TimerFragment
                        }
                        
                        if (timerFragment == null) {
                            for (fragment in fragmentManager.fragments) {
                                if (fragment is TimerFragment && fragment.isAdded) {
                                    timerFragment = fragment
                                    break
                                }
                            }
                        }
                        timerFragment?.toggleSearchBar()
                    }
                }
                true
            }
            R.id.action_refresh -> {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
                
                if (navHostFragment != null) {
                    val currentId = navHostFragment.navController.currentDestination?.id
                    val fragmentManager = navHostFragment.childFragmentManager
                    
                    if (currentId == R.id.codesFragment) {
                        // Chercher CodesFragment
                        var codesFragment: CodesFragment? = null
                        codesFragment = fragmentManager.fragments.find { 
                            it is CodesFragment && it.isAdded && it.view != null
                        } as? CodesFragment
                        
                        if (codesFragment == null) {
                            codesFragment = fragmentManager.primaryNavigationFragment as? CodesFragment
                        }
                        
                        if (codesFragment == null) {
                            for (fragment in fragmentManager.fragments) {
                                if (fragment is CodesFragment && fragment.isAdded) {
                                    codesFragment = fragment
                                    break
                                }
                            }
                        }
                        codesFragment?.refreshCodes()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        val navController = (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
            .navController
        return androidx.navigation.ui.NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission déjà accordée
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    // Expliquer pourquoi la permission est nécessaire
                    Toast.makeText(
                        this,
                        "Les notifications sont nécessaires pour vous prévenir quand vos cultures sont prêtes.",
                        Toast.LENGTH_LONG
                    ).show()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        NOTIFICATION_PERMISSION_REQUEST_CODE
                    )
                }
                else -> {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        NOTIFICATION_PERMISSION_REQUEST_CODE
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission de notification accordée", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    this,
                    "Les notifications ne fonctionneront pas sans cette permission.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
