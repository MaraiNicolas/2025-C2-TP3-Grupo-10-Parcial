// app/src/main/java/com/ort/parcial/c2/tp3/grupo10/ui/screens/savings/SavingsDetailScreen.kt
package com.ort.parcial.c2.tp3.grupo10.ui.screens.savings

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import com.ort.parcial.c2.tp3.grupo10.ui.components.BottomNavBar
import com.ort.parcial.c2.tp3.grupo10.ui.components.FinancialHeader
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.formatDateToMonth
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.formatDisplayDate
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.ExpenseViewModel
import com.ort.parcial.c2.tp3.grupo10.ui.utils.getCategoryIcon
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*

@Composable
fun SavingsDetailScreen(
    savingsName: String,
    navController: NavHostController? = null,
    bottomSelected: Int = 3,
    onBottomSelect: (Int) -> Unit = {},
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    // Obtener expenses desde Room usando el ViewModel
    val expenses by viewModel.getExpensesByCategory(savingsName)
        .collectAsStateWithLifecycle()
    
    // Obtener el icono de la categoría
    val categoryIcon = getCategoryIcon(savingsName)
    val context = LocalContext.current
    val activity = context as? Activity
    var selectedIndex by remember { mutableIntStateOf(3) }
    
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
                FinancialHeader(
                    title = savingsName,
                    navController = navController,
                    onNotificationClick = { /* Handle notification click */ },
                    totalBalance = "$7,783.00",
                    totalExpense = "-$1,187.40",
                    progressPercentage = 0.30f,
                    progressAmount = "$20,000.00",
                    descriptiveText = "30% of your expenses, looks good."
                )

                // Main Content Area
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
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Lista de expenses agrupadas por mes
                        Box(modifier = Modifier.weight(1f)) {
                            SavingsExpenseList(
                                expenses = expenses,
                                categoryIcon = categoryIcon
                            )
                        }

                        // Botón Add Expenses fijo abajo
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { 
                                    navController?.navigate("add_expense/$savingsName")
                                },
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(42.dp),
                                shape = RoundedCornerShape(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MainGreen
                                )
                            ) {
                            Text(
                                text = "Add Savings",
                                color = Void,
                                fontSize = 16.sp,
                                fontFamily = PoppinsFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavingsExpenseList(expenses: List<Expense>, categoryIcon: Int) {
    val groupedByMonth = expenses.groupBy { formatDateToMonth(it.date) }
    
    if (expenses.isEmpty()) {
        // Mostrar mensaje si no hay expenses
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No expenses found",
                color = Void,
                fontSize = 16.sp,
                fontFamily = PoppinsFamily
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupedByMonth.entries.forEachIndexed { index, entry ->
                val month = entry.key
                val monthExpenses = entry.value
                item(key = "header_$month") {
                    MonthHeader(month, showCalendar = index == 0)
                }
                items(monthExpenses, key = { it.id }) { expense ->
                    ExpenseItem(expense, categoryIcon)
                }
            }
        }
    }
}

@Composable
fun MonthHeader(month: String, showCalendar: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = month,
            color = Void,
            fontSize = 16.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold
        )
        if (showCalendar) {
            Image(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "Calendar",
                modifier = Modifier.size(32.dp)
            )
        } else {
            Spacer(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense, categoryIcon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono circular con fondo azul claro
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(LightBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = categoryIcon),
                contentDescription = expense.title,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Información del expense
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = expense.title,
                color = Void,
                fontSize = 16.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${expense.time} - ${formatDisplayDate(expense.date)}",
                color = OceanBlue,
                fontSize = 14.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Normal
            )
        }

        // Monto
        Text(
            text = "-$${String.format("%.2f", expense.amount)}",
            color = OceanBlueButton,
            fontSize = 16.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Bold
        )
    }
}

