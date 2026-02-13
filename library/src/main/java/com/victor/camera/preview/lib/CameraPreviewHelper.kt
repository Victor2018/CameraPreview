package com.victor.camera.preview.lib

import android.Manifest
import android.app.Activity
import android.graphics.Rect
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.view.PreviewView
import androidx.fragment.app.Fragment
import com.victor.camera.preview.lib.CameraScan.OnScanResultCallback
import com.victor.camera.preview.lib.analyze.Analyzer
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

class CameraPreviewHelper {

    private val TAG = "CameraPreviewHelper"
    /**
     * 相机权限请求代码
     */
    private val CAMERA_PERMISSION_REQUEST_CODE: Int = 0x86

    /**
     * CameraScan
     */
    private var mCameraScan: CameraScan? = null

    var mActivity: Activity? = null

    constructor(activity: ComponentActivity, previewView: PreviewView, callback: OnScanResultCallback?) {
        mActivity = activity
        mCameraScan = BaseCameraScan(activity, previewView)
        mCameraScan?.setOnScanResultCallback(callback)
    }

    constructor(fragment: Fragment, previewView: PreviewView,callback: OnScanResultCallback?) {
        mActivity = fragment.activity
        mCameraScan = BaseCameraScan(fragment, previewView)
        mCameraScan?.setOnScanResultCallback(callback)
    }

    /**
     * 启动相机预览
     */
    fun startCamera() {
        if (mCameraScan != null) {
            if (PermissionUtils.checkPermission(mActivity!!, Manifest.permission.CAMERA)) {
                mCameraScan!!.startCamera()
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

    fun setAnalyzer(analyzer: Analyzer) {
        mCameraScan?.setAnalyzer(analyzer)
    }

    fun enableTorch(isTorch: Boolean) {
        mCameraScan?.enableTorch(!isTorch)
    }

    fun setCropFrameRect(rect: Rect?) {
        mCameraScan?.setCropFrameRect(rect)
    }

    fun setAnalyzeImage(analyze: Boolean) {
        mCameraScan?.setAnalyzeImage(analyze)
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

    fun onDestroy() {
        releaseCamera()
    }

}