package mautz.nathan.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class GooglePlacesAPI {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String TAG = "PLACESAPITAG";
    public static Location location;


    //Based on a search returns the first 20 results
    //Likely unneeded so marking as deprecated for now
    @Deprecated
    static List<Place> findPlaces(String search)
    {
        FindPlaceAsyncTask task1 = new FindPlaceAsyncTask();
        List<String> place_ids = null;
        List<Place> places = new ArrayList<>();
        try {
            place_ids = task1.execute(search).get();
            for(String pID: place_ids)
            {
                try{
                    FindPlaceDetailAsyncTask task2 = new FindPlaceDetailAsyncTask();
                    FindPlaceImageAsyncTask task3 = new FindPlaceImageAsyncTask();
                    Place p = task2.execute(pID).get();
                    if(p != null)
                        places.add(p);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return places;
    }


    //Executing this task will return a string list of place_ids to be used in FindPlaceDetailAsyncTask to get the place object... takes a string search
    static class FindPlaceAsyncTask extends AsyncTask<String, Void, List<String>>{

        private static URL constructURLFindPlace(String search) {
            URL url = null;
            try {
                url =  new URL(BASE_URL + "nearbysearch/json"
                        + "?keyword=" + search //gives search keyword
                        + "&radius=1500"
                        + "&location=" + location.getLatitude() + "%2C" + location.getLongitude()
                        + "&key=AIzaSyAifgT1bcIKN7qQgHxvCqZqxDWGR8cFDPk"
                );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            Log.d(TAG, "FINISHED ASYNC");
        }

        @Override
        protected List<String> doInBackground(String... strings) {

            List<String> string_list = new ArrayList<>();

            URL url = constructURLFindPlace(strings[0]);
            if(url != null)

            {
                try {

                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                    String jsonResult = "";
                    // character by character we are going to build the json string from an input stream
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int data = reader.read();
                    while (data != -1) {
                        jsonResult += (char) data;
                        data = reader.read();
                    }

                    //Turn result into list of places

                    JSONObject jsonObject = new JSONObject(jsonResult);
                    //Add all places
                    for(int i = 0; i < 20; ++i)
                    {
                        string_list.add(jsonObject.getJSONArray("results").getJSONObject(i).get("place_id").toString());
                    }


                } catch (Exception e){
                    e.printStackTrace();
                }


            }

            return string_list;
        }
    }
    //Executing this task will return a Place based on a place_id string
    static class FindPlaceDetailAsyncTask extends AsyncTask<String, Void, Place>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Place p) {
            super.onPostExecute(p);
            Log.d(TAG, "FINISHED ASYNC");
        }

        @Override
        protected Place doInBackground(String... strings) {
            Place p = null;

            URL url = constructURLFindDetail(strings[0]);
            if(url != null)

            {
                try {

                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                    String jsonResult = "";
                    // character by character we are going to build the json string from an input stream
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int data = reader.read();
                    while (data != -1) {
                        jsonResult += (char) data;
                        data = reader.read();
                    }

                    //Turn result into list of places

                    JSONObject jsonObject = new JSONObject(jsonResult);
                    //Get name string
                    String name = "NO_NAME";
                    try{
                        name = jsonObject.getJSONObject("result").get("name").toString();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    //Get small address string
                    String vicinity = "NO_ADR";
                    try{
                        vicinity = jsonObject.getJSONObject("result").get("vicinity").toString();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    //Get rating string
                    String rating = "NO_RATING";
                    try{
                        rating = jsonObject.getJSONObject("result").get("rating").toString();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    //Get full address string
                    String formatted_address = "NO_ADR";
                    try{
                        formatted_address = jsonObject.getJSONObject("result").get("formatted_address").toString();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    String phone_num = "NO_PHONE";
                    try{
                        phone_num = jsonObject.getJSONObject("result").get("international_phone_number").toString();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    String review = "NO_REVIEW";
                    try{
                        review = jsonObject.getJSONObject("result").getJSONArray("reviews").getJSONObject(0).get("text").toString();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    String photo = "NO_PHOTO";
                    try{
                        String rawData = jsonObject.getJSONObject("result").getJSONArray("photos").getJSONObject(0).get("photo_reference").toString();
                        photo = rawData;
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    //TODO add open hours
                    p = new Place(null, name, rating, formatted_address, review, phone_num ,null, photo);

                } catch (Exception e){
                    e.printStackTrace();
                }


            }

            return p;
        }

        private URL constructURLFindDetail(String place_id) {
            URL url = null;
            try {
                url =  new URL(BASE_URL + "details/json"
                        + "?fields=vicinity%2Cname%2Crating%2Cformatted_address%2Creviews%2Cinternational_phone_number%2Cphotos"
                        + "&place_id=" + place_id
                        + "&key=AIzaSyAifgT1bcIKN7qQgHxvCqZqxDWGR8cFDPk"
                );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        }
    }
    //Executing this task will return a Bitmap based on an image_id
    static class FindPlaceImageAsyncTask extends  AsyncTask<String, Void, Bitmap>{

        private URL constructURL(String photo_ref){
            URL url = null;
            try {
                url =  new URL(BASE_URL + "photo"
                        + "?maxwidth=400"
                        + "&photo_reference=" + photo_ref
                        + "&key=AIzaSyAifgT1bcIKN7qQgHxvCqZqxDWGR8cFDPk"
                );
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bitmap = null;

            URL url = constructURL(strings[0]);
            if(url != null)

            {
                try {

                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                    String jsonResult = "";
                    // character by character we are going to build the json string from an input stream
                    InputStream in = urlConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(in);




                } catch (Exception e){
                    e.printStackTrace();
                }


            }





            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            super.onPostExecute(bm);
        }



    }



}
