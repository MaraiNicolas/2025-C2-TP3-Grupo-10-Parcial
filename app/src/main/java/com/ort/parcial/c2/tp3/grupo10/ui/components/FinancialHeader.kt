package com.ort.parcial.c2.tp3.grupo10.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.OceanBlue
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FinancialHeader(
    title: String,
    navController: NavHostController? = null,
    onNotificationClick: () -> Unit = {},
    totalBalance: String,
    totalExpense: String,
    progressPercentage: Float,
    progressAmount: String,
    descriptiveText: String,
    horizontalPadding: Dp = 18.dp,  // parámetro para controlar el ancho
    showBackArrow: Boolean = true,  // mostrar/ocultar flecha de atrás
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = 12.dp)
        ) {
            Column {
                // Header Section (Green/Teal Background)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainGreen)
                        .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Back Arrow (condicional)
                        if (showBackArrow) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_flecha_atras),
                                contentDescription = stringResource(R.string.back_button_desc),
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { navController?.popBackStack() }
                            )
                        }
                        
                        // Title - comportamiento diferente según si hay flecha o no
                        Text(
                            text = title,
                            color = LettersAndIcons,
                            fontSize = 24.sp,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = if (showBackArrow) TextAlign.Center else TextAlign.Start,
                            modifier = Modifier.weight(1f)
                        )
                        
                        // Notification Bell
                        Image(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = stringResource(R.string.notifications_button_desc),
                            modifier = Modifier
                                .size(29.dp)
                                .clickable(onClick = { navController?.navigate("notification") })
                        )
                    }
                }

                // Summary Section (Same Green Background)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainGreen)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Column {
                        // Total Balance and Expense Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            // Total Balance
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_income),
                                        contentDescription =stringResource(R.string.transactions_income_label),
                                        modifier = Modifier.size(14.dp),
                                        colorFilter = ColorFilter.tint(LettersAndIcons)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stringResource(R.string.home_total_balance),                                        color = LettersAndIcons,
                                        fontSize = 14.sp,
                                        fontFamily = PoppinsFamily,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = totalBalance,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontFamily = PoppinsFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            // Vertical Separator - Barra blanca
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(60.dp)
                                    .background(Color.White)
                            )
                            
                            // Total Expense
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_expense),
                                        contentDescription = stringResource(R.string.transactions_expense_label),
                                        modifier = Modifier.size(13.5.dp),
                                        colorFilter = ColorFilter.tint(LettersAndIcons)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stringResource(R.string.home_total_expense),
                                        color = LettersAndIcons,
                                        fontSize = 14.sp,
                                        fontFamily = PoppinsFamily,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = totalExpense,
                                    color = OceanBlue,
                                    fontSize = 20.sp,
                                    fontFamily = PoppinsFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Progress Bar Section
                        Column {
                            // Barra de progreso personalizada
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    // Sección izquierda (porcentaje - oscura)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(progressPercentage)
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 16.dp,
                                                    bottomStart = 16.dp,
                                                    topEnd = 0.dp,
                                                    bottomEnd = 0.dp
                                                )
                                            )
                                            .background(LettersAndIcons),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            text = "${(progressPercentage * 100).toInt()}%",
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontFamily = PoppinsFamily,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(start = 12.dp)
                                        )
                                    }
                                    
                                    // Sección derecha (resto - clara)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(1f - progressPercentage)
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 0.dp,
                                                    bottomStart = 0.dp,
                                                    topEnd = 16.dp,
                                                    bottomEnd = 16.dp
                                                )
                                            )
                                            .background(Color.Transparent),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Text(
                                            text = progressAmount,
                                            color = LettersAndIcons,
                                            fontSize = 14.sp,
                                            fontFamily = PoppinsFamily,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(end = 12.dp)
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Descriptive Text con icono check
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.svg_check),
                                    contentDescription = "Check",
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = descriptiveText,
                                    color = LettersAndIcons,
                                    fontSize = 14.sp,
                                    fontFamily = PoppinsFamily,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFinancialHeader() {
    FinancialHeader(
    title = stringResource(R.string.home_categories),
        navController = null,
        onNotificationClick = {},
    totalBalance = "$7,783.00",
    totalExpense = "-$1,187.40",
        progressPercentage = 0.30f,
    progressAmount = "$20,000.00",
    descriptiveText = stringResource(R.string.home_descriptive_30p),
        horizontalPadding = 18.dp
    )
}