package com.heartopia.timer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.heartopia.timer.R
import com.heartopia.timer.data.SettingsManager
import com.heartopia.timer.databinding.FragmentSettingsBinding
import com.heartopia.timer.viewmodel.SettingsViewModel
import com.heartopia.timer.viewmodel.ViewModelFactory

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by activityViewModels { 
        ViewModelFactory(requireActivity().application) 
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLanguageSection()
        setupThemeSection()
        setupNotificationsSection()
        setupNotificationTypeSection()
    }

    override fun onResume() {
        super.onResume()
        // Réinitialiser les adapters pour s'assurer que les chaînes traduites sont à jour
        refreshAdapters()
    }

    private fun refreshAdapters() {
        // Réinitialiser l'adapter de langue avec les nouvelles chaînes traduites
        val languages = listOf(
            getString(R.string.french),
            getString(R.string.english)
        )
        val languageAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, languages)
        binding.languageSpinner.setAdapter(languageAdapter)
        
        // Réinitialiser les listeners de langue
        binding.languageSpinner.setOnItemClickListener { _, _, position, _ ->
            val newLanguage = when (position) {
                0 -> SettingsManager.Language.FRENCH
                1 -> SettingsManager.Language.ENGLISH
                else -> SettingsManager.Language.FRENCH
            }
            if (viewModel.language.value != newLanguage) {
                viewModel.setLanguage(newLanguage)
                val activity = requireActivity() as? MainActivity
                activity?.applyLanguage()
            }
        }
        
        binding.languageSpinner.setOnClickListener {
            binding.languageSpinner.showDropDown()
        }
        
        // Réinitialiser l'adapter de thème avec les nouvelles chaînes traduites
        val themes = listOf(
            getString(R.string.light_mode),
            getString(R.string.dark_mode)
        )
        val themeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, themes)
        binding.themeSpinner.setAdapter(themeAdapter)
        
        // Réinitialiser les listeners de thème
        binding.themeSpinner.setOnItemClickListener { _, _, position, _ ->
            val newMode = when (position) {
                0 -> SettingsManager.ThemeMode.LIGHT
                1 -> SettingsManager.ThemeMode.DARK
                else -> SettingsManager.ThemeMode.DARK
            }
            if (viewModel.themeMode.value != newMode) {
                viewModel.setThemeMode(newMode)
                val activity = requireActivity() as? MainActivity
                activity?.applyTheme()
            }
        }
        
        binding.themeSpinner.setOnClickListener {
            binding.themeSpinner.showDropDown()
        }
        
        // Réinitialiser l'adapter de type de notification avec les nouvelles chaînes traduites
        val notificationTypes = listOf(
            getString(R.string.vibration),
            getString(R.string.sound)
        )
        val notificationTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, notificationTypes)
        binding.notificationTypeSpinner.setAdapter(notificationTypeAdapter)
        
        // Réinitialiser les listeners de type de notification
        binding.notificationTypeSpinner.setOnItemClickListener { _, _, position, _ ->
            val newType = when (position) {
                0 -> SettingsManager.NotificationType.VIBRATION
                1 -> SettingsManager.NotificationType.SOUND
                else -> SettingsManager.NotificationType.VIBRATION
            }
            viewModel.setNotificationType(newType)
        }
        
        binding.notificationTypeSpinner.setOnClickListener {
            binding.notificationTypeSpinner.showDropDown()
        }
        
        // Mettre à jour les valeurs affichées avec les nouvelles chaînes traduites
        viewModel.language.value?.let { language ->
            val selectedText = when (language) {
                SettingsManager.Language.FRENCH -> getString(R.string.french)
                SettingsManager.Language.ENGLISH -> getString(R.string.english)
            }
            binding.languageSpinner.setText(selectedText, false)
        }
        
        viewModel.themeMode.value?.let { mode ->
            val selectedText = when (mode) {
                SettingsManager.ThemeMode.LIGHT -> getString(R.string.light_mode)
                SettingsManager.ThemeMode.DARK -> getString(R.string.dark_mode)
            }
            binding.themeSpinner.setText(selectedText, false)
        }
        
        viewModel.notificationType.value?.let { type ->
            val selectedText = when (type) {
                SettingsManager.NotificationType.VIBRATION -> getString(R.string.vibration)
                SettingsManager.NotificationType.SOUND -> getString(R.string.sound)
            }
            binding.notificationTypeSpinner.setText(selectedText, false)
        }
    }

    private fun setupLanguageSection() {
        val languages = listOf(
            getString(R.string.french),
            getString(R.string.english)
        )
        
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, languages)
        binding.languageSpinner.setAdapter(adapter)
        
        viewModel.language.observe(viewLifecycleOwner) { language ->
            val selectedText = when (language) {
                SettingsManager.Language.FRENCH -> getString(R.string.french)
                SettingsManager.Language.ENGLISH -> getString(R.string.english)
            }
            binding.languageSpinner.setText(selectedText, false)
        }

        binding.languageSpinner.setOnItemClickListener { _, _, position, _ ->
            val newLanguage = when (position) {
                0 -> SettingsManager.Language.FRENCH
                1 -> SettingsManager.Language.ENGLISH
                else -> SettingsManager.Language.FRENCH
            }
            if (viewModel.language.value != newLanguage) {
                viewModel.setLanguage(newLanguage)
                val activity = requireActivity() as? MainActivity
                activity?.applyLanguage()
            }
        }
        
        binding.languageSpinner.setOnClickListener {
            binding.languageSpinner.showDropDown()
        }
    }

    private fun setupThemeSection() {
        val themes = listOf(
            getString(R.string.light_mode),
            getString(R.string.dark_mode)
        )
        
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, themes)
        binding.themeSpinner.setAdapter(adapter)
        
        viewModel.themeMode.observe(viewLifecycleOwner) { mode ->
            val selectedText = when (mode) {
                SettingsManager.ThemeMode.LIGHT -> getString(R.string.light_mode)
                SettingsManager.ThemeMode.DARK -> getString(R.string.dark_mode)
            }
            binding.themeSpinner.setText(selectedText, false)
        }

        binding.themeSpinner.setOnItemClickListener { _, _, position, _ ->
            val newMode = when (position) {
                0 -> SettingsManager.ThemeMode.LIGHT
                1 -> SettingsManager.ThemeMode.DARK
                else -> SettingsManager.ThemeMode.DARK
            }
            if (viewModel.themeMode.value != newMode) {
                viewModel.setThemeMode(newMode)
                val activity = requireActivity() as? MainActivity
                activity?.applyTheme()
            }
        }
        
        binding.themeSpinner.setOnClickListener {
            binding.themeSpinner.showDropDown()
        }
    }

    private fun setupNotificationsSection() {
        viewModel.notificationsEnabled.observe(viewLifecycleOwner) { enabled ->
            binding.notificationsSwitch.isChecked = enabled
        }

        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationsEnabled(isChecked)
        }
    }

    private fun setupNotificationTypeSection() {
        val notificationTypes = listOf(
            getString(R.string.vibration),
            getString(R.string.sound)
        )
        
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, notificationTypes)
        binding.notificationTypeSpinner.setAdapter(adapter)
        
        viewModel.notificationType.observe(viewLifecycleOwner) { type ->
            val selectedText = when (type) {
                SettingsManager.NotificationType.VIBRATION -> getString(R.string.vibration)
                SettingsManager.NotificationType.SOUND -> getString(R.string.sound)
            }
            binding.notificationTypeSpinner.setText(selectedText, false)
        }

        binding.notificationTypeSpinner.setOnItemClickListener { _, _, position, _ ->
            val newType = when (position) {
                0 -> SettingsManager.NotificationType.VIBRATION
                1 -> SettingsManager.NotificationType.SOUND
                else -> SettingsManager.NotificationType.VIBRATION
            }
            viewModel.setNotificationType(newType)
        }
        
        binding.notificationTypeSpinner.setOnClickListener {
            binding.notificationTypeSpinner.showDropDown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
