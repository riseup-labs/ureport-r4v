package com.riseuplabs.ureport_r4v.ui.opinions.media_capture;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.greysonparrelli.permiso.Permiso;
import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseSurveyorActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityCaptureLocationBinding;
import com.riseuplabs.ureport_r4v.utils.surveyor.Logger;
import com.riseuplabs.ureport_r4v.utils.ui.IconTextView;

public class CaptureLocationActivity extends BaseSurveyorActivity<ActivityCaptureLocationBinding> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient locationApiClient;
    private LocationCallback locationCallback;
    private Location lastLocation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture_location;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            @SuppressWarnings("ResourceType")
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (resultSet.areAllPermissionsGranted()) {
                    connectGoogleApi();
                } else {
                    finish();
                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                CaptureLocationActivity.this.showRationaleDialog(R.string.permission_location, callback);
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    onLocationUpdate(locationResult.getLastLocation());
                }
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();

        stopLocationUpdates();
    }

    protected void connectGoogleApi() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    /**
     * Start receiving location updates
     */
    @SuppressWarnings("ResourceType")
    private void startLocationUpdates() {
        Logger.d("Starting location updates...");

        IconTextView button = (IconTextView) getViewCache().getView(R.id.button_capture);
        button.setText(R.string.icon_gps_not_fixed);

        LocationRequest request = new LocationRequest();
        request.setInterval(2000);
        request.setFastestInterval(500);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationApiClient = LocationServices.getFusedLocationProviderClient(this);
        locationApiClient.requestLocationUpdates(request, locationCallback, null);
    }

    private void onLocationUpdate(Location location) {
        Logger.d("Received location update: " + location.toString());

        lastLocation = location;

        IconTextView button = (IconTextView) getViewCache().getView(R.id.button_capture);
        button.setText(R.string.icon_gps_fixed);

        TextView coordinates = (TextView) getViewCache().getView(R.id.text_coordinates);
        coordinates.setText(getString(R.string.latitude_longitude, location.getLatitude(), location.getLongitude()));

        TextView accuracy = (TextView) getViewCache().getView(R.id.text_accuracy);
        accuracy.setVisibility(View.VISIBLE);
        accuracy.setText(getString(R.string.accuracy_meters, (int) location.getAccuracy()));
    }

    /**
     * User clicked the capture button
     *
     * @param view the button
     */
    public void onActionCapture(View view) {
        if (lastLocation != null) {
            Intent data = new Intent();
            data.putExtra("latitude", lastLocation.getLatitude());
            data.putExtra("longitude", lastLocation.getLongitude());
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    /**
     * Stop receiving location updates
     */
    protected void stopLocationUpdates() {
        if (locationApiClient != null) {
            locationApiClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Logger.d("GoogleAPI client connected");

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Logger.d("GoogleAPI client suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Logger.d("GoogleAPI client failed");

        showToast(String.valueOf(R.string.error_google_api));
        finish();
    }
}