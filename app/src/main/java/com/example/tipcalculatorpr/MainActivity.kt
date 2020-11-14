package com.example.tipcalculatorpr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculatorpr.databinding.ActivityMainBinding
import com.example.tipcalculatorpr.R
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    //data binding - latwiejszy dostep do views
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //ustawienie glownego widoku
        setContentView(binding.root)
        //click listener dla buttonu liczacego napiwek
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    //funkcja obliczanie napiwku
    private fun calculateTip() {
        //wyciagniecie wartosci dziesietnej z edittext
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        //obslugo wartosci null i zero - wyswietlenie wtedy 0
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        //wybor zaznaczonego procentu do naliczenia napiwku
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        //obliczanie napiwku
        var tip = tipPercentage * cost

        //zaokraglanie napiwku w gore jesli zaznaczona taka opcje
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }

        //wyswietlenie wartosci napiwku
        displayTip(tip)
    }

    //formatowanie napiwku do waluty oraz wyswietlenie go
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}