package mautz.nathan.finalproject

import android.graphics.Bitmap

class Place(var name:String, var vicinity:String, var rating:String,
            var formatted_address:String, var review:String, var photo_ref:String, val phone_num:String, var photo_bitmap:Bitmap?) {



    override fun toString(): String {
        return "Place(name='$name')"
    }
}