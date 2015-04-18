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


public class FunFactsActivity extends ActionBarActivity
{
    private TextView factLabel;
    private Button showFactButton;
    private LinearLayout relativeLayout;
    private LinearLayout factLayout;

    private FactsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);





        factLabel = (TextView) findViewById(R.id.factTextView);
        showFactButton = (Button) findViewById(R.id.showFactButton);
        relativeLayout = (LinearLayout) findViewById(R.id.backgroundView);
        factLayout = (LinearLayout) findViewById(R.id.factLayout);

        setColors();
        showFactButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                factLabel.setText(FactBook.getInstance().getRandomFact());
                setColors();
            }
        });

        try {
            dataSource = new FactsDataSource(this);
        } catch (Exception e) {
            Log.e("FunFactActivity", "Exception for Open SQLite DB");
        }
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
        String favoriteFact = factLabel.getText().toString();
        dataSource.addFact(favoriteFact);
        Toast.makeText(this, favoriteFact, Toast.LENGTH_SHORT).show();
    }
}
