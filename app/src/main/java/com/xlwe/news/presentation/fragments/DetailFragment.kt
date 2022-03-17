package com.xlwe.news.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.xlwe.news.databinding.FragmentDetailBinding
import com.xlwe.news.presentation.viewmodels.MainViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        mainViewModel.saveArticle.observe(viewLifecycleOwner) {
            Glide.with(binding.image)
                .load(it.urlToImage)
                .into(binding.image)

            var content = ""

            for (i in it.content) {
                if (i != '[')
                    content += i
                else
                    break
            }

            content = content.replace("\n", "").replace("...", "")

            binding.title.text = it.title.replace("\n", "")
            binding.description.text = content
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = DetailFragment()
    }
}