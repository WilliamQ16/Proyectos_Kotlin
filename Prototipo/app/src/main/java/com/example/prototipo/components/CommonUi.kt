package com.example.prototipo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.prototipo.R

// Diseño para el subtotal de los productos en la facturación.
@Composable
fun FormattedPriceLabel(subtotal: String, modifier: Modifier = Modifier){
    Column() {
        Text(
            text = stringResource(R.string.subtotal_price, subtotal),
            modifier = modifier,
            style = MaterialTheme.typography.h1
        )
    }
}