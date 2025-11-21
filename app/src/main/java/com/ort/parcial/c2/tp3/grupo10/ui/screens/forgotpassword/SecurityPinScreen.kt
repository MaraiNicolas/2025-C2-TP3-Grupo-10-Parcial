package com.ort.parcial.c2.tp3.grupo10.ui.screens.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppButton
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*
import com.ort.parcial.c2.tp3.grupo10.ui.components.SocialAuthButton
import androidx.compose.runtime.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.LocalTextStyle //

@Composable
fun SecurityPinScreen(navController: NavHostController) {

    var pinValue by remember { mutableStateOf("") }
    val maxPinLength = 6

    val onSendAgainClick: () -> Unit = {
        pinValue = ""
    }

    val isPinComplete = pinValue.length == maxPinLength

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainGreen)
    ) {
        // ... (HEADER) ...
        Text(
            text = stringResource(R.string.security_pin_title),
            color = LettersAndIcons,
            fontSize = 30.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )

        //  CUERPO ---
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp),
            color = BackgroundGreenWhiteAndLetters,
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(40.dp))

                // Título de la tarjeta
                Text(
                    text = stringResource(R.string.enter_security_pin_label),
                    color = DarkModeGreenBar,
                    fontFamily = PoppinsFamily,
                    fontSize = 20.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(Modifier.height(40.dp))

                // --- CONTENEDOR INTERACTIVO DEL PIN ---
                PinInputContainer(pinValue = pinValue, onPinChange = { pinValue = it }, maxPinLength = maxPinLength)
                // --- FIN CONTENEDOR PIN ---

                Spacer(Modifier.height(64.dp))

                // --- BOTÓN ACCEPT (Habilitación por estado) ---
                AppButton(
                    text = stringResource(R.string.accept_button),
                    onClick = {
                        if (isPinComplete) {
                            navController.navigate("newPassword")
                        }
                    },
                    enabled = isPinComplete, // Habilitación real
                    buttonWidth = 169.dp,
                    buttonHeight = 36.dp,
                    containerColor = MainGreen,
                    textColor = LettersAndIcons
                )

                Spacer(Modifier.height(16.dp))

                // --- BOTÓN SEND AGAIN ---
                AppButton(
                    text = stringResource(R.string.send_again_button),
                    onClick = onSendAgainClick,
                    buttonWidth = 169.dp,
                    buttonHeight = 36.dp,
                    containerColor = BackgroundGreenWhiteAndLetters,
                    textColor = MainGreen,
                    borderColor = MainGreen
                )

                Spacer(Modifier.weight(1f))

                // ... (El resto de la sección social/registro) ...
                Text(
                    text = stringResource(R.string.or_sign_up_with),
                    color = LettersAndIcons.copy(alpha = 0.5f),
                    fontFamily = LeagueSpartanFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp,
                    lineHeight = 15.sp
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ... (Botones Sociales) ...
                    SocialAuthButton(
                        iconId = R.drawable.ic_facebook,
                        contentDesc = stringResource(R.string.login_with_facebook),
                        onClick = { /* Acción de login con Facebook */ },
                        size = 33.dp
                    )
                    SocialAuthButton(
                        iconId = R.drawable.ic_google,
                        contentDesc = stringResource(R.string.login_with_google),
                        onClick = { /* Acción de login con Google */ },
                        size = 33.dp
                    )
                }

                Spacer(Modifier.height(32.dp))

                // Texto "Don't have an account?"
                Row {
                    Text(
                        text = stringResource(R.string.dont_have_account),
                        color = LettersAndIcons.copy(alpha = 0.5f),
                        fontFamily = LeagueSpartanFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp,
                        lineHeight = 15.sp
                    )
                    Text(
                        text = stringResource(R.string.sign_up_link),
                        color = MainGreen,
                        fontFamily = LeagueSpartanFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp,
                        lineHeight = 15.sp
                    )
                }
                Spacer(Modifier.height(86.dp))
            }
        }
    }
}

// Componente PIN. se usa solo en esta pantalla
@Composable
private fun PinInputContainer(
    pinValue: String,
    onPinChange: (String) -> Unit,
    maxPinLength: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.8.dp)
            .padding(horizontal = 24.dp)
    ) {

        // 1. MAQUETACIÓN VISUAL DE LOS CÍRCULOS (La capa de frente y visible)
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            repeat(maxPinLength) { index ->
                val isFilled = index < pinValue.length

                Box(
                    modifier = Modifier
                        .size(38.8.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, MainGreen, CircleShape)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    // Texto se muestra solo si el PIN está lleno
                    if (isFilled) {
                        Text(
                            text = stringResource(R.string.pin),
                            color = DarkModeGreenBar,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } // FIN de la capa visual de círculos

        // 2. BasicTextField INVISIBLE para la entrada (Capta el teclado)
        // Se dibuja DEBAJO de la capa visual (porque está primero en el Box)
        // Esto asegura que los círculos estén siempre visibles.
        BasicTextField(
            value = pinValue,
            onValueChange = { newValue ->
                if (newValue.length <= maxPinLength && newValue.all { it.isDigit() }) {
                    onPinChange(newValue)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Transparent, textAlign = TextAlign.Center),
            // Esto asegura que el campo de texto se superponga a toda la fila de círculos para capturar el click
            modifier = Modifier.fillMaxSize()
        )
    }
}