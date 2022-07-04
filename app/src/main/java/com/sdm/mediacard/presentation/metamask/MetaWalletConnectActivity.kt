package com.sdm.mediacard.presentation.metamask

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.sdei.base.BaseActivity
import com.sdei.domaindata.data.thirdparty.metamask.MetaMaskWalletHelperImpl
import com.sdei.domaindata.domain.thirdparty.wallet.WalletRepository
import com.sdei.domaindata.domain.thirdparty.wallet.WalletStatus
import com.sdm.mediacard.R
import com.sdm.mediacard.databinding.ActivityMetaMaskBinding
import com.sdm.mediacard.domain.thirdparty.wallet.WalletResponseCallback
import com.sdm.mediacard.utils.uiScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MetaWalletConnectActivity : BaseActivity<ActivityMetaMaskBinding>(), WalletResponseCallback {

    override val layoutId: Int
        get() = R.layout.activity_meta_mask
    override var binding: ActivityMetaMaskBinding
        get() = setUpBinding()
        set(value) {}

    //can use DI to inject this, in future.
    private val walletHelper: WalletRepository = MetaMaskWalletHelperImpl(this)

    override fun onCreate() {
        walletHelper.addCallBack(this)
        walletHelper.init()

        binding.btnStartTrans.setOnClickListener {

            val txRequest = System.currentTimeMillis()
            walletHelper.sendTransaction(
                txRequest,
                "0x497f8aFC8Ba46508DeA159Ef8fb03a8ef536d4e8",
                "0x497f8aFC8Ba46508DeA159Ef8fb03a8ef536d4e8",
                null,
                null,
                null,
                "0x5AF3107A4000"
            )
        }

        binding.btnConnect.setOnClickListener {
            walletHelper.connect()
        }
        binding.btnDisconnect.setOnClickListener {
            walletHelper.disconnect()
        }
    }



    override fun onDestroy() {
        walletHelper.onDestroy()
        super.onDestroy()
    }

    override fun walletStatusCallback(walletStatus: WalletStatus) {
        when (walletStatus) {
            is WalletStatus.Approved        -> {
                sessionApproved()
            }
            is WalletStatus.Connected       -> {
                sessionConnected()
            }
            is WalletStatus.DisConnected    -> {
                sessionDisconnected()
            }
            is WalletStatus.Closed          -> {
                sessionClosed()
            }
            is WalletStatus.TransactionDone -> {
                transactionDone(walletStatus.transHex)
            }
            is WalletStatus.TransactionError -> {
                transactionError(walletStatus.error)
            }
            else                            -> {
                sessionClosed()
            }
        }
    }

    private fun sessionConnected() {
        lifecycleScope.uiScope {
            binding.btnConnect.visibility = View.GONE
            binding.btnStartTrans.visibility = View.VISIBLE
            binding.btnDisconnect.visibility = View.VISIBLE
        }
    }

    private fun sessionDisconnected() {
        lifecycleScope.uiScope {
            binding.btnConnect.visibility = View.VISIBLE
            binding.btnStartTrans.visibility = View.GONE
            binding.btnDisconnect.visibility = View.GONE
        }
    }
    private fun sessionClosed() {
        lifecycleScope.uiScope {
            //binding.btnConnect.visibility = View.VISIBLE
            //binding.btnStartTrans.visibility = View.GONE
            //binding.btnDisconnect.visibility = View.GONE
        }
    }
    private fun transactionDone(transData: Any?) {
        lifecycleScope.uiScope {
            transData?.let {
                binding.dataToShow = "Transaction Hex is- $transData"
            } ?: run { binding.dataToShow = "Transaction Cancelled!"}
        }
    }

    private fun transactionError(error: String?) {
        lifecycleScope.uiScope {
            error?.let {
                binding.dataToShow = it
            } ?: run { binding.dataToShow = "Transaction Cancelled!"}
        }
    }

    private fun sessionApproved() {
        lifecycleScope.uiScope {
            binding.btnConnect.visibility = View.GONE
            binding.btnStartTrans.visibility = View.VISIBLE
            binding.btnDisconnect.visibility = View.VISIBLE
        }
    }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, MetaWalletConnectActivity::class.java))
        }
    }
}

