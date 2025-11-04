
package com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.BottomNavBar
import com.ort.parcial.c2.tp3.grupo10.ui.components.FinancialHeader
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.geometry.Size
import androidx.compose.material3.Icon
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.ExpenseViewModel
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import java.util.UUID

@Composable
fun AddExpenseScreen(
    defaultCategory: String? = null,  // Categoría por defecto cuando se navega desde ExpensesScreen
    navController: NavHostController? = null,
    bottomSelected: Int = 3,
    onBottomSelect: (Int) -> Unit = {},
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    // Obtener categorías desde Room
    val categoriesFlow = viewModel.getAllCategories()
    val categories by categoriesFlow.collectAsStateWithLifecycle()
    val categoryNames = categories.map { it.name }
    
    // Estados para los campos
    var date by remember { mutableStateOf(formatDate(Date())) }
    var selectedCategory by remember { mutableStateOf<String?>(defaultCategory) }  // Inicializar con la categoría por defecto
    var amount by remember { mutableStateOf("") }
    var expenseTitle by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    
    var expandedCategory by remember { mutableStateOf(false) }

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
                    title = "Add Expenses",
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Date Input
                        ExpenseInputField(
                            label = "Date",
                            value = date,
                            onValueChange = { date = it },
                            placeholder = "Select date",
                            trailingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_calendar),
                                    contentDescription = "Calendar",
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            readOnly = true,
                            onClick = { /* TODO: Abrir date picker */ }
                        )

                        // Category Input (Dropdown)
                        CategoryDropdownField(
                            label = "Category",
                            selectedCategory = selectedCategory,
                            onCategorySelected = { selectedCategory = it },
                            categories = categoryNames,
                            expanded = expandedCategory,
                            onExpandedChange = { expandedCategory = it }
                        )

                        // Amount Input
                        ExpenseInputField(
                            label = "Amount",
                            value = amount,
                            onValueChange = { 
                                // Solo permitir números y punto decimal
                                val filtered = it.filter { char -> 
                                    char.isDigit() || char == '.' 
                                }
                                // Validar que solo haya un punto decimal
                                if (filtered.count { it == '.' } <= 1) {
                                    amount = filtered
                                }
                            },
                            placeholder = "$0.00",
                            keyboardType = KeyboardType.Decimal,
                            leadingContent = {
                                Text(
                                    text = "$",
                                    color = Void,
                                    fontSize = 16.sp,
                                    fontFamily = PoppinsFamily
                                )
                            }
                        )

                        // Expense Title Input
                        ExpenseInputField(
                            label = "Expense Title",
                            value = expenseTitle,
                            onValueChange = { expenseTitle = it },
                            placeholder = "Enter expense title"
                        )

                        // Message Input (TextArea)
                        ExpenseTextAreaField(
                            label = "Enter Message",
                            value = message,
                            onValueChange = { message = it },
                            placeholder = "Enter Message"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Save Button
                        Button(
                            onClick = { 
                                // Validar que todos los campos estén completos
                                if (expenseTitle.isNotBlank() && 
                                    amount.isNotBlank() && 
                                    selectedCategory != null && 
                                    date.isNotBlank()) {
                                    
                                    try {
                                        // Convertir la fecha del formato "MMMM dd, yyyy" a "yyyy-MM-dd"
                                        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
                                        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                        val parsedDate = dateFormat.parse(date)
                                        val formattedDate = parsedDate?.let { outputFormat.format(it) } 
                                            ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                        
                                        // Obtener la hora actual
                                        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                                        val currentTime = timeFormat.format(Date())
                                        
                                        // Crear el expense con la categoría seleccionada
                                        val category = selectedCategory!! // Ya validado que no es null
                                        val expense = Expense(
                                            id = UUID.randomUUID().toString(),
                                            title = expenseTitle,
                                            amount = amount.toDoubleOrNull() ?: 0.0,
                                            date = formattedDate,
                                            time = currentTime,
                                            category = category,
                                            iconResId = getCategoryIcon(category)
                                        )
                                        
                                        // Guardar en Room
                                        viewModel.addExpense(expense)
                                        
                                        // Volver a la pantalla anterior
                                        navController?.popBackStack()
                                    } catch (e: Exception) {
                                        // Si hay error al parsear la fecha, usar la fecha actual
                                        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                                        
                                        // Crear el expense con la categoría seleccionada
                                        val category = selectedCategory!! // Ya validado que no es null
                                        val expense = Expense(
                                            id = UUID.randomUUID().toString(),
                                            title = expenseTitle,
                                            amount = amount.toDoubleOrNull() ?: 0.0,
                                            date = currentDate,
                                            time = currentTime,
                                            category = category,
                                            iconResId = getCategoryIcon(category)
                                        )
                                        
                                        viewModel.addExpense(expense)
                                        navController?.popBackStack()
                                    }
                                }
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
                                text = "Save",
                                color = Color.White,
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
fun ExpenseInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Void,
            fontSize = 14.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Void.copy(alpha = 0.6f),
                    fontSize = 16.sp,
                    fontFamily = PoppinsFamily
                )
            },
            readOnly = readOnly,
            enabled = !readOnly,
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (onClick != null) {
                        Modifier.clickable { onClick() }
                    } else {
                        Modifier
                    }
                ),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            leadingIcon = leadingContent,
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = MainGreen,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Void,
                unfocusedTextColor = Void,
                cursorColor = MainGreen
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontFamily = PoppinsFamily,
                color = Void
            )
        )
    }
}

@Composable
fun CategoryDropdownField(
    label: String,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit,
    categories: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Void,
            fontSize = 14.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Box {
            OutlinedTextField(
                value = selectedCategory ?: "",
                onValueChange = {},
                placeholder = {
                    Text(
                        text = "Select the category",
                        color = Void.copy(alpha = 0.6f),
                        fontSize = 16.sp,
                        fontFamily = PoppinsFamily
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    }
                    .clickable { onExpandedChange(!expanded) },
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    IconButton(
                        onClick = { onExpandedChange(!expanded) }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.svg_arrowdown),
                            contentDescription = "Dropdown",
                            modifier = Modifier.size(20.dp),
                            tint = Void
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MainGreen,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Void,
                    unfocusedTextColor = Void
                ),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                    fontFamily = PoppinsFamily,
                    color = Void
                )
            )
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    .background(Color.White)
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = category,
                                color = Void,
                                fontSize = 16.sp,
                                fontFamily = PoppinsFamily
                            )
                        },
                        onClick = {
                            onCategorySelected(category)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpenseTextAreaField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Void.copy(alpha = 0.7f),
            fontSize = 14.sp,
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Void.copy(alpha = 0.6f),
                    fontSize = 16.sp,
                    fontFamily = PoppinsFamily
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = MainGreen,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Void,
                unfocusedTextColor = Void,
                cursorColor = MainGreen
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontFamily = PoppinsFamily,
                color = Void
            ),
            maxLines = 5,
            singleLine = false
        )
    }
}

fun formatDate(date: Date): String {
    val format = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
    return format.format(date)
}
