package com.fjnu.labman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fjnu.labman.R;
import com.fjnu.labman.utils.StyleUtil;

import java.util.List;

public class AddDataAdapter extends ArrayAdapter {

    private Context mContext;
    private int resourceId;

    public AddDataAdapter(@NonNull Context context, int resource, List<String> listItems) {
        super(context, resource, listItems);
        this.mContext = context;
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String text = (String) getItem(position);
        View view = LayoutInflater.from(mContext).inflate(resourceId, null);
        TextView textView = view.findViewById(R.id.add_text);
        textView.setText(text);
        StyleUtil.fontStyleOnContent(mContext, textView);
        StyleUtil.fontFakeBold(textView);
        return view;
    }
}
