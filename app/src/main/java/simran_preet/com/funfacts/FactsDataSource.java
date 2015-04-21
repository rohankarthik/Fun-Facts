package simran_preet.com.funfacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jc on 3/22/15.
 */
public class FactsDataSource
{
    private static final String TAG = "FactsDataSource";

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] allcolumns = {DatabaseHelper.ID, DatabaseHelper.FACT};

    public FactsDataSource(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
    }

    public void close()
    {
        databaseHelper.close();
    }

    public Fact addFact(Fact fact)
    {
        database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FACT, fact.getFact());
        values.put(DatabaseHelper.OBJECT_ID, fact.getObjectId());
        long insertId = database.insert(DatabaseHelper.TABLE, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE, allcolumns, DatabaseHelper.ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Fact newFact = cursorToFact(cursor);
        cursor.close();
        database.close();
        return newFact;
    }

    private Fact cursorToFact(Cursor cursor) {
        Fact fact = new Fact();
        fact.setFact(cursor.getString(1));
        fact.setObjectId(cursor.getString(2));
        return fact;
    }


    public List<Fact> getAllFacts()
    {
        database = databaseHelper.getWritableDatabase();
        List<Fact> facts = new ArrayList<>();
        Cursor cursor = database.rawQuery(" SELECT * FROM "+DatabaseHelper.TABLE, null);
        if(cursor.moveToFirst())
        {
            do {
                Fact fact = cursorToFact(cursor);
                facts.add(fact);
            } while(cursor.moveToNext());
        }
        return facts;
    }
}
