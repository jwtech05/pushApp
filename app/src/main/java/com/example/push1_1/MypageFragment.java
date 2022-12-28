package com.example.push1_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MypageFragment extends Fragment implements RecyclerViewInterface{

    Bitmap imageBitmap;
    TextView nameText;
    TextView emailText;
    String name;
    String email;
    Context context;
    CircleImageView photo;
    Uri uri;
    Bitmap imgBitmap;
    String savePic;
    TextView logout;
    ArrayList<PeedRegisterItems> peedRegisterItem;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();
                        uri = intent.getData();
                        try {
                            InputStream instream = getActivity().getContentResolver().openInputStream(uri);
                            imgBitmap = BitmapFactory.decodeStream(instream);
                            instream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        saveBitmapToJpeg(imgBitmap);
                        String 현재로그인 = PreferenceManager.getString(getContext(), "현재로그인", "현재로그인사용자");
                        int idx = 현재로그인.indexOf(",");
                        String key = 현재로그인.substring(0, idx);
                        int idx2 = 현재로그인.indexOf(",", idx + 1);
                        String email = 현재로그인.substring(idx + 1, idx2);
                        int idx3 = 현재로그인.indexOf(",", idx2 + 1);
                        PreferenceManager.setString(getContext(), "프로필사진", String.valueOf(uri), "현재로그인사용자");

                    }

                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mypage_page, container, false);
        Log.d("방법","들어오나");

        logout = v.findViewById(R.id.logoutText);

        Bundle bundle = getArguments();

        String name = bundle.getString("userName");
        String email = bundle.getString("userEmail");


        peedRegisterItem = PreferenceManager.getPeedItems(v.getContext(), LoginPage.loginer+"피드DB");

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PeedRegisterPage.class);
                startActivity(intent);
            }
        });


        /*photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(getActivity())
                        .setTitle("업로드할 프로필 이미지 선택")
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
            }

        });*/

        RecyclerView 리싸이클러뷰 = (RecyclerView) v.findViewById(R.id.mypageRecycler);

        GridLayoutManager 그리드매니저 = new GridLayoutManager(getActivity(),3);
        그리드매니저.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0){
                    return 3;
                }else{
                    return 1;
                }
            }
        });
        리싸이클러뷰.setLayoutManager(그리드매니저);

        MypageAdapter 리싸이클러어댑터 = new MypageAdapter(peedRegisterItem, this);
        리싸이클러뷰.setAdapter(리싸이클러어댑터);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent serviceIntent = new Intent(getActivity(), TimerService.class);
                stopService(serviceIntent);*/

                PreferenceManager.setString(getContext(), "밀리타이머", "0", "현재로그인사용자");
                PreferenceManager.setString(getContext(), "할일중", "STOP", "현재로그인사용자");
                if(LoginPage.mGoogleSignInClient != null) {
                    LoginPage.mGoogleSignInClient.signOut()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    Intent intent = new Intent(getActivity(), LoginPage.class);
                                    startActivity(intent);
                                }
                            });
                }
                Toast.makeText(getActivity(), "로그아웃되셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return v;

    }
/*    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //String url = "tmp_"+ String.valueOf(System.currentTimeMillis()) + ".jpg";
        // captureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, captureUri);
        activityResultLauncher.launch(intent);
    }

    private void doTakeAlbumAction() {


    }*/



    @Override
    public void onItemClick() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    @Override
    public void timerClick() {
        Intent timeIntent = new Intent(getActivity(), TimerPage2.class);
        startActivity(timeIntent);
    }


    @Override
    public void accountClick() {
        Intent intent = new Intent(getActivity(), MarkerPage.class);
        getActivity().startActivity(intent);
    }



    private void saveBitmapToJpeg(Bitmap bitmap) {
        File tempFile = new File(getActivity().getFilesDir(), LoginPage.loginer+"|profile.jpg");// 파일 경로와 이름 넣기
        String internalUri = String.valueOf(tempFile);
        savePic = internalUri;
        FileOutputStream out;
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();
            Toast.makeText(getActivity(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
