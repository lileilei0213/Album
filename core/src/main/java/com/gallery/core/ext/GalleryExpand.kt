@file:Suppress("DEPRECATION")

package com.gallery.core.ext

import android.net.Uri
import com.gallery.core.GalleryBundle
import com.gallery.scan.SCAN_ALL
import com.gallery.scan.ScanEntity
import com.gallery.scan.ScanType

/** 返回拍照文件名称 为了防止重复前缀加时间戳 */
val GalleryBundle.cameraNameExpand: String
    get() = "${System.currentTimeMillis()}_${cameraName}.${cameraNameSuffix}"

/** 返回裁剪文件名称 为了防止重复前缀加时间戳 */
val GalleryBundle.cropNameExpand: String
    get() = "${System.currentTimeMillis()}_${cropName}.${cropNameSuffix}"

/** 是否是纯视频 */
val GalleryBundle.isVideoScan: Boolean
    get() = scanType == ScanType.VIDEO

/** 文件是否是动态图 */
fun ScanEntity.isGif(): Boolean = mimeType.contains("gif")

/** 文件是否是视频 */
fun ScanEntity.isVideo(): Boolean = mediaType == "3"

/** 是否是扫描全部的Id */
fun Long.isScanAll(): Boolean = this == SCAN_ALL

/** 打开相机的自定义数据携带体 */
class CameraUri(val type: ScanType, val uri: Uri)