package com.meow.meowsic.utilities

class Constants {

    private val EACH_SONG_LAYOUT_CLICKED = 0
    private val EACH_SONG_MENU_CLICKED = 1
    private val EACH_USER_LAYOUT_CLICKED = 8
    private val EACH_USER_MENU_CLICKED = 9
    private val EACH_SONG_VIEW_LONG_CLICKED = 10
    private val SEE_ALL_SONGS_CLICKED = 14
    private val SEE_ALL_USERS_CLICKED = 15
    private val EACH_PLAYLIST_LAYOUT_CLICKED = 18
    private val EACH_PLAYLIST_MENU_CLICKED = 19

    private val METHOD_GET = 2
    private val METHOD_POST = 3

    private val SEARCH_SONGS_WITH_QUERY = 4
    private val SEARCH_SONG_WITH_ID = 5
    private val SEARCH_USERS_WITH_QUERY = 6
    private val SEARCH_USER_WITH_ID = 7
    private val SEARCH_SONGS_WITH_USER_ID = 8

    private val TYPE_TRACK = 11
    private val TYPE_USER = 12
    private val TYPE_PLAYLIST = 13
    private val TYPE_HISTORY = 25
    private val TYPE_DATA = 26


    private val USER_MODEL_KEY = "user"
    private val SONG_MODEL_KEY = "song"
    private val SONG_ID_KEY = "song_id"
    private val USER_ID_KEY = "user_id"
    private val USERS_MODEL_KEY = "users"
    private val SONGS_MODEL_KEY = "songs"
    private val SONG_POSITION_MODEL_KEY = "song_position"
    private val FRAGMENT_HOME = "homeFragment"
    private val FRAGMENT_MYPROFILE = "myProfileFragment"
    private val FRAGMENT_YOUR_LIBRARY = "yourLibraryFragment"
    private val FRAGMENT_USER_PAGE = "userPageFragment"
    private val FRAGMENT_SEE_ALL = "seeAllFragment"
    private val FRAGMENT_SEARCH = "searchFragment"


    private val SEARCH_PLAYLISTS_WITH_QUERY = 16
    private val SEARCH_PLAYLISTS_WITH_ID = 17
    private val TYPE = "type"
    private val PLAYLIST_MODEL_KEY = "playlists"
    private val SEARCH_SONG_WITH_PLAYLIST_ID = 20
    private val SEE_ALL_PLAYLISTS_CLICKED = 21
    private val CURRENT_PLAYING_SONG_POSITION = "currentPlayingSongPosition"
    private val CLEAR_HISTORY_CLICKED = 22
    private val HISTORY_LAYOUT_CLICKED = 23
    private val HISTORY_CROSS_CLICKED = 24

    private val YOUR_LIBRARY_TOP_CONTENT_CLICKED = 32

    //service constants;
    private val MUSIC_PLAYED = 27
    private val MUSIC_PAUSED = 28
    private val MUSIC_STARTED = 29
    private val MUSIC_ENDED = 30
    private val MUSIC_LOADED = 31
    private val IS_PLAYING = "isPlaying"

    private val NOTIFICATION_SONG_CHANNEL_ID = "songChannelId"
    private val NOTIFICATION_SONG_ID = 1
    private val MEDIA_SESSION_TAG = "smynd_media_session"


    private val CLICK_NEXT = "com.meow.meowsic.utilities.nextClick"
    private val CLICK_PREVIOUS = "com.meow.meowsic.utilities.previousClick"
    private val CLICK_PLAY_PAUSE = "com.meow.meowsic.utilities.playPauseClick"
    private val FROM_NOTIFICATION = "fromNotification"

    private val SHOW_NO_HISTORY = 32
    private val SHOW_NO_SEARCH_RESULT = 33
    private val SHOW_NO_INTERNET_CONNECTION = 34
    private val SHOW_PROGRESS_BAR = 35
    private val SHOW_RECYCLER_VIEW = 36
}