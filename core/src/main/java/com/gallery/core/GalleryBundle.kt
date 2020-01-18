package com.gallery.core

import android.os.Parcelable
import com.gallery.scan.args.ScanConst
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GalleryBundle(
        /**
         * 扫描类型
         */
        var scanType: Int = ScanConst.IMAGE,
        /**
         * 摄像或拍照文件名称
         */
        var cameraName: String = System.currentTimeMillis().toString(),
        /**
         * 隐藏相机
         */
        var hideCamera: Boolean = false,
        /**
         * 是否单选
         */
        var radio: Boolean = false,
        /**
         * 是否裁剪
         */
        var crop: Boolean = true,
        /**
         * 拍照之后是否立即裁剪
         */
        var cameraCrop: Boolean = false,
        /**
         * 多选最多数
         */
        var multipleMaxCount: Int = 3,
        /**
         * 拍照路径
         * 支持在Android10版本以下
         */
        var cameraPath: String? = null,
        /**
         * 裁剪路径
         */
        var uCropPath: String? = null,
        /**
         * 根目录名称,有的会出现0所以设置下
         */
        var sdName: Int = R.string.gallery_sd_name,
        /**
         * 全部图片
         */
        var allName: Int = R.string.gallery_all_name,
        /**
         * 是否预览
         */
        var noPreview: Boolean = false,
        /**
         * 每行几张图片
         */
        var spanCount: Int = 3,
        /**
         * 分割线宽度
         */
        var dividerWidth: Int = 8,
        /**
         * 相机提示文字
         */
        var cameraText: Int = R.string.gallery_camera_text,
        /**
         * 相机提示文字大小
         */
        var cameraTextSize: Float = 18F,
        /**
         * 相机提示文字颜色
         */
        var cameraTextColor: Int = R.color.colorGalleryContentViewTipsColor,
        /**
         * 相机图片
         */
        var cameraDrawable: Int = R.drawable.ic_camera_drawable,
        /**
         * 相机图片背景色
         */
        var cameraDrawableColor: Int = R.color.colorGalleryContentViewCameraDrawableColor,
        /**
         * 相机背景色
         */
        var cameraBackgroundColor: Int = R.color.colorGalleryCameraBackgroundColor,
        /**
         * 图片背景色
         */
        var photoBackgroundColor: Int = R.color.colorGalleryPhotoBackgroundColor,
        /**
         * 预览图片背景色
         */
        var prevPhotoBackgroundColor: Int = R.color.colorGalleryPhotoBackgroundColor,
        /**
         * RootView背景色
         */
        var rootViewBackground: Int = R.color.colorGalleryContentViewBackground,
        /**
         * 选择框
         */
        var checkBoxDrawable: Int = R.drawable.selector_gallery_item_check,
        /**
         * 没有图片显示的占位图片
         */
        var photoEmptyDrawable: Int = R.drawable.ic_camera_drawable,
        /**
         * 没有图片显示的占位图片背景色
         */
        var photoEmptyDrawableColor: Int = R.color.colorGalleryContentEmptyDrawableColor,
        /**
         * 权限被拒之后是否销毁
         * 如果依赖的Dialog默认即可
         */
        var permissionsDeniedFinish: Boolean = false,
        /**
         * 裁剪异常是否退出
         * 如果依赖的Dialog则设置为false
         */
        var cropErrorFinish: Boolean = true,
        /**
         * 选择图片之后是否退出
         * 如果依赖的Dialog则设置为false
         */
        var selectImageFinish: Boolean = true,
        /**
         * 裁剪之后是否退出
         * 如果依赖的Dialog则设置为false
         */
        var cropFinish: Boolean = true
) : Parcelable
