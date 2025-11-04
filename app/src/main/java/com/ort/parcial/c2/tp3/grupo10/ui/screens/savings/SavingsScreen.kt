/*package com.ort.parcial.c2.tp3.grupo10.ui.screens.savings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import com.ort.parcial.c2.tp3.grupo10.ui.components.BottomNavBar
import com.ort.parcial.c2.tp3.grupo10.ui.components.FinancialHeader
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.ExpenseList
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.ExpenseListHeader
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.getCategoryIcon

import com.ort.parcial.c2.tp3.grupo10.ui.theme.BackgroundGreenWhiteAndLetters
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily
import com.ort.parcial.c2.tp3.grupo10.ui.theme.Void
import org.json.JSONObject

fun getCategoryIcon(categoryName: String): Int {-
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

fun loadSavingsFromJson(context: Context, categoryName: String): List<Expense> {
    return try {
        val jsonString = context.assets.open("savings.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        val expensesArray = jsonObject.getJSONArray("savings")

        val expenses = mutableListOf<Expense>()
        for (i in 0 until expensesArray.length()) {
            val expenseJson = expensesArray.getJSONObject(i)
            val category = expenseJson.getString("category")

            // Filtrar por categoría
            if (category.equals(categoryName, ignoreCase = true)) {
                expenses.add(
                    Expense(
                        id = expenseJson.getString("id"),
                        title = expenseJson.getString("title"),
                        amount = expenseJson.getDouble("amount"),
                        date = expenseJson.getString("date"),
                        time = expenseJson.getString("time"),
                        category = category,
                        iconResId = getCategoryIcon(category)
                    )
                )
            }
        }
        expenses
    } catch (e: Exception) {
        emptyList()
    }
}


@Composable
fun SavingsScreen(categoryName: String,
                  navController: NavHostController? = null,
                  bottomSelected: Int = 3,
                  onBottomSelect: (Int) -> Unit = {}){
    val context = LocalContext.current
    val expenses = remember { mutableStateOf<List<Expense>>(emptyList()) }

    LaunchedEffect(categoryName) {
       //expenses.value = loadExpensesFromJson(context, categoryName)
    }
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
//                            ExpenseList(
//                                expenses = expenses.value,
//                                categoryIcon = categoryIcon
//                            )
                        }

                        // Botón Add Expenses fijo abajo, separado de la lista
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                navController?.navigate("add_expense")
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
}*/