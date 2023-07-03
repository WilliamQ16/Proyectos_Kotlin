package com.example.prototipo.pages

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prototipo.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.widgets.DisappearingScaleBar
import com.google.maps.android.compose.widgets.ScaleBar

// Constantes de longitud y latitud de cada punto del CBA.
val senaCba = LatLng(4.69606, -74.21563)
val tiendaCba = LatLng(4.696385, -74.214835)
val unidadLacteosCba = LatLng(4.694562, -74.215364)
val unidadAgricolaCba = LatLng(4.694168, -74.218453)
val unidadCarnicosCba = LatLng(4.694498, -74.214999)
val unidadDeportivaCba = LatLng(4.695060, -74.215393)
val administracionCba = LatLng(4.695707, -74.215983)
val auditorioCba = LatLng(4.695373, -74.216139)
val unidadGastronomiaCba = LatLng(4.695496, -74.216455)
val bienestarCba = LatLng(4.695520, -74.217102)
val camasCba = LatLng(4.693972, -74.217305)
val unidadCuniculturaCba = LatLng(4.693665, -74.218960)
val unidadAviculturaCba = LatLng(4.692856, -74.219061)
val unidadBiotecnologiaCba = LatLng(4.692866, -74.218098)
val unidadCapriculturaCba = LatLng(4.693206, -74.220445)
val unidadPorciculturaCba = LatLng(4.692915, -74.220091)
val unidadGanaderiaCba = LatLng(4.692139, -74.219345)
val unidadApiculturaCba = LatLng(4.690594, -74.222494)

// Vista Información
// En esta vista solo se presentara los datos de contacto acerca del CBA igual como se hizo en la vista de
// contenidos pasando los datos directamente a la card, debajo de estos datos estara un mapa Google con los puntos
// importantes del Centro de Biotecnología Agropecuaria.
@Composable
fun Page_Informacion(){
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Info CBA",
            fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 60.sp
        )
        Informacion(text = R.string.titulo_contactenos, description = R.string.contactenos)
    }
}

// Card donde estara los datos y el mapa.
@Composable
private fun Informacion(
    @StringRes text: Int,
    @StringRes description: Int
) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        InformacionContent(text, description)
    }
}

// Función que recibe los datos suministrados y los acomoda para dar una vista agradable en el aplicativo.
@Composable
private fun InformacionContent(
    @StringRes text: Int,
    @StringRes description: Int
) {

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
            Text(
                text = stringResource(description),
            )
            MapasScreen()
        }
    }
}

// En esta función se llama al mapa dentro un box el cual le dara el ancho y el alto.
@Composable
fun MapasScreen() {
    val defaultCameraPosition = CameraPosition.fromLatLngZoom(senaCba, 15f)
    //objeto para pasar al mapa
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }
    //Variable para determinar si el mapa se cargo
    var isMapLoaded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .height(300.dp)
            .padding(20.dp)
    ) {
        GoogleMapView(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                isMapLoaded = true
            }
        )
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier.matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .wrapContentSize()
                )
            }
        }
    }
}

// Función que construye el mapa Google, a su vez en cada punto resaltado al momento de seleccionarlo habrá un título y una imagen acorde.
@Composable
fun GoogleMapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: () -> Unit = {},
    content: @Composable () -> Unit = {}
){
    val senaCbaState = rememberMarkerState(position = senaCba)
    val tiendaCbaState = rememberMarkerState(position = tiendaCba)
    val unidadLacteosCbaState = rememberMarkerState(position = unidadLacteosCba)
    val unidadAgricolaCbaState = rememberMarkerState(position = unidadAgricolaCba)
    val unidadCarnicosCbaState = rememberMarkerState(position = unidadCarnicosCba)
    val unidadDeportivaCbaState = rememberMarkerState(position = unidadDeportivaCba)
    val administracionCbaState = rememberMarkerState(position = administracionCba)
    val auditorioCbaState = rememberMarkerState(position = auditorioCba)
    val unidadGastronomiaCbaState = rememberMarkerState(position = unidadGastronomiaCba)
    val bienestarCbaState = rememberMarkerState(position = bienestarCba)
    val camasCbaState = rememberMarkerState(position = camasCba)
    val unidadCuniculturaCbaState = rememberMarkerState(position = unidadCuniculturaCba)
    val unidadAviculturaCbaState = rememberMarkerState(position = unidadAviculturaCba)
    val unidadBiotecnologiaCbaState = rememberMarkerState(position = unidadBiotecnologiaCba)
    val unidadCapriculturaCbaState = rememberMarkerState(position = unidadCapriculturaCba)
    val unidadPorciculturaCbaState = rememberMarkerState(position = unidadPorciculturaCba)
    val unidadGanaderiaCbaState = rememberMarkerState(position = unidadGanaderiaCba)
    val unidadApiculturaCbaState = rememberMarkerState(position = unidadApiculturaCba)

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            onMapLoaded = onMapLoaded,
            properties = MapProperties(mapType = MapType.HYBRID)
        ){
            MarkerInfoWindow(
                state = senaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.sena)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sena_prin),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "CBA Mosquera",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadAgricolaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.agro_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.agro_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de producción agricola CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadLacteosCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.lacteos_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.lacteos_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de producción lacteos CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = tiendaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.tienda_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tienda_sena),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Tienda Sena CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadCarnicosCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.carnicos_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.carnicos_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de producción de carnicos CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadDeportivaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.deportes_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.deportes_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad deportiva CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = auditorioCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.auditorio_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.auditorio),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Auditorio CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = administracionCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.administracion_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.administracion_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Administración CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadGastronomiaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.gastronomia_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gastronomia_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de gastronomía CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = bienestarCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.bienestar_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bienestar_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Oficinas de Bienestar y Sofia Plus CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = camasCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.camas_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.camas_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Camas de cultivo CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadCuniculturaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.conejo_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.conejo_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de cunicultura CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadAviculturaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.gallina_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gallina_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de avicultura CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadBiotecnologiaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.biotecnologia_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.biotecnologia_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de biotecnología CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadCapriculturaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.oveja_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.oveja_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de capricultura CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadPorciculturaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.cerdo_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cerdo_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de porcicultura CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadGanaderiaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.vaca_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vaca_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de ganadería CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            MarkerInfoWindow(
                state = unidadApiculturaCbaState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.abeja_icono)
            ){
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                    )
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.abeja_mapa),
                            contentDescription = null,
                            //contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = "Unidad de apicultura CBA",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            content()
        }
        ScaleBar(
            modifier = Modifier
                .padding(top = 5.dp, end = 15.dp)
                .align(Alignment.TopEnd),
            cameraPositionState = cameraPositionState
        )
        DisappearingScaleBar(
            modifier = Modifier
                .padding(top = 5.dp, end = 15.dp)
                .align(Alignment.TopStart),
            cameraPositionState = cameraPositionState
        )
    }
}