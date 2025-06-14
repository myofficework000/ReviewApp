import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.view.feature.user.settings.LanguageBottomSheet
import com.code4galaxy.reviewnow.viewmodel.LanguageViewModel

@Composable
fun LanguageSelector(modifier: Modifier = Modifier) {
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val selectedLanguage = languageViewModel.language.collectAsState()
    var showSheet by remember { mutableStateOf(false) }

    val languageMap = mapOf(
        "en" to "English",
        "fr" to "French",
        "de" to "German",
        "es" to "Spanish",
        "ru" to "Russian",
        "uk" to "Ukrainian",
        "hi" to "Hindi"
    )

    Column(modifier = modifier) {
        TextButton(onClick = { showSheet = true }) {
            Text("Change Language")
        }

        Text(
            text = "Current Language: ${languageMap[selectedLanguage.value] ?: selectedLanguage.value}",
            style = MaterialTheme.typography.bodyMedium
        )


        if (showSheet) {
            LanguageBottomSheet(
                languageViewModel = languageViewModel,
                onDismiss = { showSheet = false }
            )
        }
    }
}
