package com.example.baroquebeacon.ui.features.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.baroquebeacon.ui.theme.BaroqueBeaconTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.baroquebeacon.ui.features.overview.composable.OverviewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                BaroqueBeaconTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        OverviewScreen {
                            navigateToDetails(it)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetails(it: String) {
        val action = OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it)
        findNavController().navigate(action)
    }
}