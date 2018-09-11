package com.album.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * by y on 14/08/2017.
 */

@Parcelize
class AlbumEntity(private var dirPath: String, private var dirName: String, var path: String, var id: Long, var isCheck: Boolean) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlbumEntity

        if (dirPath != other.dirPath) return false
        if (dirName != other.dirName) return false
        if (path != other.path) return false
        if (id != other.id) return false
        if (isCheck != other.isCheck) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dirPath.hashCode()
        result = 31 * result + dirName.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + isCheck.hashCode()
        return result
    }


}
