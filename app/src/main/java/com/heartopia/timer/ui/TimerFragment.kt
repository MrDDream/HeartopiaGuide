package com.heartopia.timer.ui

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartopia.timer.R
import com.heartopia.timer.data.database.TimerEntity
import com.heartopia.timer.databinding.FragmentTimerBinding
import com.heartopia.timer.databinding.TimerBannerBinding
import com.heartopia.timer.viewmodel.CropViewModel
import com.heartopia.timer.viewmodel.ViewModelFactory
import java.util.concurrent.TimeUnit

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    
    private var _bannerBinding: TimerBannerBinding? = null
    private val bannerBinding get() = _bannerBinding!!
    
    private val viewModel: CropViewModel by activityViewModels { ViewModelFactory(requireActivity().application) }
    private lateinit var adapter: CropListAdapter
    private var bannerTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        _bannerBinding = TimerBannerBinding.bind(binding.root.findViewById(R.id.timerBanner))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupSearchBar()
        setupRecyclerView()
        observeViewModel()
        
        // Restaurer la notification seulement quand le fragment est visible (app vraiment rouverte)
        viewModel.restoreNotificationIfNeeded()
    }
    
    fun toggleSearchBar() {
        val binding = _binding ?: return
        val view = this.view ?: return
        
        val isCurrentlyVisible = binding.searchInputLayout.visibility == View.VISIBLE
        
        if (isCurrentlyVisible) {
            // Cacher la barre de recherche
            binding.searchInputLayout.visibility = View.GONE
            // Réinitialiser la recherche
            binding.searchEditText.setText("")
            viewModel.setSearchQuery("")
            // Cacher le clavier
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
            
            // Mettre à jour les contraintes pour que la RecyclerView soit sous le banner
            val constraintSet = androidx.constraintlayout.widget.ConstraintSet()
            constraintSet.clone(binding.root)
            constraintSet.clear(R.id.cropsRecyclerView, androidx.constraintlayout.widget.ConstraintSet.TOP)
            constraintSet.connect(R.id.cropsRecyclerView, androidx.constraintlayout.widget.ConstraintSet.TOP, R.id.timerBanner, androidx.constraintlayout.widget.ConstraintSet.BOTTOM, 0)
            constraintSet.applyTo(binding.root)
        } else {
            // Afficher la barre de recherche
            binding.searchInputLayout.visibility = View.VISIBLE
            
            // S'assurer que la RecyclerView est visible
            binding.cropsRecyclerView.visibility = View.VISIBLE
            
            // Mettre à jour les contraintes pour que la RecyclerView soit sous la barre de recherche
            val constraintSet = androidx.constraintlayout.widget.ConstraintSet()
            constraintSet.clone(binding.root)
            constraintSet.clear(R.id.cropsRecyclerView, androidx.constraintlayout.widget.ConstraintSet.TOP)
            constraintSet.connect(R.id.cropsRecyclerView, androidx.constraintlayout.widget.ConstraintSet.TOP, R.id.searchInputLayout, androidx.constraintlayout.widget.ConstraintSet.BOTTOM, 0)
            constraintSet.applyTo(binding.root)
            
            // Forcer la mise à jour du layout
            binding.root.post {
                binding.root.requestLayout()
                binding.cropsRecyclerView.requestLayout()
                
                // Attendre que le layout soit mis à jour avant de demander le focus
                binding.cropsRecyclerView.post {
                    if (_binding != null && view.isAttachedToWindow) {
                        binding.searchEditText.requestFocus()
                        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_IMPLICIT)
                    }
                }
            }
        }
    }
    
    private fun setupSearchBar() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
                true
            } else {
                false
            }
        }
        
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchQuery(s?.toString() ?: "")
            }
        })
    }


    private fun setupRecyclerView() {
        adapter = CropListAdapter(
            onStartTimerClick = { crop ->
                viewModel.startTimer(crop)
            },
            onStopTimerClick = { timerId ->
                viewModel.requestStopTimer(timerId)
            },
            onFertilizerClick = { timerId ->
                showFertilizerDialog(timerId)
            },
            onFavoriteClick = { crop ->
                viewModel.toggleFavorite(crop)
            },
            isFavorite = { cropName ->
                viewModel.isFavorite(cropName)
            }
        )

        binding.cropsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cropsRecyclerView.adapter = adapter

        // Observer la liste triée au lieu de la liste statique
        viewModel.sortedCrops.observe(viewLifecycleOwner) { sortedCrops ->
            adapter.submitList(sortedCrops)
        }
    }

    private fun observeViewModel() {
        viewModel.activeTimers.observe(viewLifecycleOwner) { timers ->
            adapter.updateActiveTimers(timers)
            updateBanner(timers)
            // Mettre à jour la liste triée quand les timers changent
            viewModel.updateSortedCrops()
        }

        viewModel.sortedCrops.observe(viewLifecycleOwner) { sortedCrops ->
            adapter.submitList(sortedCrops)
        }

        viewModel.showStopTimerDialog.observe(viewLifecycleOwner) { timer ->
            timer?.let {
                showStopTimerDialog(it)
            }
        }

        viewModel.showFertilizerDialog.observe(viewLifecycleOwner) { pair ->
            pair?.let { (timerId, amount) ->
                showFertilizerConfirmDialog(timerId, amount)
            }
        }

        viewModel.successMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                android.widget.Toast.makeText(requireContext(), it, android.widget.Toast.LENGTH_SHORT).show()
                viewModel.clearMessages()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                android.widget.Toast.makeText(requireContext(), it, android.widget.Toast.LENGTH_LONG).show()
                viewModel.clearMessages()
            }
        }
    }

    private fun showStopTimerDialog(timer: TimerEntity) {
        val cropDisplayName = com.heartopia.timer.data.Crop.getCropDisplayName(requireContext(), timer.cropName)
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.confirm_stop_timer))
            .setMessage(getString(R.string.confirm_stop_timer_message, cropDisplayName))
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                viewModel.confirmStopTimer(timer.id)
                viewModel.clearStopTimerDialog()
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                viewModel.clearStopTimerDialog()
            }
            .setOnDismissListener { _ ->
                viewModel.clearStopTimerDialog()
            }
            .show()
    }

    private fun showFertilizerDialog(timerId: Long) {
        val input = android.widget.EditText(requireContext())
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        input.hint = getString(R.string.fertilizer_amount_hint)

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.fertilizer_amount))
            .setView(input)
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                val amountText = input.text.toString()
                if (amountText.isNotBlank()) {
                    try {
                        val amount = amountText.toInt()
                        if (amount > 0) {
                            viewModel.requestApplyFertilizer(timerId, amount)
                        }
                    } catch (e: NumberFormatException) {
                        // Ignorer les valeurs invalides
                    }
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showFertilizerConfirmDialog(timerId: Long, amount: Int) {
        val reductionMinutes = amount * 15
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.fertilizer_confirm))
            .setMessage(getString(R.string.fertilizer_confirm_message, amount, reductionMinutes))
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                viewModel.confirmApplyFertilizer(timerId, amount)
                viewModel.clearFertilizerDialog()
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                viewModel.clearFertilizerDialog()
            }
            .setOnDismissListener { _ ->
                viewModel.clearFertilizerDialog()
            }
            .show()
    }

    private fun updateBanner(timers: List<TimerEntity>) {
        bannerTimer?.cancel()
        
        val bannerView = binding.root.findViewById<View>(R.id.timerBanner)
        
        if (timers.isEmpty()) {
            bannerView?.visibility = View.GONE
            return
        }

        bannerView?.visibility = View.VISIBLE
        
        val activeTimers = timers.filter { !it.isCompleted && it.endTime > System.currentTimeMillis() }
        
        if (activeTimers.isEmpty()) {
            bannerView?.visibility = View.GONE
            return
        }

        // Construire le texte de la bannière
        val bannerText = buildString {
            activeTimers.forEachIndexed { index, timer ->
                if (index > 0) append("\n")
                val remainingTime = timer.endTime - System.currentTimeMillis()
                val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
                
                val timeText = when {
                    hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
                    else -> String.format("%02d:%02d", minutes, seconds)
                }
                
                val cropDisplayName = com.heartopia.timer.data.Crop.getCropDisplayName(requireContext(), timer.cropName)
                append("• $cropDisplayName: $timeText restantes")
            }
        }

        bannerBinding.bannerContentTextView.text = bannerText

        // Trouver le timer le plus court pour mettre à jour la bannière
        val shortestTimer = activeTimers.minByOrNull { it.endTime }
        shortestTimer?.let { timer ->
            val remainingTime = timer.endTime - System.currentTimeMillis()
            if (remainingTime > 0) {
                bannerTimer = object : CountDownTimer(remainingTime, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val updatedTimers = viewModel.activeTimers.value ?: emptyList()
                        val activeTimers = updatedTimers.filter { 
                            !it.isCompleted && it.endTime > System.currentTimeMillis() 
                        }
                        
                        val bannerView = binding.root.findViewById<View>(R.id.timerBanner)
                        if (activeTimers.isEmpty()) {
                            bannerView?.visibility = View.GONE
                            cancel()
                            return
                        }

                        val bannerText = buildString {
                            activeTimers.forEachIndexed { index, timer ->
                                if (index > 0) append("\n")
                                val remainingTime = timer.endTime - System.currentTimeMillis()
                                val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
                                val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
                                val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
                                
                                val timeText = when {
                                    hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
                                    else -> String.format("%02d:%02d", minutes, seconds)
                                }
                                
                                val cropDisplayName = com.heartopia.timer.data.Crop.getCropDisplayName(requireContext(), timer.cropName)
                append("• $cropDisplayName: $timeText restantes")
                            }
                        }

                        bannerBinding.bannerContentTextView.text = bannerText
                    }

                    override fun onFinish() {
                        val updatedTimers = viewModel.activeTimers.value ?: emptyList()
                        updateBanner(updatedTimers)
                    }
                }.start()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerTimer?.cancel()
        _binding = null
        _bannerBinding = null
    }
}
