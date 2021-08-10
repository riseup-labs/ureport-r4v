package com.risuplabs.ureport_r4v.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityModule.class,
        NetworkModule.class,
        FragmentModule.class,
        RoomModule.class,
        ViewModelModule.class,
        ApiModule.class
})
public interface AppComponent {

    void inject(SurveyorApplication SurveyorApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }
}