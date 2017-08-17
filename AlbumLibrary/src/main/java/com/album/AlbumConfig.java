package com.album;

/**
 * by y on 14/08/2017.
 */

public class AlbumConfig {

    private boolean hideCamera = false;
    private boolean isRadio = false;
    private boolean isCrop = true;
    private int multipleMaxCount = 9;
    //////////////  album toolbar //////
    private int albumStatusBarColor = R.color.colorAlbumStatusBarColorDay;
    private int albumToolbarBackground = R.color.colorAlbumToolbarBackgroundDay;
    private int albumToolbarIcon = R.drawable.ic_action_back_day;
    private int albumToolbarIconColor = R.color.colorAlbumToolbarIconColorDay;
    private int albumToolbarTextColor = R.color.colorAlbumToolbarTextColorDay;
    private int albumToolbarText = R.string.album_name;
    private float albumToolbarElevation = 6f;
    ////////////// album bottom view //////
    private int albumBottomViewBackground = R.color.colorAlbumBottomViewBackgroundDay;
    private int albumBottomFinderTextSize = 16;
    private int albumBottomFinderTextColor = R.color.colorAlbumBottomFinderTextColorDay;
    private int albumBottomFinderTextDrawable = R.drawable.ic_action_album_finder_day;
    private int albumBottomFinderTextDrawableColor = R.color.colorAlbumBottomFinderTextDrawableColorDay;
    private int albumBottomPreViewText = R.string.album_preview;
    private int albumBottomPreViewTextSize = 16;
    private int albumBottomPreViewTextColor = R.color.colorAlbumBottomPreViewTextColorDay;
    private int albumBottomSelectText = R.string.album_select;
    private int albumBottomSelectTextSize = 16;
    private int albumBottomSelectTextColor = R.color.colorAlbumBottomSelectTextColorDay;
    ///////////// album list popup window /////
    private int albumListPopupWidth = 600;
    private int albumListPopupHorizontalOffset = 20;
    private int albumListPopupVerticalOffset = 80;
    private int albumListPopupItemBackground = R.color.colorAlbumListPopupItemBackgroundDay;
    private int albumListPopupItemTextColor = R.color.colorAlbumListPopupItemTextColorDay;
    //////////// album content view ////////
    private int spanCount = 3;
    private int albumContentViewBackground = R.color.colorAlbumContentViewBackgroundDay;
    private int albumContentViewCameraDrawable = R.drawable.ic_action_camera_day;
    private int albumContentViewCameraDrawableColor = R.color.colorAlbumContentViewCameraDrawableColorDay;
    private int albumContentItemCheckBoxDrawable = R.drawable.selector_album_item_check;
    /////////// album preview ////////
    private int albumPreviewTitle = R.string.preview_title;
    private int albumPreviewBackground = R.color.colorAlbumPreviewBackgroundDay;
    private int albumPreviewBottomViewBackground = R.color.colorAlbumPreviewBottomViewBackgroundDay;
    private int albumPreviewBottomOkText = R.string.preview_select;
    private int albumPreviewBottomOkTextColor = R.color.colorAlbumPreviewBottomViewOkColorDay;
    private int albumPreviewBottomOkTextSize = 16;

    public AlbumConfig() {
    }

    public AlbumConfig(int type) {
        switch (type) {
            case AlbumConstant.TYPE_NIGHT:
                albumStatusBarColor = R.color.colorAlbumStatusBarColorNight;
                albumToolbarBackground = R.color.colorAlbumToolbarBackgroundNight;
                albumToolbarTextColor = R.color.colorAlbumToolbarTextColorNight;
                albumToolbarIconColor = R.color.colorAlbumToolbarIconColorNight;

                albumBottomViewBackground = R.color.colorAlbumBottomViewBackgroundNight;
                albumBottomFinderTextColor = R.color.colorAlbumBottomFinderTextColorNight;
                albumBottomFinderTextDrawableColor = R.color.colorAlbumBottomFinderTextDrawableColorNight;
                albumBottomPreViewTextColor = R.color.colorAlbumBottomPreViewTextColorNight;
                albumBottomSelectTextColor = R.color.colorAlbumBottomSelectTextColorNight;

                albumListPopupItemTextColor = R.color.colorAlbumListPopupItemTextColorNight;
                albumListPopupItemBackground = R.color.colorAlbumListPopupItemBackgroundNight;

                albumContentViewBackground = R.color.colorAlbumContentViewBackgroundNight;
                albumContentViewCameraDrawableColor = R.color.colorAlbumContentViewCameraDrawableColorNight;

                albumPreviewBackground = R.color.colorAlbumPreviewBackgroundNight;
                albumPreviewBottomViewBackground = R.color.colorAlbumPreviewBottomViewBackgroundNight;
                albumPreviewBottomOkTextColor = R.color.colorAlbumPreviewBottomViewOkColorNight;
                break;
            default:
                throw new RuntimeException("type");
        }
    }

    public boolean isHideCamera() {
        return hideCamera;
    }

    public AlbumConfig setHideCamera(boolean hideCamera) {
        this.hideCamera = hideCamera;
        return this;
    }

    public int getAlbumToolbarBackground() {
        return albumToolbarBackground;
    }

    public AlbumConfig setAlbumToolbarBackground(int albumToolbarBackground) {
        this.albumToolbarBackground = albumToolbarBackground;
        return this;
    }

    public int getAlbumToolbarIcon() {
        return albumToolbarIcon;
    }

    public AlbumConfig setAlbumToolbarIcon(int albumToolbarIcon) {
        this.albumToolbarIcon = albumToolbarIcon;
        return this;
    }

    public int getAlbumStatusBarColor() {
        return albumStatusBarColor;
    }

    public AlbumConfig setAlbumStatusBarColor(int albumStatusBarColor) {
        this.albumStatusBarColor = albumStatusBarColor;
        return this;
    }

    public int getAlbumToolbarTextColor() {
        return albumToolbarTextColor;
    }

    public AlbumConfig setAlbumToolbarTextColor(int albumToolbarTextColor) {
        this.albumToolbarTextColor = albumToolbarTextColor;
        return this;
    }

    public int getAlbumToolbarText() {
        return albumToolbarText;
    }

    public AlbumConfig setAlbumToolbarText(int albumToolbarText) {
        this.albumToolbarText = albumToolbarText;
        return this;
    }

    public float getAlbumToolbarElevation() {
        return albumToolbarElevation;
    }

    public AlbumConfig setAlbumToolbarElevation(float albumToolbarElevation) {
        this.albumToolbarElevation = albumToolbarElevation;
        return this;
    }

    public int getAlbumBottomViewBackground() {
        return albumBottomViewBackground;
    }

    public AlbumConfig setAlbumBottomViewBackground(int albumBottomViewBackground) {
        this.albumBottomViewBackground = albumBottomViewBackground;
        return this;
    }

    public int getAlbumBottomFinderTextSize() {
        return albumBottomFinderTextSize;
    }

    public AlbumConfig setAlbumBottomFinderTextSize(int albumBottomFinderTextSize) {
        this.albumBottomFinderTextSize = albumBottomFinderTextSize;
        return this;
    }

    public int getAlbumBottomFinderTextColor() {
        return albumBottomFinderTextColor;
    }

    public AlbumConfig setAlbumBottomFinderTextColor(int albumBottomFinderTextColor) {
        this.albumBottomFinderTextColor = albumBottomFinderTextColor;
        return this;
    }

    public int getAlbumBottomPreViewText() {
        return albumBottomPreViewText;
    }

    public AlbumConfig setAlbumBottomPreViewText(int albumBottomPreViewText) {
        this.albumBottomPreViewText = albumBottomPreViewText;
        return this;
    }

    public int getAlbumBottomPreViewTextSize() {
        return albumBottomPreViewTextSize;
    }

    public AlbumConfig setAlbumBottomPreViewTextSize(int albumBottomPreViewTextSize) {
        this.albumBottomPreViewTextSize = albumBottomPreViewTextSize;
        return this;
    }

    public int getAlbumBottomPreViewTextColor() {
        return albumBottomPreViewTextColor;
    }

    public AlbumConfig setAlbumBottomPreViewTextColor(int albumBottomPreViewTextColor) {
        this.albumBottomPreViewTextColor = albumBottomPreViewTextColor;
        return this;
    }

    public int getAlbumBottomSelectText() {
        return albumBottomSelectText;
    }

    public AlbumConfig setAlbumBottomSelectText(int albumBottomSelectText) {
        this.albumBottomSelectText = albumBottomSelectText;
        return this;
    }

    public int getAlbumBottomSelectTextSize() {
        return albumBottomSelectTextSize;
    }

    public AlbumConfig setAlbumBottomSelectTextSize(int albumBottomSelectTextSize) {
        this.albumBottomSelectTextSize = albumBottomSelectTextSize;
        return this;
    }

    public int getAlbumBottomSelectTextColor() {
        return albumBottomSelectTextColor;
    }

    public AlbumConfig setAlbumBottomSelectTextColor(int albumBottomSelectTextColor) {
        this.albumBottomSelectTextColor = albumBottomSelectTextColor;
        return this;
    }

    public int getAlbumListPopupWidth() {
        return albumListPopupWidth;
    }

    public AlbumConfig setAlbumListPopupWidth(int albumListPopupWidth) {
        this.albumListPopupWidth = albumListPopupWidth;
        return this;
    }

    public int getAlbumListPopupHorizontalOffset() {
        return albumListPopupHorizontalOffset;
    }

    public AlbumConfig setAlbumListPopupHorizontalOffset(int albumListPopupHorizontalOffset) {
        this.albumListPopupHorizontalOffset = albumListPopupHorizontalOffset;
        return this;
    }

    public int getAlbumListPopupVerticalOffset() {
        return albumListPopupVerticalOffset;
    }

    public AlbumConfig setAlbumListPopupVerticalOffset(int albumListPopupVerticalOffset) {
        this.albumListPopupVerticalOffset = albumListPopupVerticalOffset;
        return this;
    }

    public int getAlbumBottomFinderTextDrawable() {
        return albumBottomFinderTextDrawable;
    }

    public AlbumConfig setAlbumBottomFinderTextDrawable(int albumBottomFinderTextDrawable) {
        this.albumBottomFinderTextDrawable = albumBottomFinderTextDrawable;
        return this;
    }

    public int getAlbumListPopupItemBackground() {
        return albumListPopupItemBackground;
    }

    public AlbumConfig setAlbumListPopupItemBackground(int albumListPopupItemBackground) {
        this.albumListPopupItemBackground = albumListPopupItemBackground;
        return this;
    }

    public int getAlbumListPopupItemTextColor() {
        return albumListPopupItemTextColor;
    }

    public AlbumConfig setAlbumListPopupItemTextColor(int albumListPopupItemTextColor) {
        this.albumListPopupItemTextColor = albumListPopupItemTextColor;
        return this;
    }

    public int getAlbumToolbarIconColor() {
        return albumToolbarIconColor;
    }

    public AlbumConfig setAlbumToolbarIconColor(int albumToolbarIconColor) {
        this.albumToolbarIconColor = albumToolbarIconColor;
        return this;
    }

    public int getAlbumBottomFinderTextDrawableColor() {
        return albumBottomFinderTextDrawableColor;
    }

    public AlbumConfig setAlbumBottomFinderTextDrawableColor(int albumBottomFinderTextDrawableColor) {
        this.albumBottomFinderTextDrawableColor = albumBottomFinderTextDrawableColor;
        return this;
    }

    public boolean isRadio() {
        return isRadio;
    }

    public AlbumConfig setRadio(boolean radio) {
        isRadio = radio;
        return this;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public AlbumConfig setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        return this;
    }

    public int getAlbumContentViewBackground() {
        return albumContentViewBackground;
    }

    public AlbumConfig setAlbumContentViewBackground(int albumContentViewBackground) {
        this.albumContentViewBackground = albumContentViewBackground;
        return this;
    }

    public int getAlbumContentViewCameraDrawable() {
        return albumContentViewCameraDrawable;
    }

    public AlbumConfig setAlbumContentViewCameraDrawable(int albumContentViewCameraDrawable) {
        this.albumContentViewCameraDrawable = albumContentViewCameraDrawable;
        return this;
    }

    public int getAlbumContentViewCameraDrawableColor() {
        return albumContentViewCameraDrawableColor;
    }

    public AlbumConfig setAlbumContentViewCameraDrawableColor(int albumContentViewCameraDrawableColor) {
        this.albumContentViewCameraDrawableColor = albumContentViewCameraDrawableColor;
        return this;
    }

    public int getAlbumPreviewTitle() {
        return albumPreviewTitle;
    }

    public AlbumConfig setAlbumPreviewTitle(int albumPreviewTitle) {
        this.albumPreviewTitle = albumPreviewTitle;
        return this;
    }

    public int getAlbumPreviewBackground() {
        return albumPreviewBackground;
    }

    public AlbumConfig setAlbumPreviewBackground(int albumPreviewBackground) {
        this.albumPreviewBackground = albumPreviewBackground;
        return this;
    }

    public boolean isCrop() {
        return isCrop;
    }

    public AlbumConfig setCrop(boolean crop) {
        isCrop = crop;
        return this;
    }

    public int getAlbumContentItemCheckBoxDrawable() {
        return albumContentItemCheckBoxDrawable;
    }

    public AlbumConfig setAlbumContentItemCheckBoxDrawable(int albumContentItemCheckBoxDrawable) {
        this.albumContentItemCheckBoxDrawable = albumContentItemCheckBoxDrawable;
        return this;
    }

    public int getMultipleMaxCount() {
        return multipleMaxCount;
    }

    public AlbumConfig setMultipleMaxCount(int multipleMaxCount) {
        this.multipleMaxCount = multipleMaxCount;
        return this;
    }

    public int getAlbumPreviewBottomViewBackground() {
        return albumPreviewBottomViewBackground;
    }

    public AlbumConfig setAlbumPreviewBottomViewBackground(int albumPreviewBottomViewBackground) {
        this.albumPreviewBottomViewBackground = albumPreviewBottomViewBackground;
        return this;
    }

    public int getAlbumPreviewBottomOkText() {
        return albumPreviewBottomOkText;
    }

    public AlbumConfig setAlbumPreviewBottomOkText(int albumPreviewBottomOkText) {
        this.albumPreviewBottomOkText = albumPreviewBottomOkText;
        return this;
    }

    public int getAlbumPreviewBottomOkTextColor() {
        return albumPreviewBottomOkTextColor;
    }

    public AlbumConfig setAlbumPreviewBottomOkTextColor(int albumPreviewBottomOkTextColor) {
        this.albumPreviewBottomOkTextColor = albumPreviewBottomOkTextColor;
        return this;
    }

    public int getAlbumPreviewBottomOkTextSize() {
        return albumPreviewBottomOkTextSize;
    }

    public AlbumConfig setAlbumPreviewBottomOkTextSize(int albumPreviewBottomOkTextSize) {
        this.albumPreviewBottomOkTextSize = albumPreviewBottomOkTextSize;
        return this;
    }
}