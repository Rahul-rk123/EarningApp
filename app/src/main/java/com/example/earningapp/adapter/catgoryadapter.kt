package com.example.earningapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.earningapp.QuizActivity
import com.example.earningapp.databinding.CatagoryitemsBinding
import com.example.earningapp.modelclass.catagorymodelclass

class catgoryadapter(
    private val catagorylist: ArrayList<catagorymodelclass>,
    var requireActivity: FragmentActivity
): RecyclerView.Adapter<catgoryadapter.MycatagoryViewHolder>() {
    class MycatagoryViewHolder(var binding: CatagoryitemsBinding) :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MycatagoryViewHolder {
       return  MycatagoryViewHolder(CatagoryitemsBinding.inflate(LayoutInflater.from(parent.context),parent,  false))
    }
    override fun getItemCount(): Int {
       return catagorylist.size
    }

    override fun onBindViewHolder(holder: MycatagoryViewHolder, position: Int) {
        var crnt_catagory = catagorylist[position]
        holder.binding.catagoryImage.setImageResource(crnt_catagory.catimage)
        holder.binding.textView11.text = crnt_catagory.cattext
        holder.binding.catagorybtn.setOnClickListener {
           val intent = Intent(requireActivity, QuizActivity::class.java)
            intent.putExtra("catogary_img", crnt_catagory.catimage)
            intent.putExtra("QuestionType", crnt_catagory.cattext)
            requireActivity.startActivity(intent)
        }
    }
}