package com.vigeo.avcm.myInfo.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.vigeo.avcm.databinding.ActivityGuideVideoBinding


class GuideVideoActivity : AppCompatActivity() {

    private lateinit var guideVideoBinding: ActivityGuideVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guideVideoBinding = ActivityGuideVideoBinding.inflate(layoutInflater)
        setContentView(guideVideoBinding.root)

        //비디오 생성 및 연결
        val guideVideoView : VideoView = guideVideoBinding.videoView

        guideVideoView.setMediaController(MediaController(this))
        
        val url: Uri = Uri.parse("android.resource://$packageName/raw/testvideo")

        guideVideoView.setVideoURI(url)
        guideVideoView.setMediaController(MediaController(this))
        guideVideoView.setOnPreparedListener {
            guideVideoView.start()
        }

        //뒤로가기
        guideVideoBinding.guideGoBack.setOnClickListener {
            Log.d("Myinfo", "Faq로 이동")
            finish()
        }
    }
}