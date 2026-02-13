package com.tomtom.dentalfirst.data.model

import com.tomtom.dentalfirst.R

object ProductMocks {
    val sampleProducts = listOf(
        Product(
            id = "1",
            name = "Dental Handpiece A1",
            price = "₽12,500",
            imageRes = R.drawable.first,
            description = "High-speed handpiece suitable for general dentistry"
        ), Product(
            id = "2",
            name = "Orthodontic Bracket Set",
            price = "₽3,200",
            imageRes = R.drawable.second,
            description = "Complete set for bracket bonding"
        ), Product(
            id = "3",
            name = "Sterilization Pouches (100)",
            price = "₽1,100",
            imageRes = R.drawable.third,
            description = "Medical-grade sterilization pouches"
        )
    )
}
