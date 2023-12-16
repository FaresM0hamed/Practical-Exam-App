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
import com.devfares.practicalexam.databinding.FragmentLoginBinding
import com.devfares.practicalexam.utils.showToast
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var database: LocalDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = LocalDatabase.getDatabase(requireContext())

        binding.loginBtn.setOnClickListener {
            val email = binding.emailText.text.toString().trim()
            val password = binding.passwordText.text.toString().trim()
            if (isInputsValid(email, password)) {
                loginProcess(email, password)
            } else requireContext().showToast("مطلوب ادخال الحقول")
        }

        binding.createAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.ForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    private fun loginProcess(email: String, password: String) {
        lifecycleScope.launch {
            val user = database.userDao().getUserByEmailAndPassword(email, password)
            if (user != null) {
                navigateToHome()
            } else requireContext().showToast("البريد الإلكتروني او كلمة المرور غير صحيحة")
        }
    }

    private fun isInputsValid(email: String, password: String) =
        email.isNotEmpty() && password.isNotEmpty()

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
}

