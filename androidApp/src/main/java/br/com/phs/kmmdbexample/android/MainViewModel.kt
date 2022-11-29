package br.com.phs.kmmdbexample.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import br.com.phs.kmmdbexample.shared.NoteDAO
import br.com.phs.kmmdbexample.shared.cache.DatabaseDriverFactory
import br.com.phs.kmmdbexample.shared.update
import br.com.phs.shared.cache.Note

class MainViewModel(dbFactory: DatabaseDriverFactory): ViewModel() {

    private val noteDao = NoteDAO(dbFactory)
    var currentNote: Note? = null
    val savedStatus = MutableLiveData<Boolean>()
    val noteList = MutableLiveData<List<Note>>()

    fun createNote() {
        currentNote?.let {
            noteDao.createNotes(arrayListOf(it))
            savedStatus.value = true
        }
    }

    fun getAllNOtes() {
        noteList.value = noteDao.getNotes()
    }

    fun deleteNote() {
        currentNote?.let { note ->
            noteDao.createNotes(arrayListOf(note.update(status = 0)))
        }
    }

    fun noteCount(): Int {
        return noteDao.getNotes().size
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val dbDriver = DatabaseDriverFactory(application.applicationContext)
                return MainViewModel(dbDriver) as T
            }
        }

    }

}