// app/src/main/java/com/ort/parcial/c2/tp3/grupo10/ui/screens/expenses/ExpensesScreen.kt
package com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

// Mapeo de categorías a iconos
fun getCategoryIcon(categoryName: String): Int {
    return when (categoryName.lowercase()) {
        "food" -> R.drawable.svg_food
        "transport" -> R.drawable.svg_transport
        "medicine" -> R.drawable.svg_medicine
        "groceries" -> R.drawable.svg_groceries
        "rent" -> R.drawable.svg_rent
        "gifts" -> R.drawable.svg_gift
        "savings" -> R.drawable.svg_savings
        "entertainment" -> R.drawable.svg_entertainment
        else -> R.drawable.svg_more
    }
}

fun formatDateToMonth(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}

@Composable
fun ExpensesScreen(
    categoryName: String,
    navController: NavHostController? = null,
    bottomSelected: Int = 3,
    onBottomSelect: (Int) -> Unit = {},
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    // Obtener expenses desde Room usando el ViewModel
    val expenses by viewModel.getExpensesByCategory(categoryName)
        .collectAsStateWithLifecycle()
    
    // Obtener el icono de la categoría
    val categoryIcon = getCategoryIcon(categoryName)
    
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = bottomSelected,
                onSelect = { index ->
                    when (index) {
                        0 -> navController?.navigate("home")
                        2 -> navController?.navigate("transactions")
                        3 -> navController?.navigate("categories")
                        else -> onBottomSelect(index)
                    }
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
                    title = categoryName,
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
                        // Header con icono de calendario solo aquí
                        ExpenseListHeader()

                        // Lista de expenses agrupadas por mes - con weight para que ocupe el espacio disponible
                        Box(modifier = Modifier.weight(1f)) {
                            ExpenseList(
                                expenses = expenses,
                                categoryIcon = categoryIcon
                            )
                        }

                        // Botón Add Expenses fijo abajo, separado de la lista
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { 
                                navController?.navigate("add_expense/$categoryName")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MainGreen
                            )
                        ) {
                            Text(
                                text = "Add Expenses",
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

@Composable
fun ExpenseListHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // El título del mes se mostrará dinámicamente en MonthHeader
        Spacer(modifier = Modifier.width(0.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Calendar",
            modifier = Modifier.size(20.dp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ExpenseList(expenses: List<Expense>, categoryIcon: Int) {
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
            groupedByMonth.forEach { (month, monthExpenses) ->
                item(key = "header_$month") {
                    MonthHeader(month)
                }
                items(monthExpenses, key = { it.id }) { expense ->
                    ExpenseItem(expense, categoryIcon)
                }
            }
        }
    }
}

@Composable
fun MonthHeader(month: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = month,
            color = Void,
            fontSize = 16.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.SemiBold
        )
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
        // Icono circular con fondo azul claro - usar el icono de la categoría
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

fun formatDisplayDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMMM dd", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}
