package com.amrdeveloper.codeview;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CodeViewAdapter extends BaseAdapter implements Filterable {

    private List<Code> codeList;
    private List<Code> originalCodes;
    private final LayoutInflater layoutInflater;
    private final int codeViewLayoutId;
    private final int codeViewTextViewId;

    public CodeViewAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Code> codes) {
        this.codeList = codes;
        this.layoutInflater = LayoutInflater.from(context);
        this.codeViewLayoutId = resource;
        this.codeViewTextViewId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(codeViewLayoutId, parent, false);
        }

        TextView textViewName = convertView.findViewById(codeViewTextViewId);

        Code currentCode = codeList.get(position);
        if (currentCode != null) {
            textViewName.setText(currentCode.getCodeTitle());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return codeList.size();
    }

    @Override
    public Object getItem(int position) {
        return codeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateCodes(List<Code> newCodeList) {
        codeList.clear();
        codeList.addAll(newCodeList);
        notifyDataSetChanged();
    }

    public void clearCodes() {
        codeList.clear();
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return codeFilter;
    }

    private final Filter codeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Code> suggestions = new ArrayList<>();

            if (originalCodes == null) {
                originalCodes = new ArrayList<>(codeList);
            }


            if (constraint == null || constraint.length() == 0) {
                results.values = originalCodes;
                results.count = originalCodes.size();
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Code item : originalCodes) {
                    if (item.getCodePrefix().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            codeList = (List<Code>) results.values;
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Code) resultValue).getCodeBody();
        }
    };

}
