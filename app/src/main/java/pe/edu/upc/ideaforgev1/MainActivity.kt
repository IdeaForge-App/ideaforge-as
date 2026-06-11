package pe.edu.upc.ideaforgev1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pe.edu.upc.ideaforgev1.navigation.AppNavHost
import pe.edu.upc.ideaforgev1.ui.theme.IdeaForgev1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IdeaForgev1Theme {
                AppNavHost()
            }
        }
    }
}
