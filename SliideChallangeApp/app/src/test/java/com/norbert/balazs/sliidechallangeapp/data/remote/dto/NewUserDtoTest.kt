package com.norbert.balazs.sliidechallangeapp.data.remote.dto

import org.junit.Assert
import org.junit.Test

class NewUserDtoTest {

    @Test
    fun testNewUserDto() {
        val dto = NewUserDto(
            "name",
            "email",
            "gender",
            "status"
        )

        Assert.assertEquals(dto.name, "name")
        Assert.assertEquals(dto.email, "email")
        Assert.assertEquals(dto.gender, "gender")
        Assert.assertEquals(dto.status, "status")
    }
}