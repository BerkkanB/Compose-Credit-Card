package com.berkkanb.creditcardform.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Stable
class CardState {

    private val cardNumberState = mutableStateOf("")
    private val expiryDateState = mutableStateOf("")
    private val cardHolderNameState = mutableStateOf("")
    private val securityNumberState = mutableStateOf("")

    val cardNumber:String get() = cardNumberState.value
    val expiryDate:String get() = expiryDateState.value
    val cardHolderName:String get() = cardHolderNameState.value
    val securityNumber:String get() = securityNumberState.value

    fun setCardNumber(value:String) {
        cardNumberState.value = value
    }

    fun setExpiryDate(value:String) {
        expiryDateState.value = value
    }

    fun setCardHolderName(value:String) {
        cardHolderNameState.value = value
    }

    fun setSecurityNumber(value:String) {
        securityNumberState.value = value
    }

}

@Composable
fun rememberCardState():CardState {
    return remember { CardState() }
}