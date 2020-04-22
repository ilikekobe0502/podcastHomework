package com.neo.pod.pages.casts.play

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.neo.pod.AppInjector
import com.neo.pod.R
import com.neo.pod.pages.base.InteractionView
import com.neo.pod.pages.base.OnPageInteractionListener
import com.neo.pod.pages.casts.detail.TAG_PICTURE
import com.neo.pod.pages.casts.detail.TAG_PLAY_INFO
import com.neo.pod.repository.data.ContentFeed
import com.neo.pod.utils.MiscUtils
import kotlinx.android.synthetic.main.fragment_cast_play.*
import kotlinx.coroutines.*

private const val ACTION_INIT: String = "com.neo.pod.pages.casts.play.init"
private const val ACTION_PLAY: String = "com.neo.pod.pages.casts.play.play"
private const val ACTION_PAUSE: String = "com.neo.pod.pages.casts.play.pause"
private const val ACTION_FORWARD: String = "com.neo.pod.pages.casts.play.forward"
private const val ACTION_REVERSE: String = "com.neo.pod.pages.casts.play.reverse"
private const val ACTION_RELEASE: String = "com.neo.pod.pages.casts.play.release"
private const val ACTION_UPDATE: String = "com.neo.pod.pages.casts.play.update"

private const val SOURCE_URL: String = "com.neo.pod.pages.casts.play.source_url"
private const val TAG_TOTAL_TIME: String = "com.neo.pod.pages.casts.play.total_time"
private const val TAG_CURRENT_TIME: String = "com.neo.pod.pages.casts.play.current_time"

class CastsPlayFragment : InteractionView<OnPageInteractionListener.Primary>(), View.OnClickListener {

    private lateinit var mViewModel: CastsPlayViewModel
    private var controlServiceIntent: Intent? = null
    private var isPlaying = false
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action.toString()) {
                ACTION_PLAY -> {
                    isPlaying = true
                    context?.let { imageView_play.background = ContextCompat.getDrawable(it, R.drawable.ic_pause_circle_filled_24dp) }
                }
                ACTION_PAUSE -> {
                    isPlaying = false
                    context?.let { imageView_play.background = ContextCompat.getDrawable(it, R.drawable.ic_play_circle_filled_24dp) }
                }
                ACTION_UPDATE -> {
                    intent?.getIntExtra(TAG_TOTAL_TIME, 0)?.let {
                        textView_end.text = MiscUtils.getTimeString(it.toLong())
                        if (it != 0) {
                            bar_duration.max = it
                        }
                    }
                    intent?.getIntExtra(TAG_CURRENT_TIME, 0)?.let {
                        textView_start.text = MiscUtils.getTimeString(it.toLong())
                        bar_duration.progress = it
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(): CastsPlayFragment = CastsPlayFragment()
        private val TAG = CastsPlayFragment::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = AppInjector.obtainViewModel(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_cast_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controlServiceIntent = Intent(activity, MyService::class.java)
        imageView_play.setOnClickListener(this)
        imageView_reverse.setOnClickListener(this)
        imageView_forward.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        Glide.with(this).load(arguments?.getString(TAG_PICTURE)).into(imageView_picture)
        val info = arguments?.getSerializable(TAG_PLAY_INFO) as ContentFeed
        textView_title.text = info.desc
        controlServiceIntent?.putExtra(SOURCE_URL, info.contentUrl)
        controlMediaPlayer(ACTION_INIT)
    }


    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(ACTION_PLAY)
        filter.addAction(ACTION_PAUSE)
        filter.addAction(ACTION_UPDATE)
        context?.registerReceiver(receiver, filter)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageView_play -> {
                if (!isPlaying)
                    controlMediaPlayer(ACTION_PLAY)
                else
                    controlMediaPlayer(ACTION_PAUSE)
            }
            R.id.imageView_forward -> {
                controlMediaPlayer(ACTION_FORWARD)
            }
            R.id.imageView_reverse -> {
                controlMediaPlayer(ACTION_REVERSE)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        controlMediaPlayer(ACTION_RELEASE)
        context?.apply {
            unregisterReceiver(receiver)
            stopService(controlServiceIntent)
        }
        return super.onBackPressed()
    }

    private fun controlMediaPlayer(action: String) {
        controlServiceIntent?.action = action
        activity?.startService(controlServiceIntent)
    }

    class MyService : Service(), MediaPlayer.OnPreparedListener {

        private var mediaPlayer: MediaPlayer? = null
        private var callbackIntent = Intent()
        private var current = 0
        private var coroutineScope: CoroutineScope? = null

        override fun onBind(intent: Intent?): IBinder? {
            return null
        }

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            when (intent?.action.toString()) {
                ACTION_INIT -> {
                    mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                                AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .build()
                        )

                        setOnPreparedListener(this@MyService)
                        setDataSource(intent?.getStringExtra(SOURCE_URL))
                        prepareAsync()
                    }
                }
                ACTION_PLAY -> {
                    mediaPlayer?.seekTo(current)
                    mediaPlayer?.start()
                    updateUI(ACTION_PLAY)
                    coroutineScope = CoroutineScope(Dispatchers.Main + Job())
                    coroutineScope?.launch(Dispatchers.IO) {
                        while (true) {
                            delay(200)
                            callbackIntent.putExtra(TAG_CURRENT_TIME, mediaPlayer?.currentPosition)
                            updateUI(ACTION_UPDATE)
                        }
                    }
                }
                ACTION_PAUSE -> {
                    mediaPlayer?.pause()
                    mediaPlayer?.currentPosition?.let { current = it }
                    updateUI(ACTION_PAUSE)
                    coroutineScope?.cancel()
                }
                ACTION_FORWARD -> {
                    mediaPlayer?.currentPosition?.plus(30000)?.let { mediaPlayer?.seekTo(it) }
                }
                ACTION_REVERSE -> {
                    mediaPlayer?.currentPosition?.minus(30000)?.let { mediaPlayer?.seekTo(it) }
                }
                ACTION_RELEASE -> {
                    mediaPlayer?.release()
                    mediaPlayer = null
                    coroutineScope?.cancel()
                }
            }
            return super.onStartCommand(intent, flags, startId)
        }

        override fun onPrepared(mp: MediaPlayer?) {
            mp?.start()
            callbackIntent.putExtra(TAG_TOTAL_TIME, mp?.duration)
            updateUI(ACTION_UPDATE)
        }

        private fun updateUI(action: String) {
            callbackIntent.action = action
            sendBroadcast(callbackIntent)
        }
    }
}