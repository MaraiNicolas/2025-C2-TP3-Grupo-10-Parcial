// Archivo: ui/components/NotificationButton.kt

package com.ort.parcial.c2.tp3.grupo10.ui.components

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ort.parcial.c2.tp3.grupo10.MainActivity // Importamos la Activity principal
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.theme.BackgroundGreenWhiteAndLetters
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons

@Composable
fun NotificationButton(
    navController: NavController, // <-- Recibe el NavController
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentActivity = context as Activity

    // Hardcoded Strings para la navegación externa
    val NAV_DESTINATION_KEY = "startDestination"
    val NOTIFICATION_ROUTE = "notification"

    // Determina si estamos en una Activity diferente a MainActivity (Flujo de Perfil/Seguridad)
    val requiresIntent = currentActivity::class.java != MainActivity::class.java

    val onClickAction: () -> Unit = {
        if (requiresIntent) {
            // Lógica de Intent (MainActivity2 -> MainActivity): Usar la navegación forzada
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(NAV_DESTINATION_KEY, NOTIFICATION_ROUTE)
            }
            context.startActivity(intent)
            currentActivity.finish() // Cierra MainActivity2
        } else {
            // Lógica de Compose (MainActivity -> NotificationScreen): Navegación interna
            navController.navigate(NOTIFICATION_ROUTE) {
                // Evitar apilar notificaciones
                launchSingleTop = true
            }
        }
    }

    IconButton(
        onClick = onClickAction,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(30.dp) // Tamaño de la campana y su fondo
                .background(BackgroundGreenWhiteAndLetters, CircleShape), // <-- FONDO CLARITO
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = stringResource(R.string.notifications_button_desc),
                tint = LettersAndIcons.copy(alpha = 0.55f),
                modifier = Modifier.size(26.dp)
            )
        }
    }
}