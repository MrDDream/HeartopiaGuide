package com.heartopia.timer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heartopia.timer.databinding.ItemDailyTaskBinding

data class DailyTask(
    val key: String,
    val nameResId: Int? = null,
    val customName: String? = null,
    val isCustom: Boolean = false
)

class DailyTaskAdapter(
    private val onTaskChecked: (String, Boolean) -> Unit,
    private val isTaskCompleted: (String) -> Boolean,
    private val onDeleteClick: ((String) -> Unit)? = null
) : ListAdapter<DailyTask, DailyTaskViewHolder>(DailyTaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTaskViewHolder {
        val binding = ItemDailyTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DailyTaskViewHolder(binding, onTaskChecked, isTaskCompleted, onDeleteClick)
    }

    override fun onBindViewHolder(holder: DailyTaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }
}

class DailyTaskViewHolder(
    private val binding: ItemDailyTaskBinding,
    private val onTaskChecked: (String, Boolean) -> Unit,
    private val isTaskCompleted: (String) -> Boolean,
    private val onDeleteClick: ((String) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: DailyTask) {
        // Afficher le nom de la tâche
        val taskName = if (task.isCustom && task.customName != null) {
            task.customName
        } else if (task.nameResId != null) {
            binding.root.context.getString(task.nameResId)
        } else {
            ""
        }
        
        binding.taskCheckBox.text = taskName
        
        // Retirer le listener temporairement pour éviter les appels multiples
        binding.taskCheckBox.setOnCheckedChangeListener(null)
        binding.taskCheckBox.isChecked = isTaskCompleted(task.key)
        
        // Réattacher le listener après la mise à jour
        binding.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onTaskChecked(task.key, isChecked)
        }
        
        // Afficher/masquer le bouton de suppression pour les tâches personnalisées
        if (task.isCustom && onDeleteClick != null) {
            binding.deleteButton.visibility = android.view.View.VISIBLE
            binding.deleteButton.setOnClickListener {
                onDeleteClick(task.key)
            }
        } else {
            binding.deleteButton.visibility = android.view.View.GONE
        }
    }
}

class DailyTaskDiffCallback : DiffUtil.ItemCallback<DailyTask>() {
    override fun areItemsTheSame(oldItem: DailyTask, newItem: DailyTask): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: DailyTask, newItem: DailyTask): Boolean {
        return oldItem == newItem
    }
}
