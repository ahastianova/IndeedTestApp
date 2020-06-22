package com.atruskova.indeedtestapp.ui.register

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.data.models.SampleAppUser
import com.atruskova.indeedtestapp.helper.extensions.CommonExtensions.asAppCompatActivity
import com.atruskova.indeedtestapp.helper.extensions.CommonExtensions.isPermissionActivity
import com.atruskova.indeedtestapp.helper.extensions.CommonExtensions.showMessage
import com.atruskova.indeedtestapp.helper.utils.BiometricPromptUtils
import com.atruskova.indeedtestapp.helper.utils.CiphertextWrapper.Companion.CIPHERTEXT_WRAPPER
import com.atruskova.indeedtestapp.helper.utils.CiphertextWrapper.Companion.SHARED_PREFS_FILENAME
import com.atruskova.indeedtestapp.helper.utils.CryptographyManager
import com.atruskova.indeedtestapp.ui._base.PermissionActivity
import com.atruskova.indeedtestapp.ui.login.LoginFragment
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.launch
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "Register Fragment"
class RegisterFragment : Fragment(), ZXingScannerView.ResultHandler {

    companion object {
        fun newInstance() =
            RegisterFragment()
    }

    private val viewModel: RegisterViewModel by viewModel()
    private lateinit var scannerView: ZXingScannerView
    private var cryptographyManager: CryptographyManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        content_frame.addView(scannerView)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scannerView = object : ZXingScannerView(context){}.apply {
            setAutoFocus(true)
            setSquareViewFinder(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        getCameraPermissions {
            scannerView.setResultHandler(this)
            scannerView.startCamera()
        }
    }
    override fun onResume() {
        super.onResume()
        if (checkForCameraPermissions()) {
            scannerView.startCamera()
        }

    }

    override fun onStop() {
        super.onStop()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        scannerView.stopCamera()
        viewModel.decodeString(rawResult?.text)
    }

    private fun getCameraPermissions(onHasPermissions: () -> Unit) {
        activity?.isPermissionActivity {
            it.requestPermission(Manifest.permission.CAMERA)
            it.launch {
                if (it.permissionChannel.receive()) {
                    onHasPermissions()
                } else {
                    it.showMessage(R.string.permissions_was_forever_denied)
                }
            }
        }
    }

    private fun checkForCameraPermissions() : Boolean {
        activity?.let {
            it as PermissionActivity
            return it.checkPermission(Manifest.permission.CAMERA)
        }
        return false
    }

    private fun initViewModel() {
        viewModel.registerState.observe(viewLifecycleOwner, Observer {
            if (it.isLoading) {
                loading_group.visibility = View.VISIBLE
            } else
                loading_group.visibility = View.GONE
            if (it.success) {
                register_state.text = getString(R.string.register_successful)
                showBiometricPromptForEncryption()

            } else {
                register_state.text = getString(it.error?:R.string.register_undefined_decode_error)
                scannerView.startCamera()
            }
        })
    }

    private fun navigateToLoginFragment() {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

    private fun showBiometricPromptForEncryption() {
        activity?.asAppCompatActivity()?.let {
            val canAuthenticate = BiometricManager.from(it).canAuthenticate()
            if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                val secretKeyName = getString(R.string.secret_key_name)
                cryptographyManager = CryptographyManager()
                val cipher = cryptographyManager!!.getInitializedCipherForEncryption(secretKeyName)
                val biometricPrompt =
                    BiometricPromptUtils.createBiometricPrompt(it, ::encryptAndStoreServerToken)
                val promptInfo = BiometricPromptUtils.createPromptInfo(it)
                biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
            }
        }
    }

    private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
        authResult.cryptoObject?.cipher?.apply {
            activity?.let {
                SampleAppUser.password?.let { password ->
                    Log.d(TAG, "The password is $password")
                    val encryptedServerTokenWrapper = cryptographyManager!!.encryptData(password, this)
                    cryptographyManager!!.persistCiphertextWrapperToSharedPrefs(
                        encryptedServerTokenWrapper,
                        it,
                        SHARED_PREFS_FILENAME,
                        Context.MODE_PRIVATE,
                        CIPHERTEXT_WRAPPER
                    )
                }

                val handler = Handler()
                handler.postDelayed({
                    navigateToLoginFragment()
                }, 2000)
            }

        }
    }

}