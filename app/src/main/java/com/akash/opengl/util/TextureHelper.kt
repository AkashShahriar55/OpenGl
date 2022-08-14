package com.akash.opengl.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils
import android.util.Log
import com.akash.opengl.ShaderHelper

class TextureHelper {



    companion object{

        const val TAG = "TextureHelper"

        fun loadTexture(context: Context,resId:Int):Int{
            val textureObjectIds = IntArray(1)
            glGenTextures(1,textureObjectIds,0)

            if(textureObjectIds[0] == 0){
                Log.e(TAG, "compileShader: couldn't create new shader")
                return 0;
            }


            val bitmapFactoryOption = BitmapFactory.Options()
            bitmapFactoryOption.inScaled = false

            val texture = BitmapFactory.decodeResource(context.resources,resId)

            if (texture == null) {
                Log.e(TAG, "Resource ID " + resId + " could not be decoded.");
                glDeleteTextures(1, textureObjectIds, 0);
                return 0;
            }

            glBindTexture(GL_TEXTURE_2D,textureObjectIds[0])
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

            GLUtils.texImage2D(GL_TEXTURE_2D,0,texture,0)
            texture.recycle()

            glGenerateMipmap(GL_TEXTURE_2D)

            glBindTexture(GL_TEXTURE_2D,0)

            return textureObjectIds[0]
        }

    }

}