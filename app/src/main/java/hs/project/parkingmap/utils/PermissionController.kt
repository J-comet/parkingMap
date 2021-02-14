package hs.project.parkingmap.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hs.project.parkingmap.Constant


class PermissionController {

    val PERMISSIONS_REQUEST_CODE = 100
    var preferences: Preferences = Preferences()

    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun checkLocationServicesStatus(activity: Activity): Boolean {
        val locationManager =
            activity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    // OS 마시멜로우 이상일 경우 권한체크
    fun GPS_Permission(activity: Activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var hasFineLocationPermission = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            var hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED
                && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
            ) {

                // permission 확인 완료
                // Preferences 에 저장
                preferences.putSharedPreference(
                    activity,
                    Constant.PREFERNECE_NAME.GPS_PERMISSION, true
                )

            } else {
                // 권한을 거부한 적이 있는 경우 토스트창으로 알려줌
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        REQUIRED_PERMISSIONS[0]
                    )
                ) {
                    Toast.makeText(activity, "앱을 이용하기 위해서는 위치 접근 권한이 필요합니다", Toast.LENGTH_LONG)
                        .show()

                    ActivityCompat.requestPermissions(
                        activity,
                        REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE
                    )
                } else {
                    // 권한을 거부한 적이 없는 경우
                    ActivityCompat.requestPermissions(
                        activity,
                        REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE
                    )
                }
            }
        }
    }
}