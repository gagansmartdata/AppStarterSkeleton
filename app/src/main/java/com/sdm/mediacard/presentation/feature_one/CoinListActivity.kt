//package com.sdm.mediacard.presentation.feature_one
//
//import android.content.Context
//import android.content.Intent
//import androidx.activity.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.sdm.mediacard.R
//import com.sdei.base.BaseActivity
//import com.sdei.base.InfiniteAdapter
//import com.sdm.mediacard.databinding.ActivityCoinListBinding
//import com.sdm.mediacard.databinding.InflateCoinListBinding
//import com.sdm.mediacard.utils.navigator.Navigator
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class CoinListActivity : BaseActivity<ActivityCoinListBinding>() {
//    override val layoutId: Int
//        get() = R.layout.activity_coin_list
//    override var binding: ActivityCoinListBinding
//        get() = setUpBinding()
//        set(value) {}
//
//    //using lazy loading to load the view model instance
//    private val viewModel:CoinListViewModel by viewModels()
//
//    //example navigator use
//   private val navigator by lazy {  Navigator(findNavController(R.id.nav_controller_view_tag))}
//
//    override fun onCreate() {
//
//        binding.coinList.layoutManager = LinearLayoutManager(this)
//
//        lifecycleScope.launchWhenStarted {
//            viewModel.state().collect{
//
//                it.data?.let {dataList->
//                    val adapter = object : InfiniteAdapter<InflateCoinListBinding>() {
//                        override fun bindData(position: Int, myViewHolderG: MyViewHolderG?) {
//                            myViewHolderG?.binding?.coinName = dataList[position].name
//                        }
//                        override val count: Int
//                            get() = dataList.size
//
//                        override fun getInflateLayout(type: Int): Int {
//                            return R.layout.inflate_coin_list
//                        }
//                    }
//                    binding.coinList.adapter = adapter
//                }
//
//            }
//        }
//    }
//
//    companion object{
//        fun start(context: Context){
//            context.startActivity(Intent(context, CoinListActivity::class.java))
//        }
//    }
//}