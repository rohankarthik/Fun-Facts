package simran_preet.com.funfacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.parse.Parse;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FunFactsActivity extends ActionBarActivity {
    private static final String TAG = "FunFactsActivity";
    private TextView factLabel;
    private Button showFactButton;
    private LinearLayout relativeLayout;
    private LinearLayout factLayout;
    private List<String> facts;
    private FactsDataSource dataSource;
    private Fact currentFact;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

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
        showFactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRandomFactAndColor();
            }
        });

        try {
            dataSource = new FactsDataSource(this);
        } catch (Exception e) {
            Log.e(TAG, "Exception for Open SQLite DB");
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
//                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_twitter200);
//                SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
//                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
//                ShareDialog dialog = new ShareDialog(FunFactsActivity.this);
//                if (dialog.canShow(SharePhotoContent.class)){
//                    dialog.show(content);
//                }
//                else{
//                    Log.d(TAG, "you cannot share photos :(");
//                }
                if (ShareDialog.canShow(ShareLinkContent.class)) {
//                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                            .setContentTitle("Hello Facebook")
//                            .setContentDescription(
//                                    "The 'Hello Facebook' sample  showcases simple Facebook integration")
//                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                            .build();
                    Bitmap image = getBitmapFromView(factLayout);
                    SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
                    ArrayList<SharePhoto> sharePhotos = new ArrayList<SharePhoto>();
                    sharePhotos.add(photo);
                    SharePhotoContent linkContent2 = new SharePhotoContent.Builder().setPhotos(sharePhotos).build();

                    shareDialog.show(linkContent2);
                }

            }
        });

        buttonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Instagram", Toast.LENGTH_SHORT).show();

                String type = "image/*";
                String filename = "/myPhoto.jpg";
                String mediaPath = Environment.getExternalStorageDirectory() + filename;
                String captionText = "Another Fun Fact for you.";
                createInstagramIntent(type, mediaPath, captionText);
            }
        });

        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Twitter", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setRandomFactAndColor() {
        currentFact = FactBook.getInstance().getRandomFact();
        factLabel.setText(currentFact.getFact());
        setColors();
    }

    public String setColors() {
        String colorValue = FactColors.getInstance().getRandomColor();
        int colorCode = Color.parseColor(colorValue);
        relativeLayout.setBackgroundColor(colorCode);
        factLayout.setBackgroundColor(colorCode);
        showFactButton.setTextColor(colorCode);
        return colorValue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fun_facts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public void addFactToFavorite() {
        Fact favoriteFact = new Fact();
        favoriteFact.setObjectId(currentFact.getObjectId());
        favoriteFact.setFact(currentFact.getFact());
        boolean doesFactExists = dataSource.doesFactExist(favoriteFact);

        if (doesFactExists) {
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

    private void createInstagramIntent(String type, String mediaPath, String caption){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri = bitmapToUri();

        // Add the URI and the caption to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, caption);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }

    private Uri bitmapToUri()
    {
        File path = null;
        File imageFile = null;
        try {
            Bitmap image = getBitmapFromView(factLayout);
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            imageFile = new File(path, new Date().getTime() + ".png");
            imageFile.createNewFile();
            FileOutputStream fileOutPutStream = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.PNG, 80, fileOutPutStream);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return Uri.parse("file://" + imageFile.getAbsolutePath());
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
}
