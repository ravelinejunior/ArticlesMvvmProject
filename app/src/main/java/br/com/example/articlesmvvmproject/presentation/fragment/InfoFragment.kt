package br.com.example.articlesmvvmproject.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.example.articlesmvvmproject.R
import br.com.example.articlesmvvmproject.databinding.FragmentInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoFragment : Fragment() {
    lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        //recupera o bundle via nav graph
        val args: InfoFragmentArgs by navArgs()
        val article = args.selectedArticle

        CoroutineScope(Dispatchers.Main).launch {
            binding.webViewInfoFragment.apply {
                webViewClient = WebViewClient()
                if (article.url.isNotEmpty())
                    loadUrl(article.url)
                visibility = VISIBLE
            }

            binding.progressBarInfoFragment.visibility = View.GONE

        }
    }

}