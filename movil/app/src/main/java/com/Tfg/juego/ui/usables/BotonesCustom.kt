package com.Tfg.juego.ui.usables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Color.Black),
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
