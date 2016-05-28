package gr.panoptis.ellak.ellakpanoptis2016;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by rkmylo and h3ph4est7s on 25/05/16.
 */
public class PwdManagerPh4ker {

    private Cipher cipher;
    private String _md5_pin = "76dcaa023162fdb1acca24b28bc54882";
    SecretKeySpec keySpec = new SecretKeySpec(this._md5_pin.getBytes(), "AES");

    public String ph4ckPass(String pass) {
        return this.decrypt(pass);
    }

    private String decrypt(String paramString) {
        byte[] arrayOfByte = Base64.decode(paramString.getBytes(), 0);
        try {
            this.cipher = Cipher.getInstance("AES");
            this.cipher.init(2, this.keySpec);
            String str = new String(this.cipher.doFinal(arrayOfByte), "UTF-8");
            return str;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
