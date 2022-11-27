package br.com.phs.kmmdbexample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform