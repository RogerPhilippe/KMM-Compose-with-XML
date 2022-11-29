package br.com.phs.kmmdbexample.shared

import br.com.phs.shared.cache.Note

fun Note.update(
    id: String = this.id,
    name: String = this.name,
    type: String = this.type,
    status: Long = this.status
): Note {
    return Note(
        id = id,
        name = name,
        type = type,
        status = status
    )
}