package com.code4galaxy.reviewnow.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.AdminNavigationItem

@Composable
fun AdminCustomDrawer(
    selectedNavigationItem: AdminNavigationItem,
    onNavigationItemClick: (AdminNavigationItem) -> Unit,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.6f)
            .padding(horizontal = dimensionResource(R.dimen.dimen_12_dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.dimen_24_dp))
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Arrow Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Image"
        )
        Spacer(modifier = Modifier.height(40.dp))
        AdminNavigationItem.entries.toTypedArray().take(AdminNavigationItem.entries.size)
            .forEach { navigationItem ->
                AdminNavigationItemView(
                    navigationItem = navigationItem,
                    selected = navigationItem == selectedNavigationItem,
                    onClick = { onNavigationItemClick(navigationItem) }
                )
                Spacer(modifier = Modifier.height(4.dp))

                if (navigationItem.title == AdminNavigationItem.UserReviews.title)
                    Spacer(modifier = Modifier.weight(1f))
            }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_24_dp)))
    }
}