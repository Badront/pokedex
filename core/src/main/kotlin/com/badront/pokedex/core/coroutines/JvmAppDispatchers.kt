package com.badront.pokedex.core.coroutines

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class JvmAppDispatchers @Inject constructor() : AppDispatchers {
    override val ui = Dispatchers.Main
    override val io = Dispatchers.IO
    override val computing = Dispatchers.Default
}