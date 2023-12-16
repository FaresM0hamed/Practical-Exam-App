package com.devfares.practicalexam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.devfares.practicalexam.R
import com.devfares.practicalexam.data.local.LocalDatabase
import com.devfares.practicalexam.data.model.User
import com.devfares.practicalexam.databinding.FragmentSignupBinding
import com.devfares.practicalexam.utils.showToast
import kotlinx.coroutines.launch


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var database: LocalDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigateTOLogin.setOnClickListener {
            navigateToLogin()
        }

        database = LocalDatabase.getDatabase(requireContext())
        binding.createAccountBtn.setOnClickListener {
            val email = binding.emailText.text.toString().trim()
            val userName = binding.userText.text.toString().trim()
            val password = binding.passwordText.text.toString().trim()
            val rePassword = binding.rePasswordText.text.toString().trim()

            if (isInputsValid(email, userName, password, rePassword)) {
                saveUserInfo(email, userName, password)
                navigateToLogin()
                requireContext().showToast("تم انشاء الحساب بنجاح")
            } else if (password != rePassword) {
                requireContext().showToast("كلمة المرور غير متطابقة")
            } else requireContext().showToast("مطلوب ادخال الحقول")
        }
    }

    private fun saveUserInfo(email: String, userName: String, password: String) {
        lifecycleScope.launch {
            database.userDao().insertUser(User(email, userName, password))
        }
    }

    private fun isInputsValid(
        email: String, userName: String, password: String, rePassword: String
    ) =
        email.isNotEmpty() && userName.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
    }
}
