package com.devnet.vault.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devnet.vault.MainActivity
import com.devnet.vault.R
import com.devnet.vault.utility.Devnet
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    private lateinit var _auth: FirebaseAuth
    private lateinit var _llLogin:LinearLayout
    private lateinit var _llSignUp: LinearLayout
    private lateinit var _etLoginEmail: TextInputEditText
    private lateinit var _etLoginPassword: TextInputEditText
    private lateinit var _etSignupEmail: TextInputEditText
    private lateinit var _etSignUpPassword: TextInputEditText
    private lateinit var _etSignUpCPassword: TextInputEditText
    private lateinit var _btnLogin: Button
    private lateinit var _btnSignUp: Button
    private lateinit var _tvRegister: TextView
    private lateinit var _tvLogin: TextView
    private lateinit var _devnet: Devnet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialize()

//        User Authentication
        _btnLogin.setOnClickListener {
            val email = _etLoginEmail.text.toString().trim()
            val password = _etLoginPassword.text.toString()

            if (email.isEmpty()) {
                _etLoginEmail.error = "Email is required"
                _etLoginEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                _etLoginPassword.error = "Password is required"
                _etLoginPassword.requestFocus()
                return@setOnClickListener
            }
            _devnet.firebaseUserLogin(email,password,_auth,onSuccess = {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            })
        }
        _btnSignUp.setOnClickListener {
            val email = _etSignupEmail.text.toString().trim()
            val password = _etSignUpPassword.text.toString()
            val cPassword = _etSignUpCPassword.text.toString()

            if (email.isEmpty()) {
                _etLoginEmail.error = "Email is required"
                _etLoginEmail.requestFocus()
                return@setOnClickListener
            }else{
                if (password.isEmpty() || cPassword.isEmpty() ||password != cPassword) {
                    _etLoginPassword.error = "Password is required"
                    _etLoginPassword.requestFocus()
                    return@setOnClickListener
                }else{
                    _devnet.firebaseUserRegister(email, password,_auth, onSuccess = {
                        startActivity(Intent(this, MainActivity::class.java))
                        Toast.makeText(this, "User Created successfully!", Toast.LENGTH_SHORT).show()
                        finish()
                    }, onFailure = {
                        Toast.makeText(this, "Registration Completed But Failed to Create Profile", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }

//        Authentication Navigation
        _tvRegister.setOnClickListener{
            _llLogin.visibility = View.GONE
            _llSignUp.visibility = View.VISIBLE
        }
        _tvLogin.setOnClickListener{
            _llLogin.visibility = View.VISIBLE
            _llSignUp.visibility = View.GONE
        }
    }

    private fun initialize(){
        _auth = FirebaseAuth.getInstance()
        _devnet =Devnet(this)
        _etLoginEmail =findViewById(R.id.authentication_TextInputEditText_Email)
        _etLoginPassword =findViewById(R.id.authentication_TextInputEditText_Password)
        _etSignupEmail =findViewById(R.id.authentication_TextInputEditText_SignUpEmail)
        _etSignUpPassword = findViewById(R.id.authentication_TextInputEditText_SignUpPassword)
        _etSignUpCPassword = findViewById(R.id.authentication_TextInputEditText_SignUpCPassword)
        _btnLogin =findViewById(R.id.authentication_Button_Login)
        _btnSignUp = findViewById(R.id.authentication_Button_Signup)
        _tvRegister= findViewById(R.id.authentication_textView_register)
        _tvLogin= findViewById(R.id.authentication_textView_login)
        _llLogin = findViewById(R.id.authentication_linearlayout_login)
        _llSignUp = findViewById(R.id.authentication_linearlayout_SignUp)
    }
}