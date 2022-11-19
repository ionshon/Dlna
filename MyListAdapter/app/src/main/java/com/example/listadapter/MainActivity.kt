package com.example.listadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productAdapter = ProductAdapter()
        binding.recyclerView.adapter = productAdapter

        productAdapter.submitList(getProductItemList())
    }

    private fun getProductItemList(): ArrayList<ProductModel> {
        var resultList = arrayListOf<ProductModel>()
        var cnt = 0
        while (cnt++ < 5) {
            val id = System.currentTimeMillis()
            val thumbnail = getDrawable(R.drawable.ic_launcher_background)
            val title = "title+$cnt"
            val price = "price_${cnt}원"

            val product = ProductModel(id, thumbnail!!, title, price)
            resultList.add(product)
        }
        return resultList
    }
}