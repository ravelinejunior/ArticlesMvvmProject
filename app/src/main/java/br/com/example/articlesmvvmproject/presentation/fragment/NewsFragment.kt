package br.com.example.articlesmvvmproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.articlesmvvmproject.MainActivity
import br.com.example.articlesmvvmproject.R
import br.com.example.articlesmvvmproject.data.util.Resource
import br.com.example.articlesmvvmproject.databinding.FragmentNewsBinding
import br.com.example.articlesmvvmproject.presentation.adapter.NewsAdapter
import br.com.example.articlesmvvmproject.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class NewsFragment() : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        binding = FragmentNewsBinding.bind(view)
        initRecyclerView()
        viewNewsHeadlinesList()

    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.recyclerViewNewsFragment.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }

    }

    private fun viewNewsHeadlinesList() {
        viewModel.getNewsHeadlines("br", 1)
        viewModel.newsHeadLines.observe(viewLifecycleOwner, { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    hideProgressBar()
                    //utilizar o diffUtil para preencher a lista com os dados da requisição
                    newsResponse.data?.let { news ->
                        newsAdapter.differ.submitList(news.articles.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    showSnackbar(newsResponse.message.toString())
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        binding.progressBarNewsFragment.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBarNewsFragment.visibility = View.GONE
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setTextColor(resources.getColor(R.color.white)).show()
    }


}