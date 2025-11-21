package com.ort.parcial.c2.tp3.grupo10.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LettersAndIcons
import com.ort.parcial.c2.tp3.grupo10.ui.theme.LightGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.MainGreen
import com.ort.parcial.c2.tp3.grupo10.ui.theme.PoppinsFamily
import com.ort.parcial.c2.tp3.grupo10.R

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        val passwordTextStyle = TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.7.em,
            color = LettersAndIcons
        )

        Spacer(Modifier.height(8.dp))

        // Campo de Texto Real
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = passwordTextStyle,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LightGreen,
                unfocusedContainerColor = LightGreen,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = LettersAndIcons,
                unfocusedTextColor = LettersAndIcons,
                cursorColor = MainGreen
            ),

            trailingIcon = {
                IconButton(onClick = { /* Lógica de toggle de visibilidad */ }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.common_toggle_password_visibility),
                        tint = LettersAndIcons.copy(alpha = 0.6f) // Color tenue
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        )
    }
}