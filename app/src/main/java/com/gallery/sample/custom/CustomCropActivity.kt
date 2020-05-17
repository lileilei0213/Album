package com.gallery.sample.custom

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.gallery.core.GalleryBundle
import com.gallery.core.ext.cropPathToUri
import com.gallery.ui.page.GalleryActivity
import com.theartofdev.edmodo.cropper.CropImage

class CustomCropActivity : GalleryActivity() {

    override fun onCustomPhotoCrop(activity: FragmentActivity, uri: Uri, galleryBundle: GalleryBundle): Intent {
        return CropImage
                .activity(uri)
                .setOutputUri(cropPathToUri(galleryBundle.cropPath, galleryBundle.cropName, galleryBundle.cropNameSuffix, galleryBundle.relativePath))
                .getIntent(this)
    }

    override fun onCropSuccessUriRule(intent: Intent?): Uri? {
        return CropImage.getActivityResult(intent).uri
    }

    override fun onCropErrorResultCode(): Int {
        return CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE
    }

    override fun onCropErrorThrowable(intent: Intent?): Throwable? {
        return CropImage.getActivityResult(intent).error
    }
}