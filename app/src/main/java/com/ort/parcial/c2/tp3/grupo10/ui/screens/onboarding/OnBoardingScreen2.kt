package com.ort.parcial.c2.tp3.grupo10.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ort.parcial.c2.tp3.grupo10.AppStrings
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.theme.BackgroundGreenWhiteAndLetters
import com.ort.parcial.c2.tp3.grupo10.ui.theme.DarkModeGreenBar
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LightGreen2
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.res.stringResource
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily

@Composable
fun OnboardingScreen2(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainGreen)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Sección superior con fondo verde
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.35f)
                    .background(MainGreen),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = AppStrings.ONBOARDING_READY,
                        color = DarkModeGreenBar,
                        fontSize = 24.sp,
                        fontFamily = PoppinsFamily, 
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.take_control_of),
                        color = DarkModeGreenBar,
                        fontSize = 24.sp,
                        fontFamily = PoppinsFamily, 
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.your_finances),
                        color = DarkModeGreenBar,
                        fontSize = 24.sp,
                        fontFamily = PoppinsFamily, 
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }   

            // Sección inferior con fondo claro y esquinas superiores redondeadas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 32.dp,
                            topEnd = 32.dp
                        )
                    )
                    .background(BackgroundGreenWhiteAndLetters),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    // Ilustración con círculo de fondo
                    Box(
                        modifier = Modifier
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Círculo de fondo 
                        Box(
                            modifier = Modifier
                                .size(255.dp)
                                .offset(y = 10.dp)  // Baja el círculo un poco
                                .clip(CircleShape)
                                .background(LightGreen2)
                        )
                        // Imagen encima del círculo (tamaño original)
                        Image(
                            painter = painterResource(id = R.drawable.ic_bankcardmobilephone),
                            contentDescription = stringResource(R.string.bank_card_and_mobile_phone),
                            modifier = Modifier.size(300.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón Next
                    Text(
                        text = stringResource(R.string.next),
                        color = DarkModeGreenBar,
                        fontSize = 18.sp,
                        fontFamily = PoppinsFamily, 
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("register")
                            }
                            .padding(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Indicadores de página
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.padding(bottom = 48.dp),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                    ) {
                        // Primer punto (vacío)
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(
                                    color = MainGreen.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                        )
                        // Segundo punto (relleno - pantalla actual)
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(MainGreen)
                        )
                    }
                }
            }
        }
    }
}