package simran_preet.com.funfacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;


public class FunFactsActivity extends ActionBarActivity
{
    private static final String TAG = "FunFactsActivity";
    private TextView factLabel;
    private Button showFactButton;
    private LinearLayout relativeLayout;
    private LinearLayout factLayout;
    private List<String> facts;
    private FactsDataSource dataSource;
    private Fact currentFact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "N47R3qB0x9lXjiwlnffFJKHXwCsqWJS4hOrk0yZT", "VhUxTyrZCgg0cP5VtlcWPTnuMamV7SwEGPY6EyFi");

        factLabel = (TextView) findViewById(R.id.factTextView);
        showFactButton = (Button) findViewById(R.id.showFactButton);
        relativeLayout = (LinearLayout) findViewById(R.id.backgroundView);
        factLayout = (LinearLayout) findViewById(R.id.factLayout);

        FetchFacts fetchFacts = new FetchFacts();
        fetchFacts.execute();

        setColors();
        showFactButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setRandomFactAndColor();
            }
        });

        try {
            dataSource = new FactsDataSource(this);
        } catch (Exception e) {
            Log.e("FunFactActivity", "Exception for Open SQLite DB");
        }
    }

    public void setRandomFactAndColor()
    {
        currentFact = FactBook.getInstance().getRandomFact();
        factLabel.setText(currentFact.getFact());
        setColors();
    }

    public String setColors()
    {
        String colorValue = FactColors.getInstance().getRandomColor();
        int colorCode = Color.parseColor(colorValue);
        relativeLayout.setBackgroundColor(colorCode);
        factLayout.setBackgroundColor(colorCode);
        showFactButton.setTextColor(colorCode);
        return colorValue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_fun_facts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            addFactToFavorite();
            return true;
        } else if (id == R.id.action_show) {
            Intent intent = new Intent(this, FavoriteFactsActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void addFactToFavorite()
    {
        Fact favoriteFact = new Fact();
        favoriteFact.setObjectId(currentFact.getObjectId());
        favoriteFact.setFact(currentFact.getFact());

        dataSource.addFact(favoriteFact);
        Toast.makeText(this, favoriteFact.toString(), Toast.LENGTH_SHORT).show();
    }

    private class FetchFacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            FactBook.getInstance().retrieveFactsFromParse();
            return null;
        }
    }
}
