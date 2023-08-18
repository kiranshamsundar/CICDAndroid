package com.shamsuk.retirementcalculator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.shamsuk.retirementcalculator.databinding.ActivityMainBinding

class MainActivityXml : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppCenter.start(application, "a7d884c8-8e04-4fdd-b5db-d83f3107109e", Analytics::class.java, Crashes::class.java);

        binding.calculateButton.setOnClickListener {
            // Crashes.generateTestCrash()
            try {
                val interestRate = binding.interestEditText.text.toString().toFloat()
                val currentAge = binding.ageEditText.text.toString().toInt()
                val retirementAge = binding.retirementEditText.text.toString().toInt()
//                val monthly = binding.monthlySavingsEditText.text.toString().toFloat()
                val monthly = "34.89".toFloat()
                val current = binding.currentEditText.text.toString().toFloat()
                val resultTextView=binding.resultTextView

                val properties:HashMap<String, String> = HashMap()
                properties["interest_rate"] = interestRate.toString()
                properties["current_age"] = currentAge.toString()
                properties["retirement_age"] = retirementAge.toString()
                properties["monthly_savings"] = monthly.toString()
                properties["current_savings"] = current.toString()

                if (interestRate <= 0) {
                    Analytics.trackEvent("wrong_interest_rate", properties)
                }
                if (retirementAge <= currentAge) {
                    Log.i("Analytics","wrong age entered")
                    Analytics.trackEvent("wrong_age", properties)
                }

                resultTextView.text="At the current rate of $interestRate%, with your current monthly savings you will have \$1,000,000 by 65."
            } catch(ex: Exception){
                Analytics.trackEvent(ex.message)
            }
        }
    }
}
