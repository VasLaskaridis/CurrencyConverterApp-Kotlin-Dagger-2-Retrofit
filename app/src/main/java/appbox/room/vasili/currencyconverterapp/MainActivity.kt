package appbox.room.vasili.currencyconverterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import appbox.room.vasili.currencyconverterapp.adapter.Adapter
import appbox.room.vasili.currencyconverterapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private var startCurrency="EUR"

    @Inject
    lateinit var currencyList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id=resources.getIdentifier("drawable/_eur",null,getPackageName())
        binding.imgviewMainCurrencyImage.setImageResource(id)
        val adapter: Adapter = Adapter(currencyList)
        val layoutManager_newTasks = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(layoutManager_newTasks)
        adapter.setCurrencyClickListener(object:Adapter.onCurrencyClickListener{
            override fun onCurrencyClick(position: Int) {
                startCurrency=currencyList[position]
                binding.txtviewMainCurrencyName.text=startCurrency
                val id=resources.getIdentifier("drawable/_"+currencyList[position].lowercase(),null,getPackageName())
                binding.imgviewMainCurrencyImage.setImageResource(id)
            }
        })
        binding.recyclerView.adapter = adapter
        binding.btnConvert.setOnClickListener{
            viewModel.convert(
                binding.inputtxtAmount.text.toString(),
                startCurrency
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.convert(
                binding.inputtxtAmount.text.toString(),
                startCurrency
            )
            viewModel.conversionObserver().collect { state ->
                when (state) {
                    is MainActivityViewModel.StateOfData.Success -> {
                        binding.progressBar.isVisible = false
                        binding.btnConvert.isVisible = true
                        adapter.setCurrencyData(viewModel.getConvertedCurrency())


                    }
                    is MainActivityViewModel.StateOfData.Error -> {
                        binding.progressBar.isVisible = false
                        binding.btnConvert.isVisible = true

                    }
                    is MainActivityViewModel.StateOfData.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.btnConvert.isVisible = false

                    }
                }
            }
        }
    }
}