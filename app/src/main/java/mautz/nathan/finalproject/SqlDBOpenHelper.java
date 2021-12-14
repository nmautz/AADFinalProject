package mautz.nathan.finalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SqlDBOpenHelper extends SQLiteOpenHelper {

    static final String TAG = "SqlDBOpenHelper";

    static final String DATABASE_NAME = "placesDatabase.db";
    static final int DATABASE_VERSION = 1;
    //TODO update for place
    static final String PLACES_TABLE = "tablePlaces";
    static final String ID = "_id"; // by convention
    static final String NAME = "name";
    static final String VICINITY = "vicinity";
    static final String RATING = "rating";
    static final String FORMATTED_ADDRESS = "formatted_address";
    static final String REVIEW = "review";
    static final String PHONE_NUM = "phone_num";
    static final String BITMAP = "bitmap";

    public SqlDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // this is where we create our tables
        // it is only called one time
        // right before the first call to getWriteableDatabase()
        // we will have one table
        // we need to construct a SQL statement to
        // create our tableContacts table
        // SQL: structured query language
        String sqlCreate = "CREATE TABLE " + PLACES_TABLE +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                VICINITY + " TEXT, " +
                RATING + " TEXT, " +
                FORMATTED_ADDRESS + " TEXT, " +
                REVIEW + " TEXT, " +
                PHONE_NUM + " TEXT, " +
                BITMAP + " BLOB)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        // execute the statement
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertPlace(Place p) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, p.getName());
        contentValues.put(VICINITY, p.getVicinity());
        contentValues.put(RATING, p.getRating());
        contentValues.put(FORMATTED_ADDRESS, p.getFormatted_address());
        contentValues.put(REVIEW, p.getReview());
        contentValues.put(PHONE_NUM,p.getPhone_num());

        if(p.getPhoto_bitmap() != null)
            contentValues.put(BITMAP, DbBitmapUtility.getBytes(p.getPhoto_bitmap()));
        else
            contentValues.put(BITMAP, (byte[]) null);


        SQLiteDatabase db = getWritableDatabase();
        db.insert(PLACES_TABLE, null, contentValues);
        // don't forget to close the database!!
        db.close();
    }

    // helper method
    public Cursor getSelectAllCursor() {
        // return a cursor for stepping through all records in our table
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLACES_TABLE,
                new String[]{ID, NAME, VICINITY, RATING, FORMATTED_ADDRESS, REVIEW, PHONE_NUM, BITMAP},
                null, null, null, null, null);
        // don't close the database, the cursor needs it open
        return cursor;
    }

    public List<Place> getSelectAllPlaces() {
        List<Place> places = new ArrayList<>();
        Cursor cursor = getSelectAllCursor();
        // the cursor starts "one before" the first record
        // in case there is no first record
        while (cursor.moveToNext()) { // false when there are no more records to process
            // parse to the column data for the current cursor record
            // into a Contact object
            int id = cursor.getInt(0);

            String name = cursor.getString(1);
            String vicinity = cursor.getString(2);
            String rating = cursor.getString(3);
            String formatted_address = cursor.getString(4);
            String review = cursor.getString(5);
            String phone_num = cursor.getString(6);
            Bitmap bitmap = DbBitmapUtility.getImage(cursor.getBlob(7));


            Place p = new Place((long) id, name, vicinity, rating, formatted_address, review, null ,phone_num, bitmap);
            places.add(p);
        }
        return places;
    }

    public Place getSelectPlaceById(int idParam) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLACES_TABLE, new String[]{ID,
                        NAME,
                        VICINITY,
                        RATING,
                        FORMATTED_ADDRESS,
                        REVIEW,
                        PHONE_NUM,
                        BITMAP},
                ID + "=?", new String[]{"" + idParam}, null, null, null);
        Place p = null;
        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);

            String name = cursor.getString(1);
            String vicinity = cursor.getString(2);
            String rating = cursor.getString(3);
            String formatted_address = cursor.getString(4);
            String review = cursor.getString(5);
            String phone_num = cursor.getString(6);
            Bitmap bitmap = DbBitmapUtility.getImage(cursor.getBlob(7));


            p = new Place((long) id, name, vicinity, rating, formatted_address, review, null ,phone_num, bitmap);
        }
        return p;
    }

    public long getIDbyTitle(String title)
    {
        /*TODO convert from video to place
        List<Video> videos = getSelectAllPlaces();
        for(int i = 0; i < videos.size(); ++i)
        {
            if(videos.get(i).getTitle().equals(title))
            {
                return videos.get(i).getId();
            }

        }
        return -1;

         */
        return 0;//Delete this line
    }

    public void updateVideoAtId(long id, Place place)
    {
        /*TODO convert from video to place

        byte[] bitmapBytes = DbBitmapUtility.getBytes(place.getBitmap());

        String idStr = Integer.toString((int) id);
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, place.getTitle());
        contentValues.put(TYPE, place.getType());
        contentValues.put(BITMAP, bitmapBytes);
        contentValues.put(WATCHED, place.isWatched());

        SQLiteDatabase db = getWritableDatabase();
        db.update(VIDEOS_TABLE, contentValues, "_id = ?", new String[]{idStr});
        db.close();

         */
    }

    public void deletePlaceById(long id)
    {
        String idStr = Integer.toString((int) id);
        SQLiteDatabase db = getWritableDatabase();
        //TODO update delete args
        db.delete(PLACES_TABLE, "_id = ?", new String[]{idStr});
        db.close();
    }

    private static class DbBitmapUtility {

        // convert from bitmap to byte array
        public static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

        // convert from byte array to bitmap
        public static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }
}
