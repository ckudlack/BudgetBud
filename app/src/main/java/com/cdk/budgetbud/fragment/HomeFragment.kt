package com.cdk.budgetbud.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import com.airbnb.mvrx.activityViewModel
import com.cdk.budgetbud.BaseFragment
import com.cdk.budgetbud.R
import com.cdk.budgetbud.mvrx.KeyedListener
import com.cdk.budgetbud.mvrx.simpleController
import com.cdk.budgetbud.view.commonCardView
import com.cdk.budgetbud.view.commonTextView
import com.cdk.budgetbud.viewmodel.BudgetItemViewModel
import com.cdk.budgetbud.viewmodel.BudgetViewType
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
        toolbar.visibility = View.GONE

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
            when (budgetItem.type) {
                BudgetViewType.HEADER -> commonTextView {
                    id("id_$index")
                    body(budgetItem.value)
                    textAppearance(R.style.BudgetItemHeader)
                }
                BudgetViewType.ITEM -> commonCardView {
                    id("id_$index")
                    body(budgetItem.value)
                    bodyTextAppearance(R.style.BudgetItem)
                    onClickListener(
                        KeyedListener.create(
                            budgetItem,
                            View.OnClickListener {
                                navigateTo(
                                    R.id.action_homeFragment_to_budgetItemFragment,
                                    Bundle().apply { putInt(ID, budgetItem.id!!) })
                            })
                    )
                }
            }
        }
    }

    companion object {
        const val ID = "id"
    }
}