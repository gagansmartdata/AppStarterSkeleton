package com.sdei.domaindata.data.thirdparty.metamask

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import timber.log.Timber
import java.lang.ref.WeakReference
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap

class BridgeServer(moshi: Moshi) : WebSocketServer(InetSocketAddress(PORT)) {

    private val adapter = moshi.adapter<Map<String, Any>>(
        Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            Any::class.java
        )
    )


    private val pubs: MutableMap<String, MutableList<WeakReference<WebSocket>>> = ConcurrentHashMap()
    private val pubsLock = Any()
    private val pubsCache: MutableMap<String, String?> = ConcurrentHashMap()

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        Timber.tag("#####").d("onOpen: " + conn?.remoteSocketAddress?.address?.hostAddress)
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        Timber.tag("#####").d("onClose: " + conn?.remoteSocketAddress?.address?.hostAddress)
        conn?.let { cleanUpSocket(it) }
    }
    //0x497f8aFC8Ba46508DeA159Ef8fb03a8ef536d4e8
    override fun onMessage(conn: WebSocket?, message: String?) {
        Timber.tag("#####").d("Message: " + message)
        try {
            conn ?: error("Unknown socket")
            message?.also {
                val msg = adapter.fromJson(it) ?: error("Invalid message")
                val type: String = msg["type"] as String? ?: error("Type not found")
                val topic: String = msg["topic"] as String? ?: error("Topic not found")
                when (type) {
                    "pub" -> {
                        var sendMessage = false
                        pubs[topic]?.forEach { r ->
                            r.get()?.apply {
                                send(message)
                                sendMessage = true
                            }
                        }
                        if (!sendMessage) {
                            Timber.tag("#####").d("Cache message: " + message)
                            pubsCache[topic] = message
                        }
                    }
                    "sub" -> {
                        pubs.getOrPut(topic, { mutableListOf() }).add(WeakReference(conn))
                        pubsCache[topic]?.let { cached ->
                            Timber.tag("#####").d("Send cached: " + cached)
                            conn.send(cached)
                        }
                    }
                    "ack" -> {
                        Timber.tag("#####").d("Send ack")
                    }
                    else -> error("Unknown type")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStart() {
        Timber.tag("#####").d("Server started")
        connectionLostTimeout = 0
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        Timber.tag("#####").d("onError")
        ex?.printStackTrace()
        conn?.let { cleanUpSocket(it) }
    }

    private fun cleanUpSocket(conn: WebSocket) {
        synchronized(pubsLock) {
            pubs.forEach {
                it.value.removeAll { r -> r.get().let { v -> v == null || v == conn } }
            }
        }
    }

    companion object {
        const val PORT = 8887//5000 + Random().nextInt(60000)
    }
}