package com.example.projekt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projekt.databinding.FragmentSecondBinding
import kotlinx.android.synthetic.main.fragment_second.*
import java.time.Instant

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private lateinit var viewModel: MainActivityViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: SecondFragmentArgs by navArgs()
    var begin: Long = 0;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        begin = Instant.now().toEpochMilli()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        binding.checkup.setOnClickListener{
            val name = args.nev
            val kaloria = args.kaloria
            val end = Instant.now().toEpochMilli()
            val ellapsed = end - begin
            var caloriesBurned = ( ellapsed.toDouble() /(1000.0*60.0*60.0)) * kaloria.toDouble()


            //Log.d("Current kaloria count DOUBLE", viewModel.kaloriaBurned().value.toString())
            //Log.d("Current kaloria count", viewModel.currentKaloriaString.value.toString())
            viewModel.addToBurned(caloriesBurned)

            //Log.d("Current kaloria count DOUBLE", viewModel.kaloriaBurned().value.toString())
            //Log.d("Current kaloria count", viewModel.currentKaloriaString.value.toString())

            currentKal.setText(viewModel.currentKaloriaString.value.toString() + " Kcal")
        }

        binding.buttonSecond.setOnClickListener {
            val name = args.nev
            val kaloria = args.kaloria
            Log.d("NAME", name)
            Log.d("KALORIA", kaloria.toString())
            val end = Instant.now().toEpochMilli()
            val ellapsed = end - begin
            Log.d("Ellapsed", ellapsed.toString())
            var caloriesBurned = ( ellapsed.toDouble() /(1000.0*60.0*60.0)) * kaloria.toDouble()
            Log.d("caloriesBurned", caloriesBurned.toString())

            Log.d(viewModel.kaloriaChange().value.toString(), "Before change")
            viewModel.changeKaloria()
            Log.d(viewModel.kaloriaChange().value.toString(), "Afer change")
            //TODO MAKE THIS ONE CHANGE A LIVEDATA THING, MAKE THAT LIVEDATE OBSERVED -> TRIGGER COROUTINE TO WRITE TO DB
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }


        Log.d(viewModel.kaloriaBurned().toString(), "asd")
        viewModel.kaloriaChange().observe(viewLifecycleOwner, Observer {
            Log.d("OBSERVER TRIGGERED", "KEK")
        })

        viewModel.currentKaloriaString.observe(viewLifecycleOwner, Observer{
            Log.d("kaloriaburnedstringchanged", "asd")
        })
        Log.d("After Observer", "asd")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}