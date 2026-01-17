package com.heartopia.timer.ui

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.heartopia.timer.R
import com.heartopia.timer.data.DailyTasksManager
import com.heartopia.timer.databinding.FragmentHomeBinding
import java.util.Calendar
import java.util.TimeZone

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private var countDownTimer: CountDownTimer? = null
    private lateinit var tasksManager: DailyTasksManager
    private lateinit var tasksAdapter: DailyTaskAdapter
    
    private val dailyTasks = listOf(
        DailyTask("inhabitants_tasks", R.string.task_inhabitants),
        DailyTask("furniture_shop", R.string.task_furniture_shop),
        DailyTask("clothing_shop", R.string.task_clothing_shop),
        DailyTask("feed_wild_animals", R.string.task_feed_animals),
        DailyTask("collect_sliding_oak", R.string.task_collect_oak),
        DailyTask("collect_fluorite", R.string.task_collect_fluorite),
        DailyTask("check_event_tasks", R.string.task_check_events)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        tasksManager = DailyTasksManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupCountdown()
        setupTasksList()
        setupAddTaskButton()
    }
    
    private fun setupCountdown() {
        // Vérifier et réinitialiser les tâches si nécessaire
        tasksManager.checkAndResetIfNeeded()
        
        startCountdown()
    }
    
    private fun startCountdown() {
        val timeUntil6AM = calculateTimeUntil6AM()
        
        countDownTimer = object : CountDownTimer(timeUntil6AM, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / (1000 * 60 * 60)
                val minutes = (millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60)
                val seconds = (millisUntilFinished % (1000 * 60)) / 1000
                
                val timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                binding.countdownTextView.text = timeText
            }

            override fun onFinish() {
                // Quand le countdown arrive à 0, réinitialiser les tâches et redémarrer
                tasksManager.checkAndResetIfNeeded()
                updateTasksList()
                binding.countdownTextView.text = "00:00:00"
                startCountdown()
            }
        }.start()
    }
    
    private fun calculateTimeUntil6AM(): Long {
        // Utiliser la timezone UTC+1
        val timeZone = TimeZone.getTimeZone("Europe/Paris") // UTC+1 (CET/CEST)
        val calendar = Calendar.getInstance(timeZone)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        
        // Calculer le temps jusqu'à 6h du matin en UTC+1
        val targetHour = 6
        val targetMinute = 0
        val targetSecond = 0
        
        calendar.set(Calendar.HOUR_OF_DAY, targetHour)
        calendar.set(Calendar.MINUTE, targetMinute)
        calendar.set(Calendar.SECOND, targetSecond)
        calendar.set(Calendar.MILLISECOND, 0)
        
        // Si on est déjà passé 6h aujourd'hui, viser 6h demain
        if (currentHour >= targetHour) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        
        // Convertir le temps UTC+1 en millisecondes UTC pour la comparaison
        val targetTimeInUTC = calendar.timeInMillis
        
        // Obtenir l'heure actuelle en UTC+1
        val nowCalendar = Calendar.getInstance(timeZone)
        val nowInUTC = nowCalendar.timeInMillis
        
        return targetTimeInUTC - nowInUTC
    }
    
    private fun setupTasksList() {
        tasksAdapter = DailyTaskAdapter(
            onTaskChecked = { taskKey, isChecked ->
                tasksManager.setTaskCompleted(taskKey, isChecked)
                updateProgressBar()
            },
            isTaskCompleted = { taskKey ->
                tasksManager.isTaskCompleted(taskKey)
            },
            onDeleteClick = { taskKey ->
                tasksManager.removeCustomTask(taskKey)
                updateTasksList()
            }
        )
        
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tasksRecyclerView.adapter = tasksAdapter
        updateTasksList()
    }
    
    private fun updateTasksList() {
        val allTasks = mutableListOf<DailyTask>()
        
        // Ajouter les tâches prédéfinies
        allTasks.addAll(dailyTasks)
        
        // Ajouter les tâches personnalisées
        tasksManager.getCustomTasks().forEach { taskKey ->
            val taskName = tasksManager.getCustomTaskName(taskKey)
            if (taskName != null) {
                allTasks.add(DailyTask(taskKey, customName = taskName, isCustom = true))
            }
        }
        
        tasksAdapter.submitList(allTasks)
        updateProgressBar()
    }
    
    private fun updateProgressBar() {
        val allTasks = mutableListOf<DailyTask>()
        
        // Ajouter les tâches prédéfinies
        allTasks.addAll(dailyTasks)
        
        // Ajouter les tâches personnalisées
        tasksManager.getCustomTasks().forEach { taskKey ->
            val taskName = tasksManager.getCustomTaskName(taskKey)
            if (taskName != null) {
                allTasks.add(DailyTask(taskKey, customName = taskName, isCustom = true))
            }
        }
        
        // Calculer le pourcentage de tâches complétées
        val totalTasks = allTasks.size
        if (totalTasks == 0) {
            binding.progressBar.progress = 0
            binding.percentageTextView.text = "0%"
            return
        }
        
        val completedTasks = allTasks.count { task ->
            tasksManager.isTaskCompleted(task.key)
        }
        
        val percentage = (completedTasks * 100) / totalTasks
        binding.progressBar.progress = percentage
        binding.percentageTextView.text = "$percentage%"
    }
    
    private fun setupAddTaskButton() {
        binding.addTaskFab.setOnClickListener {
            showAddTaskDialog()
        }
    }
    
    private fun showAddTaskDialog() {
        val input = TextInputEditText(requireContext())
        input.hint = getString(R.string.task_name_hint)
        input.maxLines = 1
        
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_task)
            .setView(input)
            .setPositiveButton(R.string.add) { _, _ ->
                val taskName = input.text?.toString()?.trim()
                if (!taskName.isNullOrBlank()) {
                    tasksManager.addCustomTask(taskName)
                    updateTasksList()
                    updateProgressBar()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
        
        dialog.show()
        
        // Activer le bouton "Ajouter" seulement si le texte n'est pas vide
        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.isEnabled = false
        
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                positiveButton.isEnabled = !s.toString().trim().isBlank()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Vérifier et réinitialiser les tâches quand le fragment devient visible
        tasksManager.checkAndResetIfNeeded()
        updateTasksList()
        updateProgressBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        countDownTimer = null
        _binding = null
    }
}
