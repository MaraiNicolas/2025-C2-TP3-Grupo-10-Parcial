package com.ort.parcial.c2.tp3.grupo10.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppButton
import com.ort.parcial.c2.tp3.grupo10.ui.theme.DarkModeGreenBar
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LeagueSpartanFamily
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons // Color oscuro para letras y iconos
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily

// Dimensiones reutilizadas
private val FINGERPRINT_CIRCLE_SIZE = 195.dp
private val BUTTON_WIDTH = 357.dp // El botón parece tener un ancho similar
private val BUTTON_HEIGHT = 45.dp



@Composable
fun FingerprintSetupScreen(navController: NavHostController) {

    val STANDARD_HEADER_HEIGHT = 140.dp

    AppScreenShell(
        // TÍTULO DEL HEADER: "Add Fingerprint"
        screenTitle = stringResource(R.string.fingerprint_add_screen_title),
        headerHeight = STANDARD_HEADER_HEIGHT,
        navController = navController,
        startSelectedIndex = 4
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp), // Padding lateral general
            horizontalAlignment = Alignment.CenterHorizontally // Centrar el contenido horizontalmente
        ) {

            // ESPACIADO DESDE EL HEADER HACIA ABAJO (AJUSTADO VISUALMENTE)
            Spacer(Modifier.height(80.dp))

            // --- 1. CÍRCULO GRANDE CON ÍCONO DE HUELLA ---
            Box(
                modifier = Modifier
                    .size(FINGERPRINT_CIRCLE_SIZE)
                    .background(MainGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_fingerprint),
                    contentDescription = stringResource(R.string.fingerprint_icon_description),
                    modifier = Modifier.fillMaxSize(0.7f) // El ícono ocupa 70% del círculo
                )
            }

            // ESPACIO ENTRE EL CÍRCULO Y EL TÍTULO PRINCIPAL
            Spacer(Modifier.height(48.dp))

            // --- 2. TÍTULO PRINCIPAL: "Use Fingerprint To Access" ---
            Text(
                text = stringResource(R.string.fingerprint_main_title_setup),
                color = DarkModeGreenBar,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.SemiBold, // Semibold
                fontSize = 18.sp, // 20px
                lineHeight = 22.sp, // 22px
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            // ESPACIO ENTRE TÍTULO Y DESCRIPCIÓN
            Spacer(Modifier.height(16.dp))

            // --- 3. TEXTO DE DESCRIPCIÓN: "Lorem ipsum dolor sit amet..." ---
            Text(
                text = stringResource(R.string.fingerprint_description_setup),
                color = LettersAndIcons.copy(alpha = 0.7f), // Color más suave, como en la imagen
                fontFamily = LeagueSpartanFamily, // <-- FUENTE
                fontWeight = FontWeight.Normal, // <-- 400 Regular
                fontSize = 14.sp, // <-- 14px
                lineHeight = 14.sp, // <-- 100% line-height
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // ESPACIO ANTES DEL BOTÓN (AJUSTADO PARA QUE EL BOTÓN ESTÉ MÁS ABAJO)
            Spacer(Modifier.height(50.dp))

            // --- 4. BOTÓN: "Use Touch Id" ---
            AppButton(
                onClick = {
                    navController.navigate("successCFingerPrintConfirmation")
                },
                text = stringResource(R.string.fingerprint_button_setup), // Texto del botón
                enabled = true,
                buttonHeight = BUTTON_HEIGHT,
                buttonWidth = BUTTON_WIDTH,
                containerColor = Color.White,
                textColor = DarkModeGreenBar,
                modifier = Modifier.wrapContentWidth(), // Ajusta el ancho si es menor a fillMaxWidth
                textStyle = TextStyle(
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center,
                    color = DarkModeGreenBar
                )
            )

            Spacer(Modifier.weight(1f)) // Empuja el contenido restante hacia arriba
        }
    }
}