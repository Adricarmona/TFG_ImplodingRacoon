package com.Tfg.juego.ui.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.Tfg.juego.R
import com.Tfg.juego.ui.usables.BotonCustom

@Composable
fun sobreNosotros(onMenuClick: () -> Unit) {
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

        Text(
            text = stringResource(R.string.sobre_nosotros),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF000000)),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row (

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    painter =  painterResource(R.drawable.img_adri_github),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .size(150.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    painter =  painterResource(R.drawable.img_hector_github),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .size(150.dp)
                )
            }

            Text(
                text = stringResource(R.string.sobre_nosotros_texto),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Image(
            painter =  painterResource(R.drawable.img_imploding_racoons_logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .size(300.dp)
        )

        BotonCustom(
            text = stringResource(R.string.volver_menu),
            width = 180.dp,
        ) { onMenuClick() }
    }
}

