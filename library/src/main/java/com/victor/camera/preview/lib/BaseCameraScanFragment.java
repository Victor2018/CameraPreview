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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.fragment.app.Fragment;

import com.victor.camera.preview.lib.analyze.Analyzer;
import com.victor.camera.preview.lib.util.PermissionUtils;

/**
 * 相机扫描基类；{@link BaseCameraScanFragment} 内部持有{@link CameraScan}，便于快速实现扫描识别。
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
@SuppressWarnings("unused")
public abstract class BaseCameraScanFragment extends Fragment implements CameraScan.OnScanResultCallback {

    private final String TAG = getClass().getSimpleName();

    /**
     * 根视图
     */
    private View mRootView;
    /**
     * 预览视图
     */
    protected PreviewView previewView;

    protected CameraPreviewHelper mCameraPreviewHelper = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = createRootView(inflater, container);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    /**
     * 初始化
     */
    public void initUI() {
        previewView = mRootView.findViewById(getPreviewViewId());
        mCameraPreviewHelper = new CameraPreviewHelper(this,previewView,this);
        mCameraPreviewHelper.setAnalyzer(createAnalyzer());
        mCameraPreviewHelper.startCamera();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mCameraPreviewHelper != null) {
            mCameraPreviewHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    @Override
    public void onDestroyView() {
        if (mCameraPreviewHelper != null) {
            mCameraPreviewHelper.releaseCamera();
        }
        super.onDestroyView();
    }

    /**
     * 创建{@link #mRootView}
     *
     * @param inflater  {@link LayoutInflater}
     * @param container {@link ViewGroup}
     * @return 返回创建的根视图
     */
    @NonNull
    public View createRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    /**
     * 布局ID；通过覆写此方法可以自定义布局
     *
     * @return 布局ID
     */
    public abstract int getLayoutId();

    /**
     * 预览视图{@link #previewView}的ID
     *
     * @return 预览视图ID
     */
    public abstract int getPreviewViewId();
    /**
     * 获取根视图
     *
     * @return {@link #mRootView}
     */
    public View getRootView() {
        return mRootView;
    }

    /**
     * 创建分析器
     *
     * @return {@link Analyzer}
     */
    @Nullable
    public abstract Analyzer createAnalyzer();

}
