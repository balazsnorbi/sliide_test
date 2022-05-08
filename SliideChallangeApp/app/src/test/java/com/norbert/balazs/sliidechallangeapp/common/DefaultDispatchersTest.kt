package com.norbert.balazs.sliidechallangeapp.common

import org.junit.Test

class DefaultDispatchersTest {

    @Test
    fun testDispatcher() {
        val provider = DefaultDispatchers()
        provider.io
        provider.main
        provider.default
        assert(true)
    }
}