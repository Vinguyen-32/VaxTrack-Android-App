package CMPE133_project.vaxtrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, "personalinfo1.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table PersonalInfo(firstName TEXT, lastName TEXT, mobileNumber TEXT, address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists PersonalInfo");
    }

    public Boolean insertuserdata(String firstName, String lastName, String mobileNumber, String address, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("mobileNumber", mobileNumber);
        contentValues.put("address", address);
        contentValues.put("dob", dob);
        long result = DB.insert("PersonalInfo", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

//    public Boolean updateuserdata(String name, String contact, String dob) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("contact", contact);
//        contentValues.put("dob", dob);
//        Cursor cursor = DB.rawQuery("Select * from PersonalInfo where name = ?", new String[]{name});
//        if (cursor.getCount() > 0) {
//
//            long result = DB.update("PersonalInfo", contentValues, "name=?", new String[]{name});
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }
//
//    public Boolean deleteuserdata(String name) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//
//        Cursor cursor = DB.rawQuery("Select * from PersonalInfo where name = ?", new String[]{name});
//        if (cursor.getCount() > 0) {
//
//            long result = DB.delete("PersonalInfo", "name=?", new String[]{name});
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }
//
//    public Cursor getdata() {
//        SQLiteDatabase DB = this.getWritableDatabase();
//
//        Cursor cursor = DB.rawQuery("Select * from PersonalInfo", null);
//        return cursor;
//    }




}
