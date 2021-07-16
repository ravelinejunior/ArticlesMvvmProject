package br.com.example.articlesmvvmproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.articlesmvvmproject.MainActivity
import br.com.example.articlesmvvmproject.R
import br.com.example.articlesmvvmproject.databinding.FragmentSavedBinding
import br.com.example.articlesmvvmproject.presentation.adapter.NewsAdapter
import br.com.example.articlesmvvmproject.presentation.viewmodel.NewsViewModel

class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var articleAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        articleAdapter = (activity as MainActivity).newsAdapter

        articleAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }

            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment, bundle
            )

        }

        initRecyclerView()

        viewModel.getSavedArticles().observe(viewLifecycleOwner,{
            articles ->
            articleAdapter.differ.submitList(articles.reversed())
        })

    }

    private fun initRecyclerView() {

        binding.recyclerViewInfoFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter

        }


    }

}