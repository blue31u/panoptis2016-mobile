package gr.panoptis.ellak.ellakpanoptis2016;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by rkmylo and h3ph4est7s on 25/05/16.
 */
public class SafeNotePh4ker {

    private Cipher cipher;
    private String pin_to_md5 = MD5("eLS095");
    SecretKeySpec keySpec = new SecretKeySpec(this.pin_to_md5.getBytes(), "AES");

    public String ph4ckNote(InputStream inputStream) {
        byte[] noteBytes;
        String encryptedNote, decryptedNote = "";
        try {
            noteBytes = this.readBytes(inputStream);
            encryptedNote = new String(noteBytes);
            decryptedNote = this.decrypt(encryptedNote);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decryptedNote;
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
            return "";
        }
    }

    private static String MD5(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            byte[] arrayOfByte = localMessageDigest.digest();
            StringBuffer localStringBuffer = new StringBuffer();
            for (int i = 0;; i++) {
                if (i >= arrayOfByte.length) {
                    return localStringBuffer.toString();
                }
                String str = Integer.toHexString(0xFF & arrayOfByte[i]);
                if (str.length() == 1) {
                    localStringBuffer.append("0");
                }
                localStringBuffer.append(str);
            }
        }
        catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        return "";
    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }
}
