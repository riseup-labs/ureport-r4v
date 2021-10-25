package com.riseuplabs.ureport_r4v.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class StorageUtils {

    public static void saveToFile(Context context, String content, String filename) {

        try {
            FileOutputStream fileout = context.openFileOutput(filename, MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(content);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String readFromFile(Context context, String filename) {

        String s = "";
        try {
            FileInputStream fileIn = context.openFileInput(filename);
            if (fileIn != null) {
                InputStreamReader InputRead = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[15000];

                int charRead;

                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    // char to string conversion
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readstring;
                }
                InputRead.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    public static String getContent(Context context, String content_file_name) {

        String s = "";
        try {
            FileInputStream fileIn = context.openFileInput(content_file_name);
            if (fileIn != null) {
                InputStreamReader InputRead = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[16384];

                int charRead;

                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    // char to string conversion
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readstring;
                }
                InputRead.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }


}
