package com.heartopia.timer.ui

import android.content.res.ColorStateList
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heartopia.timer.R
import com.heartopia.timer.data.Crop
import com.heartopia.timer.data.database.TimerEntity
import com.heartopia.timer.databinding.ItemCropBinding
import java.util.concurrent.TimeUnit

class CropListAdapter(
    private val onStartTimerClick: (Crop) -> Unit,
    private val onStopTimerClick: (Long) -> Unit,
    private val onFavoriteClick: (Crop) -> Unit,
    private val onFertilizerClick: (Long) -> Unit,
    private val isFavorite: (String) -> Boolean
) : ListAdapter<Crop, CropViewHolder>(CropDiffCallback()) {

    private var activeTimers: Map<String, TimerEntity> = emptyMap()

    fun updateActiveTimers(timers: List<TimerEntity>) {
        activeTimers = timers.associateBy { it.cropName }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropViewHolder {
        val binding = ItemCropBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CropViewHolder(
            binding,
            onStartTimerClick,
            onStopTimerClick,
            onFavoriteClick,
            onFertilizerClick,
            isFavorite
        ) { crop ->
            activeTimers[crop.nameKey]
        }
    }

    override fun onBindViewHolder(holder: CropViewHolder, position: Int) {
        val crop = getItem(position)
        val timer = activeTimers[crop.nameKey]
        holder.bind(crop, timer)
    }

    override fun onViewRecycled(holder: CropViewHolder) {
        super.onViewRecycled(holder)
        holder.stopTimer()
    }
}

class CropViewHolder(
    private val binding: ItemCropBinding,
    private val onStartTimerClick: (Crop) -> Unit,
    private val onStopTimerClick: (Long) -> Unit,
    private val onFavoriteClick: (Crop) -> Unit,
    private val onFertilizerClick: (Long) -> Unit,
    private val isFavorite: (String) -> Boolean,
    private val getTimer: (Crop) -> TimerEntity?
) : RecyclerView.ViewHolder(binding.root) {

    private var countDownTimer: CountDownTimer? = null
    private var currentCrop: Crop? = null

    fun bind(crop: Crop, timer: TimerEntity?) {
        currentCrop = crop
        
        // Arrêter le timer précédent s'il existe
        stopTimer()

        // Afficher l'icône
        binding.cropIconImageView.setImageResource(crop.getIconResId())

        binding.cropNameTextView.text = crop.getName(binding.root.context)
        binding.seedPriceTextView.text = binding.root.context.getString(
            R.string.seed_price,
            crop.seedPrice
        )
        binding.sellPriceTextView.text = binding.root.context.getString(
            R.string.sell_price,
            crop.sellPrice
        )
        binding.levelTextView.text = binding.root.context.getString(
            R.string.level_required,
            crop.unlockLevel
        )

        // Gérer l'icône de favori
        val favorite = isFavorite(crop.nameKey)
        updateFavoriteIcon(favorite)
        
        binding.favoriteButton.setOnClickListener {
            onFavoriteClick(crop)
            // Mettre à jour l'icône immédiatement
            val newFavorite = isFavorite(crop.nameKey)
            updateFavoriteIcon(newFavorite)
        }

        if (timer != null && !timer.isCompleted) {
            // Timer actif
            val remainingTime = timer.endTime - System.currentTimeMillis()
            
            if (remainingTime > 0) {
                binding.startTimerButton.visibility = ViewGroup.GONE
                binding.stopTimerButton.visibility = ViewGroup.VISIBLE
                binding.fertilizerButton.visibility = ViewGroup.VISIBLE

                binding.stopTimerButton.setOnClickListener {
                    onStopTimerClick(timer.id)
                }

                binding.fertilizerButton.setOnClickListener {
                    onFertilizerClick(timer.id)
                }

                // Ajuster les contraintes pour que le bouton arrêter soit sous le bouton engrais
                val constraintLayout = binding.root.getChildAt(0) as? androidx.constraintlayout.widget.ConstraintLayout
                constraintLayout?.let { layout ->
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(layout)
                    constraintSet.clear(binding.stopTimerButton.id, ConstraintSet.TOP)
                    constraintSet.connect(binding.stopTimerButton.id, ConstraintSet.TOP, binding.fertilizerButton.id, ConstraintSet.BOTTOM, 16)
                    constraintSet.applyTo(layout)
                }

                // Démarrer le countdown et mettre à jour le bouton
                startCountdown(remainingTime, timer.id)
            } else {
                // Timer terminé
                showStartButton(crop)
            }
        } else {
            // Pas de timer actif
            binding.fertilizerButton.visibility = ViewGroup.GONE
            
            // Remettre les contraintes par défaut pour le bouton arrêter
            val constraintLayout = binding.root.getChildAt(0) as? androidx.constraintlayout.widget.ConstraintLayout
            constraintLayout?.let { layout ->
                val constraintSet = ConstraintSet()
                constraintSet.clone(layout)
                constraintSet.clear(binding.stopTimerButton.id, ConstraintSet.TOP)
                constraintSet.connect(binding.stopTimerButton.id, ConstraintSet.TOP, R.id.pricesLayout, ConstraintSet.BOTTOM, 8)
                constraintSet.applyTo(layout)
            }
            
            showStartButton(crop)
        }
    }

    private fun showStartButton(crop: Crop) {
        binding.startTimerButton.visibility = ViewGroup.VISIBLE
        binding.stopTimerButton.visibility = ViewGroup.GONE
        
        val timeText = binding.root.context.getString(
            R.string.start_timer_with_time,
            crop.getFormattedTime()
        )
        binding.startTimerButton.text = timeText

        binding.startTimerButton.setOnClickListener {
            onStartTimerClick(crop)
        }
    }

    private fun startCountdown(remainingTimeMillis: Long, timerId: Long) {
        countDownTimer = object : CountDownTimer(remainingTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                val timeText = when {
                    hours > 0 -> String.format(
                        "%d:%02d:%02d",
                        hours,
                        minutes,
                        seconds
                    )
                    else -> String.format("%02d:%02d", minutes, seconds)
                }

                // Mettre à jour le texte du bouton avec le temps restant
                val buttonText = binding.root.context.getString(
                    R.string.stop_timer_with_time,
                    timeText
                )
                binding.stopTimerButton.text = buttonText
            }

            override fun onFinish() {
                val buttonText = binding.root.context.getString(
                    R.string.stop_timer_with_time,
                    "00:00"
                )
                binding.stopTimerButton.text = buttonText
                
                // Revenir au bouton démarrer
                currentCrop?.let { crop ->
                    showStartButton(crop)
                }
            }
        }.start()
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteButton.setImageResource(R.drawable.ic_heart_filled)
            binding.favoriteButton.imageTintList = null // Pas de tint pour garder la couleur rouge du drawable
        } else {
            binding.favoriteButton.setImageResource(R.drawable.ic_heart)
            binding.favoriteButton.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.root.context, android.R.color.white)
            )
        }
    }

    fun stopTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }
}

class CropDiffCallback : DiffUtil.ItemCallback<Crop>() {
    override fun areItemsTheSame(oldItem: Crop, newItem: Crop): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Crop, newItem: Crop): Boolean {
        return oldItem == newItem
    }
}
