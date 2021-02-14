package hs.project.parkingmap

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import hs.project.parkingmap.utils.PermissionController
import hs.project.parkingmap.utils.Preferences


class MainActivity : AppCompatActivity() {

    private var permissionController: PermissionController = PermissionController()
    private var preferences: Preferences = Preferences()

    companion object{
        internal const val GPS_ENABLE_REQUEST_CODE = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if (!permissionController.checkLocationServicesStatus(this)) {
            showDialogForLocationServiceSetting()
        } else {

            if (!preferences.getBooleanSharedPreference(this, Constant.PREFERNECE_NAME.GPS_PERMISSION)
            ) {
                permissionController.GPS_Permission(this)
            }
        }
    }

    //GPS 활성화를 위한 메소드
    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            " 앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "위치 설정을 수정하시겠습니까?".trimIndent()
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { dialog, id ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(
                callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE
            )
        }
        builder.setNegativeButton(
            "취소"
        ) { dialog, id ->
            dialog.cancel()
//            Toast.makeText(this, "위치서비스를 사용해야 내 주변의 주차장을 확인할 수 있습니다", Toast.LENGTH_LONG).show()
        }
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                //사용자가 GPS 활성 시켰는지 검사
                if (permissionController.checkLocationServicesStatus(this)) {
                    if (permissionController.checkLocationServicesStatus(this)) {
                        Log.d("hs", "onActivityResult : GPS 활성화")
                        permissionController.GPS_Permission(this)
                        return
                    }
                }
        }
    }


    /**
     * ActivityCompat.requestPermissions 퍼미션 요청 결과를 리턴 받음
     */
    override fun onRequestPermissionsResult(
        permsRequestCode: Int,
        permissions: Array<String?>,
        grandResults: IntArray
    ) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults)
        if (permsRequestCode == permissionController.PERMISSIONS_REQUEST_CODE && grandResults.size == permissionController.REQUIRED_PERMISSIONS.size) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            var check_result = true

            // 모든 퍼미션을 허용했는지 체크
            for (result in grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {

                //위치 값을 가져올 수 있음;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        permissionController.REQUIRED_PERMISSIONS.get(0)
                    )
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        permissionController.REQUIRED_PERMISSIONS.get(1)
                    )
                ) {
                    Toast.makeText(
                        this,
                        "위치 접근 권한을 허용해줘야 사용가능합니다",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "권한이 거부되었습니다\n설정(앱 정보)에서 권한을 허용해야 합니다",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}