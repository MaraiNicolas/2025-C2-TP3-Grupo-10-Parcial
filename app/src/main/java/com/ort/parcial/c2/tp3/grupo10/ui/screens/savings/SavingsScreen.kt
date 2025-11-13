package com.ort.parcial.c2.tp3.grupo10.ui.screens.savings

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.MainActivity2
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.BottomNavBar
import com.ort.parcial.c2.tp3.grupo10.ui.components.FinancialHeader
import com.ort.parcial.c2.tp3.grupo10.ui.theme.BackgroundGreenWhiteAndLetters
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LightBlue
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.OceanBlueButton
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily
import com.ort.parcial.c2.tp3.grupo10.ui.theme.Void
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import com.ort.parcial.c2.tp3.grupo10.domain.model.Category
import com.ort.parcial.c2.tp3.grupo10.data.initial.InitialExpensesData
import kotlinx.coroutines.delay




// Ya no necesitamos SavingsItem, usamos Category directamente desde InitialExpensesData

@Composable
fun SavingsScreen(
    navController: NavHostController? = null,
    bottomSelected: Int = 3,
    onBottomSelect: (Int) -> Unit = {}
) {
    //bloque INTENT
    val context = LocalContext.current
    val activity = context as? Activity
    var selectedIndex by remember { mutableIntStateOf(3) }
    val savingsItems = InitialExpensesData.getInitialSavings()
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = selectedIndex,
                navController = navController!!, // <-- Pasamos el NavController
                onSelect = { index ->
                    selectedIndex = index // Solo actualizamos el estado visual
                    // La lógica del Intent/navigate está en BottomNavBar.kt
                }
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MainGreen)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Usar el nuevo componente reutilizable
                FinancialHeader(
                    title = "Savings",
                    navController = navController,
                    onNotificationClick = { /* Handle notification click */ },
                    totalBalance = "$7,783.00",
                    totalExpense = "-$1,187.40",
                    progressPercentage = 0.30f,
                    progressAmount = "$20,000.00",
                    descriptiveText = "30% of your expenses, looks good."
                )

                // Main Content Area (Light Green/Off-White Background)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 45.dp,
                                topEnd = 45.dp
                            )
                        )
                        .background(BackgroundGreenWhiteAndLetters)
                        .padding(24.dp)
                        .padding(bottom = paddingValues.calculateBottomPadding()),
                ) {
                    // Savings Grid - scrollable
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Row 1
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            SavingsItemButton(
                                savingsItem = savingsItems[0],
                                onClick = { navController?.navigate("savings/${savingsItems[0].name}") }
                            )
                            SavingsItemButton(
                                savingsItem = savingsItems[1],
                                onClick = { navController?.navigate("savings/${savingsItems[1].name}") }
                            )
                            SavingsItemButton(
                                savingsItem = savingsItems[2],
                                onClick = { navController?.navigate("savings/${savingsItems[2].name}") }
                            )
                        }

                        // Row 2
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            SavingsItemButton(
                                savingsItem = savingsItems[3],
                                onClick = { navController?.navigate("savings/${savingsItems[3].name}") }
                            )
                            // Espacios vacíos para mantener el layout
                            Spacer(modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        // Botón "Add More" centrado
                        Spacer(modifier = Modifier.height(64.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    // TODO: Implementar acción de agregar más
                                },
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(42.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MainGreen
                                )
                            ) {
                                Text(
                                    text = "Add More",
                                    color = LettersAndIcons,
                                    fontSize = 16.sp,
                                    fontFamily = PoppinsFamily,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.SavingsItemButton(
    savingsItem: Category,
    onClick: () -> Unit = {}
) {
    // Estado para indicar que se hizo click (feedback visual)
    var isClicked by remember { mutableStateOf(false) }

    // Resetear el estado después de un tiempo corto
    LaunchedEffect(isClicked) {
        if (isClicked) {
            delay(200) // 200ms de feedback visual
            isClicked = false
        }
    }

    // Column principal que contiene el cuadrado y el texto
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cuadrado con icono
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(27.dp))
                .clickable(
                    onClick = {
                        isClicked = true
                        onClick()
                    }
                )
                .background(
                    color = if (isClicked) OceanBlueButton else LightBlue
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = savingsItem.iconResId),
                contentDescription = savingsItem.name,
                modifier = Modifier.size(50.dp),
                tint = Color.White
            )
        }

        // Texto debajo del cuadrado
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = savingsItem.name,
            color = Void,
            fontSize = 14.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}
