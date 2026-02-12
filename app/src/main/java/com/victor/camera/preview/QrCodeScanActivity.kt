package com.victor.camera.preview

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.victor.camera.preview.lib.AnalyzeResult
import com.victor.camera.preview.lib.BaseCameraScan
import com.victor.camera.preview.lib.CameraScan
import com.victor.camera.preview.lib.CameraScan.OnScanResultCallback
import com.victor.camera.preview.lib.analyze.Analyzer
import com.victor.camera.preview.lib.util.PermissionUtils

class QrCodeScanActivity<T> : AppCompatActivity(), OnScanResultCallback<T> {

    companion object {
        fun intentStart (context: Context) {
            val intent = Intent(context, QrCodeScanActivity::class.java)
            context.startActivity(intent)
        }
    }

    val  TAG = "QrCodeScanActivity"
    /**
     * 相机权限请求代码
     */
    private val CAMERA_PERMISSION_REQUEST_CODE: Int = 0x86

    /**
     * 预览视图
     */
    protected var previewView: PreviewView? = null

    /**
     * 手电筒视图
     */
    protected var ivFlashlight: View? = null

    /**
     * CameraScan
     */
    private var mCameraScan: CameraScan<T>? = null

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
        mCameraScan = createCameraScan(previewView)
        initCameraScan(mCameraScan!!)
        startCamera()
    }

    /**
     * 初始化CameraScan
     */
    fun initCameraScan(cameraScan: CameraScan<T>) {
        cameraScan.setAnalyzer(createAnalyzer())
            .bindFlashlightView(ivFlashlight)
            .setOnScanResultCallback(this)
    }

    /**
     * 切换闪光灯状态（开启/关闭）
     */
    protected fun toggleTorchState() {
        if (getCameraScan() != null) {
            val isTorch: Boolean = getCameraScan().isTorchEnabled()
            getCameraScan().enableTorch(!isTorch)
            if (ivFlashlight != null) {
                ivFlashlight!!.isSelected = !isTorch
            }
        }
    }

    /**
     * 启动相机预览
     */
    fun startCamera() {
        if (mCameraScan != null) {
            if (PermissionUtils.checkPermission(this, Manifest.permission.CAMERA)) {
                mCameraScan!!.startCamera()
            } else {
                Log.d(TAG, "Camera permission not granted, requesting permission.")
                PermissionUtils.requestPermission(
                    this,
                    Manifest.permission.CAMERA,
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    /**
     * 释放相机
     */
    private fun releaseCamera() {
        if (mCameraScan != null) {
            mCameraScan!!.release()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            requestCameraPermissionResult(permissions, grantResults)
        }
    }

    /**
     * 请求Camera权限回调结果
     *
     * @param permissions  权限
     * @param grantResults 授权结果
     */
    fun requestCameraPermissionResult(permissions: Array<String?>, grantResults: IntArray) {
        if (PermissionUtils.requestPermissionsResult(
                Manifest.permission.CAMERA,
                permissions,
                grantResults
            )
        ) {
            startCamera()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        releaseCamera()
        super.onDestroy()
    }

    /**
     * 获取[CameraScan]
     *
     * @return [.mCameraScan]
     */
    fun getCameraScan(): CameraScan<T> {
        return mCameraScan!!
    }


    /**
     * 创建[CameraScan]
     *
     * @param previewView [PreviewView]
     * @return [CameraScan]
     */
    fun createCameraScan(previewView: PreviewView?): CameraScan<T> {
        return BaseCameraScan(this, previewView!!)
    }

    /**
     * 创建分析器
     *
     * @return [Analyzer]
     */
    fun createAnalyzer(): Analyzer<T>? {
        return null
    }

    override fun onScanResultCallback(result: AnalyzeResult<T>) {
    }
}