package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var helper:RoomHelper
    lateinit var memoAdapter: RecyclerAdapter
    val memoList = mutableListOf<RoomMemo>()
    lateinit var memoDao:RoomMemoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        helper= Room.databaseBuilder(this,RoomHelper::class.java,"room_db")
            .allowMainThreadQueries() //공부할때만 사용하기
            .build()
        memoDao = helper.roomMemoDao()
        memoAdapter = RecyclerAdapter(memoList)

        refresgAdapter()

        with(binding) {
            recyclerMemo.adapter = memoAdapter
            recyclerMemo.layoutManager = LinearLayoutManager(this@MainActivity)

            buttonSave.setOnClickListener{
                val content = editMemo.text.toString()
                if (content.isNotEmpty()) {
                    val datetime = System.currentTimeMillis()
                    val memo = RoomMemo(content,datetime)
                    editMemo.setText("")
                    insertMemo(memo)
                }
            }
        }
    }

    fun insertMemo(memo: RoomMemo) {
        CoroutineScope(Dispatchers.IO).launch {
            memoDao.insert(memo)
            refresgAdapter()
        }
    }

    fun refresgAdapter(){
        CoroutineScope(Dispatchers.IO).launch {
            memoList.clear()
            memoList.addAll(memoDao.getAll())
            withContext(Dispatchers.Main) {
                memoAdapter.notifyDataSetChanged()
            }
        }
    }
}