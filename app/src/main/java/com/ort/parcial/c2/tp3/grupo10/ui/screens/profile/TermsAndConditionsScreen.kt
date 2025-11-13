package com.ort.parcial.c2.tp3.grupo10.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppScreenShell
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppButton
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily

// --- Tipografía Común ---
private val BODY_TEXT_STYLE = TextStyle(
    fontFamily = PoppinsFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 15.sp, // Line height más grande para párrafos
    color = LettersAndIcons,
    textAlign = TextAlign.Justify
)

@Composable
fun TermsAndConditionsScreen(navController: NavHostController) {

    val STANDARD_HEADER_HEIGHT = 140.dp
    var isAccepted by remember { mutableStateOf(false) } // Estado del checkbox

    AppScreenShell(
        screenTitle = stringResource(R.string.terms_title),
        headerHeight = STANDARD_HEADER_HEIGHT,
        navController = navController,
        startSelectedIndex = 4
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Padding de BottomBar/Scaffold
        ) {
            // Usamos Scroll para el contenido largo
            Column(
                modifier = Modifier
                    .weight(1f) // Ocupa el espacio disponible
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 32.dp, vertical = 20.dp), // Padding interno de la tarjeta
                horizontalAlignment = Alignment.Start
            ) {

                // --- 1. ENCABEZADO Y PÁRRAFO 1 ---
                Text(
                    text = stringResource(R.string.terms_header_1),
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    lineHeight = 15.sp,
                    color = LettersAndIcons,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(text = stringResource(R.string.terms_para_1), style = BODY_TEXT_STYLE)
                Spacer(Modifier.height(16.dp))

                // --- 2. LISTA ORDENADA (Simulada con texto y números) ---
                Text(text = "1. ${stringResource(R.string.terms_list_1_item_1)}", style = BODY_TEXT_STYLE)
                Text(text = "2. ${stringResource(R.string.terms_list_1_item_2)}", style = BODY_TEXT_STYLE)
                Text(text = "3. ${stringResource(R.string.terms_list_1_item_3)}", style = BODY_TEXT_STYLE)
                Text(text = "4. ${stringResource(R.string.terms_list_1_item_4)}", style = BODY_TEXT_STYLE)
                Spacer(Modifier.height(16.dp))

                // --- 3. PÁRRAFO 2 ---
                Text(text = stringResource(R.string.terms_para_2), style = BODY_TEXT_STYLE)
                Spacer(Modifier.height(16.dp))

                // --- 4. LISTA CON VIÑETAS (Simulada con •) ---
                Text(text = "• ${stringResource(R.string.terms_bullet_1)}", style = BODY_TEXT_STYLE)
                Text(text = "• ${stringResource(R.string.terms_bullet_2)}", style = BODY_TEXT_STYLE)
                Spacer(Modifier.height(16.dp))

                // --- 5. PÁRRAFO 3 ---
                Text(text = stringResource(R.string.terms_para_3), style = BODY_TEXT_STYLE)
                Spacer(Modifier.height(16.dp))

                // --- 6. TEXTO Y ENLACE (Simulado con AnnotatedString) ---
                Text(
                    buildAnnotatedString {
                        append(stringResource(R.string.terms_link_prefix))
                        append(" ")
                        withStyle(style = SpanStyle(color = MainGreen, fontWeight = FontWeight.SemiBold)) {
                            append(stringResource(R.string.terms_link_url))
                        }
                    },
                    style = BODY_TEXT_STYLE.copy(lineHeight = 18.sp),
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            // --- SECCIÓN INFERIOR: CHECKBOX Y BOTÓN (NO SCROLLABLE) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    // Margen que estaba en el Scroll, lo ponemos fuera
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 20.dp), // Margen inferior antes de la BottomBar
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Checkbox "I accept..."
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isAccepted = !isAccepted }, // Permite tocar toda la fila
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isAccepted,
                        onCheckedChange = { isAccepted = it },
                        // Estilo del Checkbox (opcional: usar colores de MainGreen)
                        colors = CheckboxDefaults.colors(checkedColor = MainGreen)
                    )
                    Text(
                        text = stringResource(R.string.terms_accept_checkbox),
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Light, // <-- 300
                        fontSize = 13.sp, // <-- 13px
                        lineHeight = 15.sp, // <-- 15px
                        textAlign = TextAlign.Justify,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.height(16.dp))

                // BOTÓN ACCEPT
                AppButton(
                    onClick = {
                        // NAVEGACIÓN SOLICITADA: Volver a Security
                        navController.navigate("security") {
                            // Limpia el back stack hasta Security (si esta Activity lo permite)
                            popUpTo("security") { inclusive = true }
                        }
                    },
                    text = stringResource(R.string.terms_accept_button),
                    enabled = isAccepted, // El botón se habilita al aceptar
                    buttonHeight = 50.dp,
                    containerColor = MainGreen,
                    textColor = Color.White,
                    modifier = Modifier.fillMaxWidth().height(50.dp), // Ajuste a fillMaxWidth
                    textStyle = TextStyle(
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center,
                        color = LettersAndIcons
                    )
                )
            }
        }
    }
}

