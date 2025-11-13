package com.ort.parcial.c2.tp3.grupo10.ui.screens.profile

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppScreenShell
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppButton // Tu componente de botón
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LightGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import androidx.compose.material.icons.Icons // <-- Necesaria
import androidx.compose.material.icons.filled.VisibilityOff // <-- Ojo cerrado/tachado
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign


@Composable
fun ChangePinScreen(navController: NavHostController) {

    // --- Valores Solicitados ---
    val STANDARD_HEADER_HEIGHT = 140.dp // Altura del header

    val screenTitle = stringResource(R.string.change_pin_title)

    // --- COMPONENTE LOCAL PARA EL CAMPO DE PIN (MAQUETACIÓN DIRECTA SEGÚN IMAGEN) ---
    @Composable
    fun PinInputFieldVisual(labelResId: Int) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(labelResId),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                lineHeight = 15.sp,
                color = LettersAndIcons,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp) // Altura del campo
                    .background(LightGreen, RoundedCornerShape(25.dp)) // Fondo y esquinas
                    .padding(horizontal = 20.dp), // Padding interno
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Espacio entre los puntos y el ojo
            ) {

                Row(
                    // Usamos Arrangement.spacedBy para controlar el espacio entre los puntos
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // CAMBIO 1: Reducimos el ciclo a 4 (4 puntos)
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(LettersAndIcons.copy(alpha = 0.5f), shape = RoundedCornerShape(4.dp))
                        )
                    }
                }
                // Icono del ojo (usamos un simple texto si no tenemos el Asset de icono)
                Icon(
                    imageVector = Icons.Filled.VisibilityOff, // Ojo cerrado/tachado
                    contentDescription = "Ocultar PIN",
                    tint = MainGreen,
                    modifier = Modifier.size(24.dp) // Tamaño estándar para iconos
                )
            }
        }
    }
    // --- FIN COMPONENTE LOCAL ---

    AppScreenShell(
        screenTitle = screenTitle,
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
            Spacer(Modifier.height(32.dp))

            // 1. CAMPO PIN ACTUAL
            PinInputFieldVisual(labelResId = R.string.pin_current_label)
            Spacer(Modifier.height(24.dp)) // Espacio entre campos

            // 2. CAMPO NUEVO PIN
            PinInputFieldVisual(labelResId = R.string.pin_new_label)
            Spacer(Modifier.height(24.dp))

            // 3. CAMPO CONFIRMAR PIN
            PinInputFieldVisual(labelResId = R.string.pin_confirm_label)

            Spacer(Modifier.height(48.dp)) // Espacio antes del botón

            // --- 4. BOTÓN CAMBIAR PIN (Usando AppButton) ---
            AppButton(
                onClick = {
                    navController.navigate("successChangePinConfirmation")
                },
                text = stringResource(R.string.change_pin_button),
                enabled = true,
                buttonHeight = 45.dp,
                buttonWidth = 218.dp,
                containerColor = MainGreen,
                textColor = LettersAndIcons,
                modifier = Modifier.wrapContentWidth(),
                        textStyle = TextStyle(
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Medium, // 500
                            fontSize = 15.sp, // 15px
                            lineHeight = 15.sp, // 100% line-height
                            textAlign = TextAlign.Center,
                            color = LettersAndIcons
                        )

            )

            Spacer(Modifier.weight(1f))
        }
    }
}