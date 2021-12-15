package mautz.nathan.finalproject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import mautz.nathan.finalproject.ExtraInfoFragment.Companion.newInstance

class DetailsActivity : AppCompatActivity() {

    var frag_manager: FragmentViewManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //get view refs
        val nameTV = findViewById<TextView>(R.id.detailsNameTextView)
        val hoursTV = findViewById<TextView>(R.id.detailsPlaceHoursTextView)
        val fragContainerView = findViewById<FragmentContainerView>(R.id.fragmentContainerView)


        //set up fragment
        val frag = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        if(frag != null)
        {
            supportFragmentManager.beginTransaction().remove(frag).commit();
        }
        frag_manager = FragmentViewManager(fragContainerView, supportFragmentManager)
        val extraInfoFrag = ExtraInfoFragment.newInstance("","")
        val mapsFragment = MapsFragment()
        frag_manager?.setActiveFragment(mapsFragment)

        //Unpack intent and set views
        val intent = intent
        if (intent != null) { // good practice

            /*
                data.putExtra("name", p.name)
                data.putExtra("address", p.formatted_address)
                data.putExtra("review", p.review)
                data.putExtra("rating", p.rating)
                data.putExtra("photo", p.photo_ref)
             */
            val name = intent.getStringExtra("name")
            val rating = intent.getStringExtra("rating")
            val formatted_address = intent.getStringExtra("formatted_address")
            val review = intent.getStringExtra("review")
            val phone_num = intent.getStringExtra("phone_num")
            val open_hours = intent.getStringExtra("open_hours")

            nameTV.text = "$name ($rating⭐️)"
            hoursTV.text = open_hours

        }





    }
}