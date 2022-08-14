package com.akash.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix.orthoM
import android.util.Log
import com.akash.opengl.TextResourceReader.Companion.readTextFileFromResource
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class Renderer(val context: Context) : GLSurfaceView.Renderer{


    private lateinit var vertexData:FloatBuffer
    private var program = -1
    private var uColorLocation = -1;
    private var aColorLocation = -1;
    private var aPositionLocation = -1;
    private val projectionMatrix = FloatArray(16)
    private var uMatrixLocation = -1;

    val vertices = floatArrayOf(
//        //triangle 1
//        0f,0f,
//        9f,14f,
//        0f,14f,
//
//        //triangle 2
//        0f,0f,
//        9f,0f,
//        9f,14f,
//
//        //line
//        0f, 7f,
//        9f,7f,
//
//        //Mallets
//        4.5f,2f,
//        4.5f,12f

//        // Triangle 1
//        -0.5f, -0.5f,
//        0.5f,  0.5f,
//        -0.5f,  0.5f,
//
//        // Triangle 2
//        -0.5f, -0.5f,
//        0.5f, -0.5f,
//        0.5f,  0.5f,
//
//        // Triangle 3
//        -0.51f, -0.51f,
//        0.51f,  0.51f,
//        -0.51f,  0.51f,
//
//        // Triangle 4
//        -0.51f, -0.51f,
//        0.51f, -0.51f,
//        0.51f,  0.51f,
//
//        // Line 1
//        -0.5f, 0f,
//        0.5f, 0f,
//
//        // Mallets
//        0f, -0.25f,
//        0f, 0.25f


//        // Triangle Fan
//        0f,  0f,
//        -0.5f, -0.5f,
//        0.5f, -0.5f,
//        0.5f,  0.5f,
//        -0.5f,  0.5f,
//        -0.5f, -0.5f,
//
//
//        // Line 1
//        -0.5f, 0f,
//        0.5f, 0f,
//
//        // Mallets
//        0f, -0.25f,
//        0f, 0.25f


        // Triangle Fan
        0f,    0f,   1f,   1f,   1f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f,  0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f,  0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

        // Line 1
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 1f, 0f, 0f,

        // Mallets
        0f, -0.4f, 0f, 0f, 1f,
        0f,  0.4f, 1f, 0f, 0f
    )

    init {
        vertexData = ByteBuffer.allocateDirect(
            vertices.size * BYTES_PER_FLOAT
        ).order(ByteOrder.nativeOrder()).asFloatBuffer()

        vertexData.put(vertices)
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        val vertexShaderCode = readTextFileFromResource(context,R.raw.simple_vertext_shader)
        val fragmentShaderCode = readTextFileFromResource(context,R.raw.simple_fragment_shader)

        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderCode)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderCode)


        program = ShaderHelper.linkProgram(fragmentShader,vertexShader)

        Log.d(TAG, "onSurfaceCreated: " + ShaderHelper.validateProgram(program))

        glUseProgram(program)

        aColorLocation = glGetAttribLocation(program, A_COLOR)

        aPositionLocation = glGetAttribLocation(program, A_POSITION)

        uMatrixLocation = glGetUniformLocation(program, U_MATRIX)


        vertexData.position(0)
        glVertexAttribPointer(
              aPositionLocation
            , POSITION_COMPONENT_COUNT
            , GL_FLOAT
            ,false
            , STRIDE
            , vertexData
        )

        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT,
            false, STRIDE, vertexData);

        glEnableVertexAttribArray(aPositionLocation)
        glEnableVertexAttribArray(aColorLocation);

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height);

        val aspectRatio = if(width > height) width/height.toFloat() else height / width.toFloat()

        if(width > height){
            orthoM(projectionMatrix,0,-aspectRatio,aspectRatio,-2f,2f,-1f,1f)
        }else{
            orthoM(projectionMatrix,0,-1f,1f,-aspectRatio,aspectRatio,-1f,1f)
        }

    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT);


        glUniformMatrix4fv(uMatrixLocation,1,false,projectionMatrix,0)

//        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);



        // Draw the line in the middle
//        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);



        // Draw the first mallet blue.
//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);

        // Draw the second mallet red.
//        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }

    companion object{
        val TAG = "Renderer"


        private val U_COLOR = "u_Color"
        private val A_POSITION = "a_Position"
        private val A_COLOR = "a_Color"
        private val U_MATRIX = "u_Matrix"

        private val BYTES_PER_FLOAT = 4
        private val POSITION_COMPONENT_COUNT = 2
        private val COLOR_COMPONENT_COUNT = 3
        private val STRIDE = (POSITION_COMPONENT_COUNT+ COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT

    }


}