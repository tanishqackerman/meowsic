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

class SearchSongAdapter(val context: Context?, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_HEADER = 100
    private val TYPE_TRACK = 0
    private val TYPE_ARTIST = 1
    private val TYPE_PLAYLIST = 2
    private val TYPE_FOOTER = 101
    private val headers = arrayOf("Songs", "Artists", "Playlists")
    private val footers = arrayOf("See all songs", "See all artists", "See all playlists")
    private var FOOTER_TRACK = 1
    private var FOOTER_ARTIST = 3
    private var FOOTER_PLAYLIST = 5
    private val HEADER_TRACK = 0
    private var HEADER_ARTIST = 9
    private var HEADER_PLAYLIST = 11
    private var hasTrackHeader: Boolean = false
    private var hasTrackFooter: Boolean = false
    private var hasPlaylistHeader: Boolean = false
    private var hasPlaylistFooter: Boolean = false
    private var hasArtistHeader: Boolean = false
    private var hasArtistFooter: Boolean = false
    private var songs: List<Songs>? = null
    private var artists: List<Artists>? = null
    private var playlists: List<Playlists>? = null
    var selectedPosition = 0
    private val Constants = Constants()

    init {
        this.songs = ArrayList()
        this.artists = ArrayList()
        this.playlists = ArrayList()
    }

    override fun getItemViewType(position: Int): Int {
        if (position == HEADER_TRACK && hasTrackHeader) return TYPE_HEADER
        if (position < FOOTER_TRACK && hasTrackHeader) return TYPE_TRACK
        if (position == FOOTER_TRACK && hasTrackFooter) return TYPE_FOOTER

        if (position == HEADER_ARTIST && hasArtistHeader) return TYPE_HEADER
        if (position < FOOTER_ARTIST && hasArtistHeader) return TYPE_ARTIST
        if (position == FOOTER_ARTIST && hasArtistFooter) return TYPE_FOOTER

        if (position == HEADER_PLAYLIST && hasPlaylistHeader) return TYPE_HEADER
        if (position < FOOTER_PLAYLIST && hasPlaylistHeader) return TYPE_PLAYLIST
        if (position == FOOTER_PLAYLIST && hasPlaylistFooter) return TYPE_FOOTER

        return TYPE_HEADER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.searchsongheader, parent, false)
            return SongHeaderViewHolder(v)
        } else if (viewType == TYPE_FOOTER) {
            v = LayoutInflater.from(parent.context)
                .inflate(R.layout.searchsongfooter, parent, false)
            val songFooterViewHolder: SongFooterViewHolder = SongFooterViewHolder(v)
            songFooterViewHolder.songfooter.setOnClickListener {
                if (songFooterViewHolder.adapterPosition == FOOTER_TRACK && hasTrackFooter) {
                    itemClickListener.onItemClick(
                        v,
                        songFooterViewHolder.adapterPosition,
                        Constants.SEE_ALL_SONGS_CLICKED
                    )
                } else if (songFooterViewHolder.adapterPosition == FOOTER_ARTIST && hasArtistFooter) {
                    itemClickListener.onItemClick(
                        v,
                        songFooterViewHolder.adapterPosition,
                        Constants.SEE_ALL_ARTISTS_CLICKED
                    )
                } else if (songFooterViewHolder.adapterPosition == FOOTER_PLAYLIST && hasPlaylistFooter) {
                    itemClickListener.onItemClick(
                        v,
                        songFooterViewHolder.adapterPosition,
                        Constants.SEE_ALL_PLAYLISTS_CLICKED
                    )
                }
            }
            return songFooterViewHolder
        } else if (viewType == TYPE_TRACK) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
            val eachSongVIewHolder: EachSongViewHolder = EachSongViewHolder(v)
            eachSongVIewHolder.songcard.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachSongVIewHolder.adapterPosition - 1,
                    Constants.EACH_SONG_LAYOUT_CLICKED
                )
            }
            eachSongVIewHolder.options.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachSongVIewHolder.adapterPosition - 1,
                    Constants.EACH_SONG_MENU_CLICKED
                )
            }
            eachSongVIewHolder.songcard.setOnLongClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachSongVIewHolder.adapterPosition - 1,
                    Constants.EACH_SONG_VIEW_LONG_CLICKED
                )
                return@setOnLongClickListener true
            }
            return eachSongVIewHolder
        } else if (viewType == TYPE_ARTIST) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
            val eachArtistViewHolder: EachArtistViewHolder = EachArtistViewHolder(v)
            eachArtistViewHolder.artistcard.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachArtistViewHolder.adapterPosition - (HEADER_ARTIST + 1),
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
                    eachPlaylistViewHolder.adapterPosition - (HEADER_PLAYLIST + 1),
                    Constants.EACH_PLAYLIST_LAYOUT_CLICKED
                )
            }
            eachPlaylistViewHolder.options.setOnClickListener {
                itemClickListener.onItemClick(
                    v,
                    eachPlaylistViewHolder.adapterPosition - (HEADER_PLAYLIST + 1),
                    Constants.EACH_PLAYLIST_MENU_CLICKED
                )
            }
            return eachPlaylistViewHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_TRACK -> {
                val song: Songs? = if (hasTrackHeader) songs?.get(position - HEADER_TRACK - 1) else null
                val eachSongViewHolder = holder as EachSongViewHolder
                eachSongViewHolder.songname.text = song?.name
                eachSongViewHolder.artistname.text = song?.artist
                if (context != null) {
                    Glide.with(context)
                        .load(song?.songArtwork)
                        .into(eachSongViewHolder.albumcover)
                }
                if (position == selectedPosition) eachSongViewHolder.songname.setTextColor(context?.resources?.getColor(R.color.teal_200)!!)
                else eachSongViewHolder.songname.setTextColor(context?.resources?.getColor(R.color.white)!!)
            }
            TYPE_ARTIST -> {
                val artist: Artists? = if (hasArtistHeader && position - HEADER_ARTIST - 1 >= 0) artists?.get(position - HEADER_ARTIST - 1) else null
                val eachArtistViewHolder = holder as EachArtistViewHolder
                eachArtistViewHolder.artistname.text = artist?.name
                if (context != null) {
                    Glide.with(context)
                        .load(artist?.image)
                        .into(eachArtistViewHolder.artistpic)
                }
            }
            TYPE_PLAYLIST -> {
                val playlist: Playlists? = if (hasPlaylistHeader && position - HEADER_PLAYLIST - 1 >= 0) playlists?.get(position - HEADER_PLAYLIST - 1) else null
                val eachPlaylistViewHolder = holder as EachPlaylistViewHolder
                eachPlaylistViewHolder.playlistname.text = playlist?.name
                if (context != null) {
                    Glide.with(context)
                        .load(playlist?.artwork)
                        .into(eachPlaylistViewHolder.albumcover)
                }
            }
            TYPE_HEADER -> {
                if (position == HEADER_TRACK && hasTrackHeader) {
                    val songHeaderViewHolder = holder as SongHeaderViewHolder
                    songHeaderViewHolder.songheader.text = headers[0]
                } else if (position == HEADER_ARTIST && hasArtistHeader) {
                    val songHeaderViewHolder = holder as SongHeaderViewHolder
                    songHeaderViewHolder.songheader.text = headers[1]
                } else if (position == HEADER_PLAYLIST && hasPlaylistHeader) {
                    val songHeaderViewHolder = holder as SongHeaderViewHolder
                    songHeaderViewHolder.songheader.text = headers[2]
                }
            }
            TYPE_FOOTER -> {
                if (position == FOOTER_TRACK && hasTrackFooter) {
                    val songFooterViewHolder = holder as SongFooterViewHolder
                    songFooterViewHolder.songfooter.text = footers[0]
                } else if (position == FOOTER_ARTIST && hasArtistFooter) {
                    val songFooterViewHolder = holder as SongFooterViewHolder
                    songFooterViewHolder.songfooter.text = footers[1]
                } else if (position == FOOTER_PLAYLIST && hasPlaylistFooter) {
                    val songFooterViewHolder = holder as SongFooterViewHolder
                    songFooterViewHolder.songfooter.text = footers[2]
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (hasPlaylistFooter) FOOTER_PLAYLIST + 1 else 0
    }

    fun changeSongData(songs: ArrayList<Songs>) {
        this.songs = songs
        hasTrackHeader = songs.size > 0
        FOOTER_TRACK = if (hasTrackHeader) songs.size + 1 else songs.size
        hasTrackFooter = songs.size == 4 && hasTrackHeader
        this.notifyItemRangeChanged(HEADER_TRACK, FOOTER_TRACK)
    }

    fun changeArtistData(artists: ArrayList<Artists>) {
        this.artists = artists
        hasArtistHeader = artists.size > 0
        HEADER_ARTIST = if (hasTrackFooter) FOOTER_TRACK + 1 else FOOTER_TRACK
        FOOTER_ARTIST = if (hasArtistHeader) HEADER_ARTIST + artists.size + 1 else HEADER_ARTIST
        hasArtistFooter = artists.size == 4 && hasArtistHeader
        this.notifyItemRangeChanged(HEADER_ARTIST, FOOTER_ARTIST - HEADER_ARTIST + 1)
    }

    fun changePlaylistData(playlists: ArrayList<Playlists>) {
        this.playlists = playlists
        hasPlaylistHeader = playlists.size > 0
        HEADER_PLAYLIST = if (hasArtistFooter) FOOTER_ARTIST + 1 else FOOTER_ARTIST
        FOOTER_PLAYLIST = if (hasPlaylistHeader) HEADER_PLAYLIST + playlists.size + 1 else HEADER_PLAYLIST
        hasPlaylistFooter = playlists.size == 4 && hasPlaylistHeader
        this.notifyItemRangeChanged(HEADER_PLAYLIST, FOOTER_PLAYLIST - HEADER_PLAYLIST + 1)
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int, check: Int)
    }
}
