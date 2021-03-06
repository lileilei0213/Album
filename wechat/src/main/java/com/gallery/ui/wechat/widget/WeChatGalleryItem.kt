package com.gallery.ui.wechat.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.kotlin.expand.content.colorExpand
import com.gallery.scan.ScanEntity
import com.gallery.scan.types.isGifExpand
import com.gallery.scan.types.isVideoExpand
import com.gallery.ui.wechat.R
import com.gallery.ui.wechat.util.formatTimeVideo
import kotlinx.android.synthetic.main.layout_gallery_wechat_item.view.*

class WeChatGalleryItem @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        addView(View.inflate(context, R.layout.layout_gallery_wechat_item, null))
    }

    val imageView: ImageView
        get() = viewWeChatImageView

    private val gifView: View
        get() = viewWeChatGif

    private val videoView: TextView
        get() = viewWeChatVideo

    private val bottomView: View
        get() = viewWeChatBottomView

    private val selectView: View
        get() = viewWeChatBackSelect

    fun update(galleryEntity: ScanEntity) {
        selectView.visibility = if (galleryEntity.isSelected) View.VISIBLE else View.GONE
        gifView.visibility = if (galleryEntity.isGifExpand) View.VISIBLE else View.GONE
        videoView.visibility = if (galleryEntity.isVideoExpand) View.VISIBLE else View.GONE
        bottomView.visibility = if (galleryEntity.isVideoExpand) View.VISIBLE else View.GONE
        bottomView.setBackgroundColor(if (galleryEntity.isGifExpand) Color.TRANSPARENT else context.colorExpand(R.color.color_B3000000))
        bottomView.visibility = if (galleryEntity.isVideoExpand || galleryEntity.isGifExpand) View.VISIBLE else View.GONE
        videoView.text = if (galleryEntity.isVideoExpand) galleryEntity.duration.formatTimeVideo() else ""
    }
}