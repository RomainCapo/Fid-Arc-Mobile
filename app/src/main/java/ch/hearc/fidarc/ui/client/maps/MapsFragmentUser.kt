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
    private var currentUserMarker: Marker?=null
    var isUserLocated=false
    var client: FidarcAPIService = FidarcAPI.retrofitService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mapView = inflater.inflate(R.layout.fragment_maps_user, container, false)
        var mapFrag = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)

        return mapView
    }

    private fun readCompaniesInformations(){

        GlobalScope.launch(Dispatchers.IO) {
            val companies = client.getCompaniesInfo().data
            activity?.runOnUiThread{
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

    private fun addUserLocation(latitude:Double, longitude:Double){

        currentUserMarker?.remove()

        currentUserMarker = mMap.addMarker(MarkerOptions().position(LatLng(latitude,longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation)))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        
        Locus.startLocationUpdates(this) { result ->
            result.location?.let {
                if(!isUserLocated){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude,it.longitude), 10.0f))
                    isUserLocated=!isUserLocated
                }
                addUserLocation(it.latitude, it.longitude)
            }
            result.error?.let { Toast.makeText(context!!, "Error with the geolocation", Toast.LENGTH_SHORT) }
        }

        readCompaniesInformations()
        }
    }