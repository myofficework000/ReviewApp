package com.code4galaxy.reviewnow.view.feature.user.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.feature.user.util.updateLocale
import com.code4galaxy.reviewnow.viewmodel.LanguageViewModel

@Composable
fun LanguageSelector(modifier: Modifier = Modifier) {
    val languageViewModel: LanguageViewModel = hiltViewModel()
    // Observe the selected language from the ViewModel
    val selectedLanguage = languageViewModel.language.collectAsStateWithLifecycle()

    val context = LocalContext.current

    SideEffect {
        updateLocale(context, selectedLanguage.value)
    }

    LanguageDropdown(selectedLanguage.value, onLanguageSelected = languageViewModel::changeLanguage)
}

@Composable
fun LanguageDropdown(
    selectedLanguage: String,
    modifier: Modifier = Modifier,
    onLanguageSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("en" to "English", "es" to "Spanish", "fr" to "French","ar" to "Arabic")

    Column(modifier = modifier.padding(dimensionResource(R.dimen.dimen_16_dp))) {
        Text(text = "Select Language: ${languages.find { it.first == selectedLanguage }?.second ?: selectedLanguage}",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(dimensionResource(R.dimen.dimen_8_dp))
                .clickable { expanded = true }
        )

        Spacer(modifier = modifier.padding(dimensionResource(R.dimen.dimen_8_dp)))

        DropdownMenu(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.dimen_8_dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Red),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { (code, label) ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        expanded = false
                        onLanguageSelected(code)
                    })
            }
        }
    }
}

@Preview
@Composable
private fun LanguageSelectorPreview() {
    LanguageSelector()
}