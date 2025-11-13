package com.ort.parcial.c2.tp3.grupo10.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.statusBarsPadding
val STANDARD_HEADER_HEIGHT = 180.dp

/**
 * Componente Shell para estandarizar el Layout de todas las pantallas
 * (Header Verde, Tarjeta Curva, Fondo Verde Clarito).
 */
@Composable
fun AppScreenShell(
    screenTitle: String,
    // PARÁMETROS BOOLEANOS PARA CONTROLAR LA VISIBILIDAD
    showBackButton: Boolean = true, //MUESTRA LA FLECHA HACIA ATRAS
    showNotificationButton: Boolean = true, //MUESTRA LA CAMPANITA
    startSelectedIndex: Int? = null, //MUESTRA LA BOTONERA INFERIOR y la POSICION
    // La altura del header verde puede ser variable
    headerHeight: Dp = STANDARD_HEADER_HEIGHT,
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit // Recibe el contenido único del formulario/lista
) {

    val context = LocalContext.current
    // 1. DIBUJAMOS EL FONDO PRINCIPAL Y EL CONTENEDOR DE APILAMIENTO

    var selectedIndex by remember { mutableIntStateOf(startSelectedIndex ?: 0) }// determina el estado visual de BottomNavVar

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()       // <-- primero: protegemos la status bar
            .background(MainGreen) // <--- ESTE DIBUJA EL FONDO VERDE OSCURO (MainGreen)

    ) {
        // --- 1. ICONOS DE NAVEGACIÓN Y TÍTULO (HIJOS DIRECTOS DEL BOX) ---

        // A. FLECHA DE RETROCESO (Izquierda) - CONDICIONAL
        if (showBackButton) {
            BackButton(navController = navController, modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 38.dp, top = 69.dp)
            )
        }

        // B. CAMPANA DE NOTIFICACIONES (Derecha) - CONDICIONAL
        if (showNotificationButton) {
            NotificationButton(
                navController = navController, // Se necesita para la lógica del Intent/navegación
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp, top = 61.dp)
            )
        }

        // C. TÍTULO PRINCIPAL (Centrado)
        Text(
            text = screenTitle,
            color = LettersAndIcons,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.TopCenter) // <-- Centrado
                .padding(top = 64.dp)
        )

        // --- 2. CUERPO / TARJETA CON ESQUINAS CURVAS ---
        Surface(

            modifier = Modifier
                .fillMaxSize()
                .padding(top = headerHeight),
            color = BackgroundGreenWhiteAndLetters,
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            // 3. USAMOS SCAFFOLD PARA MANEJAR LA BARRA INFERIOR
            Scaffold(
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0),
                bottomBar = {
                    // LLAMADA FINAL: SI SE PASÓ UN ÍNDICE INICIAL, DIBUJA LA BARRA.
                    if (startSelectedIndex != null) {
                        BottomNavBar(
                            selected = selectedIndex, // Usamos el estado local del Shell
                            navController = navController,
                            onSelect = { index ->
                                selectedIndex = index // Actualiza el índice local
                                // NOTA: La lógica de navegación está totalmente centralizada en BottomNavBar.kt
                            }
                        )
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // El contenido único de la pantalla
                    content(PaddingValues(horizontal = 32.dp, vertical = 0.dp))
                }
            }
        }
    }
}