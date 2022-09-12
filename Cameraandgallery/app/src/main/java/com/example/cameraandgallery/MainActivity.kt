package com.example.cameraandgallery


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
    , Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val FLAG_PERM_CAMERA =98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCamera.setOnClickListener {
            if(isPermitted(CAMERA_PERMISSION)) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, FLAG_PERM_CAMERA) }

        }
    }

    fun isPermitted(permissions:Array<String>,flag: Int): Boolean {

        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permissions)
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, FLAG_REQ_CAMERA )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            FLAG_PERM_CAMERA -> {
                var checked =true
                for(grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED) {
                        checked = false
                        break
                    }
                }
                if(checked) {
                    openCamera()
                }
            }
        }
    }

}