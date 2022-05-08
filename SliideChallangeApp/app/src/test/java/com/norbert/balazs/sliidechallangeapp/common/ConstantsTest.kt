package com.norbert.balazs.sliidechallangeapp.common

import org.junit.Assert
import org.junit.Test

class ConstantsTest {

    @Test
    fun testConstants() {
        Assert.assertEquals(APPLICATION_TAG, "SLIIDE_APP")
        Assert.assertEquals(BASE_USERS_DB_URL, "https://gorest.co.in/public/v2/")
        Assert.assertEquals(
            API_KEY,
            "3ba915eb87c8e9933f9a0a3d423bc2c0926002a8896209ba4ab7b7e0c4b6be3b"
        )
    }
}