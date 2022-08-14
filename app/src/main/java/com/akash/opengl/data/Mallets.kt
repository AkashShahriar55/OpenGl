package com.akash.opengl.data

import android.opengl.GLES20
import android.opengl.GLES20.GL_POINTS
import android.opengl.GLES20.glDrawArrays
import com.akash.opengl.Constants

class Mallets {

    private val vertexArray:VertexArray = VertexArray(VERTEX_DATA)


    fun bindData(colorShaderProgram:ColorShaderProgram){
        vertexArray.setVertexAttribPointer(
            0,
            colorShaderProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT,
            STRIDE
        )

        vertexArray.setVertexAttribPointer(
            POSITION_COMPONENT_COUNT,
            colorShaderProgram.getColorAttributeLocation(),
            COLOR_COMPONENT_COUNT,
            STRIDE
        )
    }

    fun draw(){
        glDrawArrays(GL_POINTS, 0, 2)

    }


    companion object{
        private const val POSITION_COMPONENT_COUNT = 2
        private const val COLOR_COMPONENT_COUNT= 3
        private const val STRIDE = (POSITION_COMPONENT_COUNT * COLOR_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT
        private val VERTEX_DATA = floatArrayOf(
            //order of coordinates x,y,r,g,b

            //Triangle fan
            0.0f, -0.4f, 0f,0f,1f,
            0.0f, 0.4f , 1f,0f,0f
        )
    }
}