package mautz.nathan.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExtraInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExtraInfoFragment : Fragment() {

    var place: Place? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)






        if(arguments != null)
        {
            val data = arguments
            if(data!=null){
                //Load data from arguments
                val name = data["name"]
                val rating = data["rating"]
                val formatted_address = data["formatted_address"]
                val review = data["review"]
                val phone_num = data["phone_num"]
                val open_hours = data["open_hours"]

                //create place from data
                place = Place(id=null, name = name as String?, rating = rating as String?,
                    formatted_address = formatted_address as String?, review = review as String?,
                    phone_num = phone_num as String?, open_hours = open_hours as String?, photo_ref = null
                    )


            }






        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_extra_info, container, false)
        //Get refs to views
        val addressTV = v?.findViewById<TextView>(R.id.addressTV)
        val reviewTV = v?.findViewById<TextView>(R.id.reviewTV)
        //fill views
        addressTV?.text = place?.formatted_address
        reviewTV?.text = place?.review


        // Inflate the layout for this fragment

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExtraInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExtraInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}