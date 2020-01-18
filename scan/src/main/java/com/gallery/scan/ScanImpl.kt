package com.gallery.scan

import android.content.Context
import android.os.Bundle
import androidx.loader.app.LoaderManager
import com.gallery.scan.args.Columns
import com.gallery.scan.args.ScanConst

/**
 * @author y
 * @create 2019/2/27
 * 图库扫描工具类
 */
class ScanImpl(private val scanView: ScanView) {

    companion object {
        private const val SCAN_LOADER_ID = 111
    }

    private val loaderManager: LoaderManager = LoaderManager.getInstance(scanView.scanContext)
    private val context: Context = scanView.scanContext
    private val scanType: Int = scanView.currentScanType
    private val finderList = ArrayList<ScanEntity>()

    fun scanAllOrParent(parent: Long) {
        if (loaderManager.hasRunningLoaders()) {
            return
        }
        loaderManager.restartLoader(SCAN_LOADER_ID, Bundle().apply {
            putLong(Columns.PARENT, parent)
            putInt(Columns.SCAN_TYPE, scanType)
        }, ScanTask(context) {
            if (parent == ScanConst.ALL && !it.isNullOrEmpty()) {
                refreshFinder(it)
            }
            scanView.scanSuccess(it.mergeEntity(scanView.selectEntity), finderList)
            loaderManager.destroyLoader(SCAN_LOADER_ID)
        })
    }

    fun scanResult(id: Long) {
        loaderManager.restartLoader(SCAN_LOADER_ID, Bundle().apply {
            putLong(Columns.ID, id)
            putInt(Columns.SCAN_TYPE, scanType)
        }, ScanTask(context) {
            scanView.resultSuccess(if (it.isEmpty()) null else it[0])
            loaderManager.destroyLoader(SCAN_LOADER_ID)
        })
    }

    fun scanResultFinder(finderList: ArrayList<ScanEntity>, scanEntity: ScanEntity) {
        finderList.forEach {
            if (it.parent == ScanConst.ALL || it.parent == scanEntity.parent) {
                it.id = scanEntity.id
                it.size = scanEntity.size
                it.duration = scanEntity.duration
                it.parent = if (it.parent == ScanConst.ALL) ScanConst.ALL else scanEntity.parent
                it.mimeType = scanEntity.mimeType
                it.displayName = scanEntity.displayName
                it.orientation = scanEntity.orientation
                it.bucketId = scanEntity.bucketId
                it.bucketDisplayName = scanEntity.bucketDisplayName
                it.mediaType = scanEntity.mediaType
                it.width = scanEntity.width
                it.height = scanEntity.height
                it.dataModified = scanEntity.dataModified
                it.count = it.count + 1
                it.isCheck = scanEntity.isCheck
            }
        }
    }

    private fun ArrayList<ScanEntity>.mergeEntity(selectEntity: ArrayList<ScanEntity>) = also {
        forEach { it.isCheck = false }
        selectEntity.forEach { select -> this.find { it.id == select.id }?.isCheck = true }
    }

    private fun refreshFinder(list: ArrayList<ScanEntity>) {
        finderList.clear()
        list.forEach { item ->
            if (finderList.find { it.parent == item.parent } == null) {
                finderList.add(item.copy(count = list.count { it.parent == item.parent }))
            }
        }
        finderList.add(0, finderList.first().copy(parent = ScanConst.ALL, count = list.size))
    }
}