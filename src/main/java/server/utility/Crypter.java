package server.utility;

import server.controller.Config;

public class Crypter {

    //test
    //Method for encrypting
    public static String encryptAndDecryptXor(String input) {
        if (Config.getEncryption()) {
            char[] key = {'L', 'Y', 'N'};
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < input.length(); i++) {
                output.append((char) (input.charAt(i) ^ key[i % key.length]));
            }

            return output.toString();
        } else {
            return input;
        }


    }
}
