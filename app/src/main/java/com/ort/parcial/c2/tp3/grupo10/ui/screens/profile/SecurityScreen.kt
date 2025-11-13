package com.ort.parcial.c2.tp3.grupo10.ui.screens.profile

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppScreenShell
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily

@Composable
fun SecurityScreen(navController: NavHostController) {

    val STANDARD_HEADER_HEIGHT = 140.dp

    AppScreenShell(
        screenTitle = stringResource(R.string.security_title),
        headerHeight = STANDARD_HEADER_HEIGHT,
        navController = navController,
        startSelectedIndex = 4
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // --- TÍTULO DE SECCIÓN "Security" (Estilo Figma) ---
            Text(
                text = stringResource(R.string.security_title),
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 22.sp,
                modifier = Modifier.padding(start = 16.dp, top = 40.dp, bottom = 10.dp)
            )


            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable(onClick = { navController.navigate("change_pin") }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.option_change_pin),
                        color = LettersAndIcons,
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                // 2. FINGERPRINT
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        // NAVEGACIÓN DIRECTA
                        .clickable(onClick = { navController.navigate("fingerprint_list") }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.option_fingerprint),
                        color = LettersAndIcons,
                        // Estilos Figma: Medium 15sp
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable(onClick = { navController.navigate("terms_and_conditions") }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.option_terms_and_conditions),
                        color = LettersAndIcons,
                        // Estilos Figma: Medium 15sp
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            Spacer(Modifier.weight(1f))
        }
    }
}