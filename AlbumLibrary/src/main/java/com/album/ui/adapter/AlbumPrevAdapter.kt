package com.album.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.album.Album
import com.album.AlbumBundle
import com.album.R
import com.album.core.scan.AlbumEntity

/**
 * @author y
 * @create 2019/3/7
 */
class AlbumPrevAdapter(
        private val albumBundle: AlbumBundle,
        private val onAlbumPrevItemClickListener: OnAlbumPrevItemClickListener
) : RecyclerView.Adapter<AlbumPrevAdapter.PhotoViewHolder>() {

    /**
     * 图片数据
     */
    var albumList: ArrayList<AlbumEntity> = ArrayList()

    /**
     * 多选时的临时数据
     */
    var multipleList: ArrayList<AlbumEntity> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val photoView: View = LayoutInflater.from(parent.context).inflate(R.layout.album_item_album_prev, parent, false)
        val photoViewHolder = PhotoViewHolder(photoView, albumBundle, onAlbumPrevItemClickListener)
        photoView.setOnClickListener { v -> onAlbumPrevItemClickListener.onItemClick(v, photoViewHolder.adapterPosition, albumList[photoViewHolder.adapterPosition]) }
        return photoViewHolder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val albumEntity = albumList[position]
        holder.photo(albumEntity, multipleList)
    }

    override fun getItemCount(): Int = albumList.size

    /**
     * 添加新数据
     */
    fun addAll(newList: ArrayList<AlbumEntity>) {
        albumList.addAll(newList)
        notifyDataSetChanged()
    }

    /**
     * 清除数据
     */
    fun removeAll() {
        albumList.clear()
        notifyDataSetChanged()
    }

    interface OnAlbumPrevItemClickListener {
        fun onItemCheckBoxClick(view: View, currentMaxCount: Int, albumEntity: AlbumEntity)
        fun onItemClick(view: View, position: Int, albumEntity: AlbumEntity)
    }

    class PhotoViewHolder(itemView: View,
                          private val albumBundle: AlbumBundle,
                          private val onAlbumPrevItemClickListener: OnAlbumPrevItemClickListener)
        : RecyclerView.ViewHolder(itemView) {

        private val container: FrameLayout = itemView.findViewById(R.id.album_prev_container)
//        private val checkBox: AppCompatCheckBox = itemView.findViewById(R.id.album_prev_check_box)

        fun photo(albumEntity: AlbumEntity, multipleList: ArrayList<AlbumEntity>) {
            val imageView = Album.instance.albumImageLoader?.displayAlbumPreview(albumEntity, container)
            imageView?.let { container.addView(it) }

            container.setBackgroundColor(ContextCompat.getColor(itemView.context, albumBundle.prevPhotoBackgroundColor))

//            checkBox.isChecked = albumEntity.isCheck
//            checkBox.setBackgroundResource(albumBundle.checkBoxDrawable)
//            checkBox.setOnClickListener(View.OnClickListener {
//                if (!albumEntity.path.fileExists()) {
//                    checkBox.isChecked = false
//                    Album.instance.albumListener?.onAlbumCheckFileNotExist()
//                    return@OnClickListener
//                }
//                if (!multipleList.contains(albumEntity) && multipleList.size >= albumBundle.multipleMaxCount) {
//                    checkBox.isChecked = false
//                    Album.instance.albumListener?.onAlbumMaxCount()
//                    return@OnClickListener
//                }
//                if (!albumEntity.isCheck) {
//                    albumEntity.isCheck = true
//                    multipleList.add(albumEntity)
//                } else {
//                    multipleList.remove(albumEntity)
//                    albumEntity.isCheck = false
//                }
//                onAlbumPrevItemClickListener.onItemCheckBoxClick(itemView, multipleList.size, albumEntity)
//                Album.instance.albumListener?.onAlbumCheckBox(multipleList.size, albumBundle.multipleMaxCount)
//            })
        }
    }
}
