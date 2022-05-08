package com.norbert.balazs.sliidechallangeapp.data.remote.dto

import org.junit.Assert
import org.junit.Test

class UserDtoTest {

    @Test
    fun testUserDto() {
        val dto = UserDto(
            "email",
            "gender",
            6,
            "name",
            "status"
        )
        Assert.assertEquals(dto.name, "name")
        Assert.assertEquals(dto.email, "email")
        Assert.assertEquals(dto.gender, "gender")
        Assert.assertEquals(dto.status, "status")
        Assert.assertEquals(dto.id, 6)

        val user = dto.toUser()
        Assert.assertEquals(user.name, "name")
        Assert.assertEquals(user.emailAddress, "email")
        Assert.assertEquals(user.id, 6)
    }
}