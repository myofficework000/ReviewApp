@file:OptIn(ExperimentalMaterial3Api::class)

package com.code4galaxy.reviewnow.view.feature.user.settings

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.viewmodel.LanguageViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel

@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val languageViewModel: LanguageViewModel = hiltViewModel()

    val selectedLanguage by languageViewModel.language.collectAsState()
    val selectedTheme by themeViewModel.theme.collectAsState()

    var showLanguageSheet by remember { mutableStateOf(false) }
    var showThemeSheet by remember { mutableStateOf(false) }

    val themeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // TODO: Support more language translations here if needed
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

        // Profile section (can be replaced with real data)
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

        Divider()

        // Theme selection setting
        SettingItem(
            title = "App Theme",
            value = selectedTheme.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercaseChar() },
            onClick = { showThemeSheet = true }
        )

        // TODO: Connect with real notification toggle
        SettingItem("Notifications and sounds")

        // TODO: Secure password change functionality
        SettingItem("Password")

        // TODO: Implement security checkup UI
        SettingItem("Security Checkup")

        // TODO: Add two factor toggle functionality
        SettingItem("Two Factor Authentication")

        // TODO: Navigate to change number screen
        SettingItem("Change Number")

        // TODO: Navigate to Help Center screen
        SettingItem("Help Center", onClick = {
            // TODO
        })

        // TODO: Navigate to report screen or open a problem dialog
        SettingItem("Report a Problem", onClick = {
            // TODO
        })

        // Language selector
        SettingItem(
            title = "Language",
            value = languageMap[selectedLanguage] ?: selectedLanguage,
            onClick = { showLanguageSheet = true }
        )

        // TODO: Open Terms and Privacy page
        SettingItem("Terms and Privacy Policy", onClick = {
            // TODO
        })
    }

    // Language bottom sheet
    if (showLanguageSheet) {
        LanguageBottomSheet(
            languageViewModel = languageViewModel,
            onDismiss = { showLanguageSheet = false }
        )
    }

    // Theme bottom sheet
    if (showThemeSheet) {
        ModalBottomSheet(
            onDismissRequest = { showThemeSheet = false },
            sheetState = themeSheetState,
            containerColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            ThemeBottomSheetContent(
                selectedTheme = selectedTheme,
                onThemeSelected = {
                    themeViewModel.setTheme(it)
                    showThemeSheet = false
                }
            )
        }
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
