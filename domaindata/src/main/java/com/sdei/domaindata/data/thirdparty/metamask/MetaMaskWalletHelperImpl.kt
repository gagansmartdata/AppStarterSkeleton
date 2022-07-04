package com.sdei.domaindata.data.thirdparty.metamask

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.sdei.domaindata.common.toHexString
import com.sdei.domaindata.domain.thirdparty.wallet.WalletRepository
import com.sdei.domaindata.domain.thirdparty.wallet.WalletStatus
import com.sdm.mediacard.domain.thirdparty.wallet.WalletResponseCallback
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.walletconnect.Session
import org.walletconnect.impls.*
import org.walletconnect.nullOnThrow
import timber.log.Timber
import java.io.File
import java.util.*


class MetaMaskWalletHelperImpl(val context: Context) : WalletRepository {

    private lateinit var client: OkHttpClient
    private lateinit var moshi: Moshi
    private lateinit var storage: WCSessionStore
    private lateinit var bridge: BridgeServer
    private lateinit var session: WCSession
    private lateinit var config: Session.Config


    private val metaMaskCallback = object : Session.Callback {
        override fun onMethodCall(call: Session.MethodCall) {

        }

        override fun onStatus(status: Session.Status) {
            when (status) {
                is Session.Status.Approved     -> sessionCallback?.walletStatusCallback(WalletStatus.Approved)
                is Session.Status.Closed       -> sessionCallback?.walletStatusCallback(WalletStatus.Closed)
                is Session.Status.Connected    -> sessionCallback?.walletStatusCallback(WalletStatus.Connected)
                is Session.Status.Disconnected -> sessionCallback?.walletStatusCallback(WalletStatus.DisConnected)
                is Session.Status.Error        -> sessionCallback?.walletStatusCallback(WalletStatus.Error)
            }
        }
    }


    override fun init() {
        moshi = Moshi.Builder().build()
        client = OkHttpClient.Builder().build()
        bridge = BridgeServer(moshi)
        bridge.start()
        storage = FileWCSessionStore(
            File(
                context.cacheDir,
                "session_store.json"
            ).apply { createNewFile() }, moshi
        )

       getSessionFromJson()
    }

    override fun connect() {
        resetSession()

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(toWcUri())
        context.startActivity(i)
    }

    override fun disconnect() {
        session.kill()
    }

    private val serverurl = "wss://bridge.aktionariat.com:${BridgeServer.PORT}"

    override fun resetSession() {
        nullOnThrow { session }?.clearCallbacks()
        val key = ByteArray(32).also { Random().nextBytes(it) }.toHexString()
        val topic = UUID.randomUUID().toString()
        config = Session.Config(topic, serverurl, key)
        session = WCSession(
            config,
            MoshiPayloadAdapter(moshi),
            storage,
            OkHttpTransport.Builder(client, moshi),
            Session.PeerMeta(
                name = "MetaMaskExample",
                url = "https://www.google.com",
                description = "Meta Mask Example",
                icons = listOf("https://source.unsplash.com/user/c_v_r/100x100")
            )
        )
        addCallBack()
        session.offer()
    }


    private fun
            sessionFromConfig(config: Session.Config):
            WCSession {
        val session =
            WCSession(
                config,
                MoshiPayloadAdapter(moshi),
                storage,
                OkHttpTransport.Builder(client, moshi),
                Session.PeerMeta(
                    name = "MetaMaskExample",
                    url = "https://www.google.com",
                    description = "Meta Mask Example",
                    icons = listOf("https://source.unsplash.com/user/c_v_r/100x100")
                )
            )
        session.addCallback(metaMaskCallback)
        return session
    }


    private fun getSessionFromJson(){
        val sessionInfo = storage.list().firstOrNull()
        if (sessionInfo != null) {
            session = sessionFromConfig(sessionInfo.config).apply {
                init()
            }

            approveAccounts()?.first()?.let {
                Timber.i("session is there", it)
                config = storage.list().first().config
            }
        }
    }

    private fun addCallBack() {
        session.addCallback(metaMaskCallback)
    }

    private fun removeCallBack() {
        session.removeCallback(metaMaskCallback)
    }

    private fun toWcUri(): String {
        return config.toWCUri()
    }

    private fun approveAccounts(): List<String>? {
        return session.approvedAccounts()
    }

    private var idg: Long? = 0
    override fun sendTransaction(
        id: Long,
        from: String,
        to: String,
        nonce: String?,
        gasPrice: String?,
        gasLimit: String?,
        value: String
    ) {
        idg = id
        session.performMethodCall(
            Session.MethodCall.SendTransaction(
                id,
                approveAccounts()?.first() ?: "account not there",
                "0x8287Da780378e425fEFD7C35645f7FA11f85F2C6",
                null,
                null,
                null,
                "0x5AF3107A4000",
                ""
            )
        ) {
            if (it.id == idg) {
                idg = null
                it.result?.let { result->
                    sessionCallback?.walletStatusCallback(WalletStatus.TransactionDone(result))
                } ?: kotlin.run {
                    sessionCallback?.walletStatusCallback(WalletStatus.TransactionError(it.error?.message))
                }
            }
        }

        openWalletApp()
    }


    private var sessionCallback: WalletResponseCallback? = null
    override fun addCallBack(sessionCallback: WalletResponseCallback) {
        sessionCallback.let {
            this.sessionCallback = it
        }
    }

    override fun onDestroy() {
        removeCallBack()
    }

    private fun openWalletApp() {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("wc:")
        context.startActivity(i)
    }
}
