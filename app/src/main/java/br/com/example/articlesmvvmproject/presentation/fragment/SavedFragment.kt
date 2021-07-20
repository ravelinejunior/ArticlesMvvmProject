package br.com.example.articlesmvvmproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.articlesmvvmproject.MainActivity
import br.com.example.articlesmvvmproject.R
import br.com.example.articlesmvvmproject.databinding.FragmentSavedBinding
import br.com.example.articlesmvvmproject.presentation.adapter.NewsAdapter
import br.com.example.articlesmvvmproject.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var articleAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedBinding.inflate(layoutInflater)
        viewModel = (activity as MainActivity).viewModel
        articleAdapter = (activity as MainActivity).newsAdapter

        articleAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }

            findNavController().navigate(
                R.id.action_savedFragment_to_infoFragment, bundle
            )

        }

        initRecyclerView()

        viewModel.getSavedArticles().observe(viewLifecycleOwner, { articles ->
            articleAdapter.differ.submitList(articles)
        })

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerViewInfoFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.absoluteAdapterPosition
            val article = articleAdapter.differ.currentList[position]
            viewModel.deleteArticle(article)
            Snackbar.make(view!!, "Deleted Successfully", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    viewModel.saveArticle(article)
                }.show()

        }

    }

    private fun initRecyclerView() {

        binding.recyclerViewInfoFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter

        }


    }

}