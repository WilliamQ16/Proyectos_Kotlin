package com.example.prototipo.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.prototipo.R

// Vista de ver más
// los productos de cada categoría estan en una lista en el archivo Page_Items y en estos tipos de vistas
// se llaman estas listas por medio de un builder y se envian a una carta generica la cual esta también en
// el archivo Page_Items, esta carta va acomodando la información de cada producto dentro ella.
@Composable
fun Page_Ver_Mas(){
    Column() {
        Spacer(Modifier.height(16.dp))
        SearchMas(Modifier.padding(horizontal = 16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(10.dp)
        ){
            items(masCollectionsData) { item ->
                CardDataGridItem(drawable = item.drawable, text = item.text)
            }

        }
    }
}

// Input para la busqueda de productos.
@Composable
fun SearchMas(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(R.string.buscar_mas))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}