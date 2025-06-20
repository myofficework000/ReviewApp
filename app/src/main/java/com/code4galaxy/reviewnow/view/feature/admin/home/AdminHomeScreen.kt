package com.code4galaxy.reviewnow.view.feature.admin.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    onManageUsersClick: () -> Unit = {},
    onModerateReviewsClick: () -> Unit = {},
    onAddBrandClick: () -> Unit = {},
    onFlaggedReviewsClick: () -> Unit = {},
    onLogoutNavigate: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val adminName by authViewModel.adminName
    val adminEmail by authViewModel.adminEmail

    LaunchedEffect(Unit) {
        authViewModel.loadAdminDetails()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.profile)) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back if needed */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dimen_24_dp),
                    vertical = dimensionResource(id = R.dimen.dimen_16_dp)
                )
        ) {
            val (avatar, nameEmail, actions, logout) = createRefs()

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(id = R.string.avatar),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dimen_96_dp))
                    .clip(CircleShape)
                    .constrainAs(avatar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                tint = Color.Blue
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(nameEmail) {
                        top.linkTo(avatar.bottom, margin = 12.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(adminName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(adminEmail, fontSize = 14.sp, color = Color.Gray)
            }

            Column(
                modifier = Modifier
                    .constrainAs(actions) {
                        top.linkTo(nameEmail.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                HorizontalDivider()
                AdminActionItem(
                    stringResource(id = R.string.manage_users),
                    Icons.Default.People,
                    onClick = onManageUsersClick
                )
                AdminActionItem(
                    stringResource(id = R.string.moderate_reviews),
                    Icons.Default.Flag,
                    onClick = onModerateReviewsClick
                )
                AdminActionItem(
                    stringResource(id = R.string.add_brand),
                    Icons.Default.Add,
                    onClick = onAddBrandClick
                )
                AdminActionItem(
                    stringResource(id = R.string.flagged_reviews),
                    Icons.Default.Report,
                    onClick = onFlaggedReviewsClick
                )
                HorizontalDivider(
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.dimen_12_dp),
                        bottom = dimensionResource(id = R.dimen.dimen_6_dp)
                    )
                )
            }

            val context = LocalContext.current

            Box(
                modifier = Modifier.constrainAs(logout) {
                    top.linkTo(actions.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                var logoutTriggered by remember { mutableStateOf(false) }
                val context = LocalContext.current

                if (logoutTriggered) {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(1000) // fallback in case logout callback is not called
                        onLogoutNavigate()
                    }
                }

                AdminActionItem(
                    text = stringResource(id = R.string.log_out),
                    icon = Icons.Default.Logout,
                    onClick = {
                        if (!logoutTriggered) {
                            logoutTriggered = true
                            authViewModel.logout(context) {
                                onLogoutNavigate()
                            }
                        }
                    },
                    isLogout = true
                )

            }
        }
    }
}

@Composable
fun AdminActionItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isLogout: Boolean = false
) {
    val textColor = if (isLogout) Color.Red else Color.Black
    val iconColor = if (isLogout) Color.Red else Color.DarkGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = dimensionResource(id = R.dimen.dimen_16_dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconColor)
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimen_16_dp)))
        Text(
            text,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
}

@Preview
@Composable
fun AdminHomeScreenPreview() {
    AdminHomeScreen()
}
