package com.code4galaxy.reviewnow.view.feature.admin.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.code4galaxy.reviewnow.R

@Composable
fun ManageUsersScreen(
    //we can change accordingly to our firebase
    userList: List<String> = listOf("John Doe", "Jane Smith", "Alex Ray"),
    onBackClick: () -> Unit = {},
    onSuspendClick: (String) -> Unit = {}
) {

    val dimen8 = dimensionResource(id = R.dimen.dimen_8_dp)
    val dimen10 = dimensionResource(id = R.dimen.dimen_10_dp)
    val dimen16 = dimensionResource(id = R.dimen.dimen_16_dp)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimen16)
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")

        }

        Text(
            //will replace with the users list
            text = "Users",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = dimen8)
        )

        LazyColumn {
            items(userList) { userName ->
                UserItem(name = userName, onSuspendClick = { onSuspendClick(userName) })
                Spacer(modifier = Modifier.height(dimen10))
            }
        }
    }
}

@Composable
fun UserItem(name: String, onSuspendClick: () -> Unit) {
    // dimen values
    val dimen8 = dimensionResource(id = R.dimen.dimen_8_dp)
    val dimen12 = dimensionResource(id = R.dimen.dimen_12_dp)
    val dimen40 = dimensionResource(id = R.dimen.dimen_40_dp)
    val dimen1 = dimensionResource(id = R.dimen.dimen_1_dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = dimen1,
                color = colorResource(R.color.gray).copy(alpha = 0.2f),
                shape = RoundedCornerShape(dimen12)
            )
            .padding(dimen12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "User Icon",
                tint = colorResource(R.color.gray),
                modifier = Modifier.size(dimen40)
            )
            Spacer(modifier = Modifier.width(dimen12))
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
        }

        Text(
            text = "Suspend",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { onSuspendClick() }
                .padding(horizontal = dimen8, vertical = dimen8 / 2)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ManageUsersScreenPreview() {
    ManageUsersScreen()
}
