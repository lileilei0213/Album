@file:Suppress("FunctionName")

package com.album.sample

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.album.sample.camera.SimpleCameraActivity
import com.album.sample.imageloader.SimpleGlideImageLoader
import com.album.sample.imageloader.SimplePicassoGalleryImageLoader
import com.album.sample.imageloader.SimpleSubsamplingScaleImageLoader
import com.gallery.ui.GalleryUiBundle
import com.gallery.ui.ui
import com.gallery.core.GalleryBundle
import com.gallery.core.Gallery
import com.gallery.core.GalleryCameraConst
import com.gallery.core.GalleryConst
import com.gallery.core.action.GalleryImageLoader
import com.gallery.core.ext.galleryPathFile
import com.gallery.core.ext.openCamera
import com.gallery.core.ext.permission.permissionCamera
import com.gallery.core.ext.permission.permissionStorage
import com.gallery.core.ext.uri
import com.gallery.scan.ScanEntity
import com.gallery.scan.SingleMediaScanner
import com.gallery.scan.args.ScanConst
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropFragment
import com.yalantis.ucrop.UCropFragmentCallback
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

fun NightAlbumBundle(): GalleryBundle {
    return GalleryBundle(
            spanCount = 4,
            cropFinish = false,
            checkBoxDrawable = R.drawable.simple_selector_album_item_check,
            radio = true,
            cameraPath = Environment.getExternalStorageDirectory().path + "/" + "DCIM/Album",
            uCropPath = Environment.getExternalStorageDirectory().path + "/" + "DCIM" + "/" + "uCrop",
            cameraTextColor = R.color.colorGalleryContentViewTipsColorNight,
            cameraDrawable = R.drawable.ic_camera_drawable,
            cameraDrawableColor = R.color.colorGalleryContentViewCameraDrawableColorNight,
            cameraBackgroundColor = R.color.colorGalleryToolbarBackgroundNight,
            rootViewBackground = R.color.colorGalleryContentViewBackgroundNight,
            cameraCrop = true)
}

fun NightAlbumUIBundle(): GalleryUiBundle {
    return GalleryUiBundle(
            statusBarColor = R.color.colorGalleryStatusBarColorNight,
            toolbarBackground = R.color.colorGalleryToolbarBackgroundNight,
            toolbarIconColor = R.color.colorGalleryToolbarIconColorNight,
            toolbarTextColor = R.color.colorGalleryToolbarTextColorNight,
            bottomFinderTextBackground = R.color.colorGalleryBottomViewBackgroundNight,
            bottomFinderTextColor = R.color.colorGalleryBottomFinderTextColorNight,
            bottomFinderTextDrawableColor = R.color.colorGalleryBottomFinderTextDrawableColorNight,
            bottomPreViewTextColor = R.color.colorGalleryBottomPreViewTextColorNight,
            bottomSelectTextColor = R.color.colorGalleryBottomSelectTextColorNight,
            listPopupBackground = R.color.colorGalleryListPopupBackgroundNight,
            listPopupItemTextColor = R.color.colorGalleryListPopupItemTextColorNight,
            preBackground = R.color.colorGalleryPreviewBackgroundNight,
            preBottomViewBackground = R.color.colorGalleryPreviewBottomViewBackgroundNight,
            preBottomOkTextColor = R.color.colorGalleryPreviewBottomViewOkColorNight,
            preBottomCountTextColor = R.color.colorGalleryPreviewBottomViewCountColorNight)
}

fun MainActivity.dayAlbum() {
    Gallery.instance.apply {
        galleryImageLoader = SimplePicassoGalleryImageLoader()
        galleryListener = MainGalleryListener(applicationContext, list)
        selectList = list
        options = dayOptions
    }.ui(this,
            GalleryBundle(scanType = ScanConst.IMAGE, checkBoxDrawable = R.drawable.simple_selector_album_item_check))
}

fun MainActivity.nightAlbum() {
    Gallery.instance.apply {
        galleryListener = MainGalleryListener(applicationContext, null)
        options = nightOptions
        galleryImageLoader = SimplePicassoGalleryImageLoader()
        customCameraListener = {
            if (it.permissionStorage() && it.permissionCamera()) {
                Toast.makeText(it.activity, "camera", Toast.LENGTH_SHORT).show()
                val intent = Intent(it.activity, SimpleCameraActivity::class.java)
                it.startActivityForResult(intent, GalleryCameraConst.CUSTOM_CAMERA_REQUEST_CODE)
            }
        }
    }.ui(this, NightAlbumBundle(), NightAlbumUIBundle())
}

fun MainActivity.video() {
    Gallery.instance.apply {
        galleryImageLoader = SimplePicassoGalleryImageLoader()
        galleryListener = MainGalleryListener(applicationContext, null)
    }.ui(this, GalleryBundle(
            scanType = ScanConst.VIDEO,
            cameraText = R.string.video_tips),
            GalleryUiBundle(toolbarText = R.string.album_video_title))
}

fun MainActivity.startCamera() {
    imagePath = uri(applicationContext.galleryPathFile(null, System.currentTimeMillis().toString(), "jpg"))
    val i = openCamera(imagePath, false)
    Log.d(javaClass.simpleName, i.toString())
}

fun MainActivity.imageLoader(imageLoader: GalleryImageLoader) {
    Gallery.instance.apply { galleryImageLoader = imageLoader }.ui(this)
}

class SimpleSingleScannerListener : SingleMediaScanner.SingleScannerListener {
    override fun onScanCompleted(type: Int, path: String) {}
    override fun onScanStart() {}
}

class MainActivity : AppCompatActivity(), OnClickListener, UCropFragmentCallback {

    lateinit var dayOptions: UCrop.Options
    lateinit var nightOptions: UCrop.Options
    lateinit var list: ArrayList<ScanEntity>
    lateinit var imagePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_day_album.setOnClickListener(this)
        btn_night_album.setOnClickListener(this)
        btn_open_camera.setOnClickListener(this)
        btn_video.setOnClickListener(this)
        btn_imageloader.setOnClickListener(this)

        dayOptions = UCrop.Options()
        dayOptions.apply {
            setToolbarTitle("DayTheme")
            setToolbarColor(ContextCompat.getColor(this@MainActivity, R.color.colorGalleryToolbarBackground))
            setStatusBarColor(ContextCompat.getColor(this@MainActivity, R.color.colorGalleryStatusBarColor))
            setActiveWidgetColor(ContextCompat.getColor(this@MainActivity, R.color.colorGalleryToolbarBackground))
        }

        nightOptions = UCrop.Options()
        nightOptions.setToolbarTitle("NightTheme")
        nightOptions.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.colorGalleryToolbarTextColorNight))
        nightOptions.setToolbarColor(ContextCompat.getColor(this, R.color.colorGalleryToolbarBackgroundNight))
        nightOptions.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorGalleryToolbarBackgroundNight))
        nightOptions.setStatusBarColor(ContextCompat.getColor(this, R.color.colorGalleryStatusBarColorNight))

        list = ArrayList()
    }

    override fun onClick(v: View) {
        Gallery.destroy()
        when (v.id) {
            R.id.btn_day_album -> dayAlbum()
            R.id.btn_night_album -> nightAlbum()
            R.id.btn_open_camera -> startCamera()
            R.id.btn_video -> video()
            R.id.btn_imageloader -> AlertDialog.Builder(this@MainActivity)
                    .setSingleChoiceItems(arrayOf("Glide", "Picasso", "SubsamplingScale"), -1
                    ) { dialog, which ->
                        when (which) {
                            0 -> imageLoader(SimpleGlideImageLoader())
                            1 -> imageLoader(SimplePicassoGalleryImageLoader())
                            2 -> imageLoader(SimpleSubsamplingScaleImageLoader())
                        }
                        dialog.dismiss()
                    }.show()
        }
    }

    override fun onCropFinish(result: UCropFragment.UCropResult) {
    }

    override fun loadingProgress(showLoader: Boolean) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_CANCELED -> {
            }
            UCrop.RESULT_ERROR -> {
            }
            Activity.RESULT_OK -> when (requestCode) {
                GalleryCameraConst.CAMERA_REQUEST_CODE -> {
                    SingleMediaScanner(this, imagePath.path
                            ?: "", GalleryConst.TYPE_RESULT_CAMERA, SimpleSingleScannerListener())
                }
                UCrop.REQUEST_CROP -> {
                    SingleMediaScanner(this, imagePath.path
                            ?: "", GalleryConst.TYPE_RESULT_CROP, SimpleSingleScannerListener())
                    Toast.makeText(applicationContext, imagePath.path, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
