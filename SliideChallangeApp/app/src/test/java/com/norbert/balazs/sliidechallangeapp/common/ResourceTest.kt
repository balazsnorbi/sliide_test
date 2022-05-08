package com.norbert.balazs.sliidechallangeapp.common

import org.junit.Assert
import org.junit.Test

class ResourceTest {

    @Test
    fun testSuccess() {
        val resource = Resource.Success(5)
        Assert.assertEquals(resource.data, 5)
        Assert.assertEquals(resource.message, null)
    }

    @Test
    fun testLoading() {
        val resource = Resource.Loading(null)
        Assert.assertNull(resource.data)
        Assert.assertEquals(resource.message, null)
    }

    @Test
    fun testError() {
        val resource = Resource.Error("Error", 5)
        Assert.assertEquals(resource.message, "Error")
        Assert.assertEquals(resource.data, 5)
    }
}