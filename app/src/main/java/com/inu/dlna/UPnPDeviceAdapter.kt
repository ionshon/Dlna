package com.inu.dlna

import android.annotation.SuppressLint
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListDiffer.ListListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inu.dlna.databinding.RowupnpDeviceBinding
import com.inu.dlna.model.UpnpDevice
import com.inu.dlna.utils.DiffUtilCallback
import java.util.*


class UPnPDeviceAdapter: RecyclerView.Adapter<UPnPDeviceAdapter.Holder>() {

    var mItems = mutableListOf<UPnPDevice>() //Devices.deviceList
    var deviceList = mutableListOf<UpnpDevice>()

    class SleepNightDiffCallback : DiffUtil.ItemCallback<UpnpDevice>() {
        override fun areItemsTheSame(oldItem: UpnpDevice, newItem: UpnpDevice): Boolean {
            return oldItem.location == newItem.location
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: UpnpDevice, newItem: UpnpDevice): Boolean {
            return oldItem == newItem
        }
    }

    private val mComparator: Comparator<UpnpDevice> = UPnPDeviceComparator()
    inner class Holder(val binding: RowupnpDeviceBinding): RecyclerView.ViewHolder(binding.root){
        fun setDevice(item: UpnpDevice) {
            with(binding) {
//                if (!TextUtils.isEmpty(item.iconUrl)) {
                    Glide.with(binding.root)
                        .load(item.iconUrl)
                        .error(R.drawable.ic_server_network)
                        .centerInside()
                        .into(icon)
//                } else {
//                    icon.setImageResource(R.drawable.ic_server_network)
//                }
//                icon.setImageResource(R.drawable.ic_server_network)
            }
            Log.d("item: ", "${item.iconUrl}")

            binding.location.text = item.location.toString()
            binding.friendlyName.text = item.friendlyName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = RowupnpDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d("onBindViewHolder: ", "deviceList: ${deviceList}")

        val item: UpnpDevice = deviceList[position] // getItem(position)
        holder.setDevice(item)

        holder.binding.root.setOnClickListener {
            /*val intent = Intent(mContext, AboutActivity::class.java)
            mContext.startActivity(intent)*/
//            notifyDataSetChanged()
            Log.d("onclick root: ", "friendlyName: ${item.friendlyName}")
        /*
            if (mListener != null) {
                mListener!!.onClick(mItems[position], position)
                notifyItemChanged(position)
            }*/
//            updateList(deviceList)
        }
    }

    override fun getItemCount(): Int {
        return  deviceList.size
    }

    fun add(item: UpnpDevice) {
        val index = Collections.binarySearch(deviceList, item, mComparator)
        if (index < 0) {
            val position = -index - 1
            deviceList.add(position, item)
            notifyItemInserted(position)
        } else {
            deviceList[index] = item
            notifyItemChanged(index)
        }
    }

    fun updateList(items: List<UPnPDevice>?) {
        items?.let {
            val diffCallback = DiffUtilCallback(this.mItems, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.mItems.run {
                clear()
                addAll(items)
                diffResult.dispatchUpdatesTo(this@UPnPDeviceAdapter)
            }
        }
    }

    fun updateDeviceList(list: MutableList<UpnpDevice>) {
        deviceList = list
        notifyDataSetChanged() // 리스트 변경을 adapter에 알림
    }
}