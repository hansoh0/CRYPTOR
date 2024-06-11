import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Defines encrypt and decrypt method in AES256
 * @HanSoho 
 */

public class AES256 {

  private static final int KEY_LENGTH = 256;
  private static final int ITERATION_COUNT = 65536;

  /**
   * Encrypts a byte string with a secretKey and defined Salt in AES256
   * @param bytesToEncrypt is the raw bytes that need encrypted
   * @param secretKey is the password or Secret Key used to encrypt the bytesToEncrypt
   * @param salt is the salt used to pad the data before encryption
   */
  public static byte[] encrypt(byte[] bytesToEncrypt, String secretKey, String salt) {

    try {

        // Define IV length and IV param
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // Define secret key specs & algo
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

        // Define cipher and initialize encrypt method
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);

        // Encrypt bytes and place iv
        byte[] cipherByte = cipher.doFinal(bytesToEncrypt);
        byte[] encryptedData = new byte[iv.length + cipherByte.length];
        System.arraycopy(iv, 0, encryptedData, 0, iv.length);
        System.arraycopy(cipherByte, 0, encryptedData, iv.length, cipherByte.length);

        return encryptedData;
    } catch (Exception e) {
        // Handle the exception properly
        e.printStackTrace();
        return null;
    }
  }
  
  /**
   * Decrypts an AES256 encrypted byte string with a secretKey and defined Salt
   * @param bytesToDecrypt is the raw bytes that need decrypted
   * @param secretKey is the password or Secret Key used to encrypt the bytesToEncrypt
   * @param salt is the salt used to pad the data before encryption
   */
  public static byte[] decrypt(byte[] bytesToDecrypt, String secretKey, String salt) {

    try {

        // Extract IV from encrypted bytes
        byte[] iv = Arrays.copyOfRange(bytesToDecrypt, 0, 16);
        // Extract encrypted data from encrypted bytes
        byte[] encryptedData = Arrays.copyOfRange(bytesToDecrypt, 16, bytesToDecrypt.length);
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // Define secret key specs & algo
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

        // Define cipher and initialize decrypt method
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);

        // Decrypt bytes and return decrypted bytes
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return decryptedBytes;

    } catch (Exception e) {
        // Handle the exception properly
        e.printStackTrace();
        return null;
    }
  }
}
