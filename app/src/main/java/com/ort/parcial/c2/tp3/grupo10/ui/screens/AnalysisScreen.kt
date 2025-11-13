package com.ort.parcial.c2.tp3.grupo10.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.sp
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppScreenShell
import com.ort.parcial.c2.tp3.grupo10.ui.components.BottomNavBar

@Composable
fun AnalysisScreen(navController: NavHostController) {

    // El índice 1 corresponde a "Analysis" en tu BottomNavBar
    var selectedIndex by remember { mutableIntStateOf(1) }

    AppScreenShell(
        screenTitle = "Analysis", // Título del header
        // Usamos la altura estándar del header
        navController = navController,
        startSelectedIndex = 1
    ) { padding ->
        // Contenido principal dentro de la tarjeta blanca
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center // Centramos el texto
        ) {
            Text(text = "Análisis", fontSize = 24.sp)
        }
    }
}

