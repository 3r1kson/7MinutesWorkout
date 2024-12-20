package com.example.a7minutesworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.title = "Calculate your BMI"

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            if (validateMetricUnit() && binding?.tilMetricUnitHeight?.visibility == View.VISIBLE) {
                val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue*heightValue)

                displayBMIResults(bmi)

            } else if (validateUsUnits()){
                val usUnitHeighValueFeet: String = binding?.etFeetUnitHeight?.text.toString()
                val usUnitHeighValueInch: String = binding?.etInchtUnitHeight?.text.toString()
                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val usTotalInches= usUnitHeighValueInch.toFloat() + (usUnitHeighValueFeet.toFloat() * 12)

                val bmi = (weightValue / (usTotalInches*usTotalInches) ) * 703

                displayBMIResults(bmi)
            }
        }

        binding?.rbMetricUnits?.setOnClickListener {
            binding?.etMetricUnitWeight?.hint = "Weight in kg"
            binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
            binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
            binding?.llUsUnitHeight?.visibility = View.INVISIBLE

        }

        binding?.rbUsUnits?.setOnClickListener {
            binding?.etMetricUnitWeight?.hint = "Weight in pounds"
            binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
            binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
            binding?.llUsUnitHeight?.visibility = View.VISIBLE
        }
    }

    private fun displayBMIResults(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnit(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etMetricUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etFeetUnitHeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etInchtUnitHeight?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }
}