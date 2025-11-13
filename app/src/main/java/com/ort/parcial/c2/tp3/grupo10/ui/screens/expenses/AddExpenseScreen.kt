
package com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses

import android.app.Activity
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
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*
import com.ort.parcial.c2.tp3.grupo10.data.initial.InitialExpensesData
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.geometry.Size
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.ExpenseViewModel
import com.ort.parcial.c2.tp3.grupo10.domain.model.Expense
import com.ort.parcial.c2.tp3.grupo10.ui.utils.getCategoryIcon
import java.util.UUID
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

// Lista de categorías disponibles
val categories = listOf(
    "Food", "Transport", "Medicine", "Groceries",
    "Rent", "Gifts", "Savings", "Entertainment", "More"
)

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

    // Lista de categorías de savings para comparación
    val savingsCategories = InitialExpensesData.getInitialSavings().map { it.name.lowercase().trim() }

    // Estados para los campos
    var date by remember { mutableStateOf(formatDate(Date())) }
    var selectedCategory by remember { mutableStateOf<String?>(defaultCategory) }  // Inicializar con la categoría por defecto

    // Determinar si es savings basándome en la categoría seleccionada (o por defecto si no hay selección)
    val currentCategory = selectedCategory ?: defaultCategory
    val isSavings = currentCategory != null && savingsCategories.contains(currentCategory.lowercase().trim())
    val screenTitle = if (isSavings) "Add Savings" else "Add Expenses"
    var amount by remember { mutableStateOf("") }
    var expenseTitle by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    
    var expandedCategory by remember { mutableStateOf(false) }
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
                // Simple Header with back arrow, title, and notification bell
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
                        // Back Arrow
                        Image(
                            painter = painterResource(id = R.drawable.ic_flecha_atras),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(22.dp)
                                .clickable { navController?.popBackStack() }
                        )

                        // Title
                        Text(
                            text = screenTitle,
                            color = LettersAndIcons,
                            fontSize = 24.sp,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )

                        // Notification Bell
                        Image(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "Notifications",
                            modifier = Modifier
                                .size(29.dp)
                                .clickable { /* Handle notification click */ }
                        )
                    }
                }

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
                                    modifier = Modifier.size(26.dp)
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
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
                                    .width(220.dp)
                                    .height(42.dp),
                                shape = RoundedCornerShape(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MainGreen
                                )
                            ) {
                                Text(
                                    text = "Save",
                                    color = LettersAndIcons,
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
                    color = MainGreen,
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
