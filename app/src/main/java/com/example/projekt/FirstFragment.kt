package com.example.projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projekt.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projekt.database.KaloriaDatabase
import com.example.projekt.database.KaloriaSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        Log.d("NAME", viewModel.name)
        Log.d("KALORIA", viewModel.kaloria.toString())

        nev_field.setText(viewModel.name)
        kaloria_field.setText(viewModel.kaloria.toString())

        val application = requireNotNull(this.activity).application
        var datasource = KaloriaDatabase.getInstance(application).kaloriaDatabaseDao
        viewModel.setDatasource(datasource)


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

            var session = KaloriaSession(nev_field.getText().toString(), 0.5)

            Log.d("asd", session.burnedCalories.toString())
            Log.d("asd2",session.sessionId)

            uiScope.launch(Dispatchers.IO) {
                viewModel.dataSource.insert(session)
            }

            Thread.sleep(300)
            uiScope.launch(Dispatchers.IO) {
                Log.d( "Database value: ", (viewModel.dataSource.get(session.sessionId)).toString() )
            }

            Log.d("NAME", viewModel.name)
            Log.d("KALORIA", viewModel.kaloria.toString())
        }
    }

    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
        _binding = null
    }
}