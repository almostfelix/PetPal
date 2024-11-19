package com.petpal.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petpal.R

@Composable
fun AddEventBtn(onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = colorResource(id = R.color.prim),
                contentColor = colorResource(id = R.color.bg),
                disabledContainerColor = colorResource(id = R.color.prim),
                disabledContentColor = colorResource(id = R.color.bg),
            ),
        ) {
            Text("Add Event")
        }
    }
}