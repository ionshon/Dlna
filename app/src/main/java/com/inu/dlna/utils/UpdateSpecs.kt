package com.inu.dlna.utils

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.inu.dlna.model.Devices
import com.inu.dlna.model.UpnpDevice
import okhttp3.OkHttpClient
import okhttp3.Request
import org.w3c.dom.Document
import org.xml.sax.InputSource
import org.xml.sax.SAXParseException
import java.io.IOException
import java.io.StringReader
import java.net.URL
import java.util.HashMap
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathFactory
import kotlin.concurrent.thread


object  UpdateSpecs {

    fun updateSpecs(loc: URL) {
        Log.d("updateSpeck", "loc: $loc")
        val mClient = OkHttpClient()
        var mRawXml: String? = null
        var mProperties: HashMap<String, String>? = null
        var mFriendlyName: String
        thread {
            val request: Request = Request.Builder()
                .url(loc)
                .build()
            val response = mClient.newCall(request).execute()
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            mRawXml = response.body?.string()
//            Log.d("updateSpeck", "mRawXml: $mRawXml")
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            val source = InputSource(StringReader(mRawXml))
            val doc: Document = try {
                db.parse(source)
            } catch (e: SAXParseException) {
                return@thread
            }
            Log.d("updateSpeck: ", "doc: $doc")
            val xPath = XPathFactory.newInstance().newXPath()

    //            generateIconUrl()
//            mProperties?.set("xml_friendly_name", xPath.compile("//friendlyName").evaluate(doc))
//            mFriendlyName = mProperties?.get("xml_friendly_name").toString()
            mFriendlyName = xPath.compile("//friendlyName").evaluate(doc)
    //            mFriendlyName = mProperties!!["xml_friendly_name"]
//            Devices.friendlyName = mFriendlyName
            Devices.deviceList[loc.toString()]?.iconUrl = "${loc.protocol}://${loc.host}:${loc.port}${xPath.compile("//icon/url").evaluate(doc)}"
            Devices.deviceList[loc.toString()]?.friendlyName = mFriendlyName
            Log.d("updateSpeck: ", "friendly: ${Devices.deviceList[loc.toString()]?.friendlyName}")
            Log.d("updateSpeck: ", "iconUrl: ${Devices.deviceList[loc.toString()]?.iconUrl}")
        }
    }
}