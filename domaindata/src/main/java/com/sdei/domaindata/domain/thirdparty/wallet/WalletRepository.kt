package com.sdei.domaindata.domain.thirdparty.wallet

import com.sdm.mediacard.domain.thirdparty.wallet.WalletResponseCallback

interface WalletRepository  {

    fun init()

    fun addCallBack(sessionCallback: WalletResponseCallback)
    /**
     * start with this function, followed by adding callback
     */
    fun connect()

    /**
     * disconnect connectivity in between
     */
    fun disconnect()

    fun resetSession()

    fun sendTransaction(id:Long,from:String,to:String,nonce:String?,gasPrice:String?,gasLimit:String?,value:String)

    fun onDestroy()
}