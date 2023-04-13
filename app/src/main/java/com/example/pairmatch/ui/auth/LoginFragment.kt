package com.example.pairmatch.ui.auth

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.pairmatch.BaseFragment
import com.example.pairmatch.BottomNavigationActivity
import com.example.pairmatch.R
import com.example.pairmatch.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate){

    private val vm : AuthViewModel by viewModels()
    private lateinit var dialogCustom: Dialog
    private var statePrivacyPolicy: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogCustom = Dialog(requireContext())

        initListeners()

    }

    private fun initListeners() {
        binding?.run {
            btnComeToReg.setOnClickListener {
                onBackPressed()
            }
            btnLogin.setOnClickListener {
                btnLogin.setOnClickListener {
                    if (isValidateData()){
                        /*vm.login(fieldEmail.text.toString(), fieldPassword.text.toString())*/
                        if (!statePrivacyPolicy) openDialog()
                    }  else {
                        Toast.makeText(requireContext(), "Введите корректные данные!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun isValidateData(): Boolean {
        var isValid = true
        if (binding?.fieldEmail?.text.isNullOrEmpty() && binding?.fieldPassword?.text?.length!! < 6) isValid = false
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
            statePrivacyPolicy = true
            requireActivity().finish()
            (activity)?.startActivity(Intent(requireContext(), BottomNavigationActivity::class.java))
            dialogCustom.dismiss()
        }
        btnDecline.setOnClickListener {
            statePrivacyPolicy = false
            dialogCustom.dismiss()
        }
        dialogCustom.show()
    }
}