package com.meow.meowsic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.models.Artists
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.viewHolders.*

class SeeAllAdapter(val context: Context?,
                    songs: ArrayList<Songs>,
                    val artists: ArrayList<Artists>?,
                    val playlists: ArrayList<Playlists>?,
                    val itemClickListener: ItemClickListener,
                    val TYPE: Int?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedPosition: Int = 0
    private val Constants = Constants()
    var songs = songs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
        if (TYPE == Constants.TYPE_TRACK) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
            val eachSongVIewHolder: EachSongViewHolder = EachSongViewHolder(v)
            eachSongVIewHolder.songcard.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachSongVIewHolder.adapterPosition,
                    Constants.EACH_SONG_LAYOUT_CLICKED
                )
            }
            eachSongVIewHolder.options.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachSongVIewHolder.adapterPosition,
                    Constants.EACH_SONG_MENU_CLICKED
                )
            }
            eachSongVIewHolder.songcard.setOnLongClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachSongVIewHolder.adapterPosition,
                    Constants.EACH_SONG_VIEW_LONG_CLICKED
                )
                return@setOnLongClickListener true
            }
            return eachSongVIewHolder
        } else if (TYPE == Constants.TYPE_ARTIST) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
            val eachArtistViewHolder: EachArtistViewHolder = EachArtistViewHolder(v)
            eachArtistViewHolder.artistcard.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachArtistViewHolder.adapterPosition,
                    Constants.EACH_ARTIST_LAYOUT_CLICKED
                )
            }
            return eachArtistViewHolder
        } else {
            v = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
            val eachPlaylistViewHolder: EachPlaylistViewHolder = EachPlaylistViewHolder(v)
            eachPlaylistViewHolder.playlistcard.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachPlaylistViewHolder.adapterPosition,
                    Constants.EACH_PLAYLIST_LAYOUT_CLICKED
                )
            }
            eachPlaylistViewHolder.options.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachPlaylistViewHolder.adapterPosition,
                    Constants.EACH_PLAYLIST_MENU_CLICKED
                )
            }
            return eachPlaylistViewHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (TYPE) {
            Constants.TYPE_TRACK -> {
                val song: Songs = songs[position]
                val eachSongViewHolder = holder as EachSongViewHolder
                eachSongViewHolder.songname.text = song.name
                eachSongViewHolder.artistname.text = song.artist
                if (context != null) {
                    Glide.with(context)
                        .load(song.songArtwork)
                        .into(eachSongViewHolder.albumcover)
                }
                if (position == selectedPosition) {
                    eachSongViewHolder.songname.setTextColor(context?.resources?.getColor(R.color.colorAccent)!!)
                }
            }
            Constants.TYPE_ARTIST -> {
                val artist: Artists? = artists?.get(position)
                val eachArtistViewHolder = holder as EachArtistViewHolder
                eachArtistViewHolder.artistname.text = artist?.name
                if (context != null) {
                    Glide.with(context)
                        .load(artist?.image)
                        .into(eachArtistViewHolder.artistpic)
                }
            }
            Constants.TYPE_PLAYLIST -> {
                val playlist: Playlists? = playlists?.get(position)
                val eachPlaylistViewHolder = holder as EachPlaylistViewHolder
                eachPlaylistViewHolder.playlistname.text = playlist?.name
                if (context != null) {
                    Glide.with(context)
                        .load(playlist?.artwork)
                        .into(eachPlaylistViewHolder.albumcover)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (TYPE == Constants.TYPE_TRACK) return songs.size
        else if (TYPE == Constants.TYPE_ARTIST) return artists?.size!!
        else return playlists?.size!!
    }

    fun changeSongData(songs: ArrayList<Songs>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    fun notifySongRemovedFromPlaylist(songs: ArrayList<Songs>, position: Int) {
        this.songs = songs
        notifyItemRemoved(position)
    }

    fun notifySongAddedToPlaylist(songs: ArrayList<Songs>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int, check: Int)
    }
}