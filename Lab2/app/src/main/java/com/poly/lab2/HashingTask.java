package com.poly.lab2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingTask extends AsyncTask<String, Void, String> {
    private static final String ALGORITHM = "SHA-256";
    private static final String TARGET_HASH = "a918c858d2dd1a3c69163267468804bdcd67daf50de8899183efe63e8412438a";

    private Handler handler;

    public HashingTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected String doInBackground(String... strings) {
        String targetHash = strings[0];
        String input = "";

        // Find the input string by hashing different strings until it matches the target hash
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            input = Integer.toString(i);
            String hash = calculateHash(input);
            if (hash.equals(targetHash)) {
                return input;
            }
        }

        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Bundle bundle = new Bundle();
        bundle.putString("input_string", result);

        // Send the message to the handler to handle the found input string
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private String calculateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hashedBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
