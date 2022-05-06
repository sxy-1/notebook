package com.example.notebook;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notebook.adapter.NotepadAdapter;
import com.example.notebook.bean.NotepadBean;
import com.example.notebook.database.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private NotepadAdapter adapter;
    private ImageView ivadd;
    private List<NotepadBean> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = this.findViewById(R.id.listview);
        ivadd = this.findViewById(R.id.add);


        MyDBHelper dbHelper = new MyDBHelper(this);
        data = dbHelper.query();

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
//                   String str = result.getData().getStringExtra("data");
                        this.data.clear();
                        this.data.addAll(dbHelper.query());

                        adapter.notifyDataSetChanged();
                        Log.d("cao", "ok1");
                    } else {
                        Toast.makeText(MainActivity.this, "哦吼 加载失败  返回码为：" + result.getResultCode(), Toast.LENGTH_SHORT).show();

                    }
                });


        adapter = new NotepadAdapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(MainActivity.this).setMessage("是否删除此项").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NotepadBean bean = data.get(position);
                        if (dbHelper.delete(String.valueOf(bean.getId()))) {
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            data.remove(position);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NotepadBean bean = data.get(i);
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);
                intent.putExtra("data",bean);
                launcher.launch(intent);
            }
        });


        ivadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch(new Intent(MainActivity.this, RecordActivity.class));
            }
        });
    }
}