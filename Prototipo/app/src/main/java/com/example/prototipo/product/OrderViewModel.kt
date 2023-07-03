package com.example.prototipo.product

import androidx.lifecycle.ViewModel
import com.example.prototipo.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

// Constantes las cuales contienen el precio del producto y el recargo para el caso en donde el usuario elija
// que su envio sea el mismo día de la compra.
private const val PRICE_PER_CUPCAKE = 10000.00

private const val PRICE_FOR_SAME_DAY_PICKUP = 2000.00

class OrderViewModel : ViewModel() {

    // Estado que se encargara de toda la transacción de la compra a realizar.
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    // Función para calcular las cantidades de los productos.
    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes,
                price = calculatePrice(quantity = numberCupcakes)
            )
        }
    }

    // Función que contendra todos los productos a la venta.
    fun setProduct(desiredProduct: String) {
        _uiState.update { currentState ->
            currentState.copy(product = desiredProduct)
        }
    }

    // Función que contendra todas las fechas de envio disponible.
    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate,
                price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    // Función para borrar la orden al momento de cancelarla.
    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    // Función para calcular el precio del producto mediante la fecha actual.
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date
    ): String {
        var calculatedPrice = quantity * PRICE_PER_CUPCAKE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (pickupOptions()[0] == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        return NumberFormat.getCurrencyInstance().format(calculatedPrice)
    }

    // Función para obtener fecha y hora actual.
    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()

        Locale.setDefault(Locale("es", "CO"))

        val formatter = SimpleDateFormat("E MMM d 'at' HH:mm", Locale.getDefault())

        formatter.timeZone = TimeZone.getTimeZone("America/Bogota")

        val calendar = Calendar.getInstance()

        repeat(4){
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }

}