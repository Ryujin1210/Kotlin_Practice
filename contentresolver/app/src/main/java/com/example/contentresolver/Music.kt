package com.example.contentresolver

import android.net.Uri
import android.provider.MediaStore
import java.net.URI

data class Music (
    var id: String = "",
    var title : String?,
    var artist : String?,
    var albumId : String?,
    var duration : Long?
) {
    // 음원의 주소를 가져오는 메서드
    fun getMusicUri(): Uri {
        return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id) // 음원 주소 조합
    }
    //앨범 이미지의 주소를 가져오는 메서드
    fun getAlbumUri() : Uri {
        return Uri.parse("content://media/external/audio/albumart/$albumId")
    }
}