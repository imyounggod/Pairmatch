package com.example.pairmatch.ui.auth

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pairmatch.BaseFragment
import com.example.pairmatch.BottomNavigationActivity
import com.example.pairmatch.R
import com.example.pairmatch.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {


    private val vm: AuthViewModel by viewModels()
    private lateinit var dialogCustom: Dialog
    private var statePrivacyPolicy: Boolean = false
    private var gender = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogCustom = Dialog(requireContext())

        initListeners()
    }

    private fun initListeners() {
        binding?.run {
            btnComeToLogin.setOnClickListener {
                navController.navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
            }
            btnMale.setOnClickListener {
                btnMale.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_radio_button_active)
                btnFemale.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_radio_button_inactive)
                gender = "male"
            }
            btnFemale.setOnClickListener {
                btnMale.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_radio_button_inactive)
                btnFemale.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_radio_button_active)
                gender = "female"
            }
            btnRegister.setOnClickListener {
                if (isValidateFields() && isValidatePassword()) {
                    if (!statePrivacyPolicy) openDialog()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Пожалуйста, заполните все поля!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isValidatePassword(): Boolean {
        var isValid = true
        if (binding?.fieldPassword?.text?.length!! < 6) isValid = false
        return isValid
    }

    private fun isValidateFields(): Boolean {
        var isValid = true
        binding?.run {
            if (fieldEmail.text.isNullOrEmpty() || fieldName.text.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(
                    binding?.fieldEmail?.text.toString()
                ).matches() || fieldPassword.text.isNullOrEmpty() || gender.isBlank()
            ) isValid = false
        }
        return isValid
    }

    private fun openDialog() {
        dialogCustom.setContentView(R.layout.alert_dialog)
        dialogCustom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnAccept: Button = dialogCustom.findViewById(R.id.acceptBtn)
        val btnDecline: Button = dialogCustom.findViewById(R.id.closeBtn)
        val privacyPolicy: TextView = dialogCustom.findViewById(R.id.tv_link)

        privacyPolicy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://doc-hosting.flycricket.io/pairmatch-sports-privacy-policy/e1996337-b270-4e33-8e3d-213f3767839a/privacy")
                )
            )
        }

        btnAccept.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                var result = vm.registerUser(
                    binding?.fieldName?.text?.trim().toString(),
                    binding?.fieldEmail?.text?.trim().toString(),
                    binding?.fieldPassword?.text?.trim().toString(),
                    gender
                )
                if (result) {
                    statePrivacyPolicy = true
                    requireActivity().finish()
                    (activity)?.startActivity(
                        Intent(
                            requireContext(),
                            BottomNavigationActivity::class.java
                        )
                    )
                    dialogCustom.dismiss()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Что-то пошло не так, попробуйте снова!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        btnDecline.setOnClickListener {
            statePrivacyPolicy = false
            dialogCustom.dismiss()
        }
        dialogCustom.show()
    }
}