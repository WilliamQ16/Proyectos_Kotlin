package com.example.prototipo.pages

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.prototipo.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import java.util.Locale
import kotlin.math.absoluteValue

// Vista Inicio
// En esta vista estara la página inicial en la cual el usuario al abrir la aplicación se encontratra con esta.
// La vista en cuestión solo contendra información acerca del CBA como noticias y eventos relacionados con el centro.
@Composable
fun Page_Inicio(){
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        CarouselCard()
        HomeSection(title = R.string.inicio_centro) {
            InicioRow()
        }
        HomeSection(title = R.string.destacado) {
            DestacadoRow()
        }
        HomeSection(title = R.string.noticia) {
            NoticiaRow()
        }
        HomeSection(title = R.string.evento) {
            EventoRow()
        }
    }
}

// Carrusel amimado de la parte superior
@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselCard(){
    val pagerState = rememberPagerState(initialPage = 2)
    val sliderList = listOf(
        R.drawable.ic_c1_1,
        R.drawable.ic_c1_2,
        R.drawable.ic_c1_3,
        R.drawable.ic_c1_4,
        R.drawable.ic_c1_5
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            HorizontalPager(
                count = sliderList.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 65.dp),
                modifier = Modifier
                    .height(200.dp)
                    .weight(1f)
            ) { page ->
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.50f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                ) {
                    Image(painter = painterResource(id = sliderList[page]), contentDescription = null)
                }
            }
        }
    }
}

// Los HomeSections le da un encabezado a cada parte que conforma esta vista.
@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(title).uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.h2,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

// Items en forma circular donde se presenta cada apartado que contiene el aplicativo, en caso de que todos los items ocupen toda la pantalla en su diseño
// tiene un scroll horizontal el cual permite ver todos estos sin que haya desbordamiento.
@Composable
fun InicioRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(inicioCollectionsData) { item ->
            InicioElement(item.drawable, item.text)
        }
    }
}

@Composable
fun InicioElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.h3,
            modifier = Modifier.paddingFromBaseline(
                top = 24.dp, bottom = 8.dp
            )
        )
    }
}

// Card desplegable donde estara la información más importante del centro, cuando el usuario desplega la card
// vera toda la información completa del item que se desea conocer, este apartado tendra un scroll horizontal
// el cual al momento de copar toda la pantalla del dispositivo con la información a presentar no habra un desbordamiento.
@Composable
fun DestacadoRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(destacadoCollectionsData) { item ->
            DestacadoContent(item.drawable, item.text, item.description)
        }
    }
}

// Card desplegable donde estara todos los anuncios del centro, cuando el usuario desplega la card
// vera toda la información completa del item que se desea conocer, este apartado tendra un scroll horizontal
// el cual al momento de copar toda la pantalla del dispositivo con la información a presentar no habra un desbordamiento.

@Composable
fun NoticiaRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(noticiaCollectionsData) { item ->
            NoticiaContent(item.drawable, item.text, item.description)
        }
    }
}

// Imagenes en forma circulares donde se dara información de los eventos del centro, igualmente tienen un scroll
// horizontal que al momento de haber mas información que cope toda la pantalla estos componentes no se desbordaran.
@Composable
fun EventoRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(eventoCollectionsData) { item ->
            EventoCard(item.drawable)
        }
    }
}

// Diseño de la card correspondiente a la función DestacadoRow.
@Composable
private fun DestacadoContent(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    @StringRes decription: Int,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        border = BorderStroke(1.dp, Color.Gray),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { expanded = !expanded }, modifier = Modifier.align(
                Alignment.End)) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
            Text(text = stringResource(text), style = MaterialTheme.typography.h2)
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .size(300.dp)
            )
            Spacer(Modifier.height(16.dp))
            if (expanded) {
                Text(
                    text = stringResource(decription),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

// Diseño de la card correspondiente a la función NoticiaRow.
@Composable
private fun NoticiaContent(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    @StringRes decription: Int,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        border = BorderStroke(1.dp, Color.Gray),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { expanded = !expanded }, modifier = Modifier.align(
                Alignment.End)) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
            Text(text = stringResource(text), style = MaterialTheme.typography.h2)
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .size(300.dp)
            )
            Spacer(Modifier.height(16.dp))
            if (expanded) {
                Text(
                    text = stringResource(decription),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

// Diseño de la de la imagen circular correspondiente a la función EventoRow.
@Composable
fun EventoCard(
    @DrawableRes drawable: Int
){
    Image(painter = painterResource(id = drawable), contentDescription = null, modifier = Modifier
        .padding(12.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ).size(400.dp).clip(shape = CircleShape))
}