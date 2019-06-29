package com.cdk.budgetbud

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import com.airbnb.mvrx.activityViewModel
import com.cdk.budgetbud.mvrx.simpleController
import com.cdk.budgetbud.viewmodel.ExchangeRateViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private val exchangeRateViewModel: ExchangeRateViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*record_speech_button.setOnClickListener {

            processValue("Spent 10000 pesos on lunch")


            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Record Value")
            startActivityForResult(intent, 1111)
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && null != data) {
            val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val speechResult = results.first()
            speech_result.text = speechResult
        }
    }


    private fun processValue(text: String) {
        val words = text.split(' ')
        var amount: Int? = null
        var currency: String? = null
        var article: String? = null
        var subject: String? = null

        // "Spent ten thousand pesos on dinner"
        // "[Trigger] [amount] [subject]"
        if (words.first().toLowerCase() == "spent" || words.first().toLowerCase() == "payed") {

            words.forEachIndexed { index, word ->
                when (index) {
                    1 -> word.toIntOrNull()?.let { amount = it }
                    2 -> currency = word
                    3 -> article = word
                    4 -> subject = word
                }
            }
        }

        speech_result.text = "You just spent $amount $currency on $subject"

    }

    override fun epoxyController() = simpleController(exchangeRateViewModel) { exchangeRateState ->

    }

    companion object {
        private val tensNames =
            mapOf(
                "" to 0,
                "ten" to 10,
                "twenty" to 20,
                "thirty" to 30,
                "forty" to 40,
                "fifty" to 50,
                "sixty" to 60,
                "seventy" to 70,
                "eighty" to 80,
                "ninety" to 90
            )

        private val numNames = mapOf(
            "" to 0,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
            "ten" to 10,
            "eleven" to 11,
            "twelve" to 12,
            "thirteen" to 13,
            "fourteen" to 14,
            "fifteen" to 15,
            "sixteen" to 16,
            "seventeen" to 17,
            "eighteen" to 18,
            "nineteen" to 19
        )
    }

    private val bigNumNames = mapOf("hundred" to 100, "thousand" to 1000, "million" to 1000000, "billion" to 1000000000)

}