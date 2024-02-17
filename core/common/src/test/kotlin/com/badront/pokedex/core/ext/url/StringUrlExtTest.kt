package com.badront.pokedex.core.ext.url

import org.junit.Test

class StringUrlExtTest {
    @Test
    fun `url without separator parses correctly`() {
        val url = "https://pokeapi.co/api/v2/pokemon/1"
        assert(url.lastUrlPathSegment() == "1")
    }

    @Test
    fun `url with separator parses correctly`() {
        val url = "https://pokeapi.co/api/v2/pokemon/1/"
        assert(url.lastUrlPathSegment() == "1")
    }

    @Test
    fun `empty url returns empty segment`() {
        val url = ""
        assert(url.lastUrlPathSegment() == "")
    }
}