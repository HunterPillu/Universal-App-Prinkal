package com.prinkal.searchableapp.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prinkal.searchableapp.R
import com.prinkal.searchableapp.utils.KEY_QUERY
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), View.OnClickListener {
    private var queryStr: String? = null

    companion object {
        fun newInstance(queryStr: String?): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            args.putString(KEY_QUERY, queryStr)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryStr = arguments?.getString(KEY_QUERY)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateQueryString()
        bContentProvider.setOnClickListener(this)
        bAIDL.setOnClickListener(this)
        bMessenger.setOnClickListener(this)
        bBroadcast.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.e("prinkal", "clicked")
        when (v?.id) {
            R.id.bContentProvider -> {
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.main_container,
                    ContentProviderFragment.newInstance(queryStr),
                    "Content_Provider_Fragment_TAG"
                ).addToBackStack(null).commit()
            }
            R.id.bAIDL -> {
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.main_container,
                    AidlFragment.newInstance(queryStr),
                    "Aidl_Fragment_TAG"
                ).addToBackStack(null).commit()


            }
            R.id.bMessenger -> {

                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.main_container,
                    MessengerFragment.newInstance(queryStr),
                    "Messenger_Fragment_TAG"
                ).addToBackStack(null).commit()


            }
            R.id.bBroadcast -> {

            }
        }
    }

    private fun updateQueryString() {
        tvQuery.text = queryStr
        tvDesc.setText(R.string.query_updated)
    }

}

