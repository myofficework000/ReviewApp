package com.code4galaxy.reviewnow.view.feature.admin.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.User
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.viewmodel.AdminViewModel

@Composable
fun ManageUsersScreen(
    onBackClick: () -> Unit = {},
    viewModel: AdminViewModel = hiltViewModel()
) {
    val dimen8 = dimensionResource(id = R.dimen.dimen_8_dp)
    val dimen10 = dimensionResource(id = R.dimen.dimen_10_dp)
    val dimen16 = dimensionResource(id = R.dimen.dimen_16_dp)

    val usersState by viewModel.allUsersState.collectAsState()


    // Fetch users when screen loads
    LaunchedEffect(Unit) {
        viewModel.fetchAllUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimen16)
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "Users",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = dimen8)
        )

        when (usersState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                val error = (usersState as UiState.Error).message
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    modifier = Modifier.padding(top = dimen10)
                )
            }

            is UiState.Success -> {
                val users = (usersState as UiState.Success).data
                LazyColumn {
                    items(users) { user ->
                        UserItem(
                            user = user,
                            onSuspendClick = { viewModel.suspendUser(user.id) }
                        )
                        Spacer(modifier = Modifier.height(dimen10))
                    }
                }

            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onSuspendClick: () -> Unit
) {
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
            Text(
                text = if (user.name.isNotBlank()) user.name else user.email,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (user.isSuspended) {
            Text(
                text = "Suspended",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = dimen8, vertical = dimen8 / 2)
            )
        } else {
            Text(
                text = "Suspend",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { onSuspendClick() }
                    .padding(horizontal = dimen8, vertical = dimen8 / 2)
            )
        }
    }
}

