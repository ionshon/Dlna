package com.inu.dlna

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.inu.dlna.databinding.ActivityMainBinding
import com.inu.dlna.model.Devices
import com.inu.dlna.model.UpnpDevice
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {//ActionBarActivity {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val TAG = MainActivity::class.java.simpleName

    var adapter: UPnPDeviceAdapter? = null
//    var deviceList = mutableListOf<UpnpDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = UPnPDeviceAdapter()
        with(binding) {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(applicationContext)
            recyclerview.visibility = View.INVISIBLE
            spinner.visibility = View.VISIBLE
        }


    }

    override fun onStart() {
        super.onStart()

        val observer = object :DisposableObserver<UpnpDevice>() {
            override fun onNext(t: UpnpDevice) { // 장치 출력
                Log.i(TAG, "location in onNext:  ${t.location}")
                if (0 == adapter!!.itemCount) { // 값과 종류까지 같은지
                    Log.d("TEST","observer onNext0: $t")
                    binding.spinner.animate()
                        .alpha(0f)
                        .setDuration(1000)
                        .setInterpolator(AccelerateInterpolator())
                        .start()
                    binding.recyclerview.setAlpha(0f)
                    binding.recyclerview.setVisibility(View.VISIBLE)
                    binding.recyclerview.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .setStartDelay(1000)
                        .setInterpolator(DecelerateInterpolator())
                        .start()
                }
                Log.d("TEST","observer onNext1: ${t.location}")
                adapter?.add(t)
//                val valueList = Devices.deviceList.values.toList()
//                adapter?.updateDeviceList(valueList)
            }

            override fun onError(e: Throwable) {
                Log.d("TEST","observer 에러: socket is null")
            }

            override fun onComplete() {
                    Log.d("TEST","observer onConplete")
                Log.d("TEST","observer 컴플릿: Hash: ${Devices.deviceList["http://192.168.0.1:64640/etc/linuxigd/gatedesc.xml"]}")
                val valueList = Devices.deviceList.values.toMutableList()

                adapter?.updateDeviceList(valueList)
                Log.d("TEST","observer 컴플릿: ${Devices.deviceList}")
            }
        }

        UPnPDeviceFinder().observe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}