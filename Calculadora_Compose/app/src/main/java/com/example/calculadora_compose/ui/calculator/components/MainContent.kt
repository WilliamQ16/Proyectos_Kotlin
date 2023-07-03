package com.example.calculadora_compose.ui.calculator.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.calculadora_compose.ui.calculator.utils.sp

@Composable
fun MainContent(
    inputText: String,
    outputText: String,
    height: Dp
){
 Box(
     modifier = Modifier
         .fillMaxWidth()
         .height(height),
     contentAlignment = Alignment.BottomEnd
 ) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        BasicTextField(
            value = inputText,
            onValueChange = {},
            enabled = false,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = height.sp() * 0.1,
                textAlign = TextAlign.End,
                color = Color.White
            )
        )
        Text(
            text = outputText,
            style = TextStyle(
                color = Color.White,
                fontSize = height.sp() * 0.27
            ),
            softWrap = false,
            maxLines = 1
        )
    }
 }
}