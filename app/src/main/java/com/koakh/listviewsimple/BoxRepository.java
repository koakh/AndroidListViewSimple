package com.koakh.listviewsimple;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class BoxRepository {

  private static String TAG = "listviewsimple";
  private static ArrayList<Box> repositoryData;

  public static Box getBoxForId(Context context, long id) {
    return repositoryData.get((int) id);
  }

  public static ArrayList<Box> getAllBoxes(Context context) {

    if (repositoryData == null) {
      int mockSize = 1000;
      int mockPosition = 100;

      repositoryData = new ArrayList<Box>();

      for (int i = 0; i < mockSize; i++) {
        mockPosition = (1 + i) * 10;
        repositoryData.add(new Box((long) mockPosition, String.format("Box %d", mockPosition), mockPosition * 10, String.format("Description Box %d", mockPosition)));
        Log.d(TAG, String.format("mockData: %d", mockPosition));
      }
    }

    return repositoryData;
  }

  public static void insertOrUpdate(Context context, Box box) {
    //Long id, String name, Integer slots, String description) {
    int id = box.getId().intValue();
    if (repositoryData.get(id) == null ) {
      repositoryData.add(
        new Box(box.getId(), box.getName(), box.getSlots(), box.getDescription())
      );
    }
    else {
      repositoryData.get(id).setName(box.getName());
      repositoryData.get(id).setSlots(box.getSlots());
      repositoryData.get(id).setDescription(box.getDescription());
    }
  }

  public static void deleteBoxWithId(Context context, long id) {
  }

  public static void clearBoxes(Context context) {
  }

}
