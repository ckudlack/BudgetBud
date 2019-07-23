package com.cdk.budgetbud.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.cdk.budgetbud.R
import org.jetbrains.anko.dimen
import org.jetbrains.anko.dip

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true)
class CommonCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.card_view, this)
        cardElevation = dip(2).toFloat()
        radius = dimen(R.dimen.margin_tiny).toFloat()
    }

    @TextProp
    fun setBody(body: CharSequence) {

    }

    @TextProp
    fun setSubtext(body: CharSequence) {
        
    }
}