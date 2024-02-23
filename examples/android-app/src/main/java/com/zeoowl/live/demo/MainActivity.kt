package com.zeoowl.live.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zeoowl.live.demo.build.BuildProfile
import com.zeoowl.live.demo.ui.theme.DemoTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    BuildProfile.buildType

    enableEdgeToEdge()
    setContent {
      DemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Column(
            modifier = Modifier.padding(innerPadding)
              .padding(horizontal = 10.dp)
          ) {
            Item(
              content = "Is Debuggable: ${BuildProfile.isDebuggable}",
              modifier = Modifier.padding(vertical = 2.dp),
            )
            Item(
              content = "Version: ${BuildProfile.versionName} (${BuildProfile.versionCode})",
              modifier = Modifier.padding(vertical = 2.dp),
            )
            Item(
              content = "Build Type: ${BuildProfile.buildType}",
              modifier = Modifier.padding(vertical = 2.dp),
            )
          }
        }
      }
    }
  }
}

@Composable
fun Item(content: String, modifier: Modifier = Modifier) {
  Text(
    text = content,
    modifier = modifier,
  )
}
