package com.example.prototipo.pages

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prototipo.R

// Vista de contenidos
// En esta vista se presentara información sobre el sena por medio de cards desplegables, estos datos se llama directamente
// a la función de esta card entonces por esto mismo se debe llamar a esta card dependiendo la cantidad de datos que se tiene.
@Composable
fun Page_Contenidos(){
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SENA CBA",
            fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 60.sp
        )
        Contenido(R.drawable.con_1, R.string.titulo_qs, R.string.qs)
        Contenido(R.drawable.con_2, R.string.titulo_escudo, R.string.escudo)
        Contenido(R.drawable.con_3, R.string.titulo_logo, R.string.logo)
        Contenido(R.drawable.con_4, R.string.titulo_historia, R.string.historia)
    }
}

// Card donde estaran los datos.
@Composable
private fun Contenido(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    @StringRes description: Int
) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(drawable, text, description)
    }
}

// En esta función la card tiene la animación para desplegarse y a su vez los datos se acomodan para
// que sean recibidos visualmente por parte del usuario de mejor manera.
@Composable
private fun CardContent(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    @StringRes description: Int
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(text), style = MaterialTheme.typography.h2.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = stringResource(description),
                )
                Image(
                    painter = painterResource(drawable),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = CircleShape)
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}