package com.akash.opengl

import android.opengl.GLES20.*
import android.util.Log


class ShaderHelper {


    enum class ShaderType{
        VertexShader,
        FragmentShader
    }



    companion object{
        private val TAG = "ShaderHelper"


        fun compileVertexShader(shaderCode:String):Int{
            return compileShader(GL_VERTEX_SHADER,shaderCode)
        }

        fun compileFragmentShader(shaderCode: String):Int{
            return compileShader(GL_FRAGMENT_SHADER,shaderCode)
        }

        private fun compileShader(type:Int,shaderCode: String):Int{
            val shaderObjectId = glCreateShader(type)

            if(shaderObjectId == 0){
                Log.e(TAG, "compileShader: couldn't create new shader")
                return 0;
            }


            glShaderSource(shaderObjectId,shaderCode)
            glCompileShader(shaderObjectId)

            val compileStatus = IntArray(1)
            glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS,compileStatus,0)

            Log.d(
                TAG,
                "compileShader: Result of compiling source: \n$shaderCode \n ${
                    glGetShaderInfoLog(shaderObjectId)
                }"
            )

            if(compileStatus[0] == 0){
                glDeleteShader(shaderObjectId)
                Log.e(TAG, "compileShader: compilation of shader failed")

                return 0
            }

            return shaderObjectId

        }


        fun linkProgram(fragmentShader:Int,vertexShader:Int):Int{
            val programId = glCreateProgram()
            if(programId == 0){
                Log.e(TAG, "linkProgramme: could not create new program")
                return 0
            }

            glAttachShader(programId,vertexShader)
            glAttachShader(programId,fragmentShader)
            glLinkProgram(programId)

            val linkStatus = IntArray(1)
            glGetProgramiv(programId, GL_LINK_STATUS,linkStatus,0)
            
            if(linkStatus[0] == 0){
                glDeleteProgram(programId)
                Log.e(TAG, "linkProgramme: Linking to program failed", )
            }

            return programId

        }


        fun validateProgram(programId:Int):Boolean{
            glValidateProgram(programId)

            val validateStatus = IntArray(1)
            glGetProgramiv(programId, GL_VALIDATE_STATUS,validateStatus,0)
            Log.d(
                TAG,
                "validateProgram: Result of validating program \n $programId \n ${validateStatus[0]} \n" +
                        glGetProgramInfoLog(programId)
            )

            return validateStatus[0] != 0
        }


        fun buildProgram(vertexShaderSource:String,fragmentShaderSource:String):Int{
            var program = 0;

            val vertexShader = compileVertexShader(vertexShaderSource)
            val fragmentShader = compileFragmentShader(fragmentShaderSource)

            program = linkProgram(fragmentShader,vertexShader)

            Log.d(TAG, "buildProgram: ${validateProgram(program)}")

            return program
        }


    }
}