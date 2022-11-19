package com.example.diffutil

import android.app.Person
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutil.databinding.ItemLayoutBinding

class PersonListAdapter: ListAdapter<com.example.diffutil.Person, PersonListAdapter.Holder>(PersonDiffItemCallback()) {

    inner class Holder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setPerson(item: com.example.diffutil.Person) {
            binding.textName.text = item.name
            binding.textNum.text = "${item.num}번째"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setPerson(currentList[position])
    }

    /*fun add() {
        val newPerson = mutableListOf<com.example.diffutil.Person>()
        newPerson.addAll(currentList)
        newPerson.add(com.example.diffutil.Person("Java", 20, PersonAdapter.index))
    }
*/
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<com.example.diffutil.Person>() {
            override fun areItemsTheSame(
                oldItem: com.example.diffutil.Person,
                newItem: com.example.diffutil.Person
            ): Boolean {
                return oldItem.num == newItem.num
            }

            override fun areContentsTheSame(
                oldItem: com.example.diffutil.Person,
                newItem: com.example.diffutil.Person
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}