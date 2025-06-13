package com.code4galaxy.reviewnow.view.feature.user.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.theme.AppTheme
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel

@Composable
<<<<<<< HEAD:app/src/main/java/com/code4galaxy/reviewnow/view/feature/user/settings/SettingsScreen.kt
fun SettingsScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text("Settings ...")
=======
fun UserSettingsScreen(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel = viewModel()
) {
    val selectedTheme by themeViewModel.theme.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16_dp))
    ) {
        Text(
            text = "Select App Theme",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        AppTheme.entries.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dimen_8_dp))
            ) {
                RadioButton(
                    selected = theme == selectedTheme,
                    onClick = { themeViewModel.setTheme(theme) }
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimen_8_dp)))
                Text(
                    text = theme.name.replace("_", " ")
                        .lowercase()
                        .replaceFirstChar { it.uppercaseChar() }
                )
            }
        }
>>>>>>> master:app/src/main/java/com/code4galaxy/reviewnow/view/feature/user/settings/UserSettingsScreen.kt
    }
}
