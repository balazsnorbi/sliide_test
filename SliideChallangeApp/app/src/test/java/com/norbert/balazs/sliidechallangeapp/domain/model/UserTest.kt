package com.norbert.balazs.sliidechallangeapp.domain.model

import org.junit.Assert
import org.junit.Test

class UserTest {

    @Test
    fun testUser() {
        val user = User(
            5,
            "test@gmail.com",
            "Test User"
        )

        Assert.assertEquals(user.id, 5)
        Assert.assertEquals(user.emailAddress, "test@gmail.com")
        Assert.assertEquals(user.name, "Test User")
    }
}