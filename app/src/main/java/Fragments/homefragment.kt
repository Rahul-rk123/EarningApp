package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.earningapp.R
import com.example.earningapp.adapter.catgoryadapter
import com.example.earningapp.databinding.FragmentHomefragmentBinding
import com.example.earningapp.modelclass.User
import com.example.earningapp.modelclass.catagorymodelclass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import java.util.ArrayList


class homefragment : Fragment() {
    private val binding:FragmentHomefragmentBinding by lazy {
        FragmentHomefragmentBinding.inflate(layoutInflater)
    }
    private val catagoryList = ArrayList<catagorymodelclass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catagoryList.add(catagorymodelclass(R.drawable.scince1, "Science"))
        catagoryList.add(catagorymodelclass(R.drawable.english1, "English"))
        catagoryList.add(catagorymodelclass(R.drawable.geography, "Geography"))
        catagoryList.add(catagorymodelclass(R.drawable.math, "Math"))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.coinswithdraw.setOnClickListener {
            val bottomSheetDialog:BottomSheetDialogFragment = withdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.coinwithdraw.setOnClickListener {
            val bottomSheetDialog:BottomSheetDialogFragment = withdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue<User>()
                    binding.Name.text = user?.name

                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            }
        )
        Firebase.database.reference.child("playercoin").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var currentcoins = snapshot.value as Long
                        binding.coinwithdraw.text= currentcoins.toString()
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
       return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        var adapter = catgoryadapter(catagoryList, requireActivity())
        binding.recyclerview.adapter = adapter
        binding.recyclerview.setHasFixedSize(true)

    }
    companion object {
    }
}