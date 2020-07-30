package com.example.docreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class docReaderAdapter extends ArrayAdapter<File> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<File> docReader;

    public docReaderAdapter(@NonNull Context context, ArrayList<File> docReader) {
        super(context, R.layout.adapter_reader,docReader);
        this.context = context;
        this.docReader = docReader;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        if (docReader.size() > 0){
            return docReader.size();
        }
        else return 1;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_reader, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.file_name = (TextView) convertView.findViewById(R.id.file_nameID);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.file_name.setText(docReader.get(position).getName());
        return convertView;
    }
    public class ViewHolder{
        TextView file_name;

    }
}
