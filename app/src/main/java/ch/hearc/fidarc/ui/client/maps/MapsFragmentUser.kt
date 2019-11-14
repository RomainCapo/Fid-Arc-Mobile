package ch.hearc.fidarc.ui.client.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ch.hearc.fidarc.R
import ch.hearc.fidarc.ui.network.FidarcAPI
import ch.hearc.fidarc.ui.network.FidarcAPIService
import com.birjuvachhani.locus.Locus
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import kotlinx.coroutines.*

class MapsFragmentUser : Fragment(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private var currentUserMarker: Marker?=null //contain the user location marker
    private var isUserLocated=false // indicated if user is already located
    private var client: FidarcAPIService = FidarcAPI.retrofitService // API object

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mapView = inflater.inflate(R.layout.fragment_maps_user, container, false)
        var mapFrag = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)

        return mapView
    }

    /**
     * Read companies informations from de API and create marker for each company on the map
     */
    private fun readCompaniesInfo(){

        GlobalScope.launch(Dispatchers.IO) {
            val companies = client.getCompaniesInfo().data // Read the data from the API
            activity?.runOnUiThread{

                //For each company, we add a marker with the company name and company description
                companies.forEach {
                    mMap.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        ).title(it.company_name).snippet(it.company_description)
                    )
                }
            }
        }
    }

    /**
     * Place the user marker on the map
     *
     * @param latitude latitude of the user
     * @param longitude longitude of the user
     */
    private fun addUserLocation(latitude:Double, longitude:Double){

        currentUserMarker?.remove()// remove the old user marker

        //add the new user location marker
        currentUserMarker = mMap.addMarker(
            MarkerOptions().position(
                LatLng(
                    latitude,
                    longitude)
            ).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation)))
    }

    /**
     * This method is call when the map is ready
     *
     * @param googleMap google map object
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap //init the map

        //Allow to know the user location
        Locus.startLocationUpdates(this) { result ->
            result.location?.let {

                //move the camera on the user location for the first geolocation
                if(!isUserLocated){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude,it.longitude), 10.0f))
                    isUserLocated=!isUserLocated
                }
                addUserLocation(it.latitude, it.longitude)
            }
            result.error?.let { Toast.makeText(context!!, "Error with the geolocation", Toast.LENGTH_SHORT) }
        }

        readCompaniesInfo()
        }
    }