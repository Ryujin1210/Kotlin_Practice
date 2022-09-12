package com.example.contentresolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentresolver.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requirePermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 999)
    }

    override fun permissionGranted(requestCode: Int) {
        startProcess()
    }

    override fun permissionDenied(requestCode: Int) {
        Toast.makeText(this, "외부 저장소 권한을 승인해야 앱을 사용할 수 있습니다.",
            Toast.LENGTH_LONG).show()
        finishAffinity() // 이 액티비티를 호출한 이전의 액티비티 까지 종료시켜 줍니다.
    }

    fun startProcess() {
        val musicAdapter = MusicRecyclerAdapter()
        musicAdapter.musicList.addAll(getMusicList())

        binding.recyclerView.adapter = musicAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun getMusicList() : List<Music> {
        //1. 컨텐츠가 저장된 주소 정의 (테이블)
        val listUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI //음원이 저장된 테이블
        //2. 테이블에서 읽어올 컬럼 정의
        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION
        )
        //3. 쿼리를 실행해서 데이터를 읽어오고, 변수에 저장
        val cursor = contentResolver.query(listUri,proj,null,null,null)
        //4. 데이터가 저장된 변수를 반복문으 돌면서 Musci 클래스로 변환한 후에 목록에 담는다.
        val musicList = mutableListOf<Music>()
        while (cursor?.moveToNext() == true) {
            val id = cursor?.getString(0)
            val title = cursor?.getString(1)
            val artist = cursor?.getString(2)
            val albumid = cursor?.getString(3)
            val duration = cursor?.getLong(4)

            val music = Music(id,title,artist,albumid,duration)
            musicList.add(music)
        }
        return musicList
    }
}
