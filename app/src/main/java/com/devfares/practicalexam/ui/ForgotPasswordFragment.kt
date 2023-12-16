package com.devfares.practicalexam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.devfares.practicalexam.R
import com.devfares.practicalexam.data.dao.UserDao
import com.devfares.practicalexam.data.local.LocalDatabase
import com.devfares.practicalexam.data.model.User
import com.devfares.practicalexam.databinding.FragmentForgotPasswordBinding
import com.devfares.practicalexam.utils.showToast
import kotlinx.coroutines.launch


class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDao = LocalDatabase.getDatabase(requireContext()).userDao()
        binding.updatePassword.setOnClickListener {
            val email = binding.emailText.text.toString().trim()
            val password = binding.passwordText.text.toString().trim()
            val newPassword = binding.newPasswordText.text.toString().trim()

            if (isInputValid(email, newPassword, password)) {
                changeUserPassword(email, password, newPassword)
            } else requireContext().showToast("مطلوب ادخال الحقول")
        }
    }

    private fun changeUserPassword(
        email: String, password: String, newPassword: String
    ) {
        lifecycleScope.launch {
            val user = userDao.getUserByEmailAndPassword(email, password)
            if (user != null) {
                updateUserPassword(user, newPassword)
                onSuccess()
            } else onFail()
        }
    }

    private suspend fun updateUserPassword(
        user: User,
        newPassword: String
    ) {
        user.password = newPassword
        userDao.updatePassword(user)
    }

    private fun isInputValid(
        email: String, newPassword: String, password: String
    ) = email.isNotEmpty() && newPassword.isNotEmpty() && password.isNotEmpty()

    private fun onSuccess() {
        requireContext().showToast("تم تغيير كلمة المرور بنجاح")
        findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
    }

    private fun onFail() {
        requireContext().showToast("البريد الإلكتروني او كلمة المرور غير صحيحة")
    }
}
