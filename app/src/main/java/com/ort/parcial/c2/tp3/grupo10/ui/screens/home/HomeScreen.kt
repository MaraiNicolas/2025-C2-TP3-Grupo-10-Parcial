package com.ort.parcial.c2.tp3.grupo10.ui.screens.home

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.MainActivity2
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.BottomNavBar
import com.ort.parcial.c2.tp3.grupo10.ui.components.FinancialHeader
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*

data class HomeTransaction(
    val title: String,
    val subtitle: String,
    val category: String,
    val amount: String,
    val isNegative: Boolean,
    val iconRes: Int,
    val period: String
)

@Composable
fun HomeScreen(
    navController: NavHostController? = null,
    bottomSelected: Int = 0,
    onBottomSelect: (Int) -> Unit = {}
) {
    //bloque INTENT
    val context = LocalContext.current
    val activity = context as? Activity
    var selectedIndex by remember { mutableIntStateOf(0) }
    val transactions = listOf(
        HomeTransaction("Salary", "18:27 - April 30", "Monthly", "$4.000,00", false, R.drawable.money, "Monthly"),
        HomeTransaction("Groceries", "17:00 - April 24", "Pantry", "-$100,00", true, R.drawable.svg_groceries, "Weekly"),
        HomeTransaction("Rent", "8:30 - April 15", "Rent", "-$674,40", true, R.drawable.svg_rent, "Monthly"),
        HomeTransaction("Coffee", "09:10 - April 30", "Food", "-$8,50", true, R.drawable.svg_food, "Daily"),
        HomeTransaction("Snacks", "10:40 - April 30", "Food", "-$12,00", true, R.drawable.svg_food, "Daily")
    )

    var selectedTab by remember { mutableStateOf("Monthly") }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainGreen)
        ) {
            // Header de balance
            FinancialHeader(
                title = "Hi, Welcome Back",
                navController = navController,
                onNotificationClick = { },
                totalBalance = "$7,783.00",
                totalExpense = "-$1,187.40",
                progressPercentage = 0.30f,
                progressAmount = "$20,000.00",
                descriptiveText = "30% of your expenses, looks good.",
                showBackArrow = false
            )

            // Contenedor redondeado del contenido principal
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
                    .background(BackgroundGreenWhiteAndLetters)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .padding(bottom = paddingValues.calculateBottomPadding())
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // ---- Tarjeta Savings on Goals ----
                    SavingsSection()


                    // ---- Selector Daily/Weekly/Monthly ----
                    PeriodSelector(selectedTab) { selectedTab = it }

                    val filteredTransactions = remember(selectedTab) {
                        transactions.filter { it.period == selectedTab }
                    }
                    // ---- Lista de transacciones ----
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(filteredTransactions) { tx ->
                            TransactionRow(tx)
                        }
                    }
                }
            }
        }
    }
}

// -------- Tarjeta “Savings on Goals” --------
@Composable
fun SavingsSection() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
        color = MainGreen,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🟩 IZQUIERDA: AUTO + “Savings on Goals”
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🔹 Círculo con borde mitad blanco y mitad azul
                Box(
                    modifier = Modifier.size(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Fondo translúcido
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                    )

                    // Dibuja los bordes (mitad azul, mitad blanco)
                    Canvas(modifier = Modifier.matchParentSize()) {
                        // Arco azul (mitad superior)
                        drawArc(
                            color = OceanBlueButton,
                            startAngle = -90f,
                            sweepAngle = 180f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5.dp.toPx())
                        )
                        // Arco blanco (mitad inferior)
                        drawArc(
                            color = Color.White,
                            startAngle = 90f,
                            sweepAngle = 180f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5.dp.toPx())
                        )
                    }

                    // Ícono del auto
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_car),
                        contentDescription = "Savings icon",
                        tint = LettersAndIcons,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Savings On Goals",
                    color = LettersAndIcons,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            // Línea separadora vertical
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .width(3.dp)
                    .height(100.dp)
                    .background(Color.White.copy(alpha = 0.8f))
            )


            // 🟩 DERECHA: Revenue + Food
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // 🔹 Revenue Last Week
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.money),
                        contentDescription = "Revenue",
                        tint = LettersAndIcons,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "Revenue Last Week",
                            color = LettersAndIcons,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "$4.000,00",
                            color = LettersAndIcons,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                // Línea separadora horizontal
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(2.dp)
                        .height(2.dp)
                        .background(Color.White.copy(alpha = 0.8f))
                )

                // 🔵 Food Last Week
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.svg_food),
                        contentDescription = "Food",
                        tint = LettersAndIcons,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "Food Last Week",
                            color = LettersAndIcons,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "-$100.00",
                            color = OceanBlueButton,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}


// -------- Selector Daily/Weekly/Monthly --------
@Composable
fun PeriodSelector(selected: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE7F8F2))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOf("Daily", "Weekly", "Monthly").forEach { label ->
            val isSelected = selected == label
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) MainGreen else Color.Transparent)
                    .clickable { onSelect(label) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    color = if (isSelected) Color.White else Void,
                    fontFamily = PoppinsFamily,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

// -------- Fila de transacción --------
@Composable
fun TransactionRow(tx: HomeTransaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono circular
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(LightBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = tx.iconRes),
                contentDescription = tx.title,
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = tx.title,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Medium,
                color = Void,
                fontSize = 16.sp
            )
            Text(
                text = tx.subtitle,
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = tx.category,
                fontFamily = PoppinsFamily,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = tx.amount,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Bold,
                color = if (tx.isNegative) OceanBlueButton else Void,
                fontSize = 15.sp
            )
        }
    }
}
