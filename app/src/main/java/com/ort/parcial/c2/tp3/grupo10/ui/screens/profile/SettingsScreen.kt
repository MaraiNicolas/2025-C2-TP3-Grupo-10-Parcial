package com.ort.parcial.c2.tp3.grupo10.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppScreenShell
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily

// Rutas de navegación (asumiremos estas rutas para los sub-ajustes)
private const val ROUTE_NOTIF_SETTINGS = "notification_settings"
private const val ROUTE_PASS_SETTINGS = "password_settings"
private const val ROUTE_DELETE_ACCOUNT = "delete_account"


@Composable
fun SettingsScreen(navController: NavHostController) {

    // Valores Estándar (similar a ProfileScreen)
    val STANDARD_HEADER_HEIGHT = 180.dp
    val ICON_FOREGROUND_COLOR = Color(0xFF1E88E5) // Color azul/verde claro
    val ICON_BACKGROUND_COLOR = Color(0xFFE3F2FD) // Fondo claro del círculo

    // --- FUNCIÓN LOCAL PARA EL ITEM DE AJUSTES (Con Icono y Círculo) ---
    @Composable
    fun SettingsItem(labelResId: Int, iconResId: Int, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable(onClick = onClick)
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 1. Contenedor de Ícono (Círculo)
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(ICON_BACKGROUND_COLOR, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = iconResId), // Asumimos los drawables
                        contentDescription = stringResource(labelResId),
                        modifier = Modifier.fillMaxSize(0.65f) // Ocupa la mayor parte del Box
                    )
                }

                Spacer(Modifier.width(16.dp))

                // 2. Texto de la Opción
                Text(
                    text = stringResource(labelResId),
                    color = LettersAndIcons,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    lineHeight = 15.sp,
                    modifier = Modifier.weight(1f) // Ocupa el espacio
                )
            }

            // 3. Flecha de navegación (Cheurón)
            Icon(
                painter = painterResource(id = R.drawable.ic_help), // Asumo que tienes un drawable para la flecha
                contentDescription = null,
                tint = Color.Gray.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
    // ----------------------------------------------------------------------

    AppScreenShell(
        screenTitle = "Settings",
        headerHeight = STANDARD_HEADER_HEIGHT,
        navController = navController,
        startSelectedIndex = 4
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // --- LISTA DE OPCIONES DE AJUSTES ---
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp)
            ) {
                // 1. Notification Settings
                SettingsItem(
                    labelResId = R.string.setting_notifications,
                    iconResId = R.drawable.ic_perfil, // Placeholder
                    onClick = { navController.navigate(ROUTE_NOTIF_SETTINGS) }
                )

                // 2. Password Settings
                SettingsItem(
                    labelResId = R.string.setting_password,
                    iconResId = R.drawable.ic_perfil, // Placeholder
                    onClick = { navController.navigate(ROUTE_PASS_SETTINGS) }
                )

                // 3. Delete Account
                SettingsItem(
                    labelResId = R.string.setting_delete_account,
                    iconResId = R.drawable.ic_perfil, // Placeholder
                    onClick = { navController.navigate(ROUTE_DELETE_ACCOUNT) }
                )
            }

            Spacer(Modifier.weight(1f))
        }
    }
}
