package com.mohsinziabutt.roomdatabase.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mohsinziabutt.roomdatabase.R
import com.mohsinziabutt.roomdatabase.model.User
import com.mohsinziabutt.roomdatabase.viewModel.UserViewModel
import com.mohsinziabutt.roomdatabase.databinding.FragmentAddBinding

class FragmentAdd : Fragment(), View.OnClickListener {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //UserViewModel initialization
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        navController = Navigation.findNavController(view)

        binding.btnAddUser.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnAddUser -> {
                addUserToDatabase()
            }
        }
    }

    private fun addUserToDatabase() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val age = binding.age.text.toString()

        //create user object
        val user = User(0, firstName, lastName, Integer.parseInt(age))
        //adding datat to Room database
        userViewModel.addUser(user)
        Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show()
        //Navigating back to previous fragment
        navController!!.navigate(R.id.action_fragmentAdd_to_fragmentList)
    }
}