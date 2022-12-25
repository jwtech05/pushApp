package com.example.push1_1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PeedRegisterPage extends AppCompatActivity {
    private Uri uri;
    private ImageButton picturePost;
    private Button postButton;
    private EditText comment;
    private String today;
    private RecyclerView 리싸이클러뷰;
    private Date mDate;
    private long mNow;
    private TextView tagsEdit;
    private String keyDate;
    private ArrayList<Bitmap> imageBitList;
    private ArrayList<String> imageList;
    private ArrayList<String> saveList;
    private PeedRegisterItems items;
    private ArrayList<String> tags;
    ArrayList<String> info;
    String[] infoTag;
    Bitmap imgBitmap;
    String likes = "0";
    SimpleDateFormat cFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat mFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat kFormat = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ClipData clipData = result.getData().getClipData();
                        if(clipData == null){
                            /*Intent intent = result.getData();
                            uri = intent.getData();
                            picturePost.setImageURI(uri);*/
                            Intent intent = result.getData();
                            uri = intent.getData();
                            try {
                                InputStream instream = getContentResolver().openInputStream(uri);
                                imgBitmap = BitmapFactory.decodeStream(instream);
                                imageBitList.add(imgBitmap);
                                instream.close();
                                Toast.makeText(PeedRegisterPage.this, "파일 로드 성공", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            imageList.add(String.valueOf(uri));
                            getContentResolver().takePersistableUriPermission(uri, (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
                        }else{
                            if(clipData.getItemCount() > 5){
                                Toast.makeText(PeedRegisterPage.this, "사진은 5장까지 선택가능합니다.", Toast.LENGTH_SHORT).show();
                            }else if (clipData.getItemCount() == 1){
                                imageList.add(String.valueOf(clipData.getItemAt(0).getUri()));
                            }else if(clipData.getItemCount() > 1 && clipData.getItemCount() <= 5){
                                for (int i= 0; i< clipData.getItemCount(); i++) {
                                    //URI 편집,삭제,변환하고 싶을 경우 이용하는 코드
                                    getContentResolver().takePersistableUriPermission(clipData.getItemAt(i).getUri(), (Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
                                    imageList.add(String.valueOf(clipData.getItemAt(i).getUri()));
                                    try {
                                        InputStream instream = getContentResolver().openInputStream(clipData.getItemAt(i).getUri());
                                        imgBitmap = BitmapFactory.decodeStream(instream);
                                        imageBitList.add(imgBitmap);
                                        instream.close();
                                        Toast.makeText(PeedRegisterPage.this, "파일 로드 성공", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                            }
                        }
                    }
                    GridLayoutManager 그리드매니저 = new GridLayoutManager(getApplicationContext(),3);
                    리싸이클러뷰.setLayoutManager(그리드매니저);

                    PeedRegisterAdapter 리싸이클러어댑터 = new PeedRegisterAdapter(imageList);
                    리싸이클러뷰.setAdapter(리싸이클러어댑터);
                }

            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peed_register_page);

        tagsEdit = (TextView) findViewById(R.id.tagsEdit);
        comment = (EditText) findViewById(R.id.commentEdit);
        postButton = (Button) findViewById(R.id.postPost);
        picturePost = (ImageButton) findViewById(R.id.picturPost);
        today = getTime();
        리싸이클러뷰 = (RecyclerView) findViewById(R.id.peedRegisterPage);
        info = PreferenceManager.getStringArray(getApplicationContext(), calTime()+",", LoginPage.loginer+"태그가능목록");
        Log.d("info", String.valueOf(info.size()));
        infoTag = new String[info.size()];
        for(int i=0; i<info.size(); i++){
            infoTag[i] = info.get(i);
        }

        picturePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imageBitList = new ArrayList<>();
                        imageList = new ArrayList<>();
                        saveList = new ArrayList<>();
                        doTakeAlbumAction();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(PeedRegisterPage.this)
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("취소", cancelListener)
                        .setNegativeButton("앨범선택", albumListener)
                        .show();
            }
        });



        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent post = new Intent(getApplicationContext(), NavActivity.class);
                if(imageList.size() == 0){
                    Toast.makeText(PeedRegisterPage.this, "사진을 업로드해주셔야합니다.", Toast.LENGTH_SHORT).show();
                }else {
                    for(Bitmap x : imageBitList){
                        Log.d("bitmap", String.valueOf(x));
                        saveBitmapToJpeg(x);
                    }
                    items = new PeedRegisterItems(likes, LoginPage.loginerNick, keyTime(), comment.getText().toString(), tags , saveList);

                    PreferenceManager.setString(getApplicationContext(), keyTime()+","+LoginPage.loginer, likes+"|"+LoginPage.loginer+"|"+items.days+"|"+items.comments+"|"+items.tags+"|"+items.pictures, LoginPage.loginer+"피드DB");
                    PreferenceManager.setString(getApplicationContext(), keyTime()+","+LoginPage.loginer, likes+"|"+LoginPage.loginer+"|"+items.days+"|"+items.comments+"|"+items.tags+"|"+items.pictures, "공용피드DB");
                    //PreferenceManager.clear(getApplicationContext(), "tester7피드DB");
                    //PreferenceManager.clear(getApplicationContext(), "공용피드DB");
                    //PreferenceManager.removeKey(getApplicationContext(), "2022/12/02/08:32:27,tester1","공용피드DB");
                    startActivity(post);
                }
            }
        });


        tagsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tags = new ArrayList<>();
                new AlertDialog.Builder(PeedRegisterPage.this)
                    .setTitle("태그를 선택해주세요.")
                    .setMultiChoiceItems(infoTag, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if(isChecked){
                                tags.add(infoTag[which]);
                            }else if(tags.contains(infoTag[which]))
                                tags.remove(tags.indexOf(infoTag[which]));
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String text = "";
                            for(String x : tags){
                                text += "#"+x+" ";
                            }
                            tagsEdit.setText(text);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                }).show();

            }
        });


    }


    private void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }
    private void saveBitmapToJpeg(Bitmap bitmap) {
        File tempFile = new File(getFilesDir(), LoginPage.loginer+"|"+getTime2()+".jpg");// 파일 경로와 이름 넣기
        String internalUri = String.valueOf(tempFile);
        saveList.add(internalUri);
        FileOutputStream out;
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();
            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }

    private void fileDelete(String filePath){
        File file = new File(getFilesDir()+"/"+filePath);
        String a = String.valueOf(file);
        Log.d("true1",a);
        try{
            if(file.exists()){
                Log.d("true","true");
                file.delete();
            }else{
                Log.d("else","else");
            }
        }catch (Exception e){
            Log.d("false","false");
        }
    }


    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    private String calTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return cFormat.format(mDate);
    }


    private String getTime2(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat2.format(mDate);
    }

    private String keyTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return kFormat.format(mDate);
    }

}