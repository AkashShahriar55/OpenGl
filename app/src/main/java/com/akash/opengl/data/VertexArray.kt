package com.akash.opengl.data

import android.opengl.GLES20.*
import com.akash.opengl.Constants.BYTES_PER_FLOAT
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class VertexArray(vertexData:FloatArray) {
    val floatBuffer:FloatBuffer


    fun setVertexAttribPointer(dataOffset:Int,attributeLocation:Int,componentCount:Int,stride:Int){
        floatBuffer.position(dataOffset)
        glVertexAttribPointer(attributeLocation,componentCount, GL_FLOAT,false,stride,floatBuffer)
        glEnableVertexAttribArray(attributeLocation)

        floatBuffer.position(0)
    }


    init {
        floatBuffer = ByteBuffer
            .allocateDirect(vertexData.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexData)
    }
}