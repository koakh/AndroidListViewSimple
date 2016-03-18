package com.koakh.listviewsimple;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class BoxRepository {

  private static Box getBoxDao(Context context) {
    return new Box();
  }

  public static Box getBoxForId(Context context, long id) {
    return new Box();
  }

  public static List<Box> getAllBoxes(Context context) {
    return new ArrayList<Box>();
  }

  public static void insertOrUpdate(Context context, Box box) {
  }

  public static void deleteBoxWithId(Context context, long id) {
  }

  public static void clearBoxes(Context context) {
  }

}
