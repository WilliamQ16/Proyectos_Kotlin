package com.example.prototipo.pages

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

// Vista principal
// Como tal esta vista, es más principal para la tienda ya que en esta se encuentran todos los productos,
// estos productos estan listados por categorías, favoritos, destacados, en oferta, entre otros.
@Composable
fun Page_Principal(){
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        CarouselCardPrincipal()
        HomeSection2(title = R.string.cat_item) {
            InicioRow2()
        }
        HomeSection2(title = R.string.cat_fav) {
            LazyRow(
                modifier = Modifier.padding(10.dp)
            ){
                items(favoritosCollectionsData) { item ->
                    CardDataGridItem(drawable = item.drawable, text = item.text)
                }

            }
        }
        HomeSection2(title = R.string.cat_pop) {
            LazyRow(
                modifier = Modifier.padding(10.dp)
            ){
                items(popularesCollectionsData) { item ->
                    CardDataGridItem(drawable = item.drawable, text = item.text)
                }

            }
        }
        HomeSection2(title = R.string.cat_carnicos) {
            LazyRow(
                modifier = Modifier.padding(10.dp)
            ){
                items(carnicosCollectionsData) { item ->
                    CardDataGridItem(drawable = item.drawable, text = item.text)
                }

            }
        }
        HomeSection2(title = R.string.cat_lacteos) {
            LazyRow(
                modifier = Modifier.padding(10.dp)
            ){
                items(lacteosCollectionsData) { item ->
                    CardDataGridItem(drawable = item.drawable, text = item.text)
                }

            }
        }
        HomeSection2(title = R.string.cat_frutas) {
            LazyRow(
                modifier = Modifier.padding(10.dp)
            ){
                items(frutasCollectionsData) { item ->
                    CardDataGridItem(drawable = item.drawable, text = item.text)
                }

            }
        }
        HomeSection2(title = R.string.cat_flores) {
            LazyRow(
                modifier = Modifier.padding(10.dp)
            ){
                items(floresCollectionsData) { item ->
                    CardDataGridItem(drawable = item.drawable, text = item.text)
                }

            }
        }
        HomeSection2(title = R.string.cat_mas) {
            LazyRow(
                modifier = Modifier.padding(10.dp)
            ){
                items(masCollectionsData) { item ->
                    CardDataGridItem(drawable = item.drawable, text = item.text)
                }

            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselCardPrincipal(){
    val pagerState = rememberPagerState(initialPage = 2)
    val sliderList = listOf(
        R.drawable.pr_1,
        R.drawable.pr_2,
        R.drawable.pr_3,
        R.drawable.pr_4,
        R.drawable.pr_5
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
fun HomeSection2(
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

// Items en forma circular donde se encuentra cada una de las categorías de los productos a vender, en caso de que todos los items ocupen toda la pantalla en su diseño
// tiene un scroll horizontal el cual permite ver todos estos sin que haya desbordamiento.
@Composable
fun InicioRow2(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(inicio2CollectionsData) { item ->
            Inicio2Element(item.drawable, item.text)
        }
    }
}

@Composable
fun Inicio2Element(
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