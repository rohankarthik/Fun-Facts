package simran_preet.com.funfacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jc on 3/22/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String TAG = "DatabaseHelper";

    public static final String TABLE = "facts";
    public static final String ID = "id";
    public static final String FACT = "fact";
    public static final String OBJECT_ID = "objectId";


    private static final String DATABASE_NAME = "facts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL);", TABLE, ID, FACT, OBJECT_ID);


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE);
    }

    public void insertFact(String fact)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
