package com.raytalktech.scanerme.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.raytalktech.scanerme.R;
import com.raytalktech.scanerme.config.AppConfig;
import com.raytalktech.scanerme.config.Constant;
import com.raytalktech.scanerme.databinding.ActivityLoginBinding;
import com.raytalktech.scanerme.ui.home.HomeActivity;
import com.raytalktech.scanerme.utils.GeneralHelper;
import com.raytalktech.scanerme.utils.ProgressbarUtil;
import com.raytalktech.scanerme.viewmodel.ViewModelFactory;

public class LoginActivity extends AppCompatActivity implements TextWatcher {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(view -> {
            ProgressbarUtil progress = new ProgressbarUtil(this);

            viewModel.setRequestLogin(binding.etUsername.getText().toString(), binding.etPassword.getText().toString());

            viewModel.getResultLogin().observe(this, result -> {
                try {
                    if (result.body != null) {
                        switch (result.status) {
                            case SUCCESS:
                                HomeActivity.lunchIntent(LoginActivity.this, result.body);
                                progress.dismiss();
                                finish();
                            case EMPTY:
                            case ERROR:
                                GeneralHelper.showToast(this, result.body.getMessage());
                                progress.dismiss();
                                break;
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onCreate: " + e.getLocalizedMessage());
                    GeneralHelper.showToast(this, AppConfig.GeneralResponseMessage.RC_03);
                }
            });
        });

        //addTextChangedListener
        binding.etUsername.addTextChangedListener(this);
        binding.etPassword.addTextChangedListener(this);
    }

    private void validate() {
        boolean isValid;

        //checkLength
        int userNameLength = binding.etUsername.getText().toString().length();
        int passwordLength = binding.etPassword.getText().toString().length();

        isValid = userNameLength != 0 && passwordLength != 0;

        binding.btnLogin.setEnabled(isValid);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        validate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.HOME_MENU && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}