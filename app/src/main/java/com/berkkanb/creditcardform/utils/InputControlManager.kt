package com.berkkanb.creditcardform.utils

import androidx.core.text.isDigitsOnly

sealed interface InputControlType {
    data class CardNumberInput(val input: String, val action: (cardNumber: String) -> Unit) :
        InputControlType

    data class HolderNameInput(val input: String, val action: (holderName: String) -> Unit) : InputControlType
    data class ExpiryDateInput(val input: String, val action: (expiryDate: String) -> Unit) : InputControlType
    data class SecurityNumberInput(val input: String, val action: (securityNumber: String) -> Unit) : InputControlType
}

fun handleCardInput(type: InputControlType) {
    when (type) {
        is InputControlType.CardNumberInput -> {
            if (type.input.length <= 16 && type.input.isDigitsOnly()){
                type.action.invoke(type.input)
            }
        }

        is InputControlType.HolderNameInput -> {
            type.action.invoke(type.input)
        }

        is InputControlType.ExpiryDateInput -> {
            if (type.input.isDigitsOnly() ){
                type.action.invoke(type.input)
            }
        }

        is InputControlType.SecurityNumberInput -> TODO()
    }
}