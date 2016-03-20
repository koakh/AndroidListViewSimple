package com.koakh.listviewsimple;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

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
  public static Box getFromObjectId(Context context, UUID id) {

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
   * @return Object
   */
  public static Box getFakeBox() {

    long randomVal = MIN + (int) (Math.random() * ((MAX - MIN) + 1));

    return new Box(
      getNextId(),
      String.format("BoxId %d", randomVal),
      (int) randomVal,
      String.format("Description BoxId %d", randomVal)
    );
  }

  /**
   * Get Next Valid ObjectId
   * @return
   */
  public static UUID getNextId() {

    //generate random UUIDs
    return UUID.randomUUID();

    /*
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
    */
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
        repositoryData.add(getFakeBox());
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
//int id = box.getId().intValue();
      int id = repositoryData.indexOf(box);
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
  public static void deleteBoxWithId(Context context, UUID id) {
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
