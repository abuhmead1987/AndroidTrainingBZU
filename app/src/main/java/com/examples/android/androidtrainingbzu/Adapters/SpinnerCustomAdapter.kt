package com.examples.android.androidtrainingbzu.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.examples.android.androidtrainingbzu.R

class SpinnerCustomAdapter(
    var context: Context,
    var flags: IntArray,
    var countryNames: Array<String>,
) : BaseAdapter() {
    var inflter: LayoutInflater
    override fun getCount(): Int {
        return flags.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        view = inflter.inflate(R.layout.custom_spinner_items, null)
        val icon = view.findViewById<View>(R.id.imageView) as ImageView
        val names = view.findViewById<View>(R.id.textView) as TextView
        icon.setImageResource(flags[i])
        names.text = countryNames[i]
        return view
    }

    init {
        inflter = LayoutInflater.from(context)
    }
}