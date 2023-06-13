package com.berkkanb.creditcardform.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DecorationBox(
    input: String,
    numberLen: Int
) {
    Row() {
        for (i in 0 until numberLen) {
            val char = if (i < input.length) input[i].toString() else ""
            Column(modifier = Modifier.weight(4f), horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedContent(targetState = char, transitionSpec = {
                    ContentTransform(
                        targetContentEnter = slideInVertically(initialOffsetY = {it/2}),
                        initialContentExit = slideOutVertically(targetOffsetY = { it / 2 }),
                        sizeTransform = null
                    )
                }) { TextValue ->
                    Text(text = TextValue)
                }
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black))
            }
            if (i != numberLen.minus(1)){
                val spacerWeight = if (i % 4 == 3) 5f else 1f
                Spacer(modifier = Modifier.weight(spacerWeight))
            }
        }
    }
}

