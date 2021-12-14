package mautz.nathan.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




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
                //TODO
                Toast.makeText(this, "TODO: gps", Toast.LENGTH_SHORT).show()

                return true // this event has been consumed/handled
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
                val testTV = findViewById<TextView>(R.id.testTV)
                if(testTV.visibility == View.GONE)
                    testTV.visibility = View.VISIBLE
                else
                    testTV.visibility = View.GONE

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}