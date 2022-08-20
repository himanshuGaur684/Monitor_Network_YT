package com.gaur.monitornetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gaur.monitornetwork.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding:ActivityMainBinding?=null
    private val binding:ActivityMainBinding
    get() = _binding!!

    private val viewModel : NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {

            viewModel.isConnected.collectLatest {
                if(it){
                    binding.ivWifi.setImageResource(R.drawable.ic_baseline_wifi_24)
                    binding.tvInternet.text = "Internet Available"
                }else{
                    binding.ivWifi.setImageResource(R.drawable.ic_baseline_wifi_off_24)
                    binding.tvInternet.text = "Internet Not Available"
                }
            }
        }




    }
}