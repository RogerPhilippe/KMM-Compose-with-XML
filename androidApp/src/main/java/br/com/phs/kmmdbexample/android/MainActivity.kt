package br.com.phs.kmmdbexample.android

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import br.com.phs.kmmdbexample.Greeting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //GreetingView(Greeting().greet())
                    ComposerWithXML(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun ComposerWithXML(text: String) {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.main_layout, null)
        },
        modifier = Modifier.fillMaxSize(),
        update = {
            /**
             * This is set View here you set view click and the ids
             */
            it.findViewById<TextView>(R.id.mainText)?.let { mainText ->
                mainText.text = text
                it.setOnClickListener { showText(mainText.toString(), mainText) }
            }
        }
    )
}

fun showText(toString: String, view: View) {
    Toast.makeText(view.context, toString, Toast.LENGTH_SHORT).show()
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        //GreetingView("Hello, Android!")
        ComposerWithXML("Hello, Androidwith XML!")
    }
}
