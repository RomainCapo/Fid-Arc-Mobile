package ch.hearc.fidarc.ui.maps

import android.Manifest
import android.app.DownloadManager
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ch.hearc.fidarc.R
import com.birjuvachhani.locus.Locus
import com.google.android.gms.location.*
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import java.net.URL
import java.util.concurrent.Executors
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MapsFragment : Fragment(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private lateinit var currentUserMarker: Marker


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mapView = inflater.inflate(R.layout.fragment_maps, container, false)
        var mapFrag = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)

        return mapView
    }

    private fun readCompaniesInformations(){
        val url = "http://10.0.2.2:8000/testApiMobile/companies.php"


        Executors.newSingleThreadExecutor().execute {
            var json = URL(url).readText()

            var jsonArray = JSONArray(json)

            activity?.runOnUiThread{
                for(jsonIndex in 0 until(jsonArray.length()-1)){
                    var latitude = jsonArray.getJSONObject(jsonIndex).getString("latitude").toDouble()
                    var longitude = jsonArray.getJSONObject(jsonIndex).getString("longitude").toDouble()
                    var companyName = jsonArray.getJSONObject(jsonIndex).getString("company_name")
                    var companyDescription = jsonArray.getJSONObject(jsonIndex).getString("description")
                    mMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(companyName).snippet(companyDescription))
                }
            }
        }
    }

    private fun addUserLocation(latitude:Double, longitude:Double){

        if(currentUserMarker != null)
        {
            currentUserMarker.remove()
        }

        currentUserMarker = mMap.addMarker(MarkerOptions().position(LatLng(latitude,longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation)))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(46.992006,6.930921), 10.0f))

        currentUserMarker = mMap.addMarker(MarkerOptions().position(LatLng(46.993742, 6.932406)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation)))

        Locus.startLocationUpdates(this) { result ->
            result.location?.let { addUserLocation(it.latitude, it.longitude) }
            result.error?.let { Toast.makeText(context!!, "Error with the geolocation", Toast.LENGTH_SHORT) }
        }

        //readCompaniesInformations()
        }
    }