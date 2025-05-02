package com.Tfg.juego.ui.menu.cartas

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.model.retrofit.dto.cardAndStyle
import com.Tfg.juego.model.servicios.getCartasTipo
import com.Tfg.juego.ui.theme.JuegoTheme
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.CardCustom
import com.Tfg.juego.ui.usables.dialogoCargando
import com.Tfg.juego.ui.usables.textoLoginYRegistro
import kotlinx.coroutines.launch

@Composable
fun verCartas(
    onAjustes: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val cartasState = remember { mutableStateOf<List<cardAndStyle>>(emptyList()) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // el dialogo
    var showDialog = remember { mutableStateOf(false) }
    dialogoCargando(showDialog)

    // la logica de la busqueda
    val opcion = remember { mutableStateOf(0) } // si es 0 es original si es 1 es otro

    // para añadir las cartas a las seleccionadas
    val context = LocalContext.current
    val sharedPref = remember { context.getSharedPreferences("AjustesIplodingRacoon", Context.MODE_PRIVATE) }
    val editorSharedPreferences = sharedPref.edit();

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        BotonCustom(
            text = stringResource(R.string.volver_menu),
            width = 200.dp,
            height = 60.dp,
            onClick = onAjustes
        )

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                BotonCustom(
                    text = "<",
                    width = 50.dp,
                    height = 50.dp,
                    onClick = {
                        if (opcion.value <= 0)
                            opcion.value = 1
                        else
                            opcion.value--
                    }
                )

                Spacer(modifier = Modifier.width(5.dp))

                textoLoginYRegistro(
                    text = if (opcion.value == 0) "Original" else stringResource(R.string.otro),
                    fontSize = 20,
                )

                Spacer(modifier = Modifier.width(5.dp))

                BotonCustom(
                    text = ">",
                    width = 50.dp,
                    height = 50.dp,
                    onClick = {
                        if (opcion.value >= 1)
                            opcion.value = 0
                        else
                            opcion.value++
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            BotonCustom(
                text = stringResource(R.string.ver_cartas),
                onClick = {
                    coroutineScope.launch {
                        showDialog.value = true

                        errorMessage.value = null
                        try {
                            val cartas = getCartasTipo(opcion.value)
                            cartasState.value = cartas ?: emptyList()

                            if (cartas.isNullOrEmpty())
                                errorMessage.value = "No data returned from API"

                            showDialog.value = false
                        } catch (e: Exception) {

                            e.printStackTrace()
                            errorMessage.value = "Error fetching cartas: ${e.message}"

                        } finally {
                            showDialog.value = false
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            BotonCustom(
                text = stringResource(R.string.seleccionar_carta),
                onClick = {
                    editorSharedPreferences.putInt("cartaSeleccionada",opcion.value)
                }
            )

            // cuando el error no sea nulo, muestra el mensaje de error
            errorMessage.value?.let { error ->
                Image(
                    painter =  painterResource(id = R.drawable.img_no_info),
                    contentDescription = stringResource(R.string.no_devolvieron_datos_api),
                    modifier = Modifier.size(300.dp)
                )
                Log.e("apiCards", "error al obtener cartas: "+ error, )
            }

            // si no esta vacio itera el array de cartas
            if (cartasState.value.isNotEmpty()) {
                cartasState.value.forEach { carta ->
                    CardCustom(carta.titulo, carta.tipo, carta.descripcion, carta.urlImage)
                }
            }

        }
    }
}