package com.akash.opengl

import android.content.Context
import java.io.InputStreamReader
import java.lang.Exception

class TextResourceReader {

    companion object{


        fun readTextFileFromResource(context: Context,resourceId:Int) : String{
            val stringBuilder = StringBuilder();

            try{

                val inputStream = context.resources.openRawResource(resourceId)
                val inputStreamReader = InputStreamReader(inputStream)


                inputStreamReader.forEachLine {
                    stringBuilder.appendLine(it.trim())
                }

            }catch (e:Exception){

            }

            return stringBuilder.toString()

        }

    }
}