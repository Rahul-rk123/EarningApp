package com.example.earningapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.earningapp.databinding.HistoryitemsBinding
import com.example.earningapp.modelclass.HistoryModalClass
import java.sql.Timestamp
import java.util.Date

class historyadaptor(private val arr:ArrayList<HistoryModalClass>): RecyclerView.Adapter<historyadaptor.MyhistoryViewHolder>() {
    class MyhistoryViewHolder(var binding: HistoryitemsBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyhistoryViewHolder {
        return MyhistoryViewHolder(HistoryitemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: MyhistoryViewHolder, position: Int) {
        var timeStamp = Timestamp(arr[position].timeanddata.toLong())
        holder.binding.Time.text = Date(timeStamp.time).toString()
        holder.binding.status.text=if(arr[position].iswithdrawl){"- Money Withdrawl"}else{
            "+ Money Added"
        }
        holder.binding.Coin.text = arr[position].coin
    }
}