package com.example.push1_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class SinglePeedPage extends AppCompatActivity {
    Intent intent;
    int num;
    String pictureUri, nickName, days, comment;
    ImageView mainPic, peedMenu;
    TextView comments, name, date;
    CircleImageView miniProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_mypage_fix_item);

        intent = getIntent();
        num = intent.getIntExtra("num",0);
        nickName = intent.getStringExtra("name");
        pictureUri = intent.getStringExtra("picture");
        comment = intent.getStringExtra("comment");
        days = intent.getStringExtra("days");
        mainPic = (ImageView) findViewById(R.id.mainPicture);
        peedMenu = (ImageView) findViewById(R.id.peedMenu);
        comments = (TextView) findViewById(R.id.postTag);
        name = (TextView) findViewById(R.id.userId);
        date = (TextView) findViewById(R.id.postDate);
        miniProfile = (CircleImageView) findViewById(R.id.profileImage);
        if(DBPage.imageBitmap != null){
            miniProfile.setImageBitmap(DBPage.imageBitmap);
        }

        mainPic.setImageURI(Uri.parse(pictureUri));
        comments.setText(comment);
        name.setText(nickName);
        date.setText(days);

        peedMenu.setVisibility(View.VISIBLE);
        peedMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                getMenuInflater().inflate(R.menu.recyclerview_mypage_item_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()){
                            case R.id.fix:
                                AlertDialog.Builder editBox = new AlertDialog.Builder(v.getContext());
                                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.peed_edit_page,null,false);
                                editBox.setView(view);

                                final Button editButton = (Button) view.findViewById(R.id.editButton);
                                final EditText editComment = (EditText) view.findViewById(R.id.editComment);
                                editComment.setText(comment);

                                AlertDialog dialog = editBox.create();
                                editButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String changeComment = editComment.getText().toString();

                                        PreferenceManager.setString(getApplicationContext(), days+","+LoginPage.loginer, LoginPage.loginer+"|"+days+"|"+changeComment+"|"+MypageAdapter.myPageItem.get(num).tags+"|"+MypageAdapter.myPageItem.get(num).pictures, LoginPage.loginer+"피드DB");
                                        PreferenceManager.setString(getApplicationContext(), days+","+LoginPage.loginer, LoginPage.loginer+"|"+days+"|"+changeComment+"|"+MypageAdapter.myPageItem.get(num).tags+"|"+MypageAdapter.myPageItem.get(num).pictures, "공용피드DB");
                                        Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                dialog.show();
                                break;
                            case R.id.delete:

                                PreferenceManager.removeKey(getApplicationContext(), days+","+LoginPage.loginer,LoginPage.loginer+"피드DB");
                                PreferenceManager.removeKey(getApplicationContext(), days+","+LoginPage.loginer,"공용피드DB");
                                Toast.makeText(getApplicationContext(), "게시물이 삭제되었습니다.",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                                startActivity(intent);
                                break;
                        }

                        return false;
                    }
                });

                popup.show();
            }
        });
    }
}
