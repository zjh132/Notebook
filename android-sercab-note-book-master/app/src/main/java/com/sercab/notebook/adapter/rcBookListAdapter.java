package com.sercab.notebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sercab.notebook.R;
import com.sercab.notebook.entity.Book;

import java.util.List;

public class rcBookListAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Book> mDataList;
    private OnItemClickListener mListener;

    public rcBookListAdapter(Context context, List<Book> dataList,OnItemClickListener listener) {
        mContext = context;
        mDataList = dataList;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Book getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.note_list_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.bookTitle = convertView.findViewById(R.id.bookTitle);
            viewHolder.bookBody = convertView.findViewById(R.id.bookBody);
            viewHolder.bookTime = convertView.findViewById(R.id.bookTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bookTitle.setText(mDataList.get(position).getName());
        viewHolder.bookBody.setText(mDataList.get(position).getBook());
        viewHolder.bookTime.setText("修改时间: " + mDataList.get(position).getTime());

        convertView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position,getItem(position));
            }
        });


        return convertView;
    }


    public interface OnItemClickListener {
        void onItemClick(int position,Book book);
    }

    static class ViewHolder {
        TextView bookTitle,bookBody, bookTime;
    }

}
