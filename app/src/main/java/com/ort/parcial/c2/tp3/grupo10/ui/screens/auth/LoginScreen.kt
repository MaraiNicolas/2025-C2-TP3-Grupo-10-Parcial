package com.ort.parcial.c2.tp3.grupo10.ui.screens.auth
import InputTextString
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ort.parcial.c2.tp3.grupo10.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.ort.parcial.c2.tp3.grupo10.R.drawable.ic_google
import com.ort.parcial.c2.tp3.grupo10.R.drawable.ic_facebook
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ort.parcial.c2.tp3.grupo10.ui.components.auth.AuthButton
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val viewModel: LoginViewModel = hiltViewModel()
    val token by viewModel.token.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(token) {
        if (token != null) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainGreen)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = stringResource(R.string.welcome_title),
                color = Void,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PoppinsFamily
            )
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 50.dp))
                    .background(BackgroundGreenWhiteAndLetters)
                    .padding(horizontal = 32.dp, vertical = 60.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    Column(modifier = Modifier.fillMaxWidth())
                    {
                        Text(
                            text = stringResource(R.string.username_lbl_input),
                            color = Void,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        InputTextString(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = stringResource(R.string.username_pholder_input),
                            keyboardType = KeyboardType.Email,
                            isPassword = false,
                            focusedBorderColor = Honeydew2,
                            unfocusedBorderColor = Honeydew,
                            focusedContainerColor = Honeydew,
                            unfocusedContainerColor = Honeydew,
                            cursorColor = Honeydew2
                        )
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.password_lbl_input),
                            color = Void,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        InputTextString(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = stringResource(R.string.password_pholder_input),
                            isPassword = true,
                            keyboardType = KeyboardType.Password,
                            focusedBorderColor = Honeydew2,
                            unfocusedBorderColor = Honeydew,
                            focusedContainerColor = Honeydew,
                            unfocusedContainerColor = Honeydew,
                            cursorColor = Honeydew2
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        AuthButton( text = if (isLoading)stringResource(R.string.loading_message)
                        else stringResource(R.string.login_btn_text),
                            onClick = {
                                if (!isLoading) {
                                    viewModel.login()
                                }
                            },
                            modifier = Modifier
                                .width(200.dp)
                                .height(50.dp),
                            contentColor = Void,
                            containerColor = Caribbean
                        )
                        if (isLoading) {
                            CircularProgressIndicator(color = Caribbean)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        TextButton(
                            // La acción principal del botón ahora navega
                            onClick = { navController.navigate("forgotPassword") }
                        ) {
                            Text(
                                stringResource(R.string.forgot_password_login),
                                color = Void,
                                fontFamily = PoppinsFamily,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        AuthButton(text= stringResource(R.string.signup_btn_text),
                            {
                                onSignUpClick()
                                navController.navigate( "register")
                            },
                            modifier = Modifier
                                .width(200.dp)
                                .height(50.dp),
                            contentColor = Void,
                            containerColor = LightGreen
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Use ",
                            color = Void,
                            fontFamily = PoppinsFamily)
                        Text(
                            text = "Fingerprint",
                            color = Caribbean,
                            fontWeight = FontWeight.Medium,
                            fontFamily = PoppinsFamily
                        )
                        Text(" To Access",
                            color = Void,
                            fontFamily = PoppinsFamily)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = stringResource(R.string.or_sign_up_with),
                        color = Void,
                        fontFamily = PoppinsFamily,
                        style = MaterialTheme.typography.bodySmall)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(BackgroundGreenWhiteAndLetters, shape = CircleShape)
                                .clip(CircleShape)
                                .clickable { /* TODO: Facebook login */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = ic_facebook),
                                contentDescription = stringResource(R.string.login_facebook),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(BackgroundGreenWhiteAndLetters, shape = CircleShape)
                                .clip(CircleShape)
                                .clickable { /* TODO: Google login */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = ic_google),
                                contentDescription = stringResource(R.string.login_google), // <-- Así queda perfecto
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Text(text= stringResource(R.string.dont_have_account),
                            style = MaterialTheme.typography.bodySmall,
                            color = Void)
                        Text(
                            text = stringResource(R.string.signup_btn_text),
                            color = Caribbean,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable {
                                onSignUpClick()
                                navController.navigate("register")
                            }
                        )
                    }
                }
            }
        }
    }
}
