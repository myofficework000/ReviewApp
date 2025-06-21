@file:OptIn(ExperimentalMaterial3Api::class)

package com.code4galaxy.reviewnow.view.feature.user.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.viewmodel.LanguageViewModel
import com.code4galaxy.reviewnow.viewmodel.ThemeViewModel

@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel
) {
    val languageViewModel: LanguageViewModel = hiltViewModel()

    val selectedLanguage by languageViewModel.language.collectAsState()
    val selectedTheme by themeViewModel.theme.collectAsState()

    var showLanguageSheet by remember { mutableStateOf(false) }
    var showThemeSheet by remember { mutableStateOf(false) }

    val themeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
        Text(stringResource(id = R.string.settings), style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

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
                Text(stringResource(id = R.string.person_name), style = MaterialTheme.typography.titleMedium)
                Text(stringResource(id = R.string.person_email), style = MaterialTheme.typography.bodySmall)
            }
        }

        Divider()

        SettingItem(
            title = stringResource(id = R.string.app_theme),
            value = selectedTheme.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercaseChar() },
            onClick = { showThemeSheet = true }
        )

        SettingItem(title = stringResource(id = R.string.notifications_and_sounds))

        SettingItem(title = stringResource(id = R.string.password))

        SettingItem(title = stringResource(id = R.string.security_checkup))

        SettingItem(title = stringResource(id = R.string.two_factor_authentication))

        SettingItem(title = stringResource(id = R.string.change_number))

        SettingItem(title = stringResource(id = R.string.help_center))

        SettingItem(title = stringResource(id = R.string.report_a_problem))

        SettingItem(
            title = stringResource(id = R.string.language),
            value = languageMap[selectedLanguage] ?: selectedLanguage,
            onClick = { showLanguageSheet = true }
        )

        SettingItem(title = stringResource(id = R.string.terms_and_privacy_policy))
    }

    if (showLanguageSheet) {
        LanguageBottomSheet(
            languageViewModel = languageViewModel,
            onDismiss = { showLanguageSheet = false }
        )
    }

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
