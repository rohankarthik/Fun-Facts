package simran_preet.com.funfacts;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseObject;


public class AddFactActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fact);

//        FactBook.getInstance().retrieveFactsFromParse();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "N47R3qB0x9lXjiwlnffFJKHXwCsqWJS4hOrk0yZT", "VhUxTyrZCgg0cP5VtlcWPTnuMamV7SwEGPY6EyFi");


        for(String fact: FactBook.getInstance().getFacts())
        {
            ParseObject parseObject = new ParseObject("FactsDB");
            parseObject.put("fact", fact);
            parseObject.saveInBackground();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_fact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
