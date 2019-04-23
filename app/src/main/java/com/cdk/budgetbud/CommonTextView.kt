package com.cdk.budgetbud

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import org.jetbrains.anko.dimen

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CommonTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setTextSize(R.dimen.text_size_normal)
    }

    @ModelProp(group = "body")
    fun setBody(body: CharSequence) {
        text = body
    }

    @ModelProp(group = "body")
    fun setBody(format: StringFormat) {
        text = resources.getString(format.res, format.body.toString())
    }

    @ModelProp(group = "body")
    fun setBody(res: Int) {
        text = resources.getString(res)
    }

    @ModelProp(group = "body")
    fun setHtmlFormattedBody(body: CharSequence) {
        text = Html.fromHtml(body.toString())
    }

    @ModelProp(group = "body")
    fun setHtmlFormattedBody(format: StringFormat) {
        text = Html.fromHtml(resources.getString(format.res, format.body.toString()))
    }

    @ModelProp(group = "body")
    fun setHtmlFormattedBody(res: Int) {
        text = Html.fromHtml(resources.getString(res))
    }

    @CallbackProp
    fun setClickListener(listener: View.OnClickListener?) {
        isClickable = true
        setOnClickListener(listener)
    }

    @ModelProp
    fun setPadding(padding: Padding?) =
        padding?.let {
            setPadding(
                it.leftResId.takeIf { it > 0 }?.let { dimen(it) } ?: 0,
                it.topResId.takeIf { it > 0 }?.let { dimen(it) } ?: 0,
                it.rightResId.takeIf { it > 0 }?.let { dimen(it) } ?: 0,
                it.bottomResId.takeIf { it > 0 }?.let { dimen(it) } ?: 0
            )
        }

    @ModelProp
    fun setTextAppearance(@StyleRes style: Int?) =
        style?.let { it }?.takeIf { it > 0 }?.let {
            TextViewCompat.setTextAppearance(this, it)
        }

    @ModelProp
    fun setTextSize(@DimenRes dimension: Int?) =
        dimension?.let {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen(it).toFloat())
        }

    @ModelProp
    fun setColor(@ColorRes res: Int) {
        setTextColor(ContextCompat.getColor(context, if (res == 0) android.R.color.black else res))
    }

    @ModelProp
    fun setTextGravity(gravity: Int) {
        setGravity(gravity)
    }

    @ModelProp
    fun setBackgroundDrawableRes(@DrawableRes drawableRes: Int?) {
        drawableRes?.let { setBackgroundResource(drawableRes) }
    }

    @ModelProp
    fun setBackgroundColorRes(@ColorRes colorRes: Int?) {
        colorRes?.let { setBackgroundColor(resources.getColor(colorRes)) }
    }

    companion object {
        const val DEFAULT_STRIKE_THROUGH = false
    }
}

data class StringFormat(val res: Int, val body: CharSequence)