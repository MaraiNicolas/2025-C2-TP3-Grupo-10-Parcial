package com.ort.parcial.c2.tp3.grupo10.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppScreenShell
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppButton // Tu componente de botón
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen // Verde principal
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily // Tu familia de fuentes

// Rutas de navegación (si necesitas navegar a una confirmación de borrado)
private const val ROUTE_FINGERPRINT_LIST = "fingerprint_list" // Para volver
private const val ROUTE_DELETE_CONFIRMATION = "delete_fingerprint_confirmation" // Ejemplo

private val FINGERPRINT_CIRCLE_SIZE = 195.dp
private val NAME_FIELD_HEIGHT = 41.dp
private val NAME_FIELD_RADIUS = 18.dp
private val DELETE_BUTTON_WIDTH = 202.dp
private val DELETE_BUTTON_HEIGHT = 45.dp
@Composable
fun UseFingerprintScreen(navController: NavHostController) {

    val STANDARD_HEADER_HEIGHT = 140.dp

    AppScreenShell(
        screenTitle = stringResource(R.string.use_fingerprint_title),
        headerHeight = STANDARD_HEADER_HEIGHT,
        navController = navController,
        startSelectedIndex = 4
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           // Spacer(Modifier.height(24.dp)) // Espacio desde el header

            // --- 1. CÍRCULO GRANDE CON ÍCONO DE HUELLA ---
            Spacer(Modifier.height(80.dp))
            Box(
                modifier = Modifier
                    .size(FINGERPRINT_CIRCLE_SIZE) // Tamaño del círculo grande
                    .background(MainGreen, CircleShape), // Fondo verde con forma circular
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_fingerprint),
                    contentDescription = stringResource(R.string.fingerprint_icon_description),
                    modifier = Modifier.fillMaxSize(0.7f) // El ícono ocupa 70% del círculo
                )
            }

            Spacer(Modifier.height(15.dp))

            // --- 2. BOTÓN/CAMPO DE TEXTO "John Fingerprint" ---
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(NAME_FIELD_HEIGHT)
                    .background(Color.Transparent, RoundedCornerShape(25.dp)) // Fondo transparente
                    .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(25.dp)) // Borde gris claro
                    .clickable { /* Podría navegar a editar nombre o no hacer nada */ }
                    .padding(horizontal = 20.dp), // Padding interno para el texto
                contentAlignment = Alignment.Center // Alinear texto a la izquierda
            ) {
                Text(
                    text = stringResource(R.string.fingerprint_name_label),
                    color = LettersAndIcons,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    lineHeight = 15.sp
                )
            }

            Spacer(Modifier.height(90.dp))

            // --- 3. BOTÓN "Delete" ---
            AppButton(
                onClick = {

                    navController.navigate("successFingerEliminateConfirmation") {
                        popUpTo(ROUTE_FINGERPRINT_LIST) { inclusive = true } // Limpiar back stack
                    }
                },
                text = stringResource(R.string.fingerprint_delete_button),
                enabled = true,
                buttonHeight = DELETE_BUTTON_HEIGHT,
                buttonWidth = DELETE_BUTTON_WIDTH,
                containerColor = MainGreen, // Fondo verde para "Delete"
                textColor = LettersAndIcons,
                modifier = Modifier.wrapContentWidth(),
                textStyle = TextStyle( // Estilo de texto consistente
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center,
                    color = LettersAndIcons
                )
            )

            Spacer(Modifier.weight(1f)) // Empuja el contenido hacia arriba
        }
    }
}