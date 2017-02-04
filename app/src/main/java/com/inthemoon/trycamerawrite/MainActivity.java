package com.inthemoon.trycamerawrite;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int RQS_OPEN_DOCUMENT_TREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE, Uri.fromFile(cameraDirectory));
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, RQS_OPEN_DOCUMENT_TREE);

        //writeFile();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        writeFile();
    }

    public void writeFile()  {

        File removableRoot = RootsUtil.getRemovableRoots(this)[0];
        File DCIMDirectory = new File(removableRoot, "DCIM");
        File cameraDirectory = new File(DCIMDirectory, "Camera");



        if( cameraDirectory.exists() ) {
            try {
                String filename = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".txt";


                //cameraDirectory.mkdirs();
                File file = new File(cameraDirectory, filename);
                FileWriter writer = null;

                writer = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(writer);
                printWriter.println("Hello World");

                printWriter.close();
                writer.close();

                System.out.println("Number of files: " + cameraDirectory.listFiles().length);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
