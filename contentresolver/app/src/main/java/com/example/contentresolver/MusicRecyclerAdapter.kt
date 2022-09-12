package com.example.contentresolver

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contentresolver.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class MusicRecyclerAdapter : RecyclerView.Adapter<MusicRecyclerAdapter.Holder>() {

    var musicList = mutableListOf<Music>()
    var mediaPlayer:MediaPlayer? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = musicList.get(position)
        holder.setMusic(music)
    }

    override fun getItemCount(): Int = musicList.size

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var musicUri: Uri? = null
        init {
            itemView.setOnClickListener {
                if(mediaPlayer != null) {
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                mediaPlayer = MediaPlayer.create(itemView.context, musicUri)
                mediaPlayer?.start()
            }
        }

        fun setMusic(music:Music) {
            this.musicUri = music.getMusicUri()
            binding.run {
                //imageAlbum.setImageURI(music.getAlbumUri())
                Glide.with(binding.root).load(music.getAlbumUri()).into(imageAlbum)
                textArtist.text = music.artist
                textTitle.text = music.title

                val duration = SimpleDateFormat("mm:ss").format(music.duration)
                textDuration.text = duration
            }
        }
    }
}