@file:Suppress("PrivatePropertyName")

package com.album.core.scan

import android.content.Context
import android.os.Bundle
import androidx.loader.app.LoaderManager
import com.album.core.view.AlbumView

/**
 * @author y
 * @create 2019/2/27
 * 图库扫描工具类
 */
class AlbumScanImpl(private val albumView: AlbumView,
                    private val scanType: Int,
                    private val scanCount: Int,
                    private val allName: String,
                    private val sdName: String) {

    companion object {
        const val KEY_BUCKET = "bucket"
        const val KEY_PAGE = "page"
        fun newInstance(
                albumView: AlbumView,
                scanType: Int,
                scanCount: Int,
                allName: String,
                sdName: String
        ) = AlbumScanImpl(albumView, scanType, scanCount, allName, sdName)
    }

    private val loaderManager: LoaderManager = LoaderManager.getInstance(albumView.getAlbumActivity())

    private val activity: Context = albumView.getAlbumActivity()

    fun scanAll(bucketId: String, page: Int) {
        if (loaderManager.hasRunningLoaders()) {
            return
        }
        albumView.showProgress()
        loaderManager.restartLoader(AlbumScan.ALBUM_LOADER_ID, Bundle().apply {
            putString(KEY_BUCKET, bucketId)
            putInt(KEY_PAGE, page)
        }, AlbumScanFileLoader(activity, scanCount, "") {
            albumView.hideProgress()
            if (it.isEmpty()) {
                if (albumView.getPage() == 0) {
                    albumView.onAlbumEmpty()
                } else {
                    albumView.onAlbumNoMore()
                }
            } else {
                refreshFinder()
                mergeEntity(it, albumView.getSelectEntity())
                albumView.scanSuccess(it)
            }
            destroyAlbumLoaderManager()
        })
    }

    fun scanResult(path: String) {
        loaderManager.restartLoader(
                AlbumScan.RESULT_LOADER_ID,
                null,
                AlbumScanResultLoader(activity, scanType, path) {
                    refreshFinder()
                    albumView.resultSuccess(it)
                    destroyResultLoaderManager()
                })
    }

    private fun refreshFinder() {
        loaderManager.restartLoader(AlbumScan.FINDER_LOADER_ID, null, AlbumScanFileFinderLoader(activity, allName, sdName) {
            albumView.scanFinderSuccess(it)
            destroyFinderLoaderManager()
        })
    }

    fun mergeEntity(albumList: ArrayList<AlbumEntity>, selectEntity: ArrayList<AlbumEntity>) {
        albumList.forEach { it.isCheck = false }
        selectEntity.forEach { select -> albumList.filter { it.path == select.path }.forEach { it.isCheck = true } }
    }

    private fun destroyAlbumLoaderManager() {
        loaderManager.destroyLoader(AlbumScan.ALBUM_LOADER_ID)
    }

    private fun destroyResultLoaderManager() {
        loaderManager.destroyLoader(AlbumScan.RESULT_LOADER_ID)
    }

    private fun destroyFinderLoaderManager() {
        loaderManager.destroyLoader(AlbumScan.FINDER_LOADER_ID)
    }
}