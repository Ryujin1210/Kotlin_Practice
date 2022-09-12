package com.example.googlemaps

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.googlemaps.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val seoul = LatLng(37.5663, 126.9779)
        // 마커 아이콘 만들가
        val descriptor = getDescriptorFromDrawable(R.drawable.marker)
        // 마커
        val marker =MarkerOptions()
            .position(seoul)
            .title("Marker in Seoul")
            .icon(descriptor)

        mMap.addMarker(marker)

        //카메라의 위치
        val cameraOption = CameraPosition.Builder()
            .target(seoul)
            .zoom(18f)
            .build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)

        mMap.moveCamera(camera)
    }
    //마커 만들기 마커 사이즈 함수형 호출 방식
    fun getDescriptorFromDrawable(drawableID: Int) : BitmapDescriptor {
        var bitmapDrawable:BitmapDrawable
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bitmapDrawable = getDrawable(drawableID) as BitmapDrawable
        } else {
            bitmapDrawable = resources.getDrawable(drawableID) as BitmapDrawable
        }
        //마커 크기 변환
        val scaledBitmap = Bitmap.createScaledBitmap(bitmapDrawable.bitmap,100,100,false)
        return BitmapDescriptorFactory.fromBitmap(scaledBitmap)
    }
}