package com.sdm.mediacard.domain.thirdparty.wallet

import com.sdei.domaindata.domain.thirdparty.wallet.WalletStatus

interface WalletResponseCallback{
        fun walletStatusCallback(walletStatus: WalletStatus)
    }
