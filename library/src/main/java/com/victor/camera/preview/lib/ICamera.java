package com.victor.camera.preview.lib;

import androidx.annotation.Nullable;
import androidx.camera.core.Camera;

/**
 * 相机定义
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public interface ICamera {

    /**
     * 启动相机预览
     */
    void startCamera();

    /**
     * 停止相机预览
     */
    void stopCamera();

    /**
     * 获取 {@link Camera}
     *
     * @return {@link Camera}
     */
    @Nullable
    Camera getCamera();

    /**
     * 释放
     */
    void release();

}
