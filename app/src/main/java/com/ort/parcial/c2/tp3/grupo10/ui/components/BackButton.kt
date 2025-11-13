package com.ort.parcial.c2.tp3.grupo10.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.theme.* // Asegúrate de importar el color de tu tema

@Composable
fun BackButton(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentActivity = context as? Activity

    val onClickAction: () -> Unit = {
        // 1. Intentar hacer pop de la pila interna (NavHost).
        val popped = navController.popBackStack()

        // 2. Si popBackStack devuelve false, estamos en la raíz del NavHost.
        //    En ese caso, debemos finalizar la Activity actual para volver a la anterior.
        if (!popped) {
            // FIX: Forzar la navegación al destino seguro ("home") para evitar cerrar la app.

            // Obtenemos la ruta actual para saber si ya estamos en una ruta de salida
            val currentRoute = navController.currentDestination?.route

            if (currentRoute == "home" || currentRoute == "login" || currentRoute == "splash") {
                // Si ya estamos en la salida principal, cerramos la Activity
                currentActivity?.finish()
            } else {
                // Si estamos en cualquier otra raíz (ej., "notification"), navegamos a "home"
                // y limpiamos el historial hasta allí.
                navController.navigate("home") {
                    // Esto limpia el NavHost y va a Home (el punto de salida seguro)
                    popUpTo("home") { inclusive = true }
                }
            }
        }
    }

    IconButton(
        onClick = onClickAction,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bring_back),
            contentDescription = stringResource(R.string.back_button_desc),
            tint = Color.White, // Usamos Color.White directamente si es tu Color.
            modifier = Modifier.size(19.dp)
        )
    }
}