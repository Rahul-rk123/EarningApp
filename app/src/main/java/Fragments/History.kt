package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earningapp.adapter.historyadaptor
import com.example.earningapp.databinding.FragmentHistoryBinding
import com.example.earningapp.modelclass.HistoryModalClass
import com.example.earningapp.modelclass.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import java.util.Collections


class History : Fragment() {
    val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }
    lateinit var adaptor:historyadaptor
    private val ListHistory = ArrayList<HistoryModalClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.reference.child("playercoinHistory").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object:ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListHistory.clear()
                     val ListHistory1 = ArrayList<HistoryModalClass>()
                    for(datasnapshot in snapshot.children){
                        var data = datasnapshot.getValue(HistoryModalClass::class.java)
                        ListHistory1.add(data!!)
                    }
                    ListHistory1.reverse()
                    ListHistory.addAll(ListHistory1)
                    adaptor.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // TODO("Not yet implemented")
                }

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding.coinswithdraw.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = withdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.coinwithdraw.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = withdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.HistoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adaptor = historyadaptor(ListHistory)
        binding.HistoryRecyclerView.adapter = adaptor
        binding.HistoryRecyclerView.setHasFixedSize(true)
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue<User>()
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
        return binding.root
    }
    companion object {

    }
}