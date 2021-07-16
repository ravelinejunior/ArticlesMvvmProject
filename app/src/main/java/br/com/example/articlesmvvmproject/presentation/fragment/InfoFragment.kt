package br.com.example.articlesmvvmproject.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.example.articlesmvvmproject.MainActivity
import br.com.example.articlesmvvmproject.R
import br.com.example.articlesmvvmproject.databinding.FragmentInfoBinding
import br.com.example.articlesmvvmproject.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel

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
        viewModel = (activity as MainActivity).viewModel

        //recupera o bundle via nav graph
        val args: InfoFragmentArgs by navArgs()
        val article = args.selectedArticle

        CoroutineScope(Dispatchers.Main).launch {
            binding.webViewInfoFragment.apply {
                webViewClient = WebViewClient()
                if (article.url?.isNotEmpty()!!)
                    loadUrl(article.url)
                visibility = VISIBLE
            }

            binding.progressBarInfoFragment.visibility = View.GONE
        }
        binding.fabInfoFragment.setOnClickListener {
            viewModel.saveArticle(article)
            showSnackBar("Successfully saved.")
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setTextColor(resources.getColor(R.color.white)).show()
    }


}