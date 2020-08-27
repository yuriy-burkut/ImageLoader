package com.yuriy.imageloader.file_storage

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.yuriy.imageloader.R
import com.yuriy.imageloader.entities.ImageResult
import com.yuriy.imageloader.entities.SavedImageInfo
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val IMAGES_DIR = "images"
const val PREVIEW_DIR = "preview"

class FileStorageManager(val context: Context) {

    suspend fun saveToDevice(imageResult: ImageResult): SavedImageInfo? {

        val gifFullPath: String =
            getOutputDirectory(IMAGES_DIR).absolutePath + "/" + imageResult.id + ".gif"

        val previewFullPath: String =
            getOutputDirectory(PREVIEW_DIR).absolutePath + "/" + imageResult.id + "_preview.png"

        val gifPath = downloadFile(imageResult.media.first().gif.imageUrl)?.let {
            saveFile(it, gifFullPath)
        }

        val previewPath = downloadFile(imageResult.media.first().gif.previewUrl)?.let {
            saveFile(it, previewFullPath)
        }

        return if (gifPath != null && previewPath != null) {
            SavedImageInfo(imageResult.id, imageResult.title, previewPath, gifPath)
        } else null

    }

    private fun getOutputDirectory(name: String): File {

        val subDirsPath: String = context.resources.getString(R.string.app_name) + "/" + name

        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, subDirsPath).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) {
            mediaDir
        } else context.filesDir
    }

    private suspend fun downloadFile(imageUrl: String): File? {

        return suspendCoroutine { continuation ->
            Glide.with(context).download(imageUrl).into(object : CustomTarget<File>() {

                override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                    continuation.resume(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    continuation.resume(null)
                }
            })
        }
    }

    private fun saveFile(inputFile: File, filePath: String): String? {

        var isSuccessful = true
        val newFile = File(filePath)
        val outputStream = FileOutputStream(newFile)
        val bytesToWrite = inputFile.readBytes()

        try {
            outputStream.write(bytesToWrite)
        } catch (exception: Exception) {
            isSuccessful = false
            exception.printStackTrace()
        } finally {
            outputStream.close()
        }

        return if (isSuccessful) {
            newFile.canonicalPath
        } else null
    }

}