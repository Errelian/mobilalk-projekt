package com.example.projekt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.databinding.FragmentNetDataBinding
import kotlinx.android.synthetic.main.fragment_net_data.*

class NetFragment : Fragment() {

    private var _binding: FragmentNetDataBinding? = null

    private val binding get() = _binding!!

    var images: Array<Int> = arrayOf(R.drawable.futas,R.drawable.swimming, R.drawable.gyorsasbb_futas, R.drawable.table_tennis, R.drawable.futball)

    private lateinit var localRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentNetDataBinding.inflate(inflater, container, false)
        var titleString = getResources().getStringArray(R.array.meno_adat)

        var recyclerAdapter = RecyclerAdapter(requireContext(), titleString, images)

        binding.recyclerViewForMeno.adapter = recyclerAdapter
        binding.recyclerViewForMeno.layoutManager = LinearLayoutManager(requireContext())
        Log.d("asd", "netFragment onCreateView lefutott")
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("asd", "netFragment onViewCreated lefutott")



        binding.backToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_netFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}