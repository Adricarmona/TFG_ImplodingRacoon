package com.Tfg.juego.ui.menu.cartas

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.Tfg.juego.model.retrofit.dto.cardAndStyle
import com.Tfg.juego.model.servicios.getCartasTipo
import com.Tfg.juego.ui.theme.JuegoTheme
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CardCustom
import com.Tfg.juego.ui.usables.dialogoCargando
import kotlinx.coroutines.launch

@Composable
fun verCartas(
    onAjustes: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val cartasState = remember { mutableStateOf<List<cardAndStyle>>(emptyList()) }

    val errorMessage = remember { mutableStateOf<String?>(null) }

    var showDialog = remember { mutableStateOf(false) }
    dialogoCargando(showDialog)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BotonCustom(
            text = "Volver al menu",
            onClick = onAjustes
        )

        BotonCustom(
            text = "Ver Cartas",
            onClick = {
                coroutineScope.launch {
                    showDialog.value = true

                    errorMessage.value = null
                    try {
                        val cartas = getCartasTipo(0)
                        cartasState.value = cartas ?: emptyList()

                        if (cartas.isNullOrEmpty())
                            errorMessage.value = "No data returned from API"

                        showDialog.value = false
                    } catch (e: Exception) {

                        e.printStackTrace() // Imprime el error completo para depuración
                        errorMessage.value = "Error fetching cartas: ${e.message}"

                    } finally {
                        showDialog.value = false
                    }
                }
            }
        )

        // Display error message if any
        errorMessage.value?.let { error ->
            Text(text = error)
        }
        
        // Display the list of cardAndStyle objects if available
        if (cartasState.value.isNotEmpty()) {
            cartasState.value.forEach { carta ->
                CardCustom(carta.titulo, carta.tipo, carta.descripcion, carta.urlImage)
                Text(text = carta.urlImage)
            }
        }

    }
}