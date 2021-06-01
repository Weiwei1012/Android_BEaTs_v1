package com.weiwei.beats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.weiwei.beats.about.About
import com.weiwei.beats.about.FeedbackAdapter
import com.weiwei.beats.about.FollowAdapter
import com.weiwei.beats.about.TrafficAdapter
import com.weiwei.beats.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private var banqiaoList = ArrayList<About>()
    private var trafficList = ArrayList<About>()
    private var feedbackList = ArrayList<About>()
    private var followList = ArrayList<About>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)


        //configure the recyclerView
        val layoutManager_banqiao = LinearLayoutManager(context)
        val layoutManager_traffic = LinearLayoutManager(context)
        val layoutManager_feedback = LinearLayoutManager(context)
        val layoutManager_follow = LinearLayoutManager(context)


        binding.banqiaoList.layoutManager = layoutManager_banqiao
        binding.trafficList.layoutManager = layoutManager_traffic
        binding.feedbackList.layoutManager = layoutManager_feedback
        binding.followList.layoutManager = layoutManager_follow

        val adapter_banqiao = FeedbackAdapter(banqiaoList) //pass by reference
        val adapter_traffic = TrafficAdapter(trafficList) //pass by reference
        val adapter_feedback = FeedbackAdapter(feedbackList) //pass by reference 
        val adapter_follow = FollowAdapter(followList) //pass by reference 

        binding.banqiaoList.adapter = adapter_banqiao
        binding.trafficList.adapter = adapter_traffic
        binding.feedbackList.adapter = adapter_feedback
        binding.followList.adapter = adapter_follow

        binding.banqiaoList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))  //add a divider line
        binding.trafficList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))  //add a divider line
        binding.feedbackList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))  //add a divider line
        binding.followList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))  //add a divider line

        //setup our initial data
        initList()

        return binding.root
    }

    private fun initList(){
        banqiaoList.add(About(R.drawable.ic_location,"Banqiao Landmarks to Visit","https://chickenfun.com.tw/banqiao-tour/"))
        banqiaoList.add(About(R.drawable.ic_souvenir,"Banqiao souveniers","https://www.shopback.com.tw/blog/the-best-gifts-in-banqiao-station"))

        trafficList.add(About(R.drawable.ic_bus,"Bus","https://icypenguin.pixnet.net/blog/post/41636276"))
        trafficList.add(About(R.drawable.ic_subway,"Metro","https://www.metro.taipei/cp.aspx?n=91974F2B13D997F1"))
        trafficList.add(About(R.drawable.ic_train,"Train","https://www.railway.gov.tw/tra-tip-web/tip/tip00H/tipH41/viewStaInfo/1020"))
        trafficList.add(About(R.drawable.ic_high_speed_train,"Highspeed Railway","https://www.thsrc.com.tw/ArticleContent/e6e26e66-7dc1-458f-b2f3-71ce65fdc95f"))


        feedbackList.add(About(R.drawable.ic_play_store,"Rate us on Google Play","http://google.com/search?client=safari&rls=en&q=板橋&ie=UTF-8&oe=UTF-8"))
        feedbackList.add(About(R.drawable.ic_feedback,"Tell us your feedback","https://docs.google.com/forms/d/e/1FAIpQLSeaoQxLXw_wQrKPgbddOaiJEDrA9s4Md8S7bgjqFMNTABsw-Q/viewform?usp=sf_link"))


        followList.add(About(R.drawable.ic_twitter,"Twitter","https://twitter.com/hashtag/板橋區"))
        followList.add(About(R.drawable.ic_facebook,"Facebook","https://zh-tw.facebook.com/iamBanqiao"))
        followList.add(About(R.drawable.ic_instagram,"Instagram","https://www.instagram.com/explore/tags/板橋/"))
    }

}