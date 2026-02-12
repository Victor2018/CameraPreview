package com.victor.camera.preview.lib.config;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;

/**
 * 相机配置：主要用于提供相机预览时可自定义一些配置，便于拓展；
 * <p>
 * 库中内置实现{@link CameraConfig}的有{@link AdaptiveCameraConfig}；
 * <p>
 * 这里简单说下各自的特点：
 * <p>
 * {@link CameraConfig} - CameraX默认的相机配置
 * <p>
 * {@link AdaptiveCameraConfig} - 自适应相机配置：主要是根据纵横比和设备屏幕的分辨率找到与相机之间合适的相机配置
 * <p>
 * <p>
 * 当使用默认的 {@link CameraConfig}在某些机型上体验欠佳时，你可以尝试使用{@link AdaptiveCameraConfig}
 * <p>
 * 你也可以自定义或覆写 {@link CameraConfig} 中的 {@link #options} 方法，根据需要定制配置。
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public class CameraConfig {

    public CameraConfig() {

    }

    /**
     * <p>
     * 如配置前置摄像头：{@code builder.requireLensFacing(CameraSelector.LENS_FACING_FRONT)}
     * <p>
     * 切记，外部请勿直接调用 {@link #options(CameraSelector.Builder)}
     *
     * @param builder {@link CameraSelector.Builder}
     * @return {@link CameraSelector}
     */
    @NonNull
    public CameraSelector options(@NonNull CameraSelector.Builder builder) {
        return builder.build();
    }

    /**
     * <p>
     * 如配置目标旋转角度为90度：{@code builder.setTargetRotation(Surface.ROTATION_90)}
     * <p>
     * 切记，外部请勿直接调用 {@link #options(Preview.Builder)}
     *
     * @param builder {@link Preview.Builder}
     * @return {@link Preview}
     */
    @NonNull
    public Preview options(@NonNull Preview.Builder builder) {
        return builder.build();
    }

    /**
     * <p>
     * 如配置目标旋转角度为90度：{@code builder.setTargetRotation(Surface.ROTATION_90)}
     * <p>
     * 切记，外部请勿直接调用 {@link  #options(ImageAnalysis.Builder)}
     *
     * @param builder {@link ImageAnalysis.Builder}
     * @return {@link ImageAnalysis}
     */
    @NonNull
    public ImageAnalysis options(@NonNull ImageAnalysis.Builder builder) {
        return builder.build();
    }

}
