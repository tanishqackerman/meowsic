package com.meow.meowsic.utilities

open class Constants {

    val EACH_SONG_LAYOUT_CLICKED = 0
    val EACH_SONG_MENU_CLICKED = 1
    val EACH_ARTIST_LAYOUT_CLICKED = 8
    val EACH_ARTIST_MENU_CLICKED = 9
    val EACH_SONG_VIEW_LONG_CLICKED = 10
    val SEE_ALL_SONGS_CLICKED = 14
    val SEE_ALL_ARTISTS_CLICKED = 15
    val EACH_PLAYLIST_LAYOUT_CLICKED = 18
    val EACH_PLAYLIST_MENU_CLICKED = 19

    val METHOD_GET = 2
    val METHOD_POST = 3

    val SEARCH_SONGS_WITH_QUERY = 4
    val SEARCH_SONG_WITH_ID = 5
    val SEARCH_ARTISTS_WITH_QUERY = 6
    val SEARCH_ARTIST_WITH_ID = 7
    val SEARCH_SONGS_WITH_ARTIST_ID = 8

    val TYPE_TRACK = 11
    val TYPE_ARTIST = 12
    val TYPE_PLAYLIST = 13
    val TYPE_HISTORY = 25
    val TYPE_DATA = 26


    val ARTIST_MODEL_KEY = "user"
    val SONG_MODEL_KEY = "song"
    val SONG_ID_KEY = "song_id"
    val USER_ID_KEY = "user_id"
    val ARTISTS_MODEL_KEY = "users"
    val SONGS_MODEL_KEY = "songs"
    val SONG_POSITION_MODEL_KEY = "song_position"
    val FRAGMENT_HOME = "homeFragment"
    val FRAGMENT_MYPROFILE = "myProfileFragment"
    val FRAGMENT_YOUR_LIBRARY = "yourLibraryFragment"
    val FRAGMENT_USER_PAGE = "userPageFragment"
    val FRAGMENT_SEE_ALL = "seeAllFragment"
    val FRAGMENT_SEARCH = "searchFragment"


    val SEARCH_PLAYLISTS_WITH_QUERY = 16
    val SEARCH_PLAYLISTS_WITH_ID = 17
    val TYPE = "type"
    val PLAYLIST_MODEL_KEY = "playlists"
    val SEARCH_SONG_WITH_PLAYLIST_ID = 20
    val SEE_ALL_PLAYLISTS_CLICKED = 21
    val CURRENT_PLAYING_SONG_POSITION = "currentPlayingSongPosition"
    val CLEAR_HISTORY_CLICKED = 22
    val HISTORY_LAYOUT_CLICKED = 23
    val HISTORY_CROSS_CLICKED = 24

    val YOUR_LIBRARY_TOP_CONTENT_CLICKED = 32

    //service constants;
    val MUSIC_PLAYED = 27
    val MUSIC_PAUSED = 28
    val MUSIC_STARTED = 29
    val MUSIC_ENDED = 30
    val MUSIC_LOADED = 31
    val IS_PLAYING = "isPlaying"

    val NOTIFICATION_SONG_CHANNEL_ID = "songChannelId"
    val NOTIFICATION_SONG_ID = 1
    val MEDIA_SESSION_TAG = "smynd_media_session"


    val CLICK_NEXT = "com.meow.meowsic.utilities.nextClick"
    val CLICK_PREVIOUS = "com.meow.meowsic.utilities.previousClick"
    val CLICK_PLAY_PAUSE = "com.meow.meowsic.utilities.playPauseClick"
    val FROM_NOTIFICATION = "fromNotification"

    val SHOW_NO_HISTORY = 32
    val SHOW_NO_SEARCH_RESULT = 33
    val SHOW_NO_INTERNET_CONNECTION = 34
    val SHOW_PROGRESS_BAR = 35
    val SHOW_RECYCLER_VIEW = 36
}