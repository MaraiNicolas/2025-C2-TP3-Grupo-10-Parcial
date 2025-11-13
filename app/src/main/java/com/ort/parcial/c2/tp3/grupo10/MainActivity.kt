package com.ort.parcial.c2.tp3.grupo10

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ort.parcial.c2.tp3.grupo10.ui.screens.AnalysisScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.GenericConfirmationScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.WelcomeScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.auth.LoginScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.auth.RegisterScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.categories.CategoriesScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.AddExpenseScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.ExpensesScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.savings.SavingsScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.savings.SavingsDetailScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.forgotpassword.ForgotPasswordScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.forgotpassword.NewPasswordScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.forgotpassword.SecurityPinScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.home.HomeScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.notifications.NotificationsScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.onboarding.OnboardingScreen1
import com.ort.parcial.c2.tp3.grupo10.ui.screens.onboarding.OnboardingScreen2
import com.ort.parcial.c2.tp3.grupo10.ui.screens.splash.SplashScreen // Corrected import
import com.ort.parcial.c2.tp3.grupo10.ui.screens.transactions.TransactionsScreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                // 1. OBTENEMOS EL INTENT DENTRO DEL COMPOSABLE
                val context = LocalContext.current
                val currentActivity = context as? ComponentActivity

                // 1. LECTURA DE RUTA INICIAL (desde onCreate)
                val externalStartDestination = currentActivity?.intent?.getStringExtra("startDestination")
                val initialRoute = externalStartDestination ?: "splash"

                // 2. EFECTO CLAVE: NAVEGACIÓN FORZADA Y CONSUMO DEL EXTRA
                // Observa el Intent de la Activity
                LaunchedEffect(currentActivity?.intent) {
                    val intentToProcess = currentActivity?.intent
                    val destination = intentToProcess?.getStringExtra("startDestination")

                    if (destination != null) {

                        intentToProcess.removeExtra("startDestination")

                        // Navegar al destino
                        navController.navigate(destination) {
                            launchSingleTop = true
                        }
                    }
                }

               NavHost(
    navController = navController,
    startDestination = initialRoute
)

 {
                    composable("splash") { SplashScreen(navController = navController) }
                    composable("welcome") { WelcomeScreen(navController = navController) }
                    composable("login") { LoginScreen(navController = navController) }
                    composable("register") { RegisterScreen(navController = navController) }
                    composable("home") { HomeScreen(navController =navController) }
                    composable("notification") { NotificationsScreen(navController =navController) }

                    // Onboarding
                    composable("onboarding1") { OnboardingScreen1(navController = navController) }
                    composable("onboarding2") { OnboardingScreen2(navController = navController) }

                    composable("analysis") { AnalysisScreen(navController = navController) }

                    // Categories & Expenses
                    composable("categories") { CategoriesScreen(navController = navController) }
                    composable("savings") { SavingsScreen(navController = navController) }
                    composable(
                        route = "savings/{savingsName}",
                        arguments = listOf(
                            navArgument("savingsName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val savingsName = backStackEntry.arguments?.getString("savingsName") ?: "Travel"
                        SavingsDetailScreen(
                            savingsName = savingsName,
                            navController = navController
                        )
                    }
                    composable(
                        route = "expenses/{categoryName}",
                        arguments = listOf(
                            navArgument("categoryName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Food"
                        ExpensesScreen(
                            categoryName = categoryName,
                            navController = navController
                        )
                    }
                    composable("add_expense") { AddExpenseScreen(navController = navController) }

                    // Forgot Password Flow
                    composable("forgotPassword") { ForgotPasswordScreen(navController = navController) }
                    composable("securityPin") { SecurityPinScreen(navController = navController) }
                    composable("newPassword") { NewPasswordScreen(navController = navController) }
                    composable("successConfirmation") {GenericConfirmationScreen(navController = navController, message = stringResource(R.string.success_message), destinationRoute = "login")}

                    // Transactions
                    composable("transactions") { TransactionsScreen(navController) }
                    // Ruta con categoría (cuando viene desde ExpensesScreen)
                    composable(
                        route = "add_expense/{categoryName}",
                        arguments = listOf(
                            navArgument("categoryName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val categoryName = backStackEntry.arguments?.getString("categoryName")
                        AddExpenseScreen(
                            defaultCategory = categoryName,
                            navController = navController
                        )
                    }
                    // Ruta sin categoría (cuando viene desde otra pantalla)
                    composable("add_expense") {
                        AddExpenseScreen(
                            defaultCategory = null,
                            navController = navController
                        )
                    }

                    // Reset password flow
                    //composable("security_pin") { SecurityPinScreen(navController = navController) }
                    //composable("new_password") { NewPasswordScreen(navController = navController) }
                    //composable("password_changed") { SuccessConfirmationScreen(navController = navController) }
                }
            }
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        // El LaunchedEffect en setContent() detectará este cambio y activará la navegación.
    }
}