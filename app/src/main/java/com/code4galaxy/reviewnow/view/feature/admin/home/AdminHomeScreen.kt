package com.code4galaxy.reviewnow.view.feature.admin.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.feature.user.settings.AdminActionItem
import com.code4galaxy.reviewnow.view.feature.user.settings.AdminHomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    adminName: String = "Admin",
    adminEmail: String = "admin@example.com",
    onManageUsersClick: () -> Unit = {},
    onModerateReviewsClick: () -> Unit = {},
    onAddBrandClick: () -> Unit = {},
    onFlaggedReviewsClick: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.profile)) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
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

            // Avatar
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

            // Name and Email
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

            // Action Items
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
                    onManageUsersClick
                )
                AdminActionItem(
                    stringResource(id = R.string.moderate_reviews),
                    Icons.Default.Flag,
                    onModerateReviewsClick
                )
                AdminActionItem(
                    stringResource(id = R.string.add_brand),
                    Icons.Default.Add,
                    onAddBrandClick
                )
                AdminActionItem(
                    stringResource(id = R.string.flagged_reviews),
                    Icons.Default.Report,
                    onFlaggedReviewsClick
                )
                HorizontalDivider(
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.dimen_12_dp),
                        bottom = dimensionResource(id = R.dimen.dimen_6_dp)
                    )
                )
            }

            // Logout
            Box(
                modifier = Modifier.constrainAs(logout) {
                    top.linkTo(actions.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                AdminActionItem(
                    text = stringResource(id = R.string.log_out),
                    icon = Icons.Default.Logout,
                    onClick = onLogout,
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