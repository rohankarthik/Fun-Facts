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
