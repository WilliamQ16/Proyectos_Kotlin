package com.example.prototipo.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth

// Menú superior la mayoria de este es de decoración excepto el botón de inciar sesión el cual va a guiar
// al usuario al login para que inicie sesión o cree una cuenta, en caso que esto se cumpla el boton cambiara y dira
// cerrar sesión este nuevo botón contara con la función de valga la redundancia cerrar sesión
// generando que ya el usuario tenga que volverse a loguear para acceder a los beneficios del aplicativo.
@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    menuItem: List<MenuItem>
){
    var showMenu by remember{
        mutableStateOf(false)
    }
    var currentRoute = Current_Route(navController = navController)
    var myTytle = "Sena CBA"
    menuItem.forEach{ item ->
        if (currentRoute == item.ruta) myTytle = item.title
    }

    // Este menú superior los items se crean mediante listas y se llaman por medio de un builder.
    TopAppBar(
        title = { Text(text = myTytle) },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ){
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Icono de menús"
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refrescar"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Settings,
                    contentDescription = "Configurar"
                )
            }
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Más Opciones"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier.width(150.dp)
            ) {
                if(FirebaseAuth.getInstance().currentUser?.uid == null){
                    DropdownMenuItem(onClick = {
                        navController.navigate(Apps_Bar.app1.ruta)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Login,
                            contentDescription = "Iniciar Sesión"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Iniciar Sesión")
                    }
                } else {
                    DropdownMenuItem(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Apps_Bar.app1.ruta)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Cerrar Sesión"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Cerrar Sesión")
                    }
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Idioma"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Idioma")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Compartir"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Compartir")
                }
            }
        }
    )
}