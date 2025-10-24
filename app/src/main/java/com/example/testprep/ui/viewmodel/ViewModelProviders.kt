package com.example.testprep.ui.viewmodel

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> activityViewModel(): VM {
    val activity = LocalContext.current as ComponentActivity
    return viewModel(viewModelStoreOwner = activity)
}
