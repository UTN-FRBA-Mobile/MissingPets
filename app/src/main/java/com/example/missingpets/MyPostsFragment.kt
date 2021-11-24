package com.example.missingpets


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.missingpets.databinding.FragmentMyPostsBinding



class MyPostsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMyPostsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMyMissingPost1.setOnClickListener{
            val bundle = bundleOf("action" to "0")
            findNavController().navigate(R.id.action_myPostsFragment_to_myMissingPostFragment,bundle)
        }

        binding.btnMyMissingPost2.setOnClickListener{
            val bundle = bundleOf("action" to "1")
            findNavController().navigate(R.id.action_myPostsFragment_to_myMissingPostFragment,bundle)
        }

    }

}