package simran_preet.com.funfacts;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class FunFactsActivity extends ActionBarActivity {


    TextView factLabel;
    Button showFactButton;
    RelativeLayout relativeLayout;
    LinearLayout factLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);


        factLabel = (TextView) findViewById(R.id.factTextView);
        showFactButton = (Button) findViewById(R.id.showFactButton);
        relativeLayout = (RelativeLayout) findViewById(R.id.backgroundView);
        factLayout = (LinearLayout) findViewById(R.id.factLayout);


        setColors(); // Setting the color of the background and the button text


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // The button was clicked so update the fact label

                factLabel.setText(FactBook.getInstance().getRandomFact());
                setColors(); // Setting the color of the background and the button text


            }
        };
        showFactButton.setOnClickListener(listener);
    }


    public void setColors() {

        int colorCode = Color.parseColor(FactColors.getInstance().getRandomColor());
        relativeLayout.setBackgroundColor(colorCode);
        factLayout.setBackgroundColor(colorCode);
        showFactButton.setTextColor(colorCode);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fun_facts, menu);
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
