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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
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

        // in Activity Context
        ImageView icon = new ImageView(this); // Create an icon

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.ic_share200)
                .build();


        ImageView iconFacebook = new ImageView(this);
        ImageView iconTwitter = new ImageView(this);
        ImageView iconInstagram = new ImageView(this);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton buttonFacebook = itemBuilder.setContentView(iconFacebook).build();
        buttonFacebook.setBackgroundResource(R.drawable.ic_facebook200);

        SubActionButton buttonTwitter = itemBuilder.setContentView(iconTwitter).build();
        buttonTwitter.setBackgroundResource(R.drawable.ic_twitter200);

        SubActionButton buttonInstagram = itemBuilder.setContentView(iconInstagram).build();
        buttonInstagram.setBackgroundResource(R.drawable.ic_instagram200);

        FloatingActionMenu socialShareMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonFacebook)
                .addSubActionView(buttonTwitter)
                .addSubActionView(buttonInstagram)
                .attachTo(actionButton)
                .build();

        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Facebook", Toast.LENGTH_SHORT).show();
            }
        });

        buttonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Instagram", Toast.LENGTH_SHORT).show();
            }
        });

        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Twitter", Toast.LENGTH_SHORT).show();
            }
        });

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
        boolean doesFactExists = dataSource.doesFactExist(favoriteFact);

        if(doesFactExists)
        {
            Toast.makeText(this, "Fact already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        dataSource.addFact(favoriteFact);
        Toast.makeText(this, "Fact added", Toast.LENGTH_SHORT).show();
    }

    private class FetchFacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            FactBook.getInstance().retrieveFactsFromParse();
            return null;
        }
    }
}
