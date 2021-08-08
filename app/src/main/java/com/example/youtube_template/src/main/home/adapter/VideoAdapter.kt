package com.example.youtube_template.src.main.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtube_template.R
import com.example.youtube_template.databinding.ItemVideoBinding
import com.example.youtube_template.src.main.home.models.VideoMeta

class VideoAdapter(private val context: Context) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private val dataList = ArrayList<VideoMeta>()
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemVideoBinding
    private var profileData = mutableMapOf<String, String>()

    class ViewHolder(binding : ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        val thumbnail = binding.ivThumbnail
        val videoTitle = binding.tvVideoTitle
        val videoInfo = binding.tvVideoInfo
        val userProfile = binding.ivProfile
        //val btnMore = binding.btnMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemVideoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val temp = dataList[position].snippet.channelTitle + " \u00b7 조회수 " + getInformationString(dataList[position].statistics.viewCount)
        holder.videoTitle.text = dataList[position].snippet.title
        holder.videoInfo.text = temp
        Glide.with(context).load(dataList[position].snippet.thumbnails.high.url).into(holder.thumbnail)
        if (profileData.containsKey(dataList[position].snippet.channelId)){
            Glide.with(context).load(profileData[dataList[position].snippet.channelId]).into(holder.userProfile)
        } else {
            holder.userProfile.setBackgroundColor(context.getColor(R.color.black))
        }

            // 클릭 이벤트와 long click 은 나중에
         /*else {
            holder.videoTitle.text = " ".repeat(10)
            holder.videoInfo.text = " ".repeat(15)
        }*/
    }

    override fun getItemCount(): Int = dataList.size

    private fun getInformationString(watchCount : Long) : String {
        return when(watchCount / 1000){
            0L -> watchCount.toString() + "회 "
            in 1..9 -> (watchCount / 1000).toString() + "천회 "
            else -> (watchCount / 10000).toString() + "만회 "
        }
    }

    fun addDataList(newData : List<VideoMeta>, newProfile : Map<String, String>? = null){
        dataList.addAll(newData)
        if (newProfile != null){
            for (key in newProfile.keys){
                profileData[key] = newProfile[key].toString()
            }
            /*profileData = newProfile*/
        }
        notifyDataSetChanged()
    }

    fun clearDataList(){
        dataList.clear()
        profileData.clear()
        notifyDataSetChanged()
    }

}