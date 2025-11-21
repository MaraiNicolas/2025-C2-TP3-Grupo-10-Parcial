import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.ort.parcial.c2.tp3.grupo10.R

@Composable
fun InputTextString(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    focusedBorderColor: Color,
    unfocusedBorderColor: Color,
    focusedContainerColor: Color,
    unfocusedContainerColor: Color,
    cursorColor: Color,
    textColor: Color = focusedBorderColor
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(color = textColor),
        placeholder = { Text(placeholder, color = textColor.copy(alpha = 0.6f)) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50),
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {

            if (isPassword) {
                val image = if (passwordVisible)R.drawable.ic_eye_pass else R.drawable.ic_eye_off

                IconButton(onClick = { passwordVisible = !passwordVisible}) {
                   Image(painter = painterResource(id = image),
                        contentDescription = stringResource(R.string.common_toggle_password_visibility),)
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedContainerColor = focusedContainerColor,
            unfocusedContainerColor = unfocusedContainerColor,
            cursorColor = cursorColor
        )
    )
}
