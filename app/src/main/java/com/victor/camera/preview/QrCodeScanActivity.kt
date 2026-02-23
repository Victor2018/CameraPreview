package com.victor.camera.preview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.victor.camera.preview.lib.AnalyzeResult
import com.victor.camera.preview.lib.CameraPreviewHelper
import com.victor.camera.preview.lib.CameraScan.OnScanResultCallback

class QrCodeScanActivity : AppCompatActivity(),OnScanResultCallback<String> {

    companion object {
        fun intentStart (context: Context) {
            val intent = Intent(context, QrCodeScanActivity::class.java)
            context.startActivity(intent)
        }
    }

    val  TAG = "QrCodeScanActivity"

    /**
     * 预览视图
     */
    protected var previewView: PreviewView? = null

    protected var mCameraPreviewHelper: CameraPreviewHelper<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_qr_code_scan)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        previewView = findViewById(R.id.previewView)

        mCameraPreviewHelper = CameraPreviewHelper(this,previewView!!,this)
        mCameraPreviewHelper?.setAnalyzer(TestAnalyzer())
        mCameraPreviewHelper?.startCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mCameraPreviewHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        mCameraPreviewHelper?.releaseCamera()
        super.onDestroy()
    }

    override fun onScanResultCallback(result: AnalyzeResult<String>) {
        Log.e(TAG,"onScanResultCallback-result.imageData = ${result.imageData}")
    }
}