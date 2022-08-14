package com.akash.opengl

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    lateinit var glSurfaceView:GLSurfaceView
    var rendererSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glSurfaceView = GLSurfaceView(this)



        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo

        val supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2) {
// Request an OpenGL ES 2.0 compatible context. glSurfaceView.setEGLContextClientVersion(2);
            // Assign our renderer.
            glSurfaceView.setEGLContextClientVersion(2)
            glSurfaceView.setRenderer(Renderer(this))
            rendererSet = true
        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.", Toast.LENGTH_LONG)
                .show()
            return
        }

        setContentView(glSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        if(rendererSet) glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        if(rendererSet) glSurfaceView.onPause()
    }


}
