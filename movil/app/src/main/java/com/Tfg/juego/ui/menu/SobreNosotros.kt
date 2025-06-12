package com.Tfg.juego.ui.menu

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.usables.BotonCustom
import com.Tfg.juego.ui.usables.nombreUsuarios
import com.Tfg.juego.ui.usables.textoLoginYRegistro

@Composable
fun sobreNosotros(onMenuClick: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        textoLoginYRegistro(
            text = stringResource(R.string.sobre_nosotros),
            fontSize = 40
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(14.dp))

            nombreUsuarios(
                text = "Creadores",
                with = 400.dp,
                textAlign = TextAlign.Center,
                fontSize = 30
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row (

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    nombreUsuarios(
                        text =  "Adrian Carmona",
                        with = 140.dp,
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter =  painterResource(R.drawable.github_icono),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Adricarmona"))
                                context.startActivity(intent)
                            }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter =  painterResource(R.drawable.linkedin_icono),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/adriancarmonagalvez/"))
                                context.startActivity(intent)
                            }
                    )

                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    nombreUsuarios(
                        text =  "Hector Domínguez",
                        with = 140.dp,
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter =  painterResource(R.drawable.github_icono),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/HectorDominguezG"))
                                context.startActivity(intent)
                            }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter =  painterResource(R.drawable.linkedin_icono),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/h%C3%A9ctor-dom%C3%ADnguez-garc%C3%ADa-058191310/"))
                                context.startActivity(intent)
                            }
                    )

                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            nombreUsuarios(
                text = "Informacion",
                with = 400.dp,
                textAlign = TextAlign.Center,
                fontSize = 30
            )

            Text(
                text = stringResource(R.string.sobre_nosotros_texto),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        BotonCustom(
            text = stringResource(R.string.volver_menu),
            width = 180.dp,
        ) { onMenuClick() }
    }
}

