@file:Suppress("FunctionName")

package com.gallery.sample

import android.app.Activity
import android.content.Intent
import android.media.MediaScannerConnection.scanFile
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gallery.core.GalleryBundle
import com.gallery.core.callback.IGallery
import com.gallery.core.ext.findUriByFile
import com.gallery.core.ext.galleryPathFile
import com.gallery.core.ext.openCamera
import com.gallery.core.ext.uriToFilePath
import com.gallery.scan.ScanType
import com.gallery.ui.Gallery
import com.gallery.ui.GalleryUiBundle
import com.gallery.ui.ui
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_main.*

fun String.show(activity: Activity) {
    Toast.makeText(activity, this, Toast.LENGTH_SHORT).show()
}

@ColorInt
fun Int.color(activity: Activity): Int {
    return ContextCompat.getColor(activity, this)
}

enum class Theme {
    DEFAULT,
    BLUE,
    BLACK,
    PINK,
}

class MainActivity : AppCompatActivity() {

    private var fileUri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openCamera.setOnClickListener {
            fileUri = findUriByFile(applicationContext.galleryPathFile(null, System.currentTimeMillis().toString()))
            openCamera(fileUri, false)
        }
        video.setOnClickListener {
            Gallery.instance
                    .apply {
                        galleryListener = GalleryListener(applicationContext, null)
                    }
                    .ui(this,
                            GalleryBundle(
                                    scanType = ScanType.VIDEO,
                                    cameraText = getString(R.string.video_tips)
                            ),
                            GalleryUiBundle(
                                    toolbarText = getString(R.string.gallery_video_title)
                            ))
        }
        selectCrop.setOnClickListener {
            Gallery.instance
                    .apply {
                        galleryListener = GalleryListener(applicationContext, null)
                    }
                    .ui(this,
                            GalleryTheme.cropThemeGallery(this),
                            GalleryTheme.cropThemeGalleryUi(this))
        }
        selectTheme.setOnClickListener {
            AlertDialog.Builder(this).setSingleChoiceItems(arrayOf("默认", "蓝色", "黑色", "粉红色"), View.NO_ID) { dialog, which ->
                when (which) {
                    0 -> Gallery.instance.ui(this, GalleryTheme.themeGallery(this, Theme.DEFAULT), GalleryTheme.themeGalleryUi(this, Theme.DEFAULT))
                    1 -> Gallery.instance.ui(this, GalleryTheme.themeGallery(this, Theme.BLUE), GalleryTheme.themeGalleryUi(this, Theme.BLUE))
                    2 -> Gallery.instance.ui(this, GalleryTheme.themeGallery(this, Theme.BLACK), GalleryTheme.themeGalleryUi(this, Theme.BLACK))
                    3 -> Gallery.instance.ui(this, GalleryTheme.themeGallery(this, Theme.PINK), GalleryTheme.themeGalleryUi(this, Theme.PINK))
                }
                dialog.dismiss()
            }.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_CANCELED ->
                when (requestCode) {
                    UCrop.REQUEST_CROP -> "取消裁剪".show(this)
                    IGallery.CAMERA_REQUEST_CODE -> "取消拍照".show(this)
                }
            UCrop.RESULT_ERROR -> "裁剪异常".show(this)
            Activity.RESULT_OK ->
                when (requestCode) {
                    IGallery.CAMERA_REQUEST_CODE -> {
                        uriToFilePath(fileUri)?.let {
                            scanFile(this, arrayOf(it), null) { path: String?, _: Uri? ->
                                runOnUiThread {
                                    path?.show(this)
                                }
                            }
                        }
//                        openCrop(fileUri)
                    }
                    UCrop.REQUEST_CROP -> {
                        scanFile(this, arrayOf(data?.extras?.getParcelable<Uri>(UCrop.EXTRA_OUTPUT_URI)?.path), null) { _: String?, uri: Uri? ->
                            runOnUiThread {
                                data?.extras?.getParcelable<Uri>(UCrop.EXTRA_OUTPUT_URI)?.path?.show(this)
                            }
                        }
                    }
                }
        }
    }

}
