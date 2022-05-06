package com.example.notebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notebook.bean.NotepadBean;
import com.example.notebook.database.MyDBHelper;
import com.example.notebook.utils.utils;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivback;
    private ImageView ivdelete;
    private TextView edtext;
    private NotepadBean currentBean;
    private MyDBHelper dbhelper = null;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_record);
        ivback = findViewById(R.id.back);
        ivdelete = findViewById(R.id.delete);
        edtext = findViewById(R.id.note_content);
        ivback.setOnClickListener(this);
        ivdelete.setOnClickListener(this);
        currentBean = (NotepadBean) getIntent().getSerializableExtra("data");
        if (currentBean != null) {
            edtext.setText(currentBean.getNotepadContent());
        }
        dbhelper = new MyDBHelper(this);
//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String content = edtext.getText().toString();
            NotepadBean bean = new NotepadBean(content, utils.getTime());
            if (currentBean != null) {                                                //改
                if (content.isEmpty()) {
                    dbhelper.delete(String.valueOf(currentBean.getId()));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                currentBean.setNotepadContent(bean.getNotepadContent());
                currentBean.setNotepadTime(bean.getNotepadTime());
                new MyDBHelper(this).update(currentBean);
            } else {                                                                //新建
                if (content.isEmpty()) {
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    new MyDBHelper(this).add(bean);
                }

            }
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                String content = edtext.getText().toString();
                NotepadBean bean = new NotepadBean(content, utils.getTime());
                if (currentBean != null) {                                                //改
                    if (content.isEmpty()) {
                        dbhelper.delete(String.valueOf(currentBean.getId()));
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    }
                    currentBean.setNotepadContent(bean.getNotepadContent());
                    currentBean.setNotepadTime(bean.getNotepadTime());
                    new MyDBHelper(this).update(currentBean);
                } else {                                                                //新建
                    if (content.isEmpty()) {
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    }
                   new MyDBHelper(this).add(bean);
                }
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.delete:
                if (currentBean != null) {
                    Log.d("cao", "不应该啊");
                    new AlertDialog.Builder(RecordActivity.this).setMessage("是否删除此项").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (dbhelper.delete(String.valueOf(currentBean.getId()))) {
                                Toast.makeText(RecordActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RecordActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                            }

                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create().show();
                    break;

                }
        }
    }
}
