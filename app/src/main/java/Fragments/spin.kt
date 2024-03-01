package Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.earningapp.databinding.FragmentSpinBinding
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
import java.util.Random


class spin : Fragment() {
    private lateinit var binding: FragmentSpinBinding
    private lateinit var timer: CountDownTimer
    private val itemtitles= arrayOf("100", "Try Again", "500", "Try Again", "200", "Try Again")
    var currentChance = 0L
    var currentcoins = 0L
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = FragmentSpinBinding.inflate(inflater, container, false)
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
        Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        currentChance = snapshot.value as Long
                        binding.spinchance.text = (snapshot.value as Long).toString()
                    }else{
                        binding.spinchance.text = "0"
                        binding.Spin.isEnabled = false

                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
        Firebase.database.reference.child("playercoin").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        currentcoins = snapshot.value as Long
                        binding.coinwithdraw.text= currentcoins.toString()
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    private fun showResult(itemTitle: String, spin:Int){
        if(spin%2==0){
            var wincoin = itemTitle.toInt()
            Firebase.database.reference.child("playercoin").child(Firebase.auth.currentUser!!.uid).setValue(wincoin+currentcoins)
            var historymodalclass = HistoryModalClass(System.currentTimeMillis().toString(), wincoin.toString(), false)
            Firebase.database.reference.child("playercoinHistory").child(Firebase.auth.currentUser!!.uid).push().setValue(historymodalclass)
            binding.coinwithdraw.text = (wincoin+currentcoins).toString()

        }
        Toast.makeText(requireContext(), itemTitle, Toast.LENGTH_SHORT).show()
        currentChance -= 1
        Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid).setValue(currentChance)
        binding.Spin.isEnabled = true // Enable the button again
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.Spin.setOnClickListener {
            binding.Spin.isEnabled = false
            if(currentChance>0) {
                val spin = Random().nextInt(6)
                val degrees = 60f * spin
                timer = object : CountDownTimer(5000, 50) {
                    var rotation = 0f
                    override fun onTick(miliuntilfinished: Long) {
                        rotation += 5f
                        if (rotation >= degrees) {
                            rotation = degrees
                            timer.cancel()
                            showResult(itemtitles[spin], spin)
                        }
                        binding.wheel.rotation = rotation
                    }

                    override fun onFinish() {}
                }.start()
            }else{
                Toast.makeText(activity, "Out of Chances", Toast.LENGTH_SHORT).show()
            }
        }
    }
}