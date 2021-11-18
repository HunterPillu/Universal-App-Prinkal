package com.prinkal.searchableapp.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prinkal.searchableapp.R
import com.prinkal.searchableapp.data.model.SampleData
import com.prinkal.searchableapp.ui.adapter.MainAdapter
import com.prinkal.searchableapp.utils.*
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class ContentProviderFragment : Fragment(), View.OnClickListener {

    private var queryStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryStr = arguments?.getString(KEY_QUERY)

    }

    companion object {


        fun newInstance(queryStr: String?): ContentProviderFragment {
            val args = Bundle()
            val fragment = ContentProviderFragment()
            args.putString("query", queryStr)
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
        getData(queryStr, null)
    }

    private fun getData(selection: String?, selectionArgs: Array<String>?) {
        /**
         * Query to get the cursor from database
         */
        val cursor = requireActivity().contentResolver.query(
            CONTACTS,
            CONTACT_COLUMNS, selection, selectionArgs, null
        )
        Log.e("prinkal-detData", "first")
        if (cursor != null && cursor.count > 0) {
            //Log.e("prinkal-detData", Gson().toJson(cursor))
            if (cursor.moveToFirst()) {
                val contactList: ArrayList<SampleData> = ArrayList<SampleData>()
                while (!cursor.isAfterLast) {
                    val contact = SampleData(
                        cursor.getString(cursor.getColumnIndexOrThrow(ID)).toLong(),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString((cursor.getColumnIndexOrThrow(DESC))),
                        cursor.getString((cursor.getColumnIndexOrThrow(PHOTO_URL)))
                    )
                    contactList.add(contact)
                    cursor.moveToNext()
                }
                /**
                 * Prepare recycler view adapter and set adapter to recycler view
                 */
                val adapter =
                    MainAdapter(contactList)
                recyclerView.adapter = adapter
                Log.e("prinkal-data", contactList[0].title + "_______" + contactList.size)
            }
        } else Toast.makeText(requireContext(), "No Records to Display", Toast.LENGTH_SHORT).show()
        cursor?.close()
    }

    override fun onClick(v: View?) {

    }


}
