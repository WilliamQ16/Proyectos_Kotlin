package com.example.calculadora_compose.ui.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import com.example.calculadora_compose.ui.calculator.CalculatorEvent
import com.example.calculadora_compose.ui.calculator.utils.isNumber
import com.example.calculadora_compose.ui.calculator.utils.sp

val symbolsList = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "-",
    "1", "2", "3", "+",
    "0", ".", "◄", "="
)

@Composable
fun Panel(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    onEvent: (CalculatorEvent) -> Unit
){
    Box {
        val size = (height / 5) - 10.dp
        val widthButton = (width / 4) - 6.75.dp
        
        LazyColumn(
            modifier = modifier.width(width)
        ){
            items(items = symbolsList.chunked(4)){rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    for (text in rowItems){
                        ContentButton(
                            text = text,
                            size = size,
                            width = widthButton,
                            onEvent = { onEvent(it) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun ContentButton(
    text: String,
    size: Dp,
    width: Dp,
    onEvent: (CalculatorEvent) -> Unit
){
    Surface(
        shape = CircleShape,
        modifier = Modifier
            .width(width)
            .clickable {
                when (text) {
                    "C" -> onEvent(CalculatorEvent.AllClear)
                    "◄" -> onEvent(CalculatorEvent.BackSpace)
                    "=" -> onEvent(CalculatorEvent.Calculate)
                    else -> onEvent(CalculatorEvent.Write(text))
                }
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size)
                .background(if (isNumber(text)) Color(0xFFEDE7F6) else Color(0xFF673AB7))
        ) {
            Text(
                text = text,
                color = if(isNumber(text)) Color(0xFF9C27B0) else Color(0xFFFFFFFF),
                style = TextStyle(
                    fontSize = size.sp() / 3
                )
            )
        }
    }
}