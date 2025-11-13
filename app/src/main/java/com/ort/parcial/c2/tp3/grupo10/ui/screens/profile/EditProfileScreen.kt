package com.ort.parcial.c2.tp3.grupo10.ui.screens.profile

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ort.parcial.c2.tp3.grupo10.R
import com.ort.parcial.c2.tp3.grupo10.ui.components.AppScreenShell
import com.ort.parcial.c2.tp3.grupo10.ui.theme.*
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EditProfileScreen(navController: NavHostController) {

    val PROFILE_HEADER_HEIGHT = 120.dp + 60.dp
    val viewModel: ProfileViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()


    val floatingProfileContent: @Composable () -> Unit = {
        when (uiState) {
            is UserProfileUiState.Loading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(40.dp))
                    Text("Cargando perfil...", fontSize = 18.sp)
                }
            }
            is UserProfileUiState.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(40.dp))
                    Text((uiState as UserProfileUiState.Error).message, color = Color.Red, fontSize = 16.sp)
                }
            }
            is UserProfileUiState.Success -> {
                val user = (uiState as UserProfileUiState.Success).user
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = "https://picsum.photos/200/200",
                        contentDescription = "Foto de perfil de ${user.name.firstname}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        error = painterResource(id = R.drawable.ic_perfil)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = user.name.firstname.replaceFirstChar { it.uppercase() } + " " + user.name.lastname.replaceFirstChar { it.uppercase() },
                        fontFamily = PoppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = DarkModeGreenBar
                    )
                    Row {
                        Text(
                            text = "ID: ",
                            color = LettersAndIcons,
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                        )
                        Text(
                            text = user.id.toString(),
                            color = LettersAndIcons.copy(alpha = 0.8f),
                            fontFamily = PoppinsFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 13.sp,
                        )
                    }
                }
            }
        }
    }


    AppScreenShell(
        screenTitle = "Edit My Profile",
        headerHeight = PROFILE_HEADER_HEIGHT,
        navController = navController,
        startSelectedIndex = 4
    ) { padding ->
        // Usamos Column con el modificador verticalScroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Aplica el padding de la BottomBar
                .verticalScroll(scrollState) // <--- HABILITA EL SCROLL VERTICAL
                .padding(horizontal = 6.dp), // Padding lateral del Column
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // 1. ESPACIO DE COMPENSACIÓN: Reservamos el espacio para la FOTO FLOTANTE
            // (120dp foto + 40dp margen) = 160dp. Este es el espacio que la tarjeta debe dejar libre.
            Spacer(Modifier.height(160.dp))

            // 2. CONTENIDO DE LA TARJETA
            // La tarjeta (EditProfileContent) empezará debajo de la foto
            EditProfileContent(viewModel = viewModel,
                navController = navController)

            // Espaciador final para asegurar que el botón no quede pegado al fondo
            Spacer(Modifier.height(20.dp))
        }
    }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = PROFILE_HEADER_HEIGHT - 60.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        floatingProfileContent()
    }
}




@Composable
private fun EditProfileContent(viewModel: ProfileViewModel, navController: NavHostController) {
    val viewModel: ProfileViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()
    var pushEnabled by remember { mutableStateOf(true) }
    var darkEnabled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(BackgroundGreenWhiteAndLetters)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Account Settings",
                color = Void,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // --- ¡CAMBIO CLAVE AQUÍ! ---
            // Mostramos los datos del usuario si están disponibles
            when (uiState) {
                is UserProfileUiState.Success -> {
                    val user = (uiState as UserProfileUiState.Success).user
                    LabeledEditableField(
                        label = "Username",
                        value = user.username,
                        onValueChange = { /* Handle username change */ }
                    )
                    Spacer(Modifier.height(10.dp))
                    LabeledEditableField(
                        label = "Phone",
                        value = user.phone,
                        onValueChange = { /* Handle phone change */ }
                    )
                    Spacer(Modifier.height(10.dp))
                    LabeledEditableField(
                        label = "Email Address",
                        value = user.email,
                        onValueChange = { /* Handle email change */ }
                    )
                }
                is UserProfileUiState.Loading -> {
                    LabeledEditableField(label = "Username", value = "Cargando...", onValueChange = {})
                    Spacer(Modifier.height(10.dp))
                    LabeledEditableField(label = "Phone", value = "Cargando...", onValueChange = {})
                    Spacer(Modifier.height(10.dp))
                    LabeledEditableField(label = "Email Address", value = "Cargando...", onValueChange = {})
                }
                is UserProfileUiState.Error -> {
                    LabeledEditableField(label = "Username", value = "Error", onValueChange = {})
                    Spacer(Modifier.height(10.dp))
                    LabeledEditableField(label = "Phone", value = "Error", onValueChange = {})
                    Spacer(Modifier.height(10.dp))
                    LabeledEditableField(label = "Email Address", value = "Error", onValueChange = {})
                }
            }

            // --- El resto se mantiene igual ---
            Spacer(Modifier.height(16.dp))

            SettingsSwitchRow(
                title = "Push Notifications",
                checked = pushEnabled,
                onCheckedChange = { pushEnabled = it }
            )
            SettingsSwitchRow(
                title = "Turn Dark Theme",
                checked = darkEnabled,
                onCheckedChange = { darkEnabled = it }
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("profile") },
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Caribbean),
                modifier = Modifier
                    .width(169.dp)
                    .height(36.dp)
            ) {
                Text(
                    text = "Update Profile",
                    color = Void,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PoppinsFamily,
                    lineHeight = 15.sp,
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun LabeledEditableField(label: String, value: String, onValueChange: (String) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Void,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 8.dp, bottom = 6.dp)
        )
        if (isEditing) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightGreen.copy(alpha = 0.65f))
                    .padding(horizontal = 14.dp)
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightGreen.copy(alpha = 0.65f),
                    focusedContainerColor = LightGreen.copy(alpha = 0.85f),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedTextColor = Color(0xFF222222),
                    focusedTextColor = Color(0xFF222222)
                ),
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions(onDone = {
                    isEditing = false
                    focusManager.clearFocus()
                })
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightGreen.copy(alpha = 0.65f))
                    .padding(horizontal = 14.dp)
                    .clickable { isEditing = true },
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = value,
                    color = Color(0xFF222222),
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Composable
private fun LabeledReadOnlyField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Void,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 8.dp, bottom = 6.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LightGreen.copy(alpha = 0.65f))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value,
                color = LettersAndIcons.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        }
    }
}




@Composable
private fun SettingsSwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Void,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.border(
                width = 2.dp,
                color = Color.Transparent,
                shape = CircleShape
            ),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Caribbean,


                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Caribbean.copy(alpha = 0.5f),
                uncheckedBorderColor = Color.Transparent
            )
        )

    }
}

/* ---------- PREVIEW ---------- */
@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(navController = rememberNavController())
}
