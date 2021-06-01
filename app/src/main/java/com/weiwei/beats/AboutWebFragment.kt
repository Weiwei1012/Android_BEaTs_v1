package com.weiwei.beats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.weiwei.beats.databinding.FragmentAboutWebBinding


class AboutWebFragment : Fragment() {

    private lateinit var binding: FragmentAboutWebBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_web, container, false)

        val args = AboutWebFragmentArgs.fromBundle(requireArguments())
        //binding.testText.text = args.selectedWeb
        binding.aboutWeb.loadUrl(args.selectedWeb)
        binding.aboutWeb.webViewClient = CallBack()

        return binding.root
    }

    class CallBack : WebViewClient()
    {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }

}