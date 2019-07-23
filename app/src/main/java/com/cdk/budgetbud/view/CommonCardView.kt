package com.cdk.budgetbud.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StyleRes
import androidx.cardview.widget.CardView
import androidx.core.widget.TextViewCompat
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.cdk.budgetbud.R
import com.cdk.budgetbud.mvrx.KeyedListener
import kotlinx.android.synthetic.main.card_view.view.*
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
        card_body.text = body
    }

    @TextProp
    fun setSubtext(subtext: CharSequence) {
        card_subtext.text = subtext
    }

    @ModelProp
    fun setBodyTextAppearance(@StyleRes style: Int?) =
        style?.let { it }?.takeIf { it > 0 }?.let {
            TextViewCompat.setTextAppearance(card_body, it)
        }

    @ModelProp
    fun setSubTextAppearance(@StyleRes style: Int?) =
        style?.let { it }?.takeIf { it > 0 }?.let {
            TextViewCompat.setTextAppearance(card_body, it)
        }

    @ModelProp(ModelProp.Option.NullOnRecycle)
    fun setOnClickListener(listener: KeyedListener<*, OnClickListener>?) {
        setOnClickListener(listener?.callback)
    }
}