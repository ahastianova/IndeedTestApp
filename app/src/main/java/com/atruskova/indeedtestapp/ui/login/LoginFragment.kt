package com.atruskova.indeedtestapp.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.data.models.SampleAppUser
import com.atruskova.indeedtestapp.helper.extensions.CommonExtensions.asAppCompatActivity
import com.atruskova.indeedtestapp.helper.extensions.afterTextChanged
import com.atruskova.indeedtestapp.helper.extensions.getString
import com.atruskova.indeedtestapp.helper.utils.BiometricPromptUtils
import com.atruskova.indeedtestapp.helper.utils.CiphertextWrapper
import com.atruskova.indeedtestapp.helper.utils.CiphertextWrapper.Companion.CIPHERTEXT_WRAPPER
import com.atruskova.indeedtestapp.helper.utils.CiphertextWrapper.Companion.SHARED_PREFS_FILENAME
import com.atruskova.indeedtestapp.helper.utils.CryptographyManager
import com.atruskova.indeedtestapp.ui.MainActivity
import com.atruskova.indeedtestapp.ui.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var biometricPrompt: BiometricPrompt
    private val cryptographyManager = CryptographyManager()
    private var cipherTextWrapper: CiphertextWrapper? = null

    private lateinit var viewModel: LoginViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cipherTextWrapper = cryptographyManager.getCiphertextWrapperFromSharedPrefs(
            context,
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            CIPHERTEXT_WRAPPER
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViewModel() {
        viewModel.loginFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            if (loginState.usernameError != null)
                til_username.error = getString(loginState.usernameError)
            else
                til_username.error = null
            if (loginState.passwordError != null)
                til_password.error = getString(loginState.passwordError)
            else
                til_password.error = null

        })

        viewModel.loginButtonEnable.observe(this, Observer {
            login.isEnabled = it
        })


        viewModel.loginResult.observe(this, Observer
        {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                navigateToMainActivity()
            }

        })
    }

    private fun initViews() {
        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.getString(),
                password.getString()
            )
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    username.getString(),
                    password.getString()
                )
            }
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.login(
                            username.getString(),
                            password.getString()
                        )
                }
                false
            }
        }

        login.setOnClickListener {
            viewModel.login(username.getString(), password.getString())
        }

        register.setOnClickListener {
            navigateToRegisterFragment()
        }

        use_biometrics.setOnClickListener {
            showBiometricPromptForDecryption()
        }
    }


    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(activity, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(activity, MainActivity::class.java))
    }

    private fun navigateToRegisterFragment() {

        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .addToBackStack(this.tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.container, RegisterFragment.newInstance())
                .hide(this)
                .commit()
        }
    }

    private fun showBiometricPromptForDecryption() {
        cipherTextWrapper?.let { textWrapper ->
            activity?.asAppCompatActivity()?.let {
                val canAuthenticate = BiometricManager.from(it).canAuthenticate()
                if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                    val secretKeyName = getString(R.string.secret_key_name)
                    val cipher = cryptographyManager.getInitializedCipherForDecryption(
                        secretKeyName, textWrapper.initializationVector
                    )
                    biometricPrompt =
                        BiometricPromptUtils.createBiometricPrompt(
                            it,
                            ::decryptPasswordFromStorage
                        )
                    val promptInfo = BiometricPromptUtils.createPromptInfo(it)
                    biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
                }
            }

        }
    }

    private fun decryptPasswordFromStorage(authResult: BiometricPrompt.AuthenticationResult) {
        cipherTextWrapper?.let { textWrapper ->
            authResult.cryptoObject?.cipher?.let {
                val plaintext =
                    cryptographyManager.decryptData(textWrapper.ciphertext, it)
                SampleAppUser.password = plaintext
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

}