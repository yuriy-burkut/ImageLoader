package com.yuriy.imageloader.livadata

sealed class ViewAction {

    class ShowFullScreen(val imageUrl: String) : ViewAction()

}