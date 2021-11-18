package com.prinkal.searchableapp.ui.view.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Process
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prinkal.searchableapp.R
import com.prinkal.searchableapp.data.model.SampleData
import com.prinkal.searchableapp.ui.adapter.MainAdapter
import com.prinkal.searchableapp.utils.KEY_QUERY
import com.prinkal.searchableapp1.IIPCExample
import kotlinx.android.synthetic.main.fragment_list.*

class AidlFragment : Fragment(), ServiceConnection, View.OnClickListener {

    private var iRemoteService: IIPCExample? = null
    private var connected = false

    private lateinit var adapter: MainAdapter

    private var queryStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryStr = arguments?.getString(KEY_QUERY)

    }

    companion object {
        fun newInstance(queryStr: String?): AidlFragment {
            val args = Bundle()
            val fragment = AidlFragment()
            args.putString(KEY_QUERY, queryStr)
            fragment.arguments = args
            return fragment
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
        connected = if (connected) {
            disconnectToRemoteService()
            false
        } else {
            connectToRemoteService()
            true
        }
    }

    override fun onClick(v: View?) {
        connected = if (connected) {
            disconnectToRemoteService()
            false
        } else {
            connectToRemoteService()
            true
        }
    }

    private fun connectToRemoteService() {
        val intent = Intent("aidlexample")
        val pack = IIPCExample::class.java.`package`
        pack?.let {
            intent.setPackage(pack.name)
            activity?.applicationContext?.bindService(
                intent, this, Context.BIND_AUTO_CREATE
            )
        }
    }

    private fun disconnectToRemoteService() {
        if (connected) {
            activity?.applicationContext?.unbindService(this)
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.e("Prinkal", "onserviceconnected")
        // Gets an instance of the AIDL interface named IIPCExample,
        // which we can use to call on the service
        iRemoteService = IIPCExample.Stub.asInterface(service)
        iRemoteService?.setDisplayedValue(
            context?.packageName,
            Process.myPid(),
            queryStr
        )

        val resultList =
            iRemoteService!!.getSearchedResult(context?.packageName, Process.myPid(), queryStr)
        updateUIWithData(resultList)
        connected = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Toast.makeText(context, "IPC server has disconnected unexpectedly", Toast.LENGTH_LONG)
            .show()
        iRemoteService = null
        connected = false
    }

    private fun updateUIWithData(resultList: List<SampleData>) {
        val adapter =
            MainAdapter(resultList)
        recyclerView.adapter = adapter
    }
}
