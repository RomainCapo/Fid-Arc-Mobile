package ch.hearc.fidarc.ui.maps

import android.app.DownloadManager
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ch.hearc.fidarc.R
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

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentUserMarker: Marker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mapView = inflater.inflate(R.layout.fragment_maps, container, false)
        var mapFrag = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        getLastKnownLocation()

        return mapView
    }

    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->

                if(currentUserMarker != null){
                    currentUserMarker.remove()
                }

                if (location != null) {
                    currentUserMarker = mMap.addMarker(MarkerOptions().position(LatLng(location.latitude,location.longitude)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                }

            }

    }

    private fun locationThread(){

        var task = Runnable {
            getLastKnownLocation()
        }
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(task, 0,1, TimeUnit.SECONDS)
    }

    private fun readCompaniesInformations(){
        val url = "http://10.0.2.2/testApiMobile/companies.php"


        Executors.newSingleThreadExecutor().execute {
            var json = URL(url).readText()

            var jsonArray = JSONArray(json)

            activity?.runOnUiThread{
                for(jsonIndex in 0..(jsonArray.length()-1)){
                    var latitude = jsonArray.getJSONObject(jsonIndex).getString("latitude").toDouble()
                    var longitude = jsonArray.getJSONObject(jsonIndex).getString("longitude").toDouble()
                    var companyName = jsonArray.getJSONObject(jsonIndex).getString("company_name")
                    var companyDescription = jsonArray.getJSONObject(jsonIndex).getString("description")
                    mMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(companyName).snippet(companyDescription))
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(46.992006,6.930921), 10.0f))

        readCompaniesInformations()
        locationThread()
        }
    }