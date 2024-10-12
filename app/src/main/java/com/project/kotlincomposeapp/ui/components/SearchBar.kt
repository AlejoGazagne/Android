package com.project.kotlincomposeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.project.kotlincomposeapp.R

@Composable
fun SearchBar(searchQuery: TextFieldValue, onSearchQueryChanged: (TextFieldValue) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Imagen fuera de la barra de búsqueda, a la izquierda
        Image(
            painter = painterResource(id = R.drawable.logo), // Reemplaza con tu recurso de imagen
            contentDescription = "Logo",
            modifier = Modifier
                .size(50.dp) // Ajusta el tamaño de la imagen según sea necesario
                .padding(end = 8.dp)
        )

        // Barra de búsqueda
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    MaterialTheme.shapes.small
                )
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                // Ícono de búsqueda
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    // Campo de texto
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChanged,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
                    )

                    // Placeholder: Si no hay texto en el campo de búsqueda, mostramos el placeholder
                    if (searchQuery.text.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.discover_events),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                    }
                }
            }
        }
    }
}

