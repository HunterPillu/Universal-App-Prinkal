package com.prinkal.searchableapp.ui.view.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prinkal.searchableapp.R
import com.prinkal.searchableapp.data.model.SampleData
import com.prinkal.searchableapp.ui.adapter.MainAdapter
import com.prinkal.searchableapp.utils.DATA
import com.prinkal.searchableapp.utils.KEY_QUERY
import com.prinkal.searchableapp.utils.PACKAGE_NAME
import com.prinkal.searchableapp.utils.PID
import kotlinx.android.synthetic.main.fragment_list.*


class MessengerFragment : Fragment(), ServiceConnection, View.OnClickListener {

    // Is bound to the service of remote process
    private var isBound: Boolean = false

    // Messenger on the server
    private var serverMessenger: Messenger? = null

    // Messenger on the client
    private var clientMessenger: Messenger? = null

    private var queryStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryStr = arguments?.getString(KEY_QUERY)

    }

    companion object {
        fun newInstance(queryStr: String?): MessengerFragment {
            val args = Bundle()
            val fragment = MessengerFragment()
            args.putString("query", queryStr)
            fragment.arguments = args
            return fragment
        }
    }

    // Handle messages from the remote service
    private var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            // Update UI with remote process info
            val bundle = msg.data
            val searchResultStr = bundle.getString(DATA)

            val myType = object : TypeToken<List<SampleData>>() {}.type
            val searchResult = Gson().fromJson<List<SampleData>>(searchResultStr, myType)

            updateUIWithData(searchResult)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isBound) {
            doUnbindService()
        } else {
            doBindService()
        }
    }

    override fun onClick(v: View?) {
        if (isBound) {
            doUnbindService()
        } else {
            doBindService()
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        serverMessenger = Messenger(service)
        // Ready to send messages to remote service
        sendMessageToServer()
    }

    override fun onServiceDisconnected(className: ComponentName) {
        //clearUI()
        serverMessenger = null
    }

    private fun updateUIWithData(resultList: List<SampleData>) {
        val adapter =
            MainAdapter(resultList)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        doUnbindService()
        super.onDestroy()
    }

    private fun doBindService() {
        clientMessenger = Messenger(handler)
        Intent("messengerexample").also { intent ->
            intent.`package` = "com.prinkal.searchableapp1"
            activity?.applicationContext?.bindService(intent, this, Context.BIND_AUTO_CREATE)
        }
        isBound = true
    }

    private fun doUnbindService() {
        if (isBound) {
            activity?.applicationContext?.unbindService(this)
            isBound = false
        }
    }

    private fun sendMessageToServer() {
        if (!isBound) return
        val message = Message.obtain(handler)
        val bundle = Bundle()
        bundle.putString(KEY_QUERY, queryStr)
        bundle.putString(DATA, queryStr)
        bundle.putString(PACKAGE_NAME, context?.packageName)
        bundle.putInt(PID, Process.myPid())
        message.data = bundle
        message.replyTo =
            clientMessenger // we offer our Messenger object for communication to be two-way
        try {
            serverMessenger?.send(message)
        } catch (e: RemoteException) {
            e.printStackTrace()
        } finally {
            message.recycle()
        }
    }
}
