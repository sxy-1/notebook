package com.example.notebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notebook.R;
import com.example.notebook.bean.NotepadBean;

import java.util.List;

public class NotepadAdapter extends BaseAdapter {

    private List<NotepadBean> data;
    private LayoutInflater inflater;

    public NotepadAdapter(Context context, List<NotepadBean> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null){
            convertView = inflater.inflate (R.layout.notepad_item_layout,null);
        }
        TextView content = convertView.findViewById(R.id.item_content);
        TextView time = convertView.findViewById(R.id.item_time);
        NotepadBean bean = data.get(i);
        content.setText(bean.getNotepadContent());
        time.setText(bean.getNotepadTime());
        return convertView;
    }
}
