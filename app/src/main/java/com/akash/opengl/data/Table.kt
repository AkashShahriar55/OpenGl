package com.akash.opengl.data

import android.opengl.GLES20.GL_TRIANGLE_FAN
import android.opengl.GLES20.glDrawArrays
import com.akash.opengl.Constants.BYTES_PER_FLOAT

class Table {

    private val vertexArray:VertexArray = VertexArray(VERTEX_DATA)


    fun bindData(textureShaderProgram:TextureShaderProgram){
        vertexArray.setVertexAttribPointer(
            0,
            textureShaderProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT,
            STRIDE
        )

        vertexArray.setVertexAttribPointer(
            POSITION_COMPONENT_COUNT,
            textureShaderProgram.getTextureCoordinatesAttributeLocation(),
            TEXTURE_COORDINATE_COMPONENT_COUNT,
            STRIDE
        )
    }

    fun draw(){
        glDrawArrays(GL_TRIANGLE_FAN,0,6)

    }


    companion object{
        private const val POSITION_COMPONENT_COUNT = 2
        private const val TEXTURE_COORDINATE_COMPONENT_COUNT= 2
        private const val STRIDE = (POSITION_COMPONENT_COUNT * TEXTURE_COORDINATE_COMPONENT_COUNT) * BYTES_PER_FLOAT
        private val VERTEX_DATA = floatArrayOf(
            //order of coordinates x,y,s,t

            //Triangle fan
            0f,0f,0.5f,0.5f
            -0.5f,-0.8f,0f,0.9f,
            0.5f,-0.8f,1f,0.9f,
            0.5f,0.8f,1f,0.1f,
            -0.5f,0.8f,0f,0.1f,
            -0.5f,0.8f,0f,0.9f
        )
    }
}