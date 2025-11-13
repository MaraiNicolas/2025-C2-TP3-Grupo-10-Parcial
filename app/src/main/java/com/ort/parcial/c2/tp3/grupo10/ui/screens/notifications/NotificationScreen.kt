package com.ort.parcial.c2.tp3.grupo10.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.domain.model.NotificationItem // Asegúrate de que esta clase sea accesible
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppScreenShell // <-- Componente Base
import com.ort.parcial.c2.tp3.grupo10.ui.components.BottomNavBar // <-- Componente de la barra
import com.ort.parcial.c2.tp3.grupo10.ui.theme.BackgroundGreenWhiteAndLetters
import com.ort.parcial.c2.tp3.grupo10.ui.theme.Honeydew

@Composable
fun NotificationsScreen(
    navController: NavHostController
) {
    // Índice inicial nulo/inválido
    var selectedIndex by remember { mutableIntStateOf(-1) }

    AppScreenShell(
        screenTitle = "Notification",
        headerHeight = 140.dp,
        navController = navController,
        showBackButton = true,
        showNotificationButton = false,
        startSelectedIndex = -1
    ) { innerPadding -> // <-- innerPadding ahora viene del AppScreenShell

        // 1. Contenido Principal de Notificaciones (La lista)
        Box(
            // El padding de la BottomBar ya está aplicado por el AppScreenShell al Column
            // Aquí solo manejamos el layout interno.
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGreenWhiteAndLetters) // Fondo que simula la tarjeta
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)) // Curvas del Shell
                .padding(horizontal = 0.dp) // Eliminamos padding horizontal si el Shell lo pone
                .padding(innerPadding) // Usamos el padding pasado para compensar cualquier otro padding del Shell
        ) {
            // El Box interno para la lista (sin curvas, ya las puso el Shell)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp) // Padding interno de la lista
            ) {
                NotificationList(contentPadding = PaddingValues(0.dp))
            }
        }
    }
}

@Composable
private fun NotificationList(contentPadding: PaddingValues) {
    val notifications = listOf(
        // --- Today ---
        NotificationItem(
            R.drawable.ic_recordatorio, "Reminder!",
            "Set up your automatic savings to meet your savings goal...",
            time = "17:00", date = "Today"
        ),
        NotificationItem(
            R.drawable.ic_estrella, "New Update",
            "Set up your automatic savings to meet your savings goal...",
            time = "17:00", date = "Today"
        ),

        // --- Yesterday ---
        NotificationItem(
            R.drawable.ic_dinero, "Transactions",
            "A new transaction has been registered",
            category = "Groceries | Pantry ",
            amount = " -$100.00", time = "17:00", date = "Yesterday",
            amountColor = Color(0xFF00A86B)
        ),
        NotificationItem(
            R.drawable.ic_recordatorio, "Reminder!",
            "Set up your automatic savings to meet your savings goal...",
            time = "17:00", date = "Yesterday"
        ),

        // --- This Weekend ---
        NotificationItem(
            R.drawable.ic_grafico, "Expense Record",
            "We recommend that you be more attentive to your finances.",
            time = "17:00", date = "This Weekend"
        ),
        NotificationItem(
            R.drawable.ic_dinero, "Transactions",
            "A new transaction has been registered ",
            category = "Food | Dinner ",
            amount = " -$70.00", time = "17:00", date = "This Weekend",
            amountColor = Color(0xFF00A86B)
        )
    )

    val grouped = notifications.groupBy { it.date }
    val order = listOf("Today", "Yesterday", "This Weekend")
    val orderedKeys = order.filter { it in grouped.keys } + grouped.keys.filter { it !in order }

    LazyColumn(
        modifier = Modifier
            .background(Honeydew)
            .fillMaxSize()
            .padding(contentPadding)
            .padding(vertical = 12.dp)
    ) {
        orderedKeys.forEach { fecha ->
            val itemsDelGrupo = grouped[fecha].orEmpty()

            item {
                Text(
                    text = fecha,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 40.dp, top = 20.dp, bottom = 0.dp)
                )
            }

            itemsIndexed(itemsDelGrupo) { index, noti ->
                val top = if (index == 0) 0.dp else 6.dp
                NotificationCard(
                    item = noti,
                    topPadding = top,
                )
            }
        }
    }
}