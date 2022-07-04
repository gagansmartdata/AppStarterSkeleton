package com.sdei.domaindata.domain.thirdparty.wallet

sealed interface WalletStatus{
        object Approved : WalletStatus
        object Closed : WalletStatus
        object Connected : WalletStatus
        object DisConnected : WalletStatus
        class TransactionError(val error: String?) : WalletStatus
        object Error : WalletStatus
        class TransactionDone(val transHex: Any?) : WalletStatus
    }