package hs.project.parkingmap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import hs.project.parkingmap.databinding.FragmentMainBinding

class MainFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun newInstance(): MainFragment? {
        return MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.naverMap.getMapAsync(this);

        return binding.root
    }


    override fun onMapReady(p0: NaverMap) {

    }
}