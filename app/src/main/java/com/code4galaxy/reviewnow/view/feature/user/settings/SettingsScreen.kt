package com.code4galaxy.reviewnow.view.feature.user.settings

import LanguageSelector
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.theme.AppTheme
import com.code4galaxy.reviewnow.viewmodel.LanguageViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel

@Composable
fun SettingsScreen() {
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val selectedLanguage by languageViewModel.language.collectAsState()
    var showLanguageSheet by remember { mutableStateOf(false) }

    val languageMap = mapOf(
        "en" to "English",
        "fr" to "French",
        "de" to "German",
        "es" to "Spanish",
        "ru" to "Russian",
        "uk" to "Ukrainian",
        "hi" to "Hindi"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(24.dp))

        // Profile section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Person", style = MaterialTheme.typography.titleMedium)
                Text("Person@email.com", style = MaterialTheme.typography.bodySmall)
            }
        }

        HorizontalDivider()

        // Non-clickable and toggle items
        SettingItem("Dark Mode", trailing = {
            Switch(checked = true, onCheckedChange = {}) // implement toggle logic
        })

        SettingItem("Notifications and sounds")
        SettingItem("Password")
        SettingItem("Security Checkup")
        SettingItem("Two Factor Authentication")
        SettingItem("Change Number")

        // Clickable items
        SettingItem("Help Center", onClick = {
            // TODO: Navigate to Help Center screen
        })

        SettingItem("Report a Problem", onClick = {
            // TODO: Navigate to Report screen or open dialog
        })

        SettingItem(
            title = "Language",
            value = languageMap[selectedLanguage] ?: selectedLanguage,
            onClick = { showLanguageSheet = true }
        )

        SettingItem("Terms and Privacy Policy", onClick = {
            // TODO: Open Terms and Privacy page
        })
    }

    if (showLanguageSheet) {
        LanguageBottomSheet(
            languageViewModel = languageViewModel,
            onDismiss = { showLanguageSheet = false }
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    value: String? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(vertical = 16.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, modifier = Modifier.weight(1f))
        value?.let {
            Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.width(8.dp))
        }
        trailing?.invoke()
    }
}
