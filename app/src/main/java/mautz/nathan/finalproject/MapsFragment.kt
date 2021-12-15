package mautz.nathan.finalproject

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mautz.nathan.finalproject.GooglePlacesAPI.location

class MapsFragment : Fragment() {

    var formatted_address: String? = null

    private val callback = OnMapReadyCallback { googleMap ->


        val placeLatLng = LatLng(GooglePlacesAPI.location.latitude, GooglePlacesAPI.location.longitude )
        googleMap.addMarker(MarkerOptions().position(placeLatLng).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLatLng))
        googleMap.setMinZoomPreference(15F)
        googleMap.setMaxZoomPreference(15F)

    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if(arguments != null) {
            val data = arguments
            if (data != null) {
                //Load data from arguments
                formatted_address = data["formatted_address"] as String?
            }
        }


    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}