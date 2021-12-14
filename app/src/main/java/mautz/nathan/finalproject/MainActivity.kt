package mautz.nathan.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val TAG = "MAINACTIVITYTAG"

    var places : ArrayList<Place>? = null

    private var adapter: MainActivity.CustomAdapter? = null


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


        //Testing code goes here ----------------------------
        places = ArrayList<Place>()
        places?.add(
            Place("Word", "Word", "5",
        "addy", "So good dude", "No dude",
            "425-299-8171", null)
        )
        adapter?.notifyDataSetChanged()
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






    inner class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {


        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener {
            private val nameTextView = itemView.findViewById<TextView>(R.id.cardNameTextView)
            private val addressTextView = itemView.findViewById<TextView>(R.id.cardAddressTextView)


            fun updateView(p: Place) {
                nameTextView.text = "${p.name} (${p.rating})⭐️"
                addressTextView.text = p.vicinity
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
                // a demo of alert dialogs
                // use the AlertDialog.Builder class and method chaining
                // to set up an alert dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Item Long Clicked")
//                        .setMessage("You clicked on an item")
//                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, "OKAY", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("Dismiss", null);
//                builder.show();

                return true // false means this callback did not "consume" the event
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
                .inflate(R.layout.card_view_list_item, parent, false)
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
}