package com.koakh.listviewsimple;

/**
 * Created by mario on 18-03-2016.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Box> {

  private String TAG = "listviewsimple";
  private LayoutInflater inflater;
  private Context context;

  public Adapter(Context context) {
    super(context, 0);
    this.context = context;
    this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  public void mockData() {

    int mockSize = 100;
    int mockPosition = 100;
    ArrayList<Box> boxList = new ArrayList<>();


    for (int i = 0; i < mockSize; i++) {
      mockPosition = i * 10;
      boxList.add(new Box((long) mockPosition, String.format("Box %d", mockPosition), mockPosition * 10, String.format("Description Box %d", mockPosition)));
      Log.d(TAG, String.format("mockData: %d", mockPosition));
    }

    updateData(boxList);
  }

  public void updateData(List<Box> boxList) {
    this.clear();
    for (Box aBoxList : boxList) {
      add(aBoxList);
    }
    // Notifies the attached observers that the underlying data has been changed and any
    // View reflecting the data set should refresh itself.
    notifyDataSetChanged();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder viewHolder;

    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_box, null);
      viewHolder = new ViewHolder();
      viewHolder.root = (LinearLayout) convertView.findViewById(R.id.boxItem);
      viewHolder.tvId = (TextView) convertView.findViewById(R.id.tvItemId);
      viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
      viewHolder.tvSize = (TextView) convertView.findViewById(R.id.tvSize);
      viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    editBackground(position, viewHolder);
    fillViewWithData(position, viewHolder);

    return convertView;
  }

  private void editBackground(int position, ViewHolder viewHolder) {
    if (position % 2 == 0) {
      viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.white));
    } else {
      viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.lightGray));
    }
  }

  private void fillViewWithData(int position, ViewHolder viewHolder) {
    viewHolder.tvId.setText(context.getString(R.string.tv_label_item_id) + " " + getItem(position).getId().toString());
    viewHolder.tvName.setText(context.getString(R.string.tv_label_box_name) + " " + getItem(position).getName());
    viewHolder.tvSize.setText(context.getString(R.string.tv_label_box_size) + " " + getItem(position).getSlots());
    viewHolder.tvDescription.setText(context.getString(R.string.tv_label_box_description) + " " + getItem(position).getDescription());
  }

  //Inner class
  static class ViewHolder {
    LinearLayout root;
    TextView tvId;
    TextView tvName;
    TextView tvSize;
    TextView tvDescription;
  }

}
