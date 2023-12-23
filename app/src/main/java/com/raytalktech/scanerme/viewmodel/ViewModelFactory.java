package com.raytalktech.scanerme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.raytalktech.scanerme.data.AppRepository;
import com.raytalktech.scanerme.di.Injection;
import com.raytalktech.scanerme.ui.login.LoginViewModel;
import com.raytalktech.scanerme.ui.scan.ScanViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;
    private final Application application;
    private final AppRepository appRepository;

    public ViewModelFactory(Application application, AppRepository appRepository) {
        this.application = application;
        this.appRepository = appRepository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(application, Injection.provideRepository(application.getApplicationContext()));
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(appRepository);
        }
        if (modelClass.isAssignableFrom(ScanViewModel.class)) {
            return (T) new ScanViewModel(appRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
