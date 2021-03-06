package com.example.missingpets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.missingpets.databinding.FragmentMainBinding
import com.example.missingpets.viewModels.UserProfileViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val user: UserProfileViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMissingPets.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_missingFragment, null))
        binding.btnLogin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_loginFragment2, null))
        //binding.btnSettings.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_configurationFragment, null))
        binding.btnAboutAs.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_aboutUsFragment, null))
        binding.btnPetsForAdoption.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_adoptableFragment, null))
        binding.btnUploadPets.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_newPostFragment, null))
        if(user.id>=0){
            binding.btnMyPost.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_myPostsFragment, null))
        }else {
            binding.btnMyPost.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_loginFragment2, null))
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}