package com.code4galaxy.reviewnow.view.feature.user.settings

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.code4galaxy.reviewnow.viewmodel.LanguageViewModel
import com.code4galaxy.reviewnow.view.feature.user.util.updateLocale
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    languageViewModel: LanguageViewModel,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val languages = listOf(
        "en" to "English",
        "fr" to "French",
        "de" to "German",
        "es" to "Spanish",
        "ru" to "Russian",
        "uk" to "Ukrainian",
        "hi" to "Hindi"
    )

    val currentLang by languageViewModel.language.collectAsState()
    var selectedLang by remember { mutableStateOf(currentLang) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Select language",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Selected language will be applied immediately",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            languages.forEachIndexed { index, (code, label) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedLang = code
                            scope.launch {
                                languageViewModel.changeLanguage(code)
                                updateLocale(context, code)
                                onDismiss() // close sheet first
                                delay(300)  // give time before recreate
                                (context as? Activity)?.recreate()
                            }
                        }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (selectedLang == code) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                if (index < languages.size - 1) {
                    Divider(modifier = Modifier.padding(horizontal = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
