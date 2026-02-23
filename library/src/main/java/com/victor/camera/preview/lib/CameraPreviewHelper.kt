package com.victor.camera.preview.lib

import android.Manifest
import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.fragment.app.Fragment
import com.victor.camera.preview.lib.CameraScan.OnScanResultCallback
import com.victor.camera.preview.lib.analyze.Analyzer
import com.victor.camera.preview.lib.config.CameraConfig
import com.victor.camera.preview.lib.config.CameraConfigFactory
import com.victor.camera.preview.lib.util.PermissionUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CameraPreviewHelper
 * Author: Victor
 * Date: 2026/2/13 14:12
 * Description: 
 * -----------------------------------------------------------------
 */

class CameraPreviewHelper<T> {

    private val TAG = "CameraPreviewHelper"
    /**
     * 相机权限请求代码
     */
    private val CAMERA_PERMISSION_REQUEST_CODE: Int = 0x86

    /**
     * CameraScan
     */
    var mCameraScan: CameraScan<T>? = null

    var mActivity: Activity? = null

    constructor(activity: ComponentActivity, previewView: PreviewView, callback: OnScanResultCallback<T>?) {
        mActivity = activity
        mCameraScan = BaseCameraScan(activity, previewView)
        mCameraScan?.setOnScanResultCallback(callback)
    }

    constructor(fragment: Fragment, previewView: PreviewView,callback: OnScanResultCallback<T>?) {
        mActivity = fragment.activity
        mCameraScan = BaseCameraScan(fragment, previewView)
        mCameraScan?.setOnScanResultCallback(callback)
    }

    /**
     * 启动相机预览
     */
    fun startCamera() {
        if (mActivity != null) {
            if (PermissionUtils.checkPermission(mActivity!!, Manifest.permission.CAMERA)) {
                mCameraScan?.startCamera()
            } else {
                Log.d(TAG, "Camera permission not granted, requesting permission.")
                PermissionUtils.requestPermission(
                    mActivity!!,
                    Manifest.permission.CAMERA,
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    /**
     * 释放相机
     */
    fun releaseCamera() {
        mCameraScan?.release()
    }

    fun setCameraConfig(cameraConfig: CameraConfig) {
        mCameraScan?.setCameraConfig(cameraConfig)
    }

    fun setAnalyzer(analyzer: Analyzer<T>) {
        mCameraScan?.setAnalyzer(analyzer)
    }

    fun setAutoStopAnalyze(autoStopAnalyze: Boolean) {
        mCameraScan?.setAutoStopAnalyze(autoStopAnalyze)
    }

    fun bindFlashlightView(v: View) {
        mCameraScan?.bindFlashlightView(v)
    }

    fun setDarkLightLux(lightLux: Float) {
        mCameraScan?.setDarkLightLux(lightLux)
    }
    fun setBrightLightLux(lightLux: Float) {
        mCameraScan?.setBrightLightLux(lightLux)
    }

    fun enableTorch(isTorch: Boolean) {
        mCameraScan?.enableTorch(isTorch)
    }

    fun setAnalyzeImage(analyze: Boolean) {
        mCameraScan?.setAnalyzeImage(analyze)
    }

    fun setPlayBeep(playBeep: Boolean) {
        mCameraScan?.setPlayBeep(playBeep)
    }

    fun setVibrate(vibrate: Boolean) {
        mCameraScan?.setVibrate(vibrate)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
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
            mActivity?.finish()
        }
    }

    /**
     * 切换摄像头
     */
    fun cameraSwitch(isFront: Boolean) {
        val lensFacing = if (isFront) {
            CameraSelector.LENS_FACING_FRONT
        } else {
            CameraSelector.LENS_FACING_BACK
        }
        mCameraScan?.setCameraConfig(
            CameraConfigFactory.createDefaultCameraConfig(mActivity, lensFacing)
        )
        // 修改CameraConfig相关配置后，需重新调用startCamera后配置才能生效
        startCamera()
    }

    fun onDestroy() {
        releaseCamera()
    }

}