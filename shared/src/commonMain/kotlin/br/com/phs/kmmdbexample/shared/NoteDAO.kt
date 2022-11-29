package br.com.phs.kmmdbexample.shared

import br.com.phs.kmmdbexample.shared.cache.Database
import br.com.phs.kmmdbexample.shared.cache.DatabaseDriverFactory
import br.com.phs.shared.cache.Note

class NoteDAO (databaseDriverFactory: DatabaseDriverFactory) {

    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    fun createNotes(notes: List<Note>) {
        database.createNotes(notes)
    }

    @Throws(Exception::class)
    fun getNotes(): List<Note> {
        return database.getAllNotes()
    }

    @Throws(Exception::class)
    fun clearData() {
        database.clearDatabase()
    }

}