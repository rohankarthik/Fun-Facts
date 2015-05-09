package simran_preet.com.funfacts;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by simranpreetsnarang on 5/9/15.
 */
public class FunFactApplication extends Application {

    public static final String CLIENT_ID = "240a877d23db4ce0a13ada8a8e40d399";
    public static final String CLIENT_SECRET = "b6e7705414774346835d10195d2c1c76";
    public static final String CALLBACK_URL = "http://simran-preet.com";
    @Override
    public void onCreate() {
        super.onCreate();
        printHashKey();
    }

    public void printHashKey()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "simran_preet.com.funfacts",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
