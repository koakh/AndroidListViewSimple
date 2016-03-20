package com.koakh.listviewsimple;

/**
 * Created by mario on 19-03-2016.
 */
public class App {
  private static App ourInstance = new App();

  public static App getInstance() {
    return ourInstance;
  }

  private static String TAG = "listviewsimple";

  private App() {
  }

  public static String getTAG() {
    return TAG;
  }
}
