package com.berkkanb.creditcardform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.rotationMatrix
import com.berkkanb.creditcardform.components.DecorationBox
import com.berkkanb.creditcardform.components.DefaultPlaceHolder
import com.berkkanb.creditcardform.state.CardState
import com.berkkanb.creditcardform.state.rememberCardState
import com.berkkanb.creditcardform.ui.theme.CreditCardFormTheme
import com.berkkanb.creditcardform.utils.InputControlType
import com.berkkanb.creditcardform.utils.handleCardInput

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val myCardState = rememberCardState()

            CreditCardFormTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Column() {
                            CreditCard(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                cardState = myCardState
                            )
                            Text(text = myCardState.cardHolderName)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreditCard(
    modifier: Modifier,
    cardState: CardState
) {

    var isCardFlipped by remember {
        mutableStateOf(false)
    }

    val cardFlipState by animateFloatAsState(
        targetValue = if (isCardFlipped) 180f else 0f,
        animationSpec = tween(1000)
    )

    val changeLayout = remember {
        derivedStateOf {
            cardFlipState > 90f
        }
    }

    Card(
        modifier = modifier
            .aspectRatio(8560 / 5398f)
            .graphicsLayer {
                rotationY = cardFlipState
            },
        colors = CardDefaults.cardColors()
    ) {
        if (changeLayout.value.not()) {
            CreditCardFrontFace(cardState = cardState, setIsCardFlipped = { isCardFlipped = it })
        } else {
            Text(modifier = Modifier.graphicsLayer {
                scaleX = -1f
            }, text = "Arka Yüz")
        }
    }
}

@Composable
fun ColumnScope.CreditCardFrontFace(
    cardState: CardState,
    setIsCardFlipped: (state: Boolean) -> Unit
) {

    val numberLen = 16

    val focusManager = LocalFocusManager.current

    Row(modifier = Modifier.weight(1f)) {

    }
    BasicTextField(
        modifier = Modifier
            .weight(1f, true)
            .fillMaxWidth(),
        value = cardState.cardNumber,
        onValueChange = { input ->
            handleCardInput(InputControlType.CardNumberInput(input) { cardState.setCardNumber(it) })
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        decorationBox = { DecorationBox(cardState.cardNumber, numberLen) })
    Row(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(text = "Card Holder Name")
            BasicTextField(
                value = cardState.cardHolderName,
                onValueChange = { input ->
                    handleCardInput(InputControlType.HolderNameInput(input) {
                        cardState.setCardHolderName(
                            it
                        )
                    })
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                decorationBox = {
                    DefaultPlaceHolder(
                        label = "Berkkan Butun",
                        actualValue = cardState.cardHolderName
                    )
                    it()
                }
            )
        }
        Column() {
            Text(text = "Exp. Date")
            BasicTextField(
                value = cardState.expiryDate,
                onValueChange = { input ->
                    handleCardInput(InputControlType.ExpiryDateInput(input) {
                        cardState.setExpiryDate(
                            it
                        )
                    })

                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    setIsCardFlipped.invoke(true)
                }),
                decorationBox = {
                    DefaultPlaceHolder(label = "12/25", actualValue = cardState.expiryDate)
                    it()
                })
        }
    }
}