package com.meow.meowsic.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.meow.meowsic.R
import com.meow.meowsic.activities.MainActivity
import com.meow.meowsic.adapters.SeeAllAdapter
import com.meow.meowsic.backgroundTask.ColorPaletteFromImage
import com.meow.meowsic.dao.ArtistsDao
import com.meow.meowsic.dao.PlaylistDao
import com.meow.meowsic.dao.TracksDao
import com.meow.meowsic.databinding.FragmentArtistBinding
import com.meow.meowsic.models.Artists
import com.meow.meowsic.models.Playlists
import com.meow.meowsic.models.Songs
import com.meow.meowsic.utilities.Constants
import com.meow.meowsic.utilities.Utilities
import com.meow.meowsic.volley.RequestCallback

class ArtistFragment : Fragment(), RequestCallback {

    var TYPE: Int = 0
    private val Constants = Constants()
    private val Utilities = Utilities()
    private lateinit var currentArtist: Artists
    private lateinit var currentPlaylist: Playlists
    private lateinit var currentArtistId: String
    private lateinit var currentPlaylistId: String
    private lateinit var binding: FragmentArtistBinding
    private lateinit var seeAllAdapter: SeeAllAdapter
    private lateinit var tracksDao: TracksDao
    private lateinit var artistsDao: ArtistsDao
    private lateinit var playlistDao: PlaylistDao
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var colorPaletteFromImage: ColorPaletteFromImage
    private lateinit var gd: GradientDrawable
    private lateinit var songs: ArrayList<Songs>
    private var currentSongIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        TYPE = bundle?.getInt(Constants.TYPE)!!
        tracksDao = TracksDao(context, this)
        artistsDao = ArtistsDao(context, this)
        playlistDao = PlaylistDao(context, this)
        songs = ArrayList()

        when (TYPE) {
            Constants.TYPE_ARTIST -> {
                currentArtist = bundle.getParcelable(Constants.ARTIST_MODEL_KEY)!!
                currentArtistId = currentArtist.id!!
            }
            Constants.TYPE_PLAYLIST -> {
                currentPlaylist = bundle.getParcelable(Constants.PLAYLIST_MODEL_KEY)!!
                currentPlaylistId = currentPlaylist.id!!
            }
        }

        seeAllAdapter = SeeAllAdapter(context, songs, null, null, object : SeeAllAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int, check: Int) {
                if (TYPE == Constants.TYPE_PLAYLIST) {
                    val playlist = Playlists(currentPlaylistId, songs)
                    (activity as MainActivity).playSongInMainActivity(position, playlist, true)
                    changeSelectedPosition(position)
                } else if (TYPE == Constants.TYPE_ARTIST) {
                    Toast.makeText(context, songs[position].name.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }, Constants.TYPE_TRACK)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_artist, container, false)
        binding = FragmentArtistBinding.bind(view)

        gd = GradientDrawable()
        colorPaletteFromImage = ColorPaletteFromImage(context, object : ColorPaletteFromImage.PaletteCallback {
            override fun onPostExecute(palette: Palette?) {
                changeBackground(Utilities.getBackgroundColorFromPalette(palette))
            }
        })

        initialise(view)
        return view
    }

    fun changeBackground(color: Int) {
        gd.colors = intArrayOf(color, context?.resources?.getColor(R.color.bg)!!)
        gd.gradientType = GradientDrawable.RADIAL_GRADIENT
        gd.gradientRadius = 600f
        binding.appbarlayout.background = gd
    }

    private fun initialise(view: View) {
        linearLayoutManager = LinearLayoutManager(context)
        binding.artistrv.layoutManager = linearLayoutManager

        when (TYPE) {
            Constants.TYPE_ARTIST -> {
                initialiseArtist()
                binding.artistrv.adapter = seeAllAdapter
//                artistsDao.getArtistFromId(currentArtistId)
            }
            Constants.TYPE_PLAYLIST -> {
                initialisePlaylist()
                binding.artistrv.adapter = seeAllAdapter
//                playlistDao.getTracksFromPlaylistId(currentPlaylistId)
            }
        }
    }

    private fun initialisePlaylist() {
        colorPaletteFromImage.execute(currentPlaylist.artwork)
        binding.artistname.text = currentPlaylist.name?.lowercase()
        Glide.with(requireContext()).load(currentPlaylist.artwork).into(binding.artistimage)
//        songs.clear()
//        songs.addAll(currentPlaylist.songs)
        playlistDao.getPlaylistFromPlaylistId(currentPlaylistId)
//        changeSelectedPosition(Utilities.getSelectedPosition(context, songs, 0))
//        seeAllAdapter.changeSongData(songs)
    }

    private fun initialiseArtist() {
        colorPaletteFromImage.execute(currentArtist.image)
        binding.artistname.text = currentArtist.name?.lowercase()
        binding.followers.text = Utilities.formatIntWithComma(currentArtist.followers, " Followers")
        Glide.with(requireContext()).load(currentArtist.image).into(binding.artistimage)
        artistsDao.getArtistTopTracks(currentArtistId)
    }

    override fun onRequestSuccessful(`object`: Any?, check: Int, status: Boolean) {
        when (check) {
            Constants.SEARCH_PLAYLISTS_WITH_ID -> {
                if (status) {
                    val playlists = `object` as Playlists
                    currentPlaylist = playlists
                    binding.followers.text = Utilities.formatIntWithComma(currentPlaylist.track_count.toLong(), " Tracks")
                    songs.clear()
                    songs.addAll(playlists.songs)
                    changeSelectedPosition(Utilities.getSelectedPosition(context, songs, 0))
                    seeAllAdapter.changeSongData(songs)
                }
            }
            Constants.SEARCH_SONGS_WITH_ARTIST_ID -> {
                if (status) {
                    val playlists = `object` as Playlists
                    songs.clear()
                    songs.addAll(playlists.songs)
                    changeSelectedPosition(Utilities.getSelectedPosition(context, songs, 0))
                    seeAllAdapter.changeSongData(songs)
                }
            }
        }
    }

    private fun changeSelectedPosition(index: Int) {
        when (TYPE) {
            Constants.TYPE_ARTIST -> {
                seeAllAdapter.notifyItemChanged(seeAllAdapter.selectedPosition)
                currentSongIndex = index
                seeAllAdapter.selectedPosition = currentSongIndex
                seeAllAdapter.notifyItemChanged(currentSongIndex)
            }
            Constants.TYPE_PLAYLIST -> {
                seeAllAdapter.notifyItemChanged(seeAllAdapter.selectedPosition)
                currentSongIndex = index
                seeAllAdapter.selectedPosition = currentSongIndex
                seeAllAdapter.notifyItemChanged(currentSongIndex)
            }
        }
    }
}