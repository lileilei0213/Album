package com.gallery.core.ui.fragment

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.kotlin.expand.app.*
import androidx.kotlin.expand.content.openVideoExpand
import androidx.kotlin.expand.net.orEmptyExpand
import androidx.kotlin.expand.os.getLongOrDefault
import androidx.kotlin.expand.os.getParcelableArrayListOrDefault
import androidx.kotlin.expand.os.getParcelableOrDefault
import androidx.kotlin.expand.view.hideExpand
import androidx.kotlin.expand.view.showExpand
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gallery.core.GalleryBundle
import com.gallery.core.GalleryConfig
import com.gallery.core.R
import com.gallery.core.callback.*
import com.gallery.core.crop.ICrop
import com.gallery.core.expand.*
import com.gallery.core.ui.adapter.GalleryAdapter
import com.gallery.core.ui.base.GalleryBaseFragment
import com.gallery.core.ui.widget.SimpleGridDivider
import com.gallery.scan.*
import kotlinx.android.synthetic.main.gallery_fragment_gallery.*

class ScanFragment : GalleryBaseFragment(R.layout.gallery_fragment_gallery), ScanView, GalleryAdapter.OnGalleryItemClickListener, IGallery {

    companion object {
        fun newInstance(galleryBundle: GalleryBundle = GalleryBundle()): ScanFragment {
            val scanFragment = ScanFragment()
            val bundle = Bundle()
            bundle.putParcelable(GalleryConfig.GALLERY_CONFIG, galleryBundle)
            scanFragment.arguments = bundle
            return scanFragment
        }
    }

    private val galleryInterceptor: IGalleryInterceptor by lazy { galleryInterceptorExpand }
    private val galleryImageLoader: IGalleryImageLoader by lazy { galleryImageLoaderExpand }
    private val galleryCallback: IGalleryCallback by lazy { galleryCallbackExpand }
    private val galleryCrop: ICrop by lazy { galleryCropExpand }
    private val galleryBundle by lazy { getParcelableOrDefault<GalleryBundle>(GalleryConfig.GALLERY_CONFIG, GalleryBundle()) }
    private val scan: ScanImpl by lazy { ScanImpl(this) }
    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter(requireActivity().squareExpand(galleryBundle.spanCount), galleryBundle, galleryInterceptor, galleryCallback, galleryImageLoader, this)
    }
    private val openCameraLauncher: ActivityResultLauncher<CameraUri> =
            requestCameraResultLauncherExpand({ onCameraResultCanceled() }) { onCameraResultOk() }
    private val cropLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { intent ->
                galleryCrop.cropCallback(intent)
            }
    private var fileUri: Uri = Uri.EMPTY
    var parentId: Long = SCAN_ALL

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(InternalConfig.SELECT, selectEntities)
        outState.putLong(InternalConfig.PARENT_ID, parentId)
        outState.putParcelable(InternalConfig.IMAGE_URI, fileUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            parentId = it.getLongOrDefault(InternalConfig.PARENT_ID, SCAN_ALL)
            fileUri = it.getParcelableOrDefault(InternalConfig.IMAGE_URI, Uri.EMPTY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        galleryCallback.onGalleryViewCreated(savedInstanceState)
        galleryRootView.setBackgroundColor(galleryBundle.galleryRootBackground)
        galleryEmpty.setImageDrawable(drawableExpand(galleryBundle.photoEmptyDrawable))
        galleryEmpty.setOnClickListener { v ->
            if (galleryInterceptor.onEmptyPhotoClick(v)) {
                openCamera()
            }
        }
        galleryAdapter.addSelectAll(savedInstanceState.getParcelableArrayListOrDefault(InternalConfig.SELECT, galleryBundle.selectEntities))
        galleryRecyclerView.setHasFixedSize(true)
        galleryRecyclerView.layoutManager = GridLayoutManager(requireActivity(), galleryBundle.spanCount)
        galleryRecyclerView.addItemDecoration(SimpleGridDivider(galleryBundle.dividerWidth))
        (galleryRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        galleryRecyclerView.adapter = galleryAdapter
        onScanGallery(parentId)
    }

    override fun scanSuccess(arrayList: ArrayList<ScanEntity>) {
        if (arrayList.isEmpty() && parentId.isScanAll()) {
            galleryEmpty.showExpand()
            galleryCallback.onScanSuccessEmpty(context, galleryBundle)
            return
        }
        galleryCallback.onScanSuccess(arrayList)
        galleryEmpty.hideExpand()
        if (parentId.isScanAll() && !galleryBundle.hideCamera) {
            arrayList.add(0, ScanEntity(parent = InternalConfig.CAMERA_PARENT_ID))
        }
        galleryAdapter.addAll(arrayList)
        galleryAdapter.updateEntity()
    }

    override fun resultSuccess(scanEntity: ScanEntity?) {
        scanEntity?.let {
            if (parentId.isScanAll()) {
                if (galleryBundle.scanSort == Sort.DESC) {
                    galleryAdapter.addEntity(if (galleryBundle.hideCamera) 0 else 1, it)
                } else {
                    galleryAdapter.currentList.add(it)
                    scrollToPosition(galleryAdapter.currentList.size - 1)
                }
            } else if (parentId == it.parent) {
                if (galleryBundle.scanSort == Sort.DESC) {
                    galleryAdapter.addEntity(if (galleryBundle.hideCamera) 0 else 1, it)
                } else {
                    galleryAdapter.currentList.add(it)
                    scrollToPosition(galleryAdapter.currentList.size - 1)
                }
            }
            notifyDataSetChanged()
            galleryCallback.onResultSuccess(context, galleryBundle, it)
        } ?: galleryCallback.onCameraResultError(context, galleryBundle)
    }

    override fun onCameraItemClick(view: View, position: Int, galleryEntity: ScanEntity) {
        openCamera()
    }

    override fun onPhotoItemClick(view: View, position: Int, galleryEntity: ScanEntity) {
        if (!moveToNextToIdExpand(galleryEntity.externalUri())) {
            galleryCallback.onClickItemFileNotExist(context, galleryBundle, galleryEntity)
            return
        }
        if (galleryBundle.isVideoScan) {
            requireContext().openVideoExpand(galleryEntity.externalUri()) {
                galleryCallback.onOpenVideoPlayError(context, galleryEntity)
            }
            return
        }
        if (galleryBundle.radio) {
            if (galleryBundle.crop) {
                cropLauncher.launch(
                        galleryCrop.openCrop(galleryEntity.externalUri(),
                                requireActivity().cropUriExpand(galleryBundle).orEmptyExpand(),
                                requireActivity().cropUriExpand2(galleryBundle).orEmptyExpand())
                )
            } else {
                galleryCallback.onGalleryResource(context, galleryEntity)
            }
            return
        }
        galleryCallback.onPhotoItemClick(context, galleryBundle, galleryEntity, position, parentId)
    }

    public override fun onCameraResultCanceled() {
        fileUri.reset(requireContext())
        fileUri = Uri.EMPTY
        galleryCallback.onCameraCanceled(context, galleryBundle)
    }

    public override fun onCameraResultOk() {
        findPathByUriExpand(fileUri)?.let {
            scanFile(it) { onScanGallery(parentId, true) }
            if (galleryBundle.cameraCrop) {
                cropLauncher.launch(
                        galleryCrop.openCrop(fileUri,
                                requireActivity().cropUriExpand(galleryBundle).orEmptyExpand(),
                                requireActivity().cropUriExpand2(galleryBundle).orEmptyExpand())
                )
            }
        }
    }

    override fun permissionsGranted(type: PermissionCode) {
        when (type) {
            PermissionCode.WRITE -> onScanGallery(parentId)
            PermissionCode.READ -> openCamera()
        }
    }

    override fun permissionsDenied(type: PermissionCode) {
        galleryCallback.onPermissionsDenied(context, type)
    }

    override fun openCamera() {
        if (!checkPermissionAndRequestCameraExpand(cameraPermissionLauncher)) {
            galleryCallback.onCameraOpenStatus(context, CameraStatus.PERMISSION, galleryBundle)
            return
        }
        val cameraUriExpand: Uri? = requireActivity().cameraUriExpand(galleryBundle)
        cameraUriExpand?.let {
            fileUri = it
        } ?: galleryCallback.onCameraOpenStatus(context, CameraStatus.ERROR, galleryBundle)
        val onCustomCamera: Boolean = galleryInterceptor.onCustomCamera(fileUri)
        if (onCustomCamera || cameraUriExpand == null) {
            return
        }
        galleryCallback.onCameraOpenStatus(context, openCameraExpand(CameraUri(galleryBundle.scanType, fileUri)) { openCameraLauncher.launch(it) }, galleryBundle)
    }

    override fun onScanGallery(parent: Long, isCamera: Boolean) {
        if (!checkPermissionAndRequestWriteExpand(writePermissionLauncher)) {
            return
        }
        this.parentId = parent
        // 如果本机没有图片,进来拍照直接走扫描全部的方法可以兼容到hideCamera
        // resultSuccess只有在拍照成功并且之前数据不为空or裁剪成功才会回调
        // 拍照成功之后不需要特殊处理,因为肯定是SCAN_ALL的情况下,直接更新数据即可
        // 裁剪成功分为两种情况
        // 第一种:SCAN_ALL情况下直接更新数据
        // 第二种:parentId为文件夹ID的时候处理数据,如果 parentId == scan.parent
        // 可以直接插入到当前数据,如果不等于,不能插入,因为裁剪之后的图片属于另一个文件夹的数据
        // 文件夹数据更新的时候也需要处理这种情况
        if (isCamera && galleryAdapter.isNotEmpty) {
            scan.scanResult(findIdByUriExpand(fileUri))
        } else {
            scan.scanParent(parent)
        }
    }

    override fun onScanResult(uri: Uri) {
        if (uri.scheme != ContentResolver.SCHEME_CONTENT) {
            throw RuntimeException("unsupported uri")
        }
        scan.scanResult(findIdByUriExpand(uri))
    }

    override fun onUpdatePrevResult(bundle: Bundle) {
        val previewGalleryEntity: ArrayList<ScanEntity>? = bundle.getParcelableArrayList(GalleryConfig.PREV_RESULT_SELECT)
        val isRefreshUI: Boolean = bundle.getBoolean(GalleryConfig.PREV_RESULT_REFRESH, true)
        previewGalleryEntity ?: return
        if (!isRefreshUI || selectEntities == previewGalleryEntity) {
            return
        }
        galleryAdapter.addSelectAll(previewGalleryEntity)
        galleryAdapter.updateEntity()
        galleryCallback.onChangedPrevCount(selectCount)
    }

    override fun notifyItemChanged(position: Int) {
        galleryAdapter.notifyItemChanged(position)
    }

    override fun notifyDataSetChanged() {
        galleryAdapter.notifyDataSetChanged()
    }

    override fun addOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) {
        galleryRecyclerView.addOnScrollListener(onScrollListener)
    }

    override fun scrollToPosition(position: Int) {
        galleryRecyclerView.scrollToPosition(position)
    }

    override fun scanType(): Int {
        return galleryBundle.scanType
    }

    override fun scanSort(): String {
        return galleryBundle.scanSort
    }

    override fun scanSortField(): String {
        return galleryBundle.scanSortField
    }

    override val currentEntities: ArrayList<ScanEntity>
        get() = galleryAdapter.currentList.filter { it.parent != InternalConfig.CAMERA_PARENT_ID } as ArrayList<ScanEntity>

    override val selectEntities: ArrayList<ScanEntity>
        get() = galleryAdapter.currentSelectList

    override val selectEmpty: Boolean
        get() = selectEntities.isEmpty()

    override val selectCount: Int
        get() = selectEntities.size

    override val itemCount: Int
        get() = currentEntities.size

    override val scanContext: FragmentActivity
        get() = requireActivity()
}