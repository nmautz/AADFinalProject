package mautz.nathan.finalproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import java.sql.SQLClientInfoException

class MainActivity : AppCompatActivity() {

    //Used for Log.d
    private val TAG = "MAINACTIVITYTAG"
    private val LOCATION_REQUEST_CODE = 1

    //Data used in recyclerView
    var places : ArrayList<Place>? = null

    //adapter used in recyclerView
    private var adapter: MainActivity.CustomAdapter? = null

    //used for location services
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var launcher: ActivityResultLauncher<Intent>? = null

    private val locationCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set up recyclerview
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //Set up CustomAdapter for recyclerview
        adapter = CustomAdapter()
        //Connect adapter to recyclerview
        recyclerView.adapter = adapter

        //Setting up location services----------------

        //Checking for location permission
        //TODO deal with no permission
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // we don't have permission, request it
            // this is going to show an alert dialog to the user, asking for their choice
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
        //Sets GooglePlacesAPI's location
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            GooglePlacesAPI.location = location
        }
        //---------------------------------------------

        //Set up details activity launcher
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.d(TAG, "onActivityResult: ")
            // this callback executes when MainActivity returns from
            // starting an activity (e.g. SecondActivity) that was
            // started for a result
            // BRB
            if (result.resultCode == RESULT_OK) {
                val data = result.data
            }
        }


        //Load places from db on launch
        val db = SqlDBOpenHelper(this)
        places = db.selectAllPlaces as ArrayList<Place>?
        adapter?.notifyDataSetChanged()



        //Testing code goes here ----------------------------

        val TTAG = "TESTINGTAG"



        //---------------------------------------------------





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // we need to get a MenuInflater to inflate our main_menu.xml
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.gpsMenuItem -> {
                setUpLastKnownLocation()
                return true
            }
            R.id.searchMenuItem -> {
                val searchET = findViewById<EditText>(R.id.searchBarEditText)
                if(searchET.visibility == View.GONE)
                    searchET.visibility = View.VISIBLE
                else
                    searchET.visibility = View.GONE

                return true
            }
            R.id.downloadMenuItem -> {
                //TODO
                Toast.makeText(this, "TODO: download", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.filterMenuItem -> {
                val testTV = findViewById<TextView>(R.id.filtersTextView)
                if(testTV.visibility == View.GONE)
                    testTV.visibility = View.VISIBLE
                else
                    testTV.visibility = View.GONE

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }






    inner class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {


        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener {
            private val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
            private val distanceTextView = itemView.findViewById<TextView>(R.id.distanceTextView)
            private val addressTextView = itemView.findViewById<TextView>(R.id.addressTextView)


            fun updateView(p: Place) {
                nameTextView.text = "${p.name} (${p.rating})⭐️"
                // TODO: Set the text for the distance of the place from the user
                addressTextView.text = p.formatted_address
            }



            override fun onClick(v: View) {
                Log.d(TAG, "onClick: ${places?.get(adapterPosition)?.name}")

                val p = places?.get(adapterPosition)



                /* //TODO this is just copied from my PA8 code
                val data = Intent(this@MainActivity, DetailsActivity::class.java)
                data.putExtra("name", p.name)
                data.putExtra("address", p.formatted_address)
                data.putExtra("review", p.review)
                data.putExtra("rating", p.rating)
                data.putExtra("photo", p.photo_ref)
                data.putExtra("phone", p.phone_num)
                data.putExtra("bitmap", p.photo_bitmap)
                launcher?.launch(data)
                */

            }

            override fun onLongClick(v: View): Boolean {
                Log.d(TAG, "onLongClick: ")

                //We can remove this later idk if we will want it

                return true
            }

            init {


                // wire 'em up!!
                itemView.setOnClickListener(this)
                itemView.setOnLongClickListener(this)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            // inflate an XML layout for each child view
            // that is wrapped in a CustomViewHolder
            // that will initialize the values for the views in the layout
            // a few ways to set up the layout
            // 1. use a standard layout provided by android
//            View view = LayoutInflater.from(MainActivity.this)
//                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            // 2. use our own custom layout
            val view = LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.card_view_item, parent, false)
            return CustomViewHolder(view)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            // called when a ViewHolder needs to be updated to show
            // the data in the data source at position
            val p: Place? = places?.get(position)
            if (p != null) {
                holder.updateView(p)
            }
        }

        override fun getItemCount(): Int {

            if(places != null)
                return places!!.size
            return -1 //Or throw idk
        }

    }

    // setting up the user's location
    private fun setUpLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        } else {
            val locationTask: Task<Location> = fusedLocationClient.lastLocation
            locationTask.addOnSuccessListener { location ->
                if (location != null) {
                    Toast.makeText(this, "Lat ${location.latitude}, Long ${location.longitude}", Toast.LENGTH_SHORT).show()
                    GooglePlacesAPI.location = location
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpLastKnownLocation()
            }
        }
    }

}