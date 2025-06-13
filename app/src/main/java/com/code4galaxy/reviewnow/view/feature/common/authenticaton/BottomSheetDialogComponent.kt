package com.code4galaxy.reviewnow.view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.code4galaxy.reviewnow.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Define the BottomSheetDialogUI composable function
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialogUI() {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
        sheetState = sheetState,
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        }) {
        SheetContent()
    }
}

@Composable
fun SheetContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.dimen_16_dp))
    ) {

        Text("Choose an action", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        Button(onClick = {}) {
            Text("Action 1")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {}) {
            Text("Action 2")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {}) {
            Text("Action 3")
        }
    }
}

@Preview
@Composable
private fun BottomSheetDialogUIPrev() {
    BottomSheetDialogUI()
}

class SheetViewModel : ViewModel() {
    private val _showSheet = MutableStateFlow(false)
    var showSheet = _showSheet.asStateFlow()

    fun show() = _showSheet.update { true }
    fun hide() = _showSheet.update { false }
}

@Composable
fun InvokeBottomSheetUI() {
    val viewModel: SheetViewModel = viewModel()
    val showSheet = viewModel.showSheet.collectAsStateWithLifecycle()

    if (showSheet.value) {
        BottomSheetDialogUI()
    }
}