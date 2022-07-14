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
        Log.d("=========================== GuideVideoActivity : ", "GuideVideoActivity - onCreate() called ===========================")
        super.onCreate(savedInstanceState)
        guideVideoBinding = ActivityGuideVideoBinding.inflate(layoutInflater)
        setContentView(guideVideoBinding.root)

        //비디오 생성 및 연결
        val guideVideoView : VideoView = guideVideoBinding.videoView

        //비디오 위치 연결 : 인터넷 주소도 가능 , 현재 내부저장소 파일로 연결
        val url: Uri = Uri.parse("android.resource://$packageName/raw/testvideo")
        guideVideoView.setVideoURI(url)

        //비디오 컨트롤러 연결 (재생, 일시정지, 10초 앞뒤 이동등)
        guideVideoView.setMediaController(MediaController(this))
        
        //재생가능할때 
        guideVideoView.setOnPreparedListener {
            //비디오 실행
            guideVideoView.start()
        }

        //뒤로가기 버튼
        guideVideoBinding.guideGoBack.setOnClickListener {
            Log.d("Myinfo", "Faq로 이동")
            finish()
        }
    }
}