package com.riseuplabs.ureport_r4v.utils.custom_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.widget.RadioGroup;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

public class LocationChooser {

    public static void showDialog(Activity activity, SharedPrefManager prefManager) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_location_chooser);

        RadioGroup rg = dialog.findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.btnBrasil:
                        prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.BRAZIL_LABEL);
                        prefManager.putInt(PrefKeys.ORG_ID,AppConstant.BRAZIL_ORG_ID);
                        Navigator.navigate(activity, DashBoardActivity.class);
                        activity.finish();
                        break;
                    case R.id.btnEcuador:
                        prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.ECUADOR_LABEL);
                        prefManager.putInt(PrefKeys.ORG_ID,AppConstant.ECUADOR_ORG_ID);
                        Navigator.navigate(activity, DashBoardActivity.class);
                        activity.finish();
                        break;
                    case R.id.btnBolivia:
                        prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.BOLIVIA_LABEL);
                        prefManager.putInt(PrefKeys.ORG_ID,AppConstant.BOLIVIA_ORG_ID);
                        Navigator.navigate(activity, DashBoardActivity.class);
                        activity.finish();
                        break;

                }
            }
        });

        dialog.show();

    }

}
