package com.riseuplabs.ureport_r4v.ui.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityProgramChooserBinding;
import com.riseuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

public class ProgramChooserActivity extends BaseActivity<ActivityProgramChooserBinding> {

    @Override
    protected void onStart() {
        super.onStart();
        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY,"rBO"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_program_chooser;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        int org = 0;

        binding.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.btnBrasil:
                        prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.BRAZIL_LABEL);
                        prefManager.putInt(PrefKeys.ORG_ID,AppConstant.BRAZIL_ORG_ID);
                        binding.next.setVisibility(View.VISIBLE);
                        break;
                    case R.id.btnEcuador:
                        prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.ECUADOR_LABEL);
                        prefManager.putInt(PrefKeys.ORG_ID,AppConstant.ECUADOR_ORG_ID);
                        binding.next.setVisibility(View.VISIBLE);
                        break;
                    case R.id.btnBolivia:
                        prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.BOLIVIA_LABEL);
                        prefManager.putInt(PrefKeys.ORG_ID,AppConstant.BOLIVIA_ORG_ID);
                        binding.next.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

        binding.next.setOnClickListener(v->{
            prefManager.putString(PrefKeys.LOGIN,"yes");
            Navigator.navigate(ProgramChooserActivity.this, DashBoardActivity.class);
            finish();

        });

    }
}