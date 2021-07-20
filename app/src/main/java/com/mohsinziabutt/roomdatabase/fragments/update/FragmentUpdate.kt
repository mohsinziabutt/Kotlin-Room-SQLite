package com.mohsinziabutt.roomdatabase.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mohsinziabutt.roomdatabase.R
import com.mohsinziabutt.roomdatabase.databinding.FragmentUpdateBinding
import com.mohsinziabutt.roomdatabase.model.User
import com.mohsinziabutt.roomdatabase.viewModel.UserViewModel

class FragmentUpdate : Fragment(), View.OnClickListener {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null

    private var firstName = ""
    private var lastName = ""
    private var age = 0
    private var currentUserId = 0
    private lateinit var user: User

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //UserViewModel initialization
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        navController = Navigation.findNavController(view)

        var bundleParcel = arguments?.getParcelable<User>("bundleParcel")

        currentUserId = bundleParcel!!.id
        firstName = bundleParcel?.firstName
        lastName = bundleParcel?.lastName
        age = bundleParcel?.age

        binding.updateFirstName.setText(firstName)
        binding.updateLastName.setText(lastName)
        binding.updateAge.setText("" + age)

        binding.btnUpdateUser.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnUpdateUser -> {
                updateUser()
            }
        }
    }

    private fun updateUser() {
        Log.d("UPDATED_USER", "hellloooowwww\n")
        firstName = binding.updateFirstName.text.toString()
        lastName = binding.updateLastName.text.toString()
        age = Integer.parseInt(binding.updateAge.text.toString())

        //create user object
        user = User(currentUserId, firstName, lastName, age)

        Log.d("UPDATED_USER", "" + user)

        //adding datat to Room database
        userViewModel.updateUser(user)
        Toast.makeText(context, "User Updated", Toast.LENGTH_SHORT).show()
        //Navigating back to previous fragment
        navController!!.navigate(R.id.action_fragmentUpdate_to_fragmentList)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteUser)
        {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser()
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            user = User(currentUserId, firstName, lastName, age)
            userViewModel.deleteUser(user)
        }
        builder.setNegativeButton("No") {_, _ ->

        }
        builder.setTitle("Delete $firstName $lastName?")
        builder.setMessage("Are you sure you want to delete $firstName $lastName?")
        builder.create().show()
    }
}