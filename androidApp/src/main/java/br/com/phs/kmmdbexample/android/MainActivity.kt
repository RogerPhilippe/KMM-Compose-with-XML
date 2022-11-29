package br.com.phs.kmmdbexample.android

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import br.com.phs.shared.cache.Note
import java.util.*

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewModel.noteCount().let {
            Toast.makeText(this, "NoteCount: $it", Toast.LENGTH_SHORT).show()
        }
        this.viewModel.getAllNOtes()

        this.setupObservers()

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ComposerWithXML(this.viewModel, this)
                }
            }
        }

    }

    private fun setupObservers() {

        this.viewModel.savedStatus.observe(this) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        }

    }

}

@Composable
fun ComposerWithXML(viewModel: MainViewModel? = null, lifecycleOwner: LifecycleOwner? = null) {

    AndroidView(
        factory = {
            View.inflate(it, R.layout.main_layout, null)
        },
        modifier = Modifier.fillMaxSize(),
        update = {
            /**
             * This is set View here you set view click and the ids
             */
            it.findViewById<Button>(R.id.addBtn)?.let { addBtn ->
                addBtn.setOnClickListener {
                    val id = Calendar.getInstance().timeInMillis.toString()
                    viewModel?.currentNote = Note(id, "msg:$id", "msg", 1)
                    viewModel?.createNote()
                    viewModel?.getAllNOtes()
                }
            }

            lifecycleOwner?.let { owner ->
                viewModel?.noteList?.observe(owner) { list ->
                    if (!list.isNullOrEmpty()) {
                        val mainList = it.findViewById<RecyclerView>(R.id.mainList)
                        setupMainList(mainList, list)
                    } else {
                        val noContentTV = it.findViewById<TextView>(R.id.noContentTV)
                        setupEmptyContent(noContentTV)
                    }
                }
            }

        }
    )
}

private fun setupMainList(mainListRV: RecyclerView?, list: List<Note>) {
    mainListRV?.let { it ->
        val adapter = NoteAdapter()
        adapter.submitList(list)
        it.adapter = adapter
        it.visibility = View.VISIBLE
    }
}

private fun setupEmptyContent(textView: TextView?) {
    textView?.let {
        it.visibility = View.VISIBLE
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        ComposerWithXML()
    }
}
