package com.victor.camera.preview.lib;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;

import com.victor.camera.preview.lib.analyze.Analyzer;

/**
 * 相机扫描基类；{@link BaseCameraScanActivity} 内部持有{@link CameraScan}，便于快速实现扫描识别。
 * <p>
 * 快速实现扫描识别主要有以下几种方式：
 * <p>
 * 1、通过继承 {@link BaseCameraScanActivity}或者{@link BaseCameraScanFragment}或其子类，可快速实现扫描识别。
 * （适用于大多数场景，自定义布局时需覆写getLayoutId方法）
 * <p>
 * 2、在你项目的Activity或者Fragment中实例化一个{@link BaseCameraScan}。（适用于想在扫描界面写交互逻辑，又因为项目
 * 架构或其它原因，无法直接或间接继承{@link BaseCameraScanActivity}或{@link BaseCameraScanFragment}时使用）
 * <p>
 * 3、继承{@link CameraScan}自己实现一个，可参照默认实现类{@link BaseCameraScan}，其他步骤同方式2。（高级用法，谨慎使用）
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
public abstract class BaseCameraScanActivity<T> extends AppCompatActivity implements CameraScan.OnScanResultCallback {

    private final String TAG = getClass().getSimpleName();
    /**
     * 预览视图
     */
    protected PreviewView previewView;

    protected CameraPreviewHelper mCameraPreviewHelper = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isContentView()) {
            setContentView(getLayoutId());
        }
        initUI();
    }

    /**
     * 初始化
     */
    public void initUI() {
        previewView = findViewById(getPreviewViewId());
        mCameraPreviewHelper = new CameraPreviewHelper(this,previewView,this);
        mCameraPreviewHelper.setAnalyzer(createAnalyzer());
        mCameraPreviewHelper.startCamera();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mCameraPreviewHelper != null) {
            mCameraPreviewHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        if (mCameraPreviewHelper != null) {
            mCameraPreviewHelper.releaseCamera();
        }
        super.onDestroy();
    }

    /**
     * 返回true时会自动初始化{@link #setContentView(int)}，返回为false是需自己去初始化{@link #setContentView(int)}
     *
     * @return 默认返回true
     */
    public boolean isContentView() {
        return true;
    }

    /**
     * 布局ID；通过覆写此方法可以自定义布局
     *
     * @return 布局ID
     */
    public int getLayoutId() {
        return R.layout.camera_scan;
    }

    /**
     * 预览视图{@link #previewView}的ID
     *
     * @return 预览视图ID
     */
    public int getPreviewViewId() {
        return R.id.previewView;
    }


    /**
     * 创建分析器
     *
     * @return {@link Analyzer}
     */
    @Nullable
    public abstract Analyzer createAnalyzer();
}
