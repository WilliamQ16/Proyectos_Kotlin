package com.example.prototipo.pages

import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prototipo.R
import com.example.prototipo.components.CreateChannelNotification
import com.example.prototipo.components.notificacionExtensa
import com.example.prototipo.components.notificacionImagen
import com.example.prototipo.components.notificacionSencilla
import com.example.prototipo.ui.theme.Green500
import com.google.firebase.auth.FirebaseAuth

// Clases las cuales son bases para las listas que recibiran los productos o los datos para las vistas de información
// contenidos, inicio, etc.
data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

data class DrawableStringPair2(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int,
    @StringRes val description: Int
)

data class DrawableStringPair3(
    @DrawableRes val drawable: Int
)

// Lista de productos carnicos.
val carnicosCollectionsData = listOf(
    R.drawable.huevo_tipob to R.string.tipoB,
    R.drawable.huevo_tipoa to R.string.tipoA,
    R.drawable.huevo_tipoaa to R.string.tipoAA,
    R.drawable.huevo_tipoaaa to R.string.tipoAAA,
    R.drawable.carnicos_cerdo to R.string.cerdo,
    R.drawable.carnicos_res to R.string.res,
    R.drawable.carnicos_pollo to R.string.pollo,
    R.drawable.carnicos_pechuga to R.string.pechuga,
    R.drawable.carnicos_colombina to R.string.colombina,
    R.drawable.carnicos_pernil to R.string.pernil,
    R.drawable.carnicos_lomo to R.string.lomo,
    R.drawable.carnicos_chicharron to R.string.chicharron,
    R.drawable.carnicos_pata to R.string.pata,
    R.drawable.carnicos_jamon to R.string.jamon,
    R.drawable.carnicos_cabeza to R.string.cabeza,
    R.drawable.carnicos_salchichon to R.string.salchichon,
    R.drawable.carnicos_lechon to R.string.lechon,
    R.drawable.carnicos_conejo to R.string.conejo
).map { DrawableStringPair(it.first, it.second) }

// Lista de productos flores.
val floresCollectionsData = listOf(
    R.drawable.flores_girasol to R.string.girasol,
    R.drawable.flores_astromelia to R.string.astromelia,
    R.drawable.flores_tulipanes to R.string.tulipanes,
    R.drawable.flores_clavel to R.string.clavel,
    R.drawable.flores_anturio to R.string.anturio,
    R.drawable.flores_pensamiento to R.string.pensamiento,
    R.drawable.flores_rosa to R.string.rosa,
    R.drawable.flores_cartucho to R.string.cartucho,
    R.drawable.flores_lirio to R.string.lirio
).map { DrawableStringPair(it.first, it.second) }

// Lista de productos frutas y verduras.
val frutasCollectionsData = listOf(
    R.drawable.fruta_papa to R.string.papa,
    R.drawable.fruta_mango to R.string.mango,
    R.drawable.fruta_naranja to R.string.naranja,
    R.drawable.fruta_fresa to R.string.fresa,
    R.drawable.fruta_limon to R.string.limon,
    R.drawable.fruta_platano to R.string.platano,
    R.drawable.fruta_arveja to R.string.arveja,
    R.drawable.fruta_manzana to R.string.manzana,
    R.drawable.fruta_arandanos to R.string.arandanos,
    R.drawable.fruta_frijol to R.string.frijol,
    R.drawable.fruta_banano to R.string.banano,
    R.drawable.fruta_cilantro to R.string.cilantro
).map { DrawableStringPair(it.first, it.second) }

// Lista de productos lacteos.
val lacteosCollectionsData = listOf(
    R.drawable.lacteos_leche to R.string.leche,
    R.drawable.lacteos_arequipe to R.string.arequipe,
    R.drawable.lacteos_queso to R.string.queso,
    R.drawable.lacteos_mantequilla to R.string.mantequilla,
    R.drawable.lacteos_yogurt to R.string.yogurt,
    R.drawable.lacteos_cabra to R.string.cabra,
    R.drawable.lacteos_cuajada to R.string.cuajada,
    R.drawable.lacteos_calostros to R.string.calostro,
    R.drawable.lacteos_condensada to R.string.condensada,
    R.drawable.lacteos_crema to R.string.crema
).map { DrawableStringPair(it.first, it.second) }

// Lista de productos ver más.
val masCollectionsData = listOf(
    R.drawable.mas_abono to R.string.abono,
    R.drawable.mas_urea to R.string.urea,
    R.drawable.mas_tierra to R.string.tierra,
    R.drawable.mas_miel to R.string.miel,
    R.drawable.mas_cera to R.string.cera,
    R.drawable.mas_pan to R.string.pan,
    R.drawable.mas_mantecada to R.string.mantecada,
    R.drawable.mas_achiras to R.string.achiras,
    R.drawable.mas_panela to R.string.panela,
    R.drawable.mas_cal to R.string.cal,
    R.drawable.mas_matababosa to R.string.matababosa
).map { DrawableStringPair(it.first, it.second) }

// Lista de productos favoritos.
val favoritosCollectionsData = listOf(
    R.drawable.huevo_tipoaaa to R.string.tipoAAA,
    R.drawable.carnicos_cerdo to R.string.cerdo,
    R.drawable.carnicos_res to R.string.res,
    R.drawable.carnicos_pollo to R.string.pollo,
    R.drawable.flores_pensamiento to R.string.pensamiento,
    R.drawable.flores_rosa to R.string.rosa,
    R.drawable.lacteos_leche to R.string.leche,
    R.drawable.lacteos_arequipe to R.string.arequipe,
    R.drawable.lacteos_queso to R.string.queso,
    R.drawable.fruta_papa to R.string.papa,
    R.drawable.fruta_mango to R.string.mango,
    R.drawable.fruta_naranja to R.string.naranja,
    R.drawable.lacteos_cabra to R.string.cabra,
    R.drawable.mas_tierra to R.string.tierra,
    R.drawable.mas_miel to R.string.miel,
    R.drawable.mas_pan to R.string.pan
).map { DrawableStringPair(it.first, it.second) }

// Lista de productos populares.
val popularesCollectionsData = listOf(
    R.drawable.huevo_tipob to R.string.tipoB,
    R.drawable.huevo_tipoa to R.string.tipoA,
    R.drawable.huevo_tipoaa to R.string.tipoAA,
    R.drawable.fruta_banano to R.string.banano,
    R.drawable.fruta_cilantro to R.string.cilantro,
    R.drawable.mas_miel to R.string.miel,
    R.drawable.carnicos_cerdo to R.string.cerdo,
    R.drawable.carnicos_res to R.string.res,
    R.drawable.carnicos_pollo to R.string.pollo,
    R.drawable.flores_anturio to R.string.anturio,
    R.drawable.flores_pensamiento to R.string.pensamiento,
    R.drawable.flores_rosa to R.string.rosa,
    R.drawable.mas_pan to R.string.pan,
    R.drawable.mas_mantecada to R.string.mantecada,
    R.drawable.mas_achiras to R.string.achiras,
    R.drawable.mas_panela to R.string.panela,
    R.drawable.carnicos_conejo to R.string.conejo
).map { DrawableStringPair(it.first, it.second) }

// Lista de items que se usaran en los InicioRows en las vistas de principal y inicio.
val inicioCollectionsData = listOf(
    R.drawable.ic_tienda to R.string.inicio_tienda,
    R.drawable.ic_contenido to R.string.inicio_contenido,
    R.drawable.ic_informacion to R.string.inicio_informacion
).map { DrawableStringPair(it.first, it.second) }

val inicio2CollectionsData = listOf(
    R.drawable.pr_carnicos to R.string.cat1,
    R.drawable.pr_lacteos to R.string.cat2,
    R.drawable.pr_frutas to R.string.cat3,
    R.drawable.pr_flores to R.string.cat4,
    R.drawable.pr_fertilizantes to R.string.cat5
).map { DrawableStringPair(it.first, it.second) }

// Lista de items que se usaran para dotar de datos a las funciones de NoticiaRow, DestacadoRow y
// EventoRow.
val destacadoCollectionsData = listOf(
    DrawableStringPair2(R.drawable.ic_d1, R.string.titulo_d1, R.string.d1),
    DrawableStringPair2(R.drawable.ic_d2, R.string.titulo_d2, R.string.d2),
    DrawableStringPair2(R.drawable.ic_d3, R.string.titulo_d3, R.string.d3)
)

val noticiaCollectionsData = listOf(
    DrawableStringPair2(R.drawable.ic_n1, R.string.titulo_n1, R.string.n1),
    DrawableStringPair2(R.drawable.ic_n2, R.string.titulo_n2, R.string.n2),
    DrawableStringPair2(R.drawable.ic_n3, R.string.titulo_n3, R.string.n3),
    DrawableStringPair2(R.drawable.ic_n4, R.string.titulo_n4, R.string.n4),
    DrawableStringPair2(R.drawable.ic_n5, R.string.titulo_n5, R.string.n5)
)

val eventoCollectionsData = listOf(
    DrawableStringPair3(R.drawable.ic_e1),
    DrawableStringPair3(R.drawable.ic_e2),
    DrawableStringPair3(R.drawable.ic_e3),
    DrawableStringPair3(R.drawable.ic_e4),
    DrawableStringPair3(R.drawable.ic_e5),
    DrawableStringPair3(R.drawable.ic_e6),
    DrawableStringPair3(R.drawable.ic_e7)
)


// Carta base donde estaran los productos a la venta, cada boton de esta carta enviara una notificación
// eso si el usuario se registra o loguea, en caso que no enviara un alert informandole a este usuario que
// quiere acceder al servicio que se loguee o cree cuenta en el aplicativo.
@Composable
fun CardDataGridItem(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
){
    var opendialog by rememberSaveable { mutableStateOf(false) }
    val idNotification: Int = 0
    val context: Context = LocalContext.current
    val idChannel: String = stringResource(R.string.canalTienda)
    val name = stringResource(R.string.canalTienda)
    val descriptionText = stringResource(R.string.canalNotificacion)

    val textLong: String = "Saludos! Esta es una prueba de notificaciones. Debe aparecer " +
            "un icono a la derecha y el texto puede tener una longitud de 200 caracteres. " +
            "El tamaño de la notificación puede colapsar y/o expandirse. " +
            "Gracias y hasta pronto"

    //Función de creación propia como corrutina
    LaunchedEffect(Unit){
        CreateChannelNotification(
            idChannel,
            context,
            name,
            descriptionText
        )
    }

    var icono by rememberSaveable { mutableStateOf(0) }

    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
        elevation =  5.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = {
                if(FirebaseAuth.getInstance().currentUser?.uid == null){
                    opendialog = true
                } else {
                    icono = 1

                    val textShort: String = "Acabaste de añadir este articulo a favoritos."

                    notificacionSencilla(
                        context,
                        idChannel,
                        idNotification,
                        "Añadir a Favoritos",
                        textShort
                    )
                }
            }, modifier = Modifier.align(
                Alignment.End)) {
                if(icono == 0){
                    Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Añadir a Favoritos", tint = Green500
                    )
                } else {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Añadir a Favoritos", tint = Green500
                    )
                }
            }
            Image(painter = painterResource(
                id =  drawable
            ),
                contentDescription = "Grid Image",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(5.dp)),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = stringResource(text),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize =  15.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(1.dp))
            Text(
                text = "$10.000",
                modifier = Modifier
                    .padding(7.dp,0.dp,0.dp,20.dp)
                ,
                fontSize =  13.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Green
            )
            Spacer(modifier = Modifier.padding(1.dp))
            Row(
                modifier = Modifier
            ) {
                IconButton(onClick = {
                    if(FirebaseAuth.getInstance().currentUser?.uid == null){
                        opendialog = true
                    } else {
                        val iconoBig = BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.sena
                        )

                        notificacionExtensa(
                            context,
                            idChannel,
                            idNotification + 1,
                            "Detalles",
                            textLong,
                            iconoBig
                        )
                    }
                }) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "Detalles", tint = Green500)
                }
                IconButton(onClick = {
                    if(FirebaseAuth.getInstance().currentUser?.uid == null){
                        opendialog = true
                    } else {
                        val imagenBig = BitmapFactory.decodeResource(
                            context.resources,
                            drawable
                        )

                        val iconoBig = BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.sena
                        )

                        notificacionImagen(
                            context,
                            idChannel,
                            idNotification + 2,
                            "Añadir al carrito",
                            textLong,
                            iconoBig,
                            imagenBig
                        )
                    }
                }) {
                    Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Añadir al carrito", tint = Green500)
                }
                IconButton(onClick = {
                    if(FirebaseAuth.getInstance().currentUser?.uid == null){
                        opendialog = true
                    } else {
                        val textShort: String = "Acabaste de compartir este articulo."

                        notificacionSencilla(
                            context,
                            idChannel,
                            idNotification,
                            "Compartir",
                            textShort
                        )
                    }
                }) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = "Compartir", tint = Green500)
                }
            }
        }
    }

    // Función para la ventana de alerta si el usuario no ha iniciado sesión.
    Dialog(showDialog = opendialog, dismissDialog = { opendialog = false })
}

// Función con el diseño para la ventana de alerta que se usara cuando el usuario no haya iniciado sesión.
@Composable
fun Dialog(
    showDialog: Boolean,
    dismissDialog: () -> Unit
){
    if (showDialog){
        AlertDialog(
            onDismissRequest = { dismissDialog() },
            title = { Text(text = "Aviso", style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "¡Para usar estos servicios primero debe iniciar sesión!")
                    Spacer(modifier = Modifier.height(10.dp))
                    if(isSystemInDarkTheme()){
                        Image(painterResource(id = R.drawable.sena_1), contentDescription = null)
                    } else {
                        Image(painterResource(id = R.drawable.sena_2), contentDescription = null)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    dismissDialog()
                }) {
                    Text(text = "Aceptar")
                }
            }
        )
    }
}