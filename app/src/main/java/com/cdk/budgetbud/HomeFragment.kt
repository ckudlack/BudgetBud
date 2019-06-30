package com.cdk.budgetbud

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import com.airbnb.mvrx.activityViewModel
import com.cdk.budgetbud.mvrx.simpleController
import com.cdk.budgetbud.view.commonTextView
import com.cdk.budgetbud.viewmodel.BudgetItemViewModel
import com.cdk.budgetbud.viewmodel.ExchangeRateViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private val exchangeRateViewModel: ExchangeRateViewModel by activityViewModel()
    private val budgetItemViewModel: BudgetItemViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        record_item_button.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Record Value")
            startActivityForResult(intent, 1111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && null != data) {
            val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val speechResult = results.first()
            budgetItemViewModel.saveBudgetItem(speechResult)
        }
    }

    override fun epoxyController() = simpleController(budgetItemViewModel) { budgetItemState ->
        budgetItemState.budgetItems.forEachIndexed { index, budgetItem ->
            commonTextView {
                id("id_$index")
                body("${budgetItem.name} : ${budgetItem.cost}")
            }
        }
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