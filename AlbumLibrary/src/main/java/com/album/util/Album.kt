package com.album.util

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.Window
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.album.AlbumConstant


fun checkNotStringNull(notNull: String?): String {
    return notNull ?: ""
}

fun checkNotBundleNull(notNull: Bundle?): Bundle {
    return notNull ?: Bundle.EMPTY
}

fun checkNotIntentNull(notNull: Intent?): Intent {
    return notNull ?: Intent()
}

fun setStatusBarColor(@ColorInt color: Int, window: Window?) {
    if (window != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color
        }
    }
}

fun getImageViewWidth(activity: Activity, count: Int): Int {
    val display = activity.window.windowManager.defaultDisplay
    val dm = DisplayMetrics()
    display.getMetrics(dm)
    return dm.widthPixels / count
}

fun hasL(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun getDrawable(context: Context, id: Int, color: Int): Drawable {
    val drawable = context.resources.getDrawable(id)
    drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    return drawable
}

fun openCamera(any: Any, cameraUri: Uri, video: Boolean): Int {
    var cameraActivity: Activity? = null
    if (any is Activity) {
        cameraActivity = any
    }
    if (any is Fragment) {
        cameraActivity = any.activity!!
    }
    if (PermissionUtils.camera(any) && PermissionUtils.storage(any)) {
        val intent = if (video) Intent(MediaStore.ACTION_VIDEO_CAPTURE) else Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(cameraActivity!!.packageManager) != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
            } else {
                val contentValues = ContentValues(1)
                contentValues.put(MediaStore.Images.Media.DATA, cameraUri.path)
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, AlbumConstant.ITEM_CAMERA)
                val uri = cameraActivity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
            if (any is Activity) {
                cameraActivity.startActivityForResult(intent, AlbumConstant.ITEM_CAMERA)
            }
            if (any is Fragment) {
                any.startActivityForResult(intent, AlbumConstant.ITEM_CAMERA)
            }
            return 0
        } else {
            return 1
        }

    }
    return -1
}