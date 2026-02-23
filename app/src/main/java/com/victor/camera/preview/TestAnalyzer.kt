package com.victor.camera.preview

import android.graphics.ImageFormat
import android.graphics.Rect
import android.util.Log
import androidx.camera.core.ImageProxy
import com.victor.camera.preview.lib.AnalyzeResult
import com.victor.camera.preview.lib.FrameMetadata
import com.victor.camera.preview.lib.analyze.Analyzer


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TestAnalyzer
 * Author: Victor
 * Date: 2026/2/13 13:55
 * Description: 
 * -----------------------------------------------------------------
 */

class TestAnalyzer: Analyzer<String> {
    override fun analyze(
        imageProxy: ImageProxy,
        listener: Analyzer.OnAnalyzeListener<String>
    ) {
        listener.onSuccess(AnalyzeResult(byteArrayOf(6), ImageFormat.NV21,
            FrameMetadata(100,100,60), ""))
    }
}