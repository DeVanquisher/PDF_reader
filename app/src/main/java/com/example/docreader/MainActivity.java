package com.example.docreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView files;
    public static ArrayList<File> fileList = new ArrayList<>();
    docReaderAdapter obj_adapter;
    public static int REQUEST_PERMISSION = 1;
    boolean boolean_permission;
    File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        files = (ListView) findViewById(R.id.listViewID);

        dir = new File(Environment.getExternalStorageDirectory().toString());

        permission_fn();

        files.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(getApplicationContext(),FileViewActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);


            }
        });

    }

    private void permission_fn(){
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))){

            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);
            }

        }
        else {
            boolean_permission = true;
            getfile(dir);
            obj_adapter = new docReaderAdapter(getApplicationContext(),fileList);
            files.setAdapter(obj_adapter);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                boolean_permission = true;
                getfile(dir);
                obj_adapter = new docReaderAdapter(getApplicationContext(),fileList);
                files.setAdapter(obj_adapter);

            }
            else {
                Toast.makeText(this,"Allow the permission",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ArrayList<File> getfile(File dir){
        File listFile[] = dir.listFiles();

        if (listFile != null && listFile.length >0){

            for (int i=0; i<listFile.length;i++){

                if (listFile[i].isDirectory()){
                    getfile(listFile[i]);
                }
                else{
                    boolean boolean_file = false;
                    if (listFile[i].getName().endsWith(".pdf")){

                        for (int j=0;j<fileList.size();j++){
                            if (fileList.get(j).getName().equals(listFile[i].getName())){
                                boolean_file = true;

                            }
                            else{

                            }
                        }
                        if (boolean_file){
                            boolean_file = false;
                        }
                        else {
                            fileList.add(listFile[i]);
                        }
                    }
                }

            }
        }
        return fileList;
    }
}