package com.koakh.listviewsimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private static App mApp = App.getInstance();
  private ListView mListView;
  private int mListViewPosition;
  private Long mListViewId;
  private Adapter mAdapter;
  private ArrayList<Box> mBoxList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
      }
    });

    //Setup ListView and Adapter
    mListView = (ListView) this.findViewById(R.id.lvItemList);
    mBoxList = BoxRepository.getAllBoxes(MainActivity.this);
    mAdapter = new Adapter(MainActivity.this, mBoxList);
    //Add Adapter reference to Singleton
    BoxRepository.setAdapter(mAdapter);

    mListView.setAdapter(mAdapter);

    //Setup Buttons
    setupButtons();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    switch(id) {
      case R.id.action_add_item:
        addItem();
        break;
      case R.id.action_menu_delete_item:
        deleteItem();
        break;
      case R.id.action_menu_add_items:
        addItems();
        break;
      case R.id.action_menu_delete_items:
        deleteItems();
        break;
      case R.id.action_settings:
        break;
      default:
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  //Init UI

  //ListView click listener, on row click, edit record
  private void setupButtons() {
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mListViewPosition = position;
        mListViewId = id;
        Log.d(App.getTAG(), String.format("onItemClick: Position [%d] Id: [%d]", mListViewPosition, mListViewId));

        Intent editActivityIntent = new Intent(MainActivity.this, EditBoxActivity.class);

        Box clickedBox = mAdapter.getItem(position);
        editActivityIntent.putExtra("boxId", clickedBox.getId());

        startActivity(editActivityIntent);
      }
    });
  }

  /**
   * Menu Actions
   */

  private void addItem() {
    Intent addActivityIntent = new Intent(MainActivity.this, EditBoxActivity.class);
    startActivity(addActivityIntent);
  }

  private void deleteItem() {
    BoxRepository.deleteBoxWithId(MainActivity.this, mListViewId);
  }

  private void addItems() {
  }

  private void deleteItems() {
  }

}
