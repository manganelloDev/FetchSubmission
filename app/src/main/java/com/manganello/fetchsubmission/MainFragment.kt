package com.manganello.fetchsubmission

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.manganello.fetchsubmission.databinding.FragmentMainBinding
import com.manganello.fetchsubmission.recyclerview.RemoteDataAdapter


class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerAdapter: RemoteDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.retrieveData()
    }

    private fun setObservers() {

        viewModel.recyclerData.observe(viewLifecycleOwner) { recyclerData ->
            recyclerAdapter = RemoteDataAdapter(recyclerData)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = recyclerAdapter
        }

        viewModel.listIds.observe(viewLifecycleOwner) { listIds ->
            val options = mutableListOf("All")
            options.addAll(listIds.map { "ListID: $it" })

            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, options)
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            binding.mySpinner.adapter = spinnerAdapter

            binding.mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedOption = parent.getItemAtPosition(position).toString()

                    if (selectedOption == "All") {
                        recyclerAdapter.filterData(null)
                    } else {
                        val selectedListId = selectedOption.substringAfter("ListID: ").toIntOrNull()
                        selectedListId?.let {
                            recyclerAdapter.filterData(it)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }
        }
    }
}