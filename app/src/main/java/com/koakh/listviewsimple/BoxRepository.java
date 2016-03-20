package com.koakh.listviewsimple;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class BoxRepository {

  private static App mApp = App.getInstance();
  private static ArrayList<Box> repositoryData;
  private static Adapter mAdapter;
  private static final int MIN = 0, MAX = 10000;

  public static void setAdapter(Adapter adapter) {
    mAdapter = adapter;
  }

  /**
   * Get Object from ObjectId
   * @param context
   * @param id
   * @return Object
   */
  public static Box getFromObjectId(Context context, long id) {

    Box iterBox = null;
    Iterator<Box> iterator = repositoryData.iterator();

    while(iterator.hasNext()) {
      iterBox = iterator.next();
      if(iterBox.getId().equals(id)) {
        Log.d(App.getTAG(), String.format("Found BoxId: %s", iterBox.getName()));
        return iterBox;
      }
    }

    return iterBox;
  }

  /**
   * Create a Fake Moked Box
   * @param position
   * @return
   */
  public static Box getFakeBox(long position) {

    long randomVal = MIN + (int) (Math.random() * ((MAX - MIN) + 1));

    return new Box(
      position,
      String.format("BoxId %d", position),
      (int) randomVal,
      String.format("Description BoxId %d", position)
    );
  }

  /**
   * Get Next Valid ObjectId
   * @return
   */
  public static Long getNextId() {

    Box iterBox = null;
    long nextId = 0L;

    Iterator<Box> iterator = repositoryData.iterator();

    while(iterator.hasNext()) {
      iterBox = iterator.next();
      if(iterBox.getId().equals(nextId)) {
        nextId++;
      }
      else {
        return nextId;
      }
    }
    nextId++;
    return nextId;
  }

  /**
   * Get all Repository Objects
   * @param context
   * @return
   */
  public static ArrayList<Box> getAllBoxes(Context context) {

    if (repositoryData == null) {
      int repositorySize = 5;

      repositoryData = new ArrayList<Box>();

      for (int i = 0; i < repositorySize; i++) {
        repositoryData.add(getFakeBox(i));
        Log.d(App.getTAG(), String.format("mockData Box Id: %d", i));
      }
    }

    return repositoryData;
  }

  /**
   * Insert or Update
   * @param context
   * @param box
   */
  public static void insertOrUpdate(Context context, Box box) {

    if (repositoryData.contains(box)) {
      int id = box.getId().intValue();
      repositoryData.get(id).setName(box.getName());
      repositoryData.get(id).setSlots(box.getSlots());
      repositoryData.get(id).setDescription(box.getDescription());
    }
    else {
      repositoryData.add(
        new Box(getNextId(), box.getName(), box.getSlots(), box.getDescription())
      );
    }
    notifyAdapter();
  }

  /**
   * Delete ObjectId
   * @param context
   * @param id
   */
  public static void deleteBoxWithId(Context context, long id) {
    Box box = getFromObjectId(context, id);
    //int index = repositoryData.indexOf(box);
    repositoryData.remove(box);
    notifyAdapter();
  }

  /**
   * Clear all Repository
   * @param context
   */
  public static void clearBoxes(Context context) {
    repositoryData.clear();
    notifyAdapter();
  }

  /**
   * Notify Adapter that data has Changed
   */
  public static void notifyAdapter() {
    if (mAdapter != null) {
      mAdapter.notifyDataSetChanged();
    }
  }

}
