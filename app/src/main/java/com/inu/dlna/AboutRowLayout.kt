package com.inu.dlna

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.inu.dlna.databinding.ActivityMainBinding
import com.inu.dlna.databinding.RowAboutBinding

//@SuppressLint("ViewConstructor")
class AboutRowLayout: LinearLayout {

    constructor(context: Context) : super(context) {
        Log.d("TAG", "CompassView(context) called")
        View.inflate(context, R.layout.row_about, this)
        Log.d("TAG", "Inflation started from constructor.")
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        Log.d("TAG", "CompassView(context, attrs) called")
        View.inflate(context, R.layout.row_about, this)
        Log.d("TAG", "Inflation started from constructor.")

        orientation = HORIZONTAL
        val a = context.obtainStyledAttributes(attrs, R.styleable.AboutRowLayout)
        val src = a.getDrawable(R.styleable.AboutRowLayout_avatar)
        val projectStr = a.getString(R.styleable.AboutRowLayout_projectStr)
        val projectUrl = a.getString(R.styleable.AboutRowLayout_projectUrl)
        val projectStr2 = a.getString(R.styleable.AboutRowLayout_projectStr2)
        val projectUrl2 = a.getString(R.styleable.AboutRowLayout_projectUrl2)
        val projectStr3 = a.getString(R.styleable.AboutRowLayout_projectStr3)
        val projectUrl3 = a.getString(R.styleable.AboutRowLayout_projectUrl3)
        val description = a.getText(R.styleable.AboutRowLayout_description)
        val linkStr = a.getString(R.styleable.AboutRowLayout_linkStr)
        val linkUrl = a.getString(R.styleable.AboutRowLayout_linkUrl)
        val linkStr2 = a.getString(R.styleable.AboutRowLayout_linkStr2)
        val linkUrl2 = a.getString(R.styleable.AboutRowLayout_linkUrl2)
        a.recycle()
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.row_about, this, true)
        val binding = RowAboutBinding.inflate(inflater, this@AboutRowLayout)
//        ButterKnife.bind(this)
        binding.avatar.visibility = if (src == null) GONE else VISIBLE
        binding.avatar.setImageDrawable(src)
        val project = SpannableBuilder(getContext())
        appendProject(project, projectStr, projectUrl)
        appendProject(project, projectStr2, projectUrl2)
        appendProject(project, projectStr3, projectUrl3)
        if (project.length() === 0) {
            binding.project.visibility = GONE
        } else {
            binding.project.visibility = VISIBLE
            binding.project.text = project.build()
            binding.project.movementMethod = LinkMovementMethod.getInstance()
        }
        linkify(binding.description, description, null)
        linkify(binding.link1, linkStr, linkUrl)
        linkify(binding.link2, linkStr2, linkUrl2)
    }

    private fun appendProject(builder: SpannableBuilder, str: CharSequence?, url: String?) {
        var str = str
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(url)) {
            return
        }
        if (builder.length() !== 0) {
            builder.append(", ")
        }
        if (TextUtils.isEmpty(url)) {
            builder.append(str!!, StyleSpan(Typeface.BOLD))
            return
        }
        if (TextUtils.isEmpty(str)) {
            str = url
        }
        builder.append(str!!, PlainURLSpan(url), StyleSpan(Typeface.BOLD))
        builder.append(R.drawable.ic_external_link_arrow)
    }

    private fun linkify(view: TextView?, str: CharSequence?, url: String?) {
        var str = str
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(url)) {
            view!!.visibility = GONE
            return
        }
        view!!.visibility = VISIBLE
        if (TextUtils.isEmpty(url)) {
            view.text = str
            return
        }
        if (TextUtils.isEmpty(str)) {
            str = url
        }
        val builder = SpannableBuilder(context)
        builder.append(str!!, URLSpan(url))
        view.text = builder.build()
        view.movementMethod = LinkMovementMethod.getInstance()
    }

    private class PlainURLSpan(url: String?) : URLSpan(url) {
        override fun updateDrawState(ds: TextPaint) {
            // intentionally do nothing here
        }
    }
    /*
    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = HORIZONTAL
        val a = context.obtainStyledAttributes(attrs, R.styleable.AboutRowLayout)
        val src = a.getDrawable(R.styleable.AboutRowLayout_avatar)
        val projectStr = a.getString(R.styleable.AboutRowLayout_projectStr)
        val projectUrl = a.getString(R.styleable.AboutRowLayout_projectUrl)
        val projectStr2 = a.getString(R.styleable.AboutRowLayout_projectStr2)
        val projectUrl2 = a.getString(R.styleable.AboutRowLayout_projectUrl2)
        val projectStr3 = a.getString(R.styleable.AboutRowLayout_projectStr3)
        val projectUrl3 = a.getString(R.styleable.AboutRowLayout_projectUrl3)
        val description = a.getText(R.styleable.AboutRowLayout_description)
        val linkStr = a.getString(R.styleable.AboutRowLayout_linkStr)
        val linkUrl = a.getString(R.styleable.AboutRowLayout_linkUrl)
        val linkStr2 = a.getString(R.styleable.AboutRowLayout_linkStr2)
        val linkUrl2 = a.getString(R.styleable.AboutRowLayout_linkUrl2)
        a.recycle()
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.row_about, this, true)
        ButterKnife.bind(this)
        vAvatar!!.visibility = if (src == null) GONE else VISIBLE
        vAvatar!!.setImageDrawable(src)
        val project = SpannableBuilder(getContext())
        appendProject(project, projectStr, projectUrl)
        appendProject(project, projectStr2, projectUrl2)
        appendProject(project, projectStr3, projectUrl3)
        if (project.length() === 0) {
            vProject!!.visibility = GONE
        } else {
            vProject!!.visibility = VISIBLE
            vProject!!.text = project.build()
            vProject!!.movementMethod = LinkMovementMethod.getInstance()
        }
        linkify(vDescription, description, null)
        linkify(vLink1, linkStr, linkUrl)
        linkify(vLink2, linkStr2, linkUrl2)
    }*/

}