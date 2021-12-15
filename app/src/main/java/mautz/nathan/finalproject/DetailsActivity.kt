package mautz.nathan.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val nameTV = findViewById<TextView>(R.id.detailsNameTextView)
        val hoursTV = findViewById<TextView>(R.id.detailsPlaceHoursTextView)



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