package com.ort.parcial.c2.tp3.grupo10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ort.parcial.c2.tp3.grupo10.ui.screens.GenericConfirmationScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.WelcomeScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.auth.LoginScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.auth.RegisterScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.categories.CategoriesScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.AddExpenseScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.expenses.ExpensesScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.forgotpassword.ForgotPasswordScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.forgotpassword.NewPasswordScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.forgotpassword.SecurityPinScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.forgotpassword.SuccessConfirmationScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.home.HomeScreen
import com.ort.parcial.c2.tp3.grupo10.ui.screens.onboarding.OnboardingScreen1
import com.ort.parcial.c2.tp3.grupo10.ui.screens.onboarding.OnboardingScreen2
import com.ort.parcial.c2.tp3.grupo10.ui.screens.profile.ProfileScreen
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
               NavHost(
    navController = navController,
    startDestination = "splash" 
)

 {
                    composable("splash") { SplashScreen(navController = navController) }
                    composable("welcome") { WelcomeScreen(navController = navController) }
                    composable("login") { LoginScreen(navController = navController) }
                    composable("register") { RegisterScreen(navController = navController) }
                    composable("home") { HomeScreen(navController) }

                    // Onboarding
                    composable("onboarding1") { OnboardingScreen1(navController = navController) }
                    composable("onboarding2") { OnboardingScreen2(navController = navController) }

                    // Categories & Expenses
                    composable("categories") { CategoriesScreen(navController = navController) }
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

                    // Forgot Password Flow
                    composable("forgotPassword") { ForgotPasswordScreen(navController = navController) }
                    composable("securityPin") { SecurityPinScreen(navController = navController) }
                    composable("newPassword") { NewPasswordScreen(navController = navController) }

                    // Generic Confirmation Screens
                    composable("successConfirmation") {GenericConfirmationScreen(navController = navController, message = stringResource(R.string.success_message), destinationRoute = "login")}
                    composable("fingerprintChangedSuccess") {GenericConfirmationScreen(navController = navController, message = stringResource(R.string.fingerprint_changed_success), destinationRoute = "login")}
                    composable("pinChangedSuccess") {GenericConfirmationScreen(navController = navController, message = stringResource(R.string.pin_changed_success), destinationRoute = "login") }
                    composable("fingerprintDeletedSuccess") {GenericConfirmationScreen( navController = navController, message = stringResource(R.string.fingerprint_deleted_success), destinationRoute = "login")}

                    // Profile & Transactions
                    composable("profile") {ProfileScreen(navController = navController)}
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
}