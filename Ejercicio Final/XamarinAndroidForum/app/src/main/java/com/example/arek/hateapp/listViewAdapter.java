package com.example.arek.hateapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arek on 13.07.2017.
 */

public class ListViewAdapter extends ArrayAdapter<MPost> {

    private ArrayList<MPost> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtLogin;
        TextView txtPost;

    }

    public ListViewAdapter(ArrayList<MPost> data, Context context) {
        super(context, R.layout.adapter_list_view, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MPost dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_list_view, parent, false);
            viewHolder.txtLogin = (TextView) convertView.findViewById(R.id.adapter_txtLogin);
            viewHolder.txtPost = (TextView) convertView.findViewById(R.id.adapter_txtPost);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.txtLogin.setText(dataModel.getLogin());
        viewHolder.txtPost.setText(dataModel.getPost());

        return convertView;
    }
}
