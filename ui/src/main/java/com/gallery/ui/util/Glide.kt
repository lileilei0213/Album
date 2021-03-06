package com.gallery.ui.util

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gallery.core.ui.widget.GalleryImageView
import com.gallery.scan.ScanEntity
import com.gallery.scan.types.externalUriExpand
import com.gallery.ui.R

private val defaultLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
    gravity = Gravity.CENTER
}

internal fun FrameLayout.displayGallery(width: Int, height: Int, galleryEntity: ScanEntity) {
    removeAllViews()
    val imageView = GalleryImageView(context)
    Glide.with(context).load(galleryEntity.externalUriExpand).apply(RequestOptions().placeholder(R.drawable.ic_gallery_default_loading).error(R.drawable.ic_gallery_default_loading).centerCrop().override(width, height)).into(imageView)
    addView(imageView, FrameLayout.LayoutParams(width, height))
}

internal fun FrameLayout.displayGalleryThumbnails(finderEntity: ScanEntity) {
    removeAllViews()
    val imageView = GalleryImageView(context)
    Glide.with(context).load(finderEntity.externalUriExpand).apply(RequestOptions().placeholder(R.drawable.ic_gallery_default_loading).error(R.drawable.ic_gallery_default_loading).centerCrop()).into(imageView)
    addView(imageView)
}

internal fun FrameLayout.displayGalleryPrev(scanEntity: ScanEntity) {
    removeAllViews()
    val imageView: GalleryImageView = GalleryImageView(context).apply {
        layoutParams = defaultLayoutParams
    }
    Glide.with(context).load(scanEntity.externalUriExpand).into(imageView)
    addView(imageView)
}