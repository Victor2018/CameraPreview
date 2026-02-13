package com.victor.camera.preview.lib.analyze;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageProxy;

import com.victor.camera.preview.lib.AnalyzeResult;

/**
 * 分析器：主要用于分析相机预览的帧数据
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@SuppressWarnings("unused")
public interface Analyzer {
    /**
     * 分析图像并将分析的结果通过分析监听器返回
     *
     * @param imageProxy 需要分析的图像
     * @param listener   分析监听器，参见：{@link OnAnalyzeListener}
     */
    void analyze(@NonNull ImageProxy imageProxy, @NonNull Rect cropFrameRect, @NonNull OnAnalyzeListener listener);

    /**
     * Analyze listener
     *
     */
    interface OnAnalyzeListener {
        /**
         * 成功
         *
         * @param result 分析结果
         */
        void onSuccess(@NonNull AnalyzeResult result);

        /**
         * 失败
         *
         * @param e 异常
         */
        void onFailure(@Nullable Exception e);
    }
}
