package com.example.sejonggoodsmallproject.ui.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.data.model.FindEmailPost
import com.example.sejonggoodsmallproject.databinding.FragmentFindEmailBinding
import com.example.sejonggoodsmallproject.databinding.FragmentLoginBinding
import com.example.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import com.example.sejonggoodsmallproject.util.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FindEmailFragment : Fragment() {
    private var _binding : FragmentFindEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFindEmailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFindEmailBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this).commit()

            requireActivity().onBackPressed()
        }

        binding.btnFindEmailComplete.setOnClickListener {
            val username = binding.etFindEmailName.text.toString()
            val birth = binding.etFindEmailBirth.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val response = retrofitService.findEmail(FindEmailPost(username, birth))

                withContext(Dispatchers.Main) {
                    binding.tvFindEmallResult.text = response.body()?.email
                }
            }
        }

        binding.btnFindPasswordComplete.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.horizon_enter_front,0)
                .add(R.id.init_container, FindPasswordFragment(),"backStack")
                .addToBackStack("backStack")
                .commitAllowingStateLoss()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}