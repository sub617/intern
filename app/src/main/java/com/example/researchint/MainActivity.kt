package com.example.researchint


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.internship2.api.ApiUtilities
import com.example.internship2.api.MyCoordinatesModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import okhttp3.ResponseBody
import retrofit2.Callback

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var toolbar: Toolbar

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var mMap: GoogleMap
    private lateinit var lastlocal: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var myCoordinatesModel: MyCoordinatesModel
    lateinit var latitude:String
    lateinit var longitude:String

    companion object{
        private const val LOCATION_REQUEST_CODE=1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbarMember)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout_main)
        navigationView = findViewById(R.id.nav_view_main)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar ,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white);
        toggle.syncState()


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled=true

        mMap.setOnMarkerClickListener (this)
        setupMap()
    }

    private fun setupMap() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled=true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->

            if(location !=null){
                lastlocal = location
                val currentLatLng = LatLng(location.latitude,location.longitude)
                placeMarkerOnmap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,12f))

                latitude=location.latitude.toString()
                longitude=location.longitude.toString()



                createUser();
            }
        }
    }

    private fun placeMarkerOnmap(currentLatLng: LatLng) {

        val markerOptions = MarkerOptions().position(currentLatLng)
        markerOptions.title("$currentLatLng")
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker): Boolean =false

    private fun createUser(){

        myCoordinatesModel = MyCoordinatesModel()

        myCoordinatesModel.lat = latitude
        myCoordinatesModel.lon = longitude
        Log.d("COORDINATES" , "The lat is "+latitude+"  and the longitude is "+longitude)

        ApiUtilities.apiInterface
            .createUser(myCoordinatesModel)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseBody>,
                    response: retrofit2.Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext , "Success" , Toast.LENGTH_LONG).show()
                        Log.d("STATUS" , "Success")
                    }
                    else {
                        Toast.makeText(applicationContext , "Unsuccessful - Failed" , Toast.LENGTH_LONG).show()
                        Log.d("STATUS" , "Unsuccessful - Failed")
                    }

                }

                override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                    Log.d("charge" , "Failure"+"  "+t.message)
                    Toast.makeText(applicationContext , "Failure" , Toast.LENGTH_LONG).show()

                }

            })



    }
}