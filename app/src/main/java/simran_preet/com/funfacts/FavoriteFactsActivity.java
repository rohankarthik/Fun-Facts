package simran_preet.com.funfacts;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class FavoriteFactsActivity extends ActionBarActivity
{
    private static final String TAG = "FavoriteFactsActivity";

    private FactsDataSource dataSource;
    private ListView factsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_facts);
        factsList = (ListView) findViewById(R.id.listView);


        try {
            dataSource = new FactsDataSource(this);
            List<Fact> facts = dataSource.getAllFacts();
            ArrayAdapter<List> adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, facts);
            factsList.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("FavoriteFactsActivity", "Exception for Open SQLite DB");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite_facts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
