package com.koakh.listviewsimple;

/**
 * Created by mario on 18-03-2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {

  private String TAG = "listviewsimple";
  private LayoutInflater mLayoutInflater;
  private Context mContext;
  private List<Box> mList;

  //Constructor
  public Adapter(Context context, List<Box> list) {
    this.mContext = context;
    this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //Store BoxRepository Reference
    this.mList = list;
  }

  @Override
  public int getCount() {
    return mList.size();
  }

  @Override
  public Box getItem(int position) {
    return mList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  /*
  public void updateData(List<Box> boxList) {
    this.clear();
    for (Box aBoxList : boxList) {
      add(aBoxList);
    }
    // Notifies the attached observers that the underlying data has been changed and any
    // View reflecting the data set should refresh itself.
    notifyDataSetChanged();
  }
  */

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder viewHolder;

    if (convertView == null) {
      convertView = mLayoutInflater.inflate(R.layout.item_box, null);
      viewHolder = new ViewHolder();
      viewHolder.root = (LinearLayout) convertView.findViewById(R.id.boxItem);
      viewHolder.tvId = (TextView) convertView.findViewById(R.id.tvItemId);
      viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
      viewHolder.tvSlots = (TextView) convertView.findViewById(R.id.tvSize);
      viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    editBackground(position, viewHolder);
    fillViewWithData(position, viewHolder);

    return convertView;
  }

  private void fillViewWithData(int position, ViewHolder viewHolder) {
    viewHolder.tvId.setText(String.format("%s %s",
      mContext.getString(R.string.tv_label_item_id),
      getItem(position).getId().toString()
      )
    );
    viewHolder.tvName.setText(String.format("%s %s",
        mContext.getString(R.string.tv_label_box_name),
        getItem(position).getName().toString()
      )
    );
    viewHolder.tvSlots.setText(String.format("%s %s",
        mContext.getString(R.string.tv_label_box_slot),
        getItem(position).getSlots().toString()
      )
    );
    viewHolder.tvDescription.setText(String.format("%s %s",
        mContext.getString(R.string.tv_label_box_description),
        getItem(position).getDescription().toString()
      )
    );
  }

  private void editBackground(int position, ViewHolder viewHolder) {
    if (position % 2 == 0) {
      viewHolder.root.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    } else {
      viewHolder.root.setBackgroundColor(mContext.getResources().getColor(R.color.lightGray));
    }
  }

  //Inner class to strore ViewHolder Properties
  static class ViewHolder {
    LinearLayout root;
    TextView tvId;
    TextView tvName;
    TextView tvSlots;
    TextView tvDescription;
  }

}
