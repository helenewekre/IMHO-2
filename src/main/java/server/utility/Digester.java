package server.utility;

import java.security.MessageDigest;

public class Digester {

    private String salt;
    private static MessageDigest digester;

    // Setting the default salt.
    public Digester() {
        this.salt = "hashingtest";
    }
    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hash string AND salt with MD5 hash
     * @param password input string
     * @return MD5 hashed of string
     */
    //Method for hashing the password with the salt.
    public String hashWithSalt(String password){

        password = password + this.salt;

        return Digester.performHashing(password);
    }

    /**
     * Performing MD5 hashing of string
     * @param password input
     * @return MD5 hash of string
     */
    // The logic behind the hashing.
    private static String performHashing(String password){
        // vigtigste metode, som laver hash for os
        digester.update(password.getBytes());
        byte[] hash = digester.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            if ((0xff & aHash) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & aHash)));
            } else {
                hexString.append(Integer.toHexString(0xFF & aHash));
            }
        }
        return hexString.toString();
    }

}