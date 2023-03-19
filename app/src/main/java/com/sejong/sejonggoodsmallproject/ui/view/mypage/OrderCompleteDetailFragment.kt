package com.sejong.sejonggoodsmallproject.ui.view.mypage

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.sejong.sejonggoodsmallproject.R
import com.sejong.sejonggoodsmallproject.data.model.OrderListResponse
import com.sejong.sejonggoodsmallproject.databinding.FragmentOrderCompleteDetailBinding
import com.sejong.sejonggoodsmallproject.ui.view.home.MainActivity
import com.sejong.sejonggoodsmallproject.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class OrderCompleteDetailFragment : Fragment() {
    private var _binding : FragmentOrderCompleteDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderCompleteDetailAdapter: OrderCompleteDetailListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrderCompleteDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        binding.btnOrderCompleteDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, R.anim.horizon_exit_front)
                .remove(this)
                .commit()

            requireActivity().onBackPressed()
        }

        setRv()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setRv() {
        val response = arguments?.getSerializable("response") as OrderListResponse

        val createStr = response.createdAt

        val cal = Calendar.getInstance()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val date: Date = df.parse(createStr)
        cal.time = date
        cal.add(Calendar.HOUR_OF_DAY,9)

        val reDate = df.format(cal.time).toString().replace("-", ".").replace('T', ' ')

        binding.tvItemOrderCompleteDetailDate.text = "주문일자 : $reDate"

        var cal2 = cal
        cal2.add(Calendar.DATE, 2)

        val reDate2 = df.format(cal2.time).toString().replace("-", ".").replace('T', ' ')

        binding.tvOrderDeadline.text = "마감일자 : $reDate2"

        orderCompleteDetailAdapter = OrderCompleteDetailListAdapter(requireContext(), response, viewModel)

        binding.rvOrderCompleteDetail.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderCompleteDetailAdapter
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}