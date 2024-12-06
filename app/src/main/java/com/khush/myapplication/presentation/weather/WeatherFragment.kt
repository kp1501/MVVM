package com.khush.myapplication.presentation.weather

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.appizona.yehiahd.fastsave.FastSave
import com.khush.myapplication.R
import com.khush.myapplication.data.remote.request.WeatherRequest
import com.khush.myapplication.databinding.FragmentWeatherBinding
import com.khush.myapplication.util.ApiResources
import com.khush.myapplication.util.Constants.IS_USER_LOGIN
import com.khush.myapplication.util.Constants.lat
import com.khush.myapplication.util.Constants.lon
import com.khush.myapplication.util.Constants.units
import com.khush.myapplication.util.Constants.appId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()
    private var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        binding = FragmentWeatherBinding.bind(view)

        binding.layoutTopbar.tvTitle.text = activity?.getString(R.string.weather)
        binding.layoutTopbar.ivAdd.visibility = View.GONE
        binding.layoutTopbar.btnLogout.visibility = View.VISIBLE

        binding.layoutTopbar.btnLogout.setOnClickListener {
            FastSave.getInstance().saveBoolean(IS_USER_LOGIN, false)
            findNavController().navigate(R.id.action_weatherFragment_to_boardingFragment)
        }

        binding.layoutTopbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Invoking the api call directly when opening the fragment
        viewModel.getWeatherData(WeatherRequest(lat, lon, units, appId))

        // handling the response here
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.mWeatherState.collect { weatherResponse ->
                when (weatherResponse) {
                    is ApiResources.Loading -> {
                        // show progress
                        showLoading()
                    }

                    is ApiResources.Success -> {
                        // hide progress
                        hideLoading()
                        if (weatherResponse.data != null) {
                            activity?.runOnUiThread {
                                binding.tvTemp.text =
                                    "Temp :- ${weatherResponse.data?.current?.temp}"
                                binding.tvWeatherOne.text =
                                    "Weather :- ${weatherResponse.data?.current?.weather?.get(0)?.main}"
                                binding.tvWeatherTwo.text =
                                    "Weather :- ${weatherResponse.data?.current?.weather?.get(0)?.description}"
                                binding.tvHumidity.text =
                                    "Humidity :- ${weatherResponse.data?.current?.humidity}"
                                binding.tvWind.text =
                                    "Wind Speed :- ${weatherResponse.data?.current?.windSpeed}"
                            }
                        }
                    }

                    is ApiResources.Error -> {
                        Log.d("TAG", "api response error :- ${weatherResponse.message}")
                    }

                    is ApiResources.Unknown -> {
                        Log.d("TAG", "api response error :- ${weatherResponse.message}")
                    }
                }
            }
        }

        return view
    }

    /**
     * @Note :- We can define this in the base fragment after creating the base fragment if this function need to be invoke for
     * many times.
     *
     * A method to show progress dialog on center of screen during api calls
     */
    private fun showLoading() {
        if (progressDialog == null) {
            progressDialog = Dialog(requireContext())
        } else {
            return
        }
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.app_loading_dialog, null, false)
        val imageView1 = view.findViewById<ImageView>(R.id.imageView2)
        val a1 = AnimationUtils.loadAnimation(requireContext(), R.anim.progress_anim)
        a1.duration = 1500
        imageView1.startAnimation(a1)

        progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog?.setContentView(view)
        val window = progressDialog?.window
        window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                android.R.color.transparent
            )
        )
        progressDialog?.setCancelable(false)
        progressDialog?.setCanceledOnTouchOutside(false)
        progressDialog?.show()
    }

    /**
     * A method to hide the progress dialog
     */
    fun hideLoading() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }
}