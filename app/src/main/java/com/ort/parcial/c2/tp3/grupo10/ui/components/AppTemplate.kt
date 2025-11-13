package com.ort.parcial.c2.tp3.grupo10.ui.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.theme.Honeydew
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MyApplicationTheme
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily
import com.ort.parcial.c2.tp3.grupo10.ui.theme.Void
// USAR AppScreenShell PARA UNIFICAR  TEMPLATES
/*
@Composable
fun AppTemplate(
    title: String,
    navController: NavController? = null,
    onBack: (() -> Unit)? = null,
    onBellClick: () -> Unit,
    bottomSelected: Int,
    onBottomSelect: (Int) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    //bloque INTENT
    val context = LocalContext.current
    val activity = context as? Activity
    var selectedIndex by remember { mutableIntStateOf(3) }
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavBar(
                selected = selectedIndex,
                navController = navController!!, // <-- Pasamos el NavController
                onSelect = { index ->
                    selectedIndex = index // Solo actualizamos el estado visual
                    // La lógica del Intent/navigate está en BottomNavBar.kt
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainGreen)
        ) {
            SimpleHeader(
                title = title,
                navController = navController,
                onBack = onBack,
                onBellClick = onBellClick,

            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
                    .background(Honeydew)
            ) {
                Box(modifier = Modifier.padding(innerPadding)) {
                    content(PaddingValues())
                }
            }
        }
    }
}

@Composable
private fun SimpleHeader(
    title: String,
    navController: NavController? = null,
    onBack: (() -> Unit)?,
    onBellClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Ícono de "Atrás" a la izquierda
        if (onBack != null || navController != null) {
            val backAction: () -> Unit = {
                if (onBack != null) {
                    // 1. Si se pasó una función 'onBack' personalizada, la ejecutamos.
                    onBack()
                } else {
                    // 2. Si no, y tenemos un NavController, usamos la navegación estándar de Compose.
                    navController?.popBackStack()
                }
            }
            IconButton(onClick = backAction, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_flecha_atras),
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        // Título en el centro
        Text(
            text = title,
            color = Void,
            fontSize = 20.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )





        IconButton(
            onClick = { navController?.navigate("notification") },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(

                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notificaciones",
                    tint = Void,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun AppTemplatePreview() {
    MyApplicationTheme {
        AppTemplate(
            title = "Preview Title",
            onBack = {},
            onBellClick = {},
            bottomSelected = 0,
            onBottomSelect = {}
        ) {
            // Contenido de ejemplo para la vista previa
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Aquí va el contenido de la pantalla")
            }
        }
    }
}
*/