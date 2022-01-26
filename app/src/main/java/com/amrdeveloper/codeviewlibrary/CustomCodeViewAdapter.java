package com.amrdeveloper.codeviewlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeViewAdapter;
import com.amrdeveloper.codeview.Snippet;

import java.util.List;

public class CustomCodeViewAdapter extends CodeViewAdapter {

    private final LayoutInflater layoutInflater;

    public CustomCodeViewAdapter(@NonNull Context context, @NonNull List<Code> codes) {
        super(context, R.layout.list_item_modern_autocomplete, 0, codes);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_modern_autocomplete, parent, false);
        }

        ImageView codeType = convertView.findViewById(R.id.code_type);
        TextView codeTitle = convertView.findViewById(R.id.code_title);
        Code currentCode = (Code) getItem(position);
        if (currentCode != null) {
            codeTitle.setText(currentCode.getCodeTitle());
            if (currentCode instanceof Snippet) {
                codeType.setImageResource(R.drawable.ic_snippet);
            } else {
                codeType.setImageResource(R.drawable.ic_keyword);
            }
        }

        return convertView;
    }
}
