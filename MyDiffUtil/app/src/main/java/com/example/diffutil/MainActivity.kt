package com.example.diffutil

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.diffutil.databinding.ActivityMainBinding
import java.net.URL
import java.net.URLEncoder
import javax.xml.xpath.XPathFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: PersonListAdapter
    val imageSrc = "http://192.168.0.12:38520/description.xml"

//    var mLocation = uri.encodedPath
    val xPath = XPathFactory.newInstance().newXPath()
//    val icon = xPath.compile("//icon/url").evaluate(doc)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val str = "http://192.168.0.12:38520/icons/deviceIcon48.png"
//        val imageUrl = mLocation.
        val split = str.split("/")
        val split1 = str.split("*")
        val split2 = str.split("*", limit = 2)
        println("답: http://${split[2]}/")
    }
}