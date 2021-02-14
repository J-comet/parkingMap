package hs.project.parkingmap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import hs.project.parkingmap.MainActivity
import hs.project.parkingmap.databinding.FragmentMapBinding
import hs.project.parkingmap.utils.GpsTracker


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun newInstance(): MapFragment {
        return MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.naverMap.getMapAsync(this);

        return binding.root
    }

    override fun onMapReady(naverMap: NaverMap) {
//        locationSource = FusedLocationSource(this, MainActivity.GPS_ENABLE_REQUEST_CODE)
//        naverMap.locationSource = locationSource
//        naverMap.locationTrackingMode = LocationTrackingMode.Follow

//        val marker = Marker()
        val gpsTracker = GpsTracker(requireActivity().baseContext)

        val cameraUpdate: CameraUpdate = CameraUpdate.scrollAndZoomTo(
            LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15.0
        )
            .animate(CameraAnimation.Fly, 3000)

        naverMap.moveCamera(cameraUpdate)  // 지도 이동

        // marker 생성
//        marker.position = LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude())
//        marker.map = naverMap


    }

}