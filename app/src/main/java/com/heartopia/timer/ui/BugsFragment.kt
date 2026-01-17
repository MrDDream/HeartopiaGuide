package com.heartopia.timer.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.heartopia.timer.R
import com.heartopia.timer.databinding.FragmentBugsBinding
import com.heartopia.timer.viewmodel.BugViewModel
import com.heartopia.timer.viewmodel.FilterType
import com.heartopia.timer.viewmodel.SortOrder
import com.heartopia.timer.viewmodel.ViewModelFactory

class BugsFragment : Fragment() {

    private var _binding: FragmentBugsBinding? = null
    private val binding get() = _binding!!
    private lateinit var bugAdapter: BugAdapter
    private val viewModel: BugViewModel by activityViewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBugsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupSearchBar()
        setupFilterTypeButton()
        setupFilterButton()
        setupRecyclerView()
        observeViewModel()
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
                val query = s?.toString() ?: ""
                Log.d("BugsFragment", "Search query changed: '$query'")
                viewModel.setSearchQuery(query)
            }
        })
    }
    
    private fun setupFilterTypeButton() {
        binding.filterTypeButton.setOnClickListener {
            showFilterTypeDialog()
        }
    }
    
    private fun setupFilterButton() {
        updateFilterButtonIcon()
        
        binding.filterButton.setOnClickListener {
            // Toggle l'ordre de tri
            val currentOrder = viewModel.sortOrder.value ?: SortOrder.ASCENDING
            val newOrder = if (currentOrder == SortOrder.ASCENDING) {
                SortOrder.DESCENDING
            } else {
                SortOrder.ASCENDING
            }
            viewModel.setSortOrder(newOrder)
            updateFilterButtonIcon()
            
            // Scroller vers le haut
            binding.bugsRecyclerView.scrollToPosition(0)
        }
        
        // Observer le changement d'ordre pour mettre à jour l'icône
        viewModel.sortOrder.observe(viewLifecycleOwner) {
            updateFilterButtonIcon()
        }
    }
    
    private fun updateFilterButtonIcon() {
        val currentOrder = viewModel.sortOrder.value ?: SortOrder.ASCENDING
        val iconRes = if (currentOrder == SortOrder.ASCENDING) {
            R.drawable.ic_arrow_up
        } else {
            R.drawable.ic_arrow_down
        }
        binding.filterButton.setIconResource(iconRes)
    }
    
    private fun showFilterTypeDialog() {
        val filterTypes = arrayOf(
            getString(R.string.filter_by_name),
            getString(R.string.filter_by_level),
            getString(R.string.filter_by_price)
        )
        
        val currentFilterType = viewModel.filterType.value ?: FilterType.NAME
        val filterSelectedIndex = when (currentFilterType) {
            FilterType.NAME -> 0
            FilterType.LEVEL -> 1
            FilterType.PRICE -> 2
        }
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.filter_by))
            .setSingleChoiceItems(filterTypes, filterSelectedIndex) { dialog, which ->
                val newFilterType = when (which) {
                    0 -> FilterType.NAME
                    1 -> FilterType.LEVEL
                    2 -> FilterType.PRICE
                    else -> FilterType.NAME
                }
                viewModel.setFilterType(newFilterType)
                dialog.dismiss()
                
                // Scroller vers le haut quand un filtre est sélectionné
                binding.bugsRecyclerView.scrollToPosition(0)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
    
    private fun setupRecyclerView() {
        bugAdapter = BugAdapter()
        
        binding.bugsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.bugsRecyclerView.adapter = bugAdapter
    }
    
    private fun observeViewModel() {
        viewModel.sortedBugs.observe(viewLifecycleOwner) { sortedBugs ->
            Log.d("BugsFragment", "Observer received ${sortedBugs.size} bugs")
            // S'assurer que la RecyclerView est visible
            binding.bugsRecyclerView.visibility = View.VISIBLE
            bugAdapter.submitList(sortedBugs) {
                Log.d("BugsFragment", "List submitted, adapter has ${bugAdapter.itemCount} items")
                // S'assurer que la RecyclerView reste visible
                binding.bugsRecyclerView.visibility = View.VISIBLE
                // Scroller vers le haut après la mise à jour de la liste
                if (sortedBugs.isNotEmpty()) {
                    binding.bugsRecyclerView.scrollToPosition(0)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
