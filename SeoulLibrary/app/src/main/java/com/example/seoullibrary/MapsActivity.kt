package com.example.seoullibrary

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.seoullibrary.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import data.Library
import data.Row
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        mMap.setOnMarkerClickListener { marker ->
            val libraryObject = marker.tag as Row
            var homepage = libraryObject.HMPG_URL
            if(homepage.startsWith("http")){
                homepage = "http://$homepage}"
            }
            Intent(Intent.ACTION_VIEW, Uri.parse(homepage)).run{
                startActivity(this)
            }
            true
        }
        // Add a marker in Sydney and move the camera
    //    val sydney = LatLng(-34.0, 151.0)
    //    mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
    //    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        loadLibrary()
    }

    fun loadLibrary() {
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SeoulOpenService::class.java)
        service.getLibrary(SeoulOpenApi.API_KEY).enqueue(object : Callback<Library> {
            override fun onResponse(call: Call<Library>, response: Response<Library>) {
                showLibraries(response.body())
            }

            override fun onFailure(call: Call<Library>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun showLibraries(libraries: Library?) {

        val latLngBounds = LatLngBounds.Builder()

        for (lib in libraries?.SeoulPublicLibraryInfo?.row?: listOf()){
            val position = LatLng(lib.XCNTS.toDouble(), lib.YDNTS.toDouble())
            val marker = MarkerOptions().position(position)
            var markerObject = mMap.addMarker(marker)
            markerObject?.title = lib.LBRRY_NAME

            markerObject?.tag = lib

            //마커들이 보이는 뷰로 지도좌표를 이동시키기 위한 바운드 작업
            latLngBounds.include(marker.position)
        }
        val bounds = latLngBounds.build()
        val padding = 0
        val updatedCamera = CameraUpdateFactory.newLatLngBounds(bounds,padding)
        mMap.moveCamera(updatedCamera)
    }
}