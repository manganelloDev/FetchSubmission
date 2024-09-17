package com.manganello.fetchsubmission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.manganello.fetchsubmission.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
        viewModel.remoteData.observe(viewLifecycleOwner) { hiringData ->
            binding.TextView.text = "$hiringData"
        }
    }
}