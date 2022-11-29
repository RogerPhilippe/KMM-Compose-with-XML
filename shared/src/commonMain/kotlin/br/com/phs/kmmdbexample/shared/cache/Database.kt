package br.com.phs.kmmdbexample.shared.cache

import br.com.phs.shared.cache.AppDatabase
import br.com.phs.shared.cache.Note

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllNotes()
        }
    }

    internal fun getAllNotes(): List<Note> {
        return dbQuery.selectAllNote().executeAsList()
    }

    internal fun createNotes(notes: List<Note>) {
        dbQuery.transaction {
            notes.forEach { note ->
                this@Database.insertNote(note)
            }
        }
    }

    // *********************************************************************************************
    // ** Privet Methods
    // *********************************************************************************************

    private fun insertNote(note: Note) {
        dbQuery.insertNote(
            id = note.id,
            name = note.name,
            type = note.type,
            status = note.status
        )
    }

}