package com.example.proyecto_cupcake.data

import com.example.proyecto_cupcake.R

object DataSource {
    val flavors = listOf(
        R.string.vainilla,
        R.string.chocolate,
        R.string.arequipe,
        R.string.salted_caramel,
        R.string.coffee
    )

    val quantityOptions = listOf(
        Pair(R.string.one_cupcake, 1),
        Pair(R.string.six_cupcakes, 6),
        Pair(R.string.twelve_cupcakes, 12)
    )
}