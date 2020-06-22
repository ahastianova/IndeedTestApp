package com.atruskova.indeedtestapp.helper.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.atruskova.indeedtestapp.R

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.getString() : String {
    return this.text.toString()
}
fun ImageButton.changeFavoriteState(isFavorite: Boolean) {
    setImageResource(
        if (isFavorite) {
            R.drawable.ic_baseline_favorite_24
        } else {
            R.drawable.ic_baseline_favorite_border_24
        }
    )
}
