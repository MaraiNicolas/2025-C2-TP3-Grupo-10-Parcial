package com.ort.parcial.c2.tp3.grupo10.ui.screens.transactions

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.MainActivity2
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.domain.model.Transaction
import com.ort.parcial.c2.tp3.grupo10.domain.model.TransactionType
import com.ort.parcial.c2.tp3.grupo10.ui.components.BottomNavBar
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*

@Composable
fun TransactionsScreen(
    navController: NavHostController? = null,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    //bloque INTENT
    val context = LocalContext.current
    val activity = context as? Activity
    var selectedIndex by remember { mutableIntStateOf(2) }

    androidx.compose.material3.Scaffold(
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
    Column(modifier = Modifier.fillMaxSize()) {
        TransactionsHeader(
            title = "Transaction",
            totalBalance = state.totalBalance,
            totalIncome = state.totalIncome,
            totalExpense = state.totalExpense,
            selected = state.filter,
            onBack = { navController?.popBackStack() },
            onSelect = { viewModel.setFilter(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Honeydew)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            state.sections.forEach { section ->
                item(key = section.month) {
                    MonthHeader(section.month)
                }
                items(section.items, key = { it.id }) { tx ->
                    TransactionRow(tx)
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
    }
}

@Composable
private fun TransactionsHeader(
    title: String,
    totalBalance: String,
    totalIncome: String,
    totalExpense: String,
    selected: TransactionsFilter,
    onBack: () -> Unit,
    onSelect: (TransactionsFilter) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MainGreen)
            .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_flecha_atras),
                contentDescription = "Back",
                modifier = Modifier
                    .size(22.dp)
                    .clickable { onBack() }
            )

            Text(
                text = title,
                color = LettersAndIcons,
                fontSize = 24.sp,
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.SemiBold
            )

            Image(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notifications",
                modifier = Modifier.size(29.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Honeydew),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Total Balance",
                    color = Honeydew2,
                    fontFamily = PoppinsFamily,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = totalBalance,
                    color = LettersAndIcons,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SummaryCard(
                label = "Income",
                amount = totalIncome,
                iconRes = R.drawable.ic_income,
                selected = selected == TransactionsFilter.INCOME,
                onClick = { onSelect(TransactionsFilter.INCOME) },
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                label = "Expense",
                amount = totalExpense,
                iconRes = R.drawable.ic_expense,
                selected = selected == TransactionsFilter.EXPENSE,
                onClick = { onSelect(TransactionsFilter.EXPENSE) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SummaryCard(
    label: String,
    amount: String,
    iconRes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (selected) OceanBlue else Honeydew
    val fg = if (selected) Void else LettersAndIcons

    Card(
        colors = CardDefaults.cardColors(containerColor = bg),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = label, color = fg, fontSize = 12.sp, fontFamily = PoppinsFamily)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = amount, color = fg, fontSize = 16.sp, fontFamily = PoppinsFamily, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun MonthHeader(month: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = month, color = Void, fontFamily = PoppinsFamily, fontWeight = FontWeight.SemiBold)
        Image(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun TransactionRow(tx: Transaction) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(LightBlue),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = tx.iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tx.title,
                    fontFamily = PoppinsFamily,
                    fontWeight = FontWeight.Medium,
                    color = Void,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${tx.time} - ${tx.date}",
                    fontFamily = PoppinsFamily,
                    fontSize = 12.sp,
                    color = Honeydew2
                )
            }
            val sign = if (tx.type == TransactionType.EXPENSE) "-" else "+"
            Text(
                text = "$sign$${String.format("%.2f", tx.amount)}",
                fontFamily = PoppinsFamily,
                fontWeight = FontWeight.Medium,
                color = OceanBlue
            )
        }
        Divider(modifier = Modifier.padding(start = 88.dp), color = Caribbean.copy(alpha = 0.3f))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSummaryCard() {
    SummaryCard(label = "Income", amount = "$4,120.00", iconRes = R.drawable.ic_expense, selected = true, onClick = {})
}
