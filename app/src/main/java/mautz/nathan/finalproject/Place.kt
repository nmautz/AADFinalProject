package mautz.nathan.finalproject

import android.graphics.Bitmap
import java.lang.Exception
import java.math.BigDecimal

class Place(val id: Long?, var name:String, var rating:String,
            var formatted_address:String, var review:String, val phone_num:String, val open_hours: String?, photo_ref:String?) {
    var bitmap: Bitmap? = null

    init {
        if(photo_ref != null)
        {
            try{
                val task = GooglePlacesAPI.FindPlaceImageAsyncTask()
                bitmap = task.execute(photo_ref).get()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    override fun toString(): String {
        return "Place(name='$name')"
    }


}