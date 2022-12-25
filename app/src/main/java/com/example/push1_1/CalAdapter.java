package com.example.push1_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.CustomViewHolder> {

    TextView editTodo, editLocation;
    Button editButton, deleteButton;
    Context mContext;
    String 할일;
    String 장소;
    int i = 0;
    int j = 0;
    ArrayList<CalPageItems> calPageItem;

    public CalAdapter(ArrayList<CalPageItems> calPageItems){
        calPageItem = calPageItems;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private final TextView tview;
        private final TextView pview;
        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            /*itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getBindingAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){

                        AlertDialog.Builder editBox = new AlertDialog.Builder(v.getContext());
                        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.cal_edit_page,null,false);
                        editBox.setView(view);

                        editTodo = (TextView) view.findViewById(R.id.editTodo);
                        editLocation = (TextView) view.findViewById(R.id.editLocation);
                        editButton = (Button) view.findViewById(R.id.editButton);
                        deleteButton = (Button) view.findViewById(R.id.deleteButton);

                        String info = DBPage.날짜별정보.get(pos);
                        int idx = info.indexOf(",");
                        int lastIdx = info.lastIndexOf(",");
                        editTodo.setText(info.substring(idx+1,lastIdx));
                        editLocation.setText(info.substring(lastIdx+1));

                        AlertDialog dialog = editBox.create();
                        editButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String concat = DBPage.changeDate+","+info.substring(idx+1,lastIdx)+","+info.substring(lastIdx+1);
                                String todoComment = editTodo.getText().toString();
                                String locaComment = editLocation.getText().toString();
                                for(String x : DBPage.전체정보){
                                    if(x.equals(concat)){
                                        Log.d("바꾼기전", DBPage.전체정보.get(j));
                                        DBPage.전체정보.set(j, DBPage.changeDate+","+todoComment+","+locaComment);
                                    }
                                    j++;
                                }
                                Toast.makeText(v.getContext(), "게시물이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                Intent intent = new Intent(v.getContext(), NavActivity.class);
                                startActivity(intent);
                            }
                        });

                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String concat = DBPage.changeDate+","+info.substring(idx+1,lastIdx)+","+info.substring(lastIdx+1);

                                Iterator<String> iter = DBPage.전체정보.iterator();
                                while(iter.hasNext()){
                                    String x = iter.next();
                                    if(concat.equals(x)){
                                        iter.remove();
                                    }
                                }

                                Toast.makeText(v.getContext(), "게시물이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                }

            });*/
            mContext = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(v.getContext(), CalEditPage.class);
                        intent.putExtra("todo",calPageItem.get(pos).todo);
                        intent.putExtra("location",calPageItem.get(pos).location);
                        intent.putExtra("date",calPageItem.get(pos).date);
                        v.getContext().startActivity(intent);
                    }
                }
            });
            tview =  (TextView) itemView.findViewById(R.id.todoCheck);
            pview = (TextView) itemView.findViewById(R.id.locateCheck);
        }
    }

    @NonNull
    @NotNull
    @Override
    public CalAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cal_item,parent,false);
        Log.d("CalAdapter", "onCreateViewHolder");
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CalAdapter.CustomViewHolder holder, int position) {
        Log.d("CalAdapter", "onBindViewHolder");

        holder.tview.setText(calPageItem.get(position).todo);
        holder.pview.setText(calPageItem.get(position).location);
        if(!PreferenceManager.getString(mContext, calPageItem.get(position).date +"," +calPageItem.get(position).todo, LoginPage.loginer+"태그가능목록").equals("")) {
            holder.tview.setPaintFlags(holder.tview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {

        return calPageItem.size();
    }


}
