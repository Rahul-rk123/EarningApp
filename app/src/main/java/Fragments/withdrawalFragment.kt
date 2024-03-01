package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.earningapp.R
import com.example.earningapp.databinding.FragmentWithdrawalBinding
import com.example.earningapp.modelclass.HistoryModalClass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class withdrawalFragment : BottomSheetDialogFragment(){
    var currentcoins=0L
    private lateinit var binding:FragmentWithdrawalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWithdrawalBinding.inflate(inflater, container, false)
        Firebase.database.reference.child("playercoin").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        currentcoins = snapshot.value as Long

                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
        binding.transfer.setOnClickListener {
            if(binding.amount.text.toString().toDouble()<=currentcoins){
                Firebase.database.reference.child("playercoin").child(Firebase.auth.currentUser!!.uid).setValue(currentcoins-binding.amount.text.toString().toDouble())
                var historymodalclass = HistoryModalClass(System.currentTimeMillis().toString(), binding.amount.text.toString(), true)
                Firebase.database.reference.child("playercoinHistory").child(Firebase.auth.currentUser!!.uid).push().setValue(historymodalclass)

            }else{
                Toast.makeText(activity, "insufficient coins", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    companion object {

    }
}