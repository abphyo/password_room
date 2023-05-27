package com.example.room1

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.room1.room.AppDatabase
import com.example.room1.room.Dao
import com.example.room1.room.RoomApplication
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: Dao
    private lateinit var viewModel: ViewModelActivity1
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var input1: TextInputLayout
    private lateinit var input2: TextInputLayout
    private lateinit var editText1: TextInputEditText
    private lateinit var editText2: TextInputEditText
    private lateinit var username: Editable
    private lateinit var password: Editable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        appDatabase = (requireActivity().application as RoomApplication).database
        dao = appDatabase.getDao()
        viewModel = ViewModelActivity1(dao)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clickableTextView: TextView = view.findViewById(R.id.textBtn1)
        val loginBtn: Button = view.findViewById(R.id.loginButton)
        input1 = view.findViewById(R.id.textField1)
        input2 = view.findViewById(R.id.textField2)
        editText1 = view.findViewById(R.id.username)
        editText2 = view.findViewById(R.id.password)
        username = editText1.editableText
        password = editText2.editableText
        clickableTextView.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frameLayout, RegisterFragment()).addToBackStack(null).commit()
        }
        loginBtn.setOnClickListener {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.userLogin( username = "$username", password = "$password")
                coroutineScope.launch {
                    viewModel.accStatus.collect {
                        value1 ->
                        if (value1) {
                            viewModel.loginStatus.collect {
                                    value2 ->
                                if (value2) {
                                    Toast.makeText(requireContext(), "Login success!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(activity, MainActivity2::class.java)
                                    intent.putExtra("key", "$username")
                                    startActivity(intent)
                                    Log.d("checkingKey", "onViewCreated: $username")
                                } else input2.helperText = "wrong password!"
                            }
                        } else input1.helperText = "user doesn't exist!"
                    }
                }
            }
            else {
                when {
                    username.isEmpty() && password.isEmpty() -> {
                        input1.helperText = "username can't be empty"
                        input2.helperText = "password can't be empty"
                    }
                    username.isEmpty() -> {
                        input1.helperText = "username can't be empty"
                        input2.helperText = null
                    }
                    password.isEmpty() -> {
                        input1.helperText = null
                        input2.helperText = "password can't be empty"
                    }
                }
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            coroutineScope.launch {
                editText1.setText("${viewModel.username}")
                editText2.setText("${viewModel.password}")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.memorize("$username", "$password")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}