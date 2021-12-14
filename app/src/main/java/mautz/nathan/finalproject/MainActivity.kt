package mautz.nathan.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
        val itemId = item.itemId
        when (itemId) {
            R.id.gpsMenuItem -> {
                //TODO
                Toast.makeText(this, "TODO: gps", Toast.LENGTH_SHORT).show()

                return true // this event has been consumed/handled
            }
            R.id.searchMenuItem -> {
                //TODO
                Toast.makeText(this, "TODO: search", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.downloadMenuItem -> {
                //TODO
                Toast.makeText(this, "TODO: download", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.filterMenuItem -> {
                //TODO
                Toast.makeText(this, "TODO: filter", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}