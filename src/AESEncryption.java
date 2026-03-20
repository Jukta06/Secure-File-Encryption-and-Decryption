import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public class AESEncryption {

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    public static String encrypt(String message, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedMessage, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        return new String(decryptedBytes);
    }

    public static void encryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        try (FileInputStream fileInput = new FileInputStream(inputFile);
                FileOutputStream fileOutput = new FileOutputStream(outputFile)) {
            byte[] inputBytes = new byte[(int) inputFile.length()];
            fileInput.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            fileOutput.write(outputBytes);
        }
    }

    public static void decryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (FileInputStream fileInput = new FileInputStream(inputFile);
                FileOutputStream fileOutput = new FileOutputStream(outputFile)) {
            byte[] inputBytes = new byte[(int) inputFile.length()];
            fileInput.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            fileOutput.write(outputBytes);
        }
    }
}
