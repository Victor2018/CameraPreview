/*
 * Copyright (C) Jenly, CameraScan Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.victor.camera.preview.lib;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.victor.camera.preview.lib.util.BitmapUtils;

/**
 * 分析结果
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@SuppressWarnings("unused")
public class AnalyzeResult {

    /**
     * 图像数据
     */
    private final byte[] imageData;
    /**
     * 图像格式：{@link ImageFormat}
     */
    private final int imageFormat;
    /**
     * 帧元数据
     */
    private final FrameMetadata frameMetadata;
    /**
     * 分析的图像
     */
    private Bitmap bitmap;

    /**
     * 二维码识别区域
     */
    private Rect cropFrameRect;

    /**
     * 分析结果
     */
    private final String result;

    public AnalyzeResult(@NonNull byte[] imageData, int imageFormat, @NonNull FrameMetadata frameMetadata, @NonNull String result,Rect cropFrameRect) {
        this.imageData = imageData;
        this.imageFormat = imageFormat;
        this.frameMetadata = frameMetadata;
        this.result = result;
        this.cropFrameRect = cropFrameRect;
    }

    public void setCropFrameRect(Rect cropFrameRect) {
        this.cropFrameRect = cropFrameRect;
    }

    public Rect getCropFrameRect() {
        return cropFrameRect;
    }

    /**
     * 获取图像帧数据：YUV数据
     *
     * @return 图像帧数据：YUV数据
     */
    @NonNull
    public byte[] getImageData() {
        return imageData;
    }

    /**
     * 获取图像格式：{@link ImageFormat}
     *
     * @return {@link ImageFormat}
     */
    public int getImageFormat() {
        return imageFormat;
    }

    /**
     * 获取帧元数据：{@link FrameMetadata}
     *
     * @return {@link FrameMetadata}
     */
    @NonNull
    public FrameMetadata getFrameMetadata() {
        return frameMetadata;
    }

    /**
     * 获取分析图像
     *
     * @return 分析的图像
     */
    @Nullable
    public Bitmap getBitmap() {
        if (imageFormat != ImageFormat.NV21) {
            throw new IllegalArgumentException("only support ImageFormat.NV21 for now.");
        }
        if (bitmap == null) {
            bitmap = BitmapUtils.getBitmap(imageData, frameMetadata);
        }
        return bitmap;
    }

    /**
     * 获取图像的宽
     *
     * @return 图像的宽
     * @deprecated 替换为 {@link #getImageWidth()}
     */
    @Deprecated
    public int getBitmapWidth() {
        return getImageWidth();
    }

    /**
     * 获取图像的宽
     *
     * @return 图像的宽
     */
    public int getImageWidth() {
        if (frameMetadata.getRotation() % 180 == 0) {
            return frameMetadata.getWidth();
        }
        return frameMetadata.getHeight();
    }

    /**
     * 获取图像的高
     *
     * @return 图像的高
     * @deprecated 替换为 {@link #getImageHeight()}
     */
    @Deprecated
    public int getBitmapHeight() {
        return getImageHeight();
    }

    /**
     * 获取图像的高
     *
     * @return 图像的高
     */
    public int getImageHeight() {
        if (frameMetadata.getRotation() % 180 == 0) {
            return frameMetadata.getHeight();
        }
        return frameMetadata.getWidth();
    }

    /**
     * 获取分析结果
     *
     * @return 分析结果
     */
    @NonNull
    public String getResult() {
        return result;
    }
}
