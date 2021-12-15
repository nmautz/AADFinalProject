package mautz.nathan.finalproject

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
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

    var place_location: LatLng? = null
    var googleMap: GoogleMap? = null

    private val callback = OnMapReadyCallback { googleMap ->

        this.googleMap = googleMap
        var placeLatLng: LatLng? = null
        if(place_location != null) {
            placeLatLng = LatLng(place_location!!.latitude, place_location!!.longitude)

            googleMap.addMarker(MarkerOptions().position(placeLatLng).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLatLng))
            googleMap.setMinZoomPreference(15F)
            googleMap.setMaxZoomPreference(15F)
        }
        Log.d("testttt", "2")

    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if(arguments != null) {
            val data = arguments
            if (data != null) {
                //Load data from arguments
                val formatted_address = data["formatted_address"] as String?
                val task = GooglePlacesAPI.FindLatLngAsyncTask()
                place_location = task.execute(formatted_address).get()
                Log.d("testttt", "1")
                var placeLatLng: LatLng? = null
                if(place_location != null) {
                    placeLatLng = LatLng(place_location!!.latitude, place_location!!.longitude)

                    googleMap?.addMarker(MarkerOptions().position(placeLatLng).title("Marker in Sydney"))
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLng(placeLatLng))
                    googleMap?.setMinZoomPreference(15F)
                    googleMap?.setMaxZoomPreference(15F)
                }
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