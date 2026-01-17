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
import com.heartopia.timer.databinding.FragmentCodesBinding

class CodesFragment : Fragment() {

    private var _binding: FragmentCodesBinding? = null
    private val binding get() = _binding!!
    
    private val CODES_URL = "https://www.heartopia.gg/codes"
    
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (_binding?.codesWebView?.canGoBack() == true) {
                _binding?.codesWebView?.goBack()
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
        _binding = FragmentCodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupWebView()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
    
    private fun setupWebView() {
        binding.codesWebView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
            setSupportMultipleWindows(false)
        }
        
        binding.codesWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.loadingProgressBar.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.GONE
            }
            
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.loadingProgressBar.visibility = View.GONE
            }
            
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (request?.isForMainFrame == true) {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.codesWebView.visibility = View.GONE
                }
            }
        }
        
        binding.codesWebView.loadUrl(CODES_URL)
    }
    
    fun refreshCodes() {
        binding.codesWebView.reload()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding.codesWebView.destroy()
        _binding = null
    }
}
