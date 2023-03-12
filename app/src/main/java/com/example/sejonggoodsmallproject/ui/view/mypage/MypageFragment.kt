package com.example.sejonggoodsmallproject.ui.view.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejonggoodsmallproject.R
import com.example.sejonggoodsmallproject.databinding.FragmentMypageBinding
import com.example.sejonggoodsmallproject.ui.view.home.LoginDialog
import com.example.sejonggoodsmallproject.ui.view.home.MainActivity
import com.example.sejonggoodsmallproject.ui.view.login.InitActivity
import com.example.sejonggoodsmallproject.ui.view.login.LogoutDialog
import com.example.sejonggoodsmallproject.ui.view.login.Terms2Fragment
import com.example.sejonggoodsmallproject.ui.view.login.TermsFragment
import com.example.sejonggoodsmallproject.ui.view.search.SearchFragment
import com.example.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import com.example.sejonggoodsmallproject.util.MyApplication
import kotlinx.android.synthetic.main.dialog_login_confirm.*
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MypageFragment : Fragment() {
    private var _binding : FragmentMypageBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteThumbnailAdapter: FavoriteThumbnailAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        binding.btnMypageBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this)
                .commit()

            requireActivity().onBackPressed()
        }

        if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {
            binding.tvMypageLogout.text = "로그인"
            binding.tvMypageLogout.setOnClickListener {
                setLoginDialog()
            }
        } else {
            binding.tvMypageLogout.setOnClickListener {
                setLogoutDialog()
            }
        }

        binding.btnOrderList.setOnClickListener {
            if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {
                setLoginDialog()
            } else {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.horizon_enter_front, 0)
                    .add(R.id.main_container, OrderCompleteListFragment(), "backStack")
                    .addToBackStack("backStack")
                    .commitAllowingStateLoss()
            }
        }
        binding.tvMypageBoard.setOnClickListener {
            Toast.makeText(requireContext(), "업데이트 준비중입니다.", Toast.LENGTH_SHORT).show()
        }
        binding.tvMypageTerms1.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.horizon_enter_front, 0)
                .add(R.id.main_container, Terms2Fragment(), "backStack")
                .addToBackStack("backStack")
                .commitAllowingStateLoss()
        }
        binding.tvMypageTerms2.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.horizon_enter_front, 0)
                .add(R.id.main_container, TermsFragment(), "backStack")
                .addToBackStack("backStack")
                .commitAllowingStateLoss()
        }
    }

    private fun setLoginDialog() {
        val loginDialog = LoginDialog(requireContext())

        loginDialog.showDialog()

        loginDialog.dialog.btn_dialog_login.setOnClickListener {
            startActivity(Intent(requireActivity(), InitActivity::class.java))
            requireActivity().finish()
        }

        loginDialog.dialog.btn_dialog_login_close.setOnClickListener {
            loginDialog.dialog.dismiss()
        }
    }

    fun setLogoutDialog() {
        val logoutDialog = LogoutDialog(requireContext())
        logoutDialog.showDialog()

        logoutDialog.dialog.btn_dialog_logout.setOnClickListener {
            MyApplication.prefs.setString("accessToken","Not Login State")

            requireActivity().finish()
            startActivity(Intent(requireActivity(), InitActivity::class.java))

            logoutDialog.dialog.dismiss()
        }

        logoutDialog.dialog.btn_dialog_logout_close.setOnClickListener {
            logoutDialog.dialog.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        if (MyApplication.prefs.getString("accessToken","") == "Not Login State") {

        } else {
            setRvFavoriteThumbnail()
            setOrderHistory()
        }
    }

    private fun setOrderHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val allOrderList = viewModel.getOrderList()
            val orderVisitList = allOrderList.filter {
                it.orderMethod == "pickup"
            }
            val orderDeliveryList = allOrderList.filter {
                it.orderMethod == "delivery"
            }

            var orderVisitCnt = 0
            orderVisitList.forEach {
                orderVisitCnt += it.orderItems.size
            }

            var orderDeliveryCnt = 0
            orderDeliveryList.forEach {
                orderDeliveryCnt += it.orderItems.size
            }

            withContext(Dispatchers.Main) {
                binding.tvMypageOrderVisitCnt.text = orderVisitCnt.toString()
                binding.tvMypageOrderDeliveryCnt.text = orderDeliveryCnt.toString()
            }
        }
    }

    private fun setRvFavoriteThumbnail() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = viewModel.getFavoriteList()

            withContext(Dispatchers.Main) {
                favoriteThumbnailAdapter = FavoriteThumbnailAdapter(requireContext(), response)

                binding.rvFavoriteThumbnail.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                    adapter = favoriteThumbnailAdapter
                }
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}