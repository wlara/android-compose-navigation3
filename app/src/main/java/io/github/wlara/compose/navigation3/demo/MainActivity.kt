package io.github.wlara.compose.navigation3.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.wlara.compose.navigation3.demo.ui.theme.ComposeNavigation3DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigation3DemoTheme {
                // TODO: Comment/Uncomment bellow to select active AppNavigation version
                io.github.wlara.compose.navigation3.demo.basic.AppNavigation()
                //io.github.wlara.compose.navigation3.demo.enhanced.AppNavigation()
                //io.github.wlara.compose.navigation3.demo.animations.AppNavigation()
                //io.github.wlara.compose.navigation3.demo.bottom.AppNavigation()
            }
        }
    }
}
