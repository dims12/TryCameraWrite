package com.inthemoon.trycamerawrite;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.provider.DocumentFile;
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
import java.io.OutputStream;
import java.io.PrintStream;
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

        intentWriteFile();
    }

    protected void intentWriteFile() {
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE, Uri.fromFile(cameraDirectory));
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, RQS_OPEN_DOCUMENT_TREE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if( requestCode == RQS_OPEN_DOCUMENT_TREE ) {

            Uri cameraDirectoryUri = data.getData();
            DocumentFile cameraDirectoryDF = DocumentFile.fromTreeUri(this, cameraDirectoryUri);
            try {
                writeFile(cameraDirectoryDF);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void writeFile()  {

        File removableRoot = RootsUtil.getRemovableRoots(this)[0];
        File DCIMDirectory = new File(removableRoot, "DCIM");
        File cameraDirectory = new File(DCIMDirectory, "Camera");


        DocumentFile cameraDirectoryDF = DocumentFile.fromFile(cameraDirectory);

        if( cameraDirectoryDF.exists() && cameraDirectoryDF.canWrite() ) {
            try {
                writeFile(cameraDirectoryDF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void writeFile2()  {


    }

    public void writeFile(DocumentFile cameraDirectoryDF) throws IOException {
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".txt";

        DocumentFile destinationFile = cameraDirectoryDF.createFile("text/plain", filename);

        Uri destinationUri = destinationFile.getUri();

        OutputStream outputStream = getContentResolver().openOutputStream(destinationUri);

        PrintWriter printWriter = new PrintWriter(outputStream);

        printWriter.println("Hello World");

        printWriter.close();
        outputStream.close();

    }
}
