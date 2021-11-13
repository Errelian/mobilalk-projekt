package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*
import android.util.Log
import androidx.lifecycle.ViewModelProvider


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(requireActivity()).get(FirstFragmentViewModel::class.java)

        Log.d("NAME", viewModel.name)
        Log.d("KALORIA", viewModel.kaloria.toString())

        nev_field.setText(viewModel.name)
        kaloria_field.setText(viewModel.kaloria.toString())


        binding.mero.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(viewModel.kaloria, viewModel.name)
            findNavController().navigate(action)
        }

        binding.net.setOnClickListener{
            val action = FirstFragmentDirections.actionFirstFragmentToNetFragment()
            findNavController().navigate(action)
        }

        binding.mento.setOnClickListener{
            viewModel.setTheName( nev_field.getText().toString())
            viewModel.setTheKaloria(kaloria_field.getText().toString().toInt())
            Log.d("NAME", viewModel.name)
            Log.d("KALORIA", viewModel.kaloria.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}