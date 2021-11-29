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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.projekt.database.KaloriaDatabase
import com.example.projekt.database.KaloriaSession
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


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

            var databaseValue: String = "asd"
            Thread.sleep(300)
            uiScope.launch(Dispatchers.IO) {
                databaseValue = viewModel.dataSource.get(session.sessionId).toString()
                Log.d( "Database value: ", databaseValue )
            }
            Thread.sleep(300)

            Toast.makeText(context,databaseValue,Toast.LENGTH_LONG).show()
            Log.d("NAME", viewModel.name)
            Log.d("KALORIA", viewModel.kaloria.toString())
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .build()

        val catService: CatService = retrofit.create(CatService::class.java)

        binding.cat.setOnClickListener{

            catService.getFact().enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    response.body()?.let {
                        val json = it.string()
                        val jsonObject = JSONObject(json);
                        val fact = jsonObject.getString("fact")


                        Toast.makeText(context, fact, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(context, "Cats are animals", Toast.LENGTH_LONG).show()
                }
            })

        }

    }

    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
        _binding = null
    }
}