package br.com.example.articlesmvvmproject.presentation.fragment

import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.articlesmvvmproject.MainActivity
import br.com.example.articlesmvvmproject.R
import br.com.example.articlesmvvmproject.data.util.Resource
import br.com.example.articlesmvvmproject.databinding.FragmentNewsBinding
import br.com.example.articlesmvvmproject.presentation.adapter.NewsAdapter
import br.com.example.articlesmvvmproject.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment() : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private val country = "br"
    private val mPage = 1

    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var page = 1
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //pegar os dois valores da injeção criada na mainAcitivity
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }

            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment, bundle
            )

        }

        initRecyclerView()
        viewNewsHeadlinesList()
        setSearchView()

    }

    private fun initRecyclerView() {
        binding.recyclerViewNewsFragment.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }

    }

    private fun viewNewsHeadlinesList() {
        viewModel.getNewsHeadlines(country, mPage)
        viewModel.newsHeadLines.observe(viewLifecycleOwner, { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    hideProgressBar()
                    //utilizar o diffUtil para preencher a lista com os dados da requisição
                    newsResponse.data?.let { news ->
                        newsAdapter.differ.submitList(news.articles.toList())
                        pages = if (news.totalResults % 20 == 0) {
                            news.totalResults / 20
                        } else {
                            news.totalResults / 20 + 1
                        }

                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    showSnackBar(newsResponse.message.toString())
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    //search
    private fun setSearchView() {
        binding.searchViewNewsFragmentId.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //setar função que observa a variavel com viewModel do searchview
                viewModel.getSearchNews(query.toString(), "br", 1)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //mainScope é utilizado para UI, quando for apresentar algum dado direto para o usuario
                MainScope().launch {

                    delay(3000L)
                    //setar função que observa a variavel com viewModel do searchview
                    viewModel.getSearchNews(newText.toString(), "br", 1)
                    viewSearchedNews()
                }

                return false
            }
        })

        //reiniciar adapter chamando o recycler view novamente
        binding.searchViewNewsFragmentId.setOnCloseListener {
            initRecyclerView()
            viewNewsHeadlinesList()
            false
        }

    }

    private fun viewSearchedNews() {

        viewModel.searchedNews.observe(viewLifecycleOwner, { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    hideProgressBar()
                    //utilizar o diffUtil para preencher a lista com os dados da requisição
                    newsResponse.data?.let { news ->
                        newsAdapter.differ.submitList(news.articles.toList())
                        pages = if (news.totalResults % 20 == 0) {
                            news.totalResults / 20
                        } else {
                            news.totalResults / 20 + 1
                        }

                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    showSnackBar(newsResponse.message.toString())
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        isLoading = true
        binding.progressBarNewsFragment.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.progressBarNewsFragment.visibility = View.GONE
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setTextColor(resources.getColor(R.color.white)).show()
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            //verificar se scroll do recyclerView foi tocado
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager =
                binding.recyclerViewNewsFragment.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            //recuperar a position do primeiro item que aparece apos ser scrollado
            val topPosition = layoutManager.findFirstVisibleItemPosition()
            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if (shouldPaginate) {
                page++
                viewModel.getNewsHeadlines(country, page)
                isScrolling = false
            }
        }
    }

    companion object {
        const val TAG = "TAG_INFO"
    }


}