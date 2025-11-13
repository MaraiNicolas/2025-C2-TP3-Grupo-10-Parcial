package com.ort.parcial.c2.tp3.grupo10.ui.components

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ort.parcial.c2.tp3.grupo10.MainActivity2
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.theme.Caribbean
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LightGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.Void
import androidx.navigation.NavController
import com.ort.parcial.c2.tp3.grupo10.MainActivity
import android.app.Activity
import androidx.navigation.compose.rememberNavController

data class BottomNavItem(
    val label: String,
    @DrawableRes val iconRes: Int
)

val bottomNavItems = listOf(
    BottomNavItem("home", R.drawable.ic_home),
    BottomNavItem("analysis", R.drawable.ic_analysis),
    BottomNavItem("transactions", R.drawable.ic_transactions),
    BottomNavItem("categories", R.drawable.ic_category),
    BottomNavItem("profile", R.drawable.ic_perfil)
)


@Composable
fun BottomNavBar(
    selected: Int,
    navController: NavController, // <-- NavController
    onSelect: (Int) -> Unit,
    cornerRadius: Dp = 34.dp
) {
    val context = LocalContext.current
    val currentActivity = context as Activity // La Activity actual

    // Hardcoded Strings para la navegación externa
    val NAV_DESTINATION_KEY = "startDestination"

    // Determinamos si estamos en MainActivity (la Activity principal)
    val isUsedInMainActivity = currentActivity::class.java == MainActivity::class.java

    // Función auxiliar para navegar de vuelta a MainActivity (cierra MainActivity2)
    fun navigateBackToMain(route: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            // Flags de reinicio forzado: Destruye MainActivity2 y fuerza a MainActivity a arrancar limpio
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(NAV_DESTINATION_KEY, route)
        }
        context.startActivity(intent)
        if (!isUsedInMainActivity) {
            currentActivity.finish() // Cierra MainActivity2
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LightGreen,
                shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
            )
            .height(90.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                // 1. LÓGICA DE NAVEGACIÓN
                                val destination = item.label.lowercase() // home, transactions, categories, profile

                                when (index) {
                                    // -----------------------------------------------------
                                    // A. BOTÓN PROFILE (Índice 4): Siempre navegación externa
                                    // -----------------------------------------------------
                                    4 -> {
                                        if (isUsedInMainActivity) {
                                            // Si estamos en MainActivity, lanzamos MainActivity2
                                            val intent = Intent(context, MainActivity2::class.java)
                                            context.startActivity(intent)

                                        } else {
                                            // Si estamos en MainActivity2, no hacemos nada (ya estamos en profile)
                                        }
                                    }

                                    // -----------------------------------------------------
                                    // B. BOTONES DE RETORNO (0, 2, 3)
                                    // -----------------------------------------------------
                                    0, 1, 2, 3 -> {
                                        if (isUsedInMainActivity) {
                                            // Si estamos en MainActivity, usamos navegación Compose (interna)
                                            navController.navigate(destination) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        } else {
                                            // Si estamos en MainActivity2 (flujo de perfil), CERRAMOS y NAVEGAMOS
                                            navigateBackToMain(destination)
                                        }
                                    }
                                }

                                /*
                                    // -----------------------------------------------------
                                    // C. ANÁLISIS (Índice 1) - Asumimos navegación Compose interna en Main
                                    // -----------------------------------------------------
                                    1 -> {
                                        if (isUsedInMainActivity) {
                                            navController.navigate(destination) {
                                                launchSingleTop = true
                                            }
                                        } else {
                                            // Si está en el flujo de perfil, lo mandamos a Main/Analysis
                                            navigateBackToMain(destination)
                                        }
                                    }
                                }
*/
                                // 2. ACTUALIZACIÓN VISUAL (Callback)
                                onSelect(index)
                            }
                        )
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (selected == index) {
                            Box(
                                modifier = Modifier
                                    .size(size = 50.dp)
                                    .background(
                                        color = Caribbean,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            )
                        }
                        Image(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.label,
                            colorFilter = ColorFilter.tint(Void)
                        )
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewBottomNavBar() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = selectedIndex,
                navController = navController,
                onSelect = { newIndex ->
                    selectedIndex = newIndex
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Contenido de la pantalla")
        }
    }
}