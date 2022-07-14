package com.vigeo.avcm.myInfo.view

import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.avcm.R
import com.vigeo.avcm.databinding.ActivityFaqBinding
import com.vigeo.avcm.myInfo.service.MyInfoService
import com.vigeo.avcm.myInfo.viewModel.MyInfoVO
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FaqActivity : AppCompatActivity() {

    private lateinit var faqbinding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("=========================== FaqActivity : ", "FaqActivity - onCreate() called ===========================")
        super.onCreate(savedInstanceState)
        faqbinding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(faqbinding.root)

        /* retrofit DB 연결 */
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.193:8080/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(MyInfoService::class.java).faqList(userGb = "04").enqueue(object : Callback<MyInfoVO> {
            override fun onResponse(call: Call<MyInfoVO>, response: Response<MyInfoVO>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성공된 경우
                    var faqInfoVo = response.body()!!
                    Log.d("\"FAQ : ", "onResponse 성공: " + faqInfoVo.toString());

                    val adapter = faqAdapter(layoutInflater, faqInfoVo)
                    faqbinding.recyclerFaq.adapter = adapter
                    faqbinding.recyclerFaq.layoutManager = LinearLayoutManager(this@FaqActivity)
                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("\"FAQ : ", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<MyInfoVO>, t: Throwable) {
                Log.d("FAQ : ", "onResponse 실패")
            }
        })

        //뒤로가기
        faqbinding.faqGoBack.setOnClickListener {
            Log.d("Myinfo", "Faq로 이동")
            finish()
        }
    }
}

/* 어뎁터 */
class faqAdapter(
    val layoutInflater: LayoutInflater,
    var faqInfoVo2: MyInfoVO
) : RecyclerView.Adapter<faqAdapter.ViewHolder>() {

    // Item의 클릭 상태를 저장할 array 객체
    private var selectedItems: SparseBooleanArray = SparseBooleanArray()

    // 직전에 클릭됐던 Item의 position
    private var prePosition = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val faqtitle :TextView
        val faqcontent :TextView

        init {
            faqtitle = itemView.findViewById(R.id.faq_title)
            faqcontent = itemView.findViewById(R.id.faq_content)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = layoutInflater.inflate(R.layout.recycler_faq, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var content = faqInfoVo2.faqList.get(position).content
        var title = faqInfoVo2.faqList.get(position).title

        holder.faqtitle.setText(title)
        holder.faqcontent.setText(content)

        holder.faqtitle.apply {

            setOnClickListener {

                if(selectedItems.get(position)){
                    selectedItems.delete(position)
                    holder.faqcontent.visibility = View.GONE
                    Log.d("아코디언 : ",position.toString() + "내용 숨김")
                }else{
                    selectedItems.put(position, true)
                    holder.faqcontent.visibility = View.VISIBLE
                    Log.d("아코디언 : ",position.toString() + " 내용 표출")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return faqInfoVo2.faqList.size
    }

}