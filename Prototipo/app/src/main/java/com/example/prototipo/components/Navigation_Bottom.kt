package com.example.prototipo.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prototipo.R
import com.example.prototipo.pages.Dialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//Componentes inferiores del aplicativo.
@Composable
fun ButtomMenu(
    navController: NavHostController,
    menu_item_bar: List<Items_Bar>
){
    // Barra de menu inferior
    BottomAppBar (
        cutoutShape  = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
    ){
        BottomNavigation(
            modifier = Modifier.padding(
                0.dp,
                0.dp,
                60.dp,
                0.dp
            )
        ) {
            val currentRouteBar = Current_Route(navController = navController)
            menu_item_bar.forEach{
                item ->
                BottomNavigationItem(
                    selected = currentRouteBar == item.ruta,
                    onClick = { navController.navigate(item.ruta) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = ""
                        )
                    },
                )
            }
        }
    }
}

//Botton flotante el cual va a dirigir al usuario al sistema de facturación y a su vez va a enviar una notificación programada.
@Composable
fun Fab(
    navController: NavHostController,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
){
    var opendialog by rememberSaveable { mutableStateOf(false) }
    val context: Context = LocalContext.current
    val idChannel: String = stringResource(R.string.canalTienda)
    val name = stringResource(R.string.canalTienda)
    val descriptionText = stringResource(R.string.canalNotificacion)

    //Función de creación propia como corrutina
    LaunchedEffect(Unit){
        CreateChannelNotification(
            idChannel,
            context,
            name,
            descriptionText
        )
    }

    FloatingActionButton(
        onClick = {
            if(FirebaseAuth.getInstance().currentUser?.uid == null){
                opendialog = true
            } else {
                scope.launch { scaffoldState.snackbarHostState.showSnackbar(
                    "Va a iniciar una compra",
                    actionLabel = "Aceptar",
                    duration = SnackbarDuration.Indefinite
                ) }
                navController.navigate(Apps_Bar.app3.ruta)
                notificacionProgramada(
                    context
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant
    ){
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Recompensas"
        )
    }

    //Ventana de alerta que solo se llama en caso que el usuario no se haya registrado o logueado.
    
    Dialog(showDialog = opendialog, dismissDialog = { opendialog = false })
}