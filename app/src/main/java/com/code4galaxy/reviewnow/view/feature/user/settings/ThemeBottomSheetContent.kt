package com.code4galaxy.reviewnow.view.feature.user.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.theme.AppTheme

@Composable
fun ThemeBottomSheetContent(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "App Theme",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose how the app looks",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppTheme.entries.forEach { theme ->
                ThemeOptionCard(
                    theme = theme,
                    isSelected = selectedTheme == theme,
                    onClick = { onThemeSelected(theme) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ThemeOptionCard(
    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(0.40f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(id = getThemeIcon(theme)),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(135.dp)
            )


            Text(
                text = theme.name.lowercase().replaceFirstChar { it.uppercaseChar() },
                style = MaterialTheme.typography.bodyMedium
            )

            RadioButton(
                selected = isSelected,
                onClick = null
            )
        }
    }
}

fun getThemeIcon(theme: AppTheme): Int {
    return when (theme) {
        AppTheme.LIGHT -> R.drawable.ic_theme_light
        AppTheme.DARK -> R.drawable.ic_theme_dark
        AppTheme.SYSTEM -> R.drawable.ic_theme_system
    }
}
