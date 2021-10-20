package com.risuplabs.ureport_r4v.ui.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityProgramChooserBinding;
import com.risuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport_r4v.utils.AppConstant;
import com.risuplabs.ureport_r4v.utils.Navigator;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

public class ProgramChooserActivity extends BaseActivity<ActivityProgramChooserBinding> {

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
            Navigator.navigate(ProgramChooserActivity.this, DashBoardActivity.class);
            finish();

        });

    }
}