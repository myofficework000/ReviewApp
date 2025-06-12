package com.code4galaxy.reviewnow.view.feature.admin.home
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.code4galaxy.reviewnow.R
//add brand screen
@Composable
fun AddBrandScreen(
    onBackClick: () -> Unit = {},
    onEnrollClick: (String) -> Unit = {}
) {
    var brandName by remember { mutableStateOf(TextFieldValue("")) }

    val dimen1 = dimensionResource(R.dimen.dimen_1_dp)
    val dimen8 = dimensionResource(R.dimen.dimen_8_dp)
    val dimen12 = dimensionResource(R.dimen.dimen_12_dp)
    val dimen16 = dimensionResource(R.dimen.dimen_16_dp)
    val dimen24 = dimensionResource(R.dimen.dimen_24_dp)
    val dimen50 = dimensionResource(R.dimen.dimen_50_dp)
    val dimen60 = dimensionResource(R.dimen.dimen_60_dp)
    val dimen120 = dimensionResource(R.dimen.dimen_120_dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimen16)
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "Add Brand/Org",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = dimen24)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = dimen16)
        ) {
            Box(
                modifier = Modifier
                    .size(dimen60)
                    .border(dimen1, Color.Gray, shape = RoundedCornerShape(dimen8))
                    .background(Color.LightGray.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Logo", tint = Color.Gray)
            }

            Spacer(modifier = Modifier.width(dimen12))

            Text(text = "Brand/Org", style = MaterialTheme.typography.titleMedium)
        }

        OutlinedTextField(
            value = brandName,
            onValueChange = { brandName = it },
            label = { Text("Brand/Org Name") },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimen120)
        )

        Spacer(modifier = Modifier.height(dimen24))

        Button(
            onClick = { onEnrollClick(brandName.text) },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimen50),
            shape = RoundedCornerShape(dimen12)
        ) {
            Text("Enroll")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddBrandScreenPreview() {
    AddBrandScreen()
}
