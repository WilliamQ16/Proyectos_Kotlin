package com.example.prototipo.pages

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.prototipo.product.OrderViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.prototipo.R
import com.example.prototipo.data.DataSource
import com.example.prototipo.product.OrderSummaryScreen
import com.example.prototipo.product.SelectOptionScreen
import com.example.prototipo.product.StartOrderScreen

// Lista donde defino las rutas para el sistema de facturación.
enum class ProductScreen (@StringRes val title: Int) {
    Start(title = R.string.pedido_cba),
    Flavor(title = R.string.choose_product),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

// Diseño de la barra superior en el sistema de facturación, esta barra superior permite devolver al
// usuario a la vista anterior por medio de la navegación que se pasa como parametro de forma de función
// lambda Unit().
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAppBar(
    currentScreen: ProductScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(stringResource(currentScreen.title), color = Color.White) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colors.primary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.White
                    )
                }
            }
        }
    )
}

// Estructura del sistema de facuración, mediante un scaffold se define el esqueleto del aplicativo
// pero en su cuerpo habra una navegación interna el cual al interactuar con estas vistas solo va a afectar esa
// parte no la estructura completa, por ende alguna de estas vistas tendran parametros los cuales pasaran algunas
// constantes para realizar la facutura del producto que el usuario va a adquirir.
@Composable
fun ProductApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = ProductScreen.valueOf(
        backStackEntry?.destination?.route ?: ProductScreen.Start.name
    )

    Scaffold(
        topBar = {
            ProductAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ProductScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = ProductScreen.Start.name){
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(ProductScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(route = ProductScreen.Flavor.name){
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(ProductScreen.Pickup.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.products.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setProduct(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = ProductScreen.Pickup.name){
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(ProductScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = ProductScreen.Summary.name){
                val context = LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }

    }
}

// Función que se encarga de cancelar la compra en el sistema de facturación.
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
){
    viewModel.resetOrder()
    navController.popBackStack(ProductScreen.Start.name, inclusive = false)
}

// Función para compartir la factura por medio de las redes sociales.
private fun shareOrder(context: Context, subject: String, summary: String){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_product_order)
        )
    )
}