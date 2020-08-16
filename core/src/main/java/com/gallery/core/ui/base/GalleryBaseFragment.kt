package com.gallery.core.ui.base

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.gallery.core.expand.PermissionCode
import com.gallery.core.expand.requestPermissionResultLauncherExpand

/**
 * @author y
 */
abstract class GalleryBaseFragment(layoutId: Int) : Fragment(layoutId) {

    protected val cameraPermissionLauncher: ActivityResultLauncher<String> =
            requestPermissionResultLauncherExpand {
                if (it) {
                    permissionsGranted(PermissionCode.READ)
                } else {
                    permissionsDenied(PermissionCode.READ)
                }
            }

    protected val writePermissionLauncher: ActivityResultLauncher<String> =
            requestPermissionResultLauncherExpand {
                if (it) {
                    permissionsGranted(PermissionCode.WRITE)
                } else {
                    permissionsDenied(PermissionCode.WRITE)
                }
            }

    protected open fun onCameraResultCanceled() {}

    protected open fun onCameraResultOk() {}

    protected open fun permissionsGranted(type: PermissionCode) {}

    protected open fun permissionsDenied(type: PermissionCode) {}
}

