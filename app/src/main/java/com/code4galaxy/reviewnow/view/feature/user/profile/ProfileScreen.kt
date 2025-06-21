package com.code4galaxy.reviewnow.view.feature.user.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.navigation.Screen
import com.code4galaxy.reviewnow.viewmodel.AuthViewModel


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel= hiltViewModel(),
    name: String = "John Doe",
    email: String = "johndoe@example.com",
    onMyReviewsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.dimen_16_dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile icon
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_80_dp)),
            tint = Color.Blue
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8_dp)))

        // Display user name
        Text(name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        // Display user email
        Text(email, fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_24_dp)))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_12_dp)))
        // Row for "My Reviews" section with click listener
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onMyReviewsClick() }
                .padding(vertical = dimensionResource(id = R.dimen.dimen_12_dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.my_reviews), fontSize = 16.sp)
        }

        Divider()
        // Row for "Log out" section with click listener
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onLogoutClick()

                }
                .padding(vertical = dimensionResource(id = R.dimen.dimen_12_dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Log out", fontSize = 16.sp)
        }

        Divider()
    }
}

@Composable
fun ProfileRoute(navController: NavController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = hiltViewModel()

    ProfileScreen(
        name = "John Doe",
        email = "johndoe@example.com",
        onMyReviewsClick = { navController.navigate("my_reviews") },
        onLogoutClick = {
            Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()
            authViewModel.logout(context) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Profile.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

    )

}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(
        name = "Jane Smith",
        email = "jane.smith@example.com",
        onMyReviewsClick = {},
        onLogoutClick = {}
    )
}

