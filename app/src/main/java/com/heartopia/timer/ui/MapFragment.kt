package com.heartopia.timer.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.heartopia.timer.R
import com.heartopia.timer.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    
    private val MAP_URL = "https://ytbasdrick-bot.github.io/Maptopia/indexfr.html?auto=1"
    
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (_binding?.mapWebView?.canGoBack() == true) {
                _binding?.mapWebView?.goBack()
            } else {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupWebView()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
    
    private fun setupWebView() {
        binding.mapWebView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
            setSupportMultipleWindows(false)
        }
        
        binding.mapWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.mapProgressBar.visibility = View.VISIBLE
                binding.mapErrorTextView.visibility = View.GONE
            }
            
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.mapProgressBar.visibility = View.GONE
            }
            
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (request?.isForMainFrame == true) {
                    binding.mapProgressBar.visibility = View.GONE
                    binding.mapErrorTextView.visibility = View.VISIBLE
                    binding.mapWebView.visibility = View.GONE
                }
            }
        }
        
        binding.mapWebView.loadUrl(MAP_URL)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapWebView.destroy()
        _binding = null
    }
}
