package com.berkkanb.creditcardform.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultPlaceHolder(
    label:String,
    actualValue:String
) {
    if (actualValue.isEmpty()){
        Text(text = label, color = Color.LightGray)
    }
}