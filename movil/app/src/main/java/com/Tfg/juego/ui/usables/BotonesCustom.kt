package com.Tfg.juego.ui.usables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Botón reutilizable
@Composable
fun BotonCustom(
    text: String,
    width: Dp = 150.dp,
    height: Dp = 50.dp,
    enabled: Boolean = true,
    onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Color.Black),
        enabled = enabled,
        modifier = Modifier
            .width(width)
            .height(height)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center)
    }
}

@Composable
fun textoLoginYRegistro(
    text: String,
    fontSize: Int = 64,
    textAlign: TextAlign = TextAlign.Start
){
    Text(
        text = text,
        style = TextStyle(
            textAlign = textAlign,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            // Sombra para simular el contorno y profundidad
            shadow = Shadow(
                color = Color.Black.copy(alpha = 1f),
                offset = androidx.compose.ui.geometry.Offset(0f, 0f),
                blurRadius = 8f
            )
        )
    )
}

@Composable
fun textoOscuroLoginRegistro(
    texto: String
) {
    Text(
        text = texto,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun outlinedTextFieldLoginRegistro(
    text: MutableState<String>,
    placeholderTexto: String,
    tipo: String = "",
    enabled: Boolean = true,
    width: Dp = 300.dp
) {
    OutlinedTextField(
        modifier = Modifier.width(width),
        value = text.value,
        onValueChange = { newText -> text.value = newText },
        shape = RoundedCornerShape(25.dp),
        placeholder = { Text(text = placeholderTexto) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,

            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,

            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,

            cursorColor = Color.Black,
        ),
        enabled = enabled,
        singleLine = true,
        visualTransformation = if (tipo != "password") VisualTransformation.None else PasswordVisualTransformation(),
    )
}

fun validarEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
    return email.matches(emailPattern.toRegex())
}

@Composable
fun CheckBoxLoginRegistro(
    recuerdaCuenta: MutableState<Boolean>,
    string: String) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
        ) {
        Checkbox(
            checked = recuerdaCuenta.value,
            onCheckedChange = { recuerdaCuenta.value = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                checkmarkColor = Color.White,
                disabledCheckedColor = Color.Black,
            )
        )
        Text(string)
    }

}
