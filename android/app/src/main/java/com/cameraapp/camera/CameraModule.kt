package com.cameraapp.camera

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule

class CameraModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {

    init {
        reactContext.addActivityEventListener(this)
    }

    override fun getName(): String {
        return "CameraModule"
    }

    @ReactMethod
    fun openCamera() {
        val activity = currentActivity
        if (activity == null) {
            return
        }
        val intent = Intent(activity, CameraActivity::class.java)
        activity.startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val imagePath = data?.getStringExtra("image_path")
            sendEvent("onImageCaptured", imagePath)
        }
    }

    private fun sendEvent(eventName: String, imagePath: String?) {
        reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, imagePath)
    }

    override fun onNewIntent(intent: Intent?) {}
}
