package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.earningapp.R
import com.example.earningapp.databinding.FragmentUserBinding
import com.example.earningapp.modelclass.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue


class user : Fragment() {
    val binding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
    }
    var isexpand = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.imageButton.setOnClickListener {
            if (isexpand) {
                binding.expandableconstraintlayout.visibility = View.VISIBLE
                binding.imageButton.setImageResource(R.drawable.arrowup)
            } else {
                binding.expandableconstraintlayout.visibility = View.GONE
                binding.imageButton.setImageResource(R.drawable.downarrow)
            }
            isexpand = !isexpand
        }
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue<User>()
                    binding.Name.text = user?.name
                    binding.textView12.text = user?.name
                    binding.Email.text = user?.Email
                    binding.Password.text = user?.password
                    binding.Age.text = user?.age.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            }
        )
       return binding.root
    }

    companion object {

    }
}