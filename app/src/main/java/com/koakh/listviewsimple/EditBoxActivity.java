package com.koakh.listviewsimple;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class EditBoxActivity extends AppCompatActivity {

  private Button btnSave;
  private EditText etBoxName;
  private EditText etBoxSlots;
  private EditText etBoxDescription;

  private UUID boxId;
  private Box box;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_box);

    btnSave = (Button) findViewById(R.id.btnSave);
    etBoxName = (EditText) findViewById(R.id.etBoxName);
    etBoxSlots = (EditText) findViewById(R.id.etBoxSlots);
    etBoxDescription = (EditText) findViewById(R.id.etBoxDescription);

    //Assign boxId and box from bundle Extras
    if (getIntent() != null && getIntent().getExtras() != null) {
      //boxId = UUID.fromString(getIntent().getExtras().getString("boxId"));
      //get UUID From Object
      boxId = (UUID) getIntent().getExtras().get("boxId");
      box = BoxRepository.getFromObjectId(EditBoxActivity.this, boxId);
    }

    setupButtons();
    fillViewWithData();
  }

  private void setupButtons() {
    btnSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (validateFields()) {
          if (box == null) {
            box = new Box();
            box.setId(BoxRepository.getNextId());
          } else {
            box.setId(boxId);
          }
          //Assign input data to box
          box.setName(etBoxName.getText().toString());
          box.setSlots(Integer.parseInt(etBoxSlots.getText().toString()));
          box.setDescription(etBoxDescription.getText().toString());
          //Update Repository with updated box
          BoxRepository.insertOrUpdate(EditBoxActivity.this, box);
          finish();
        } else {
          Toast.makeText(EditBoxActivity.this, getString(R.string.toast_validation_error), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private boolean validateFields() {
    if (etBoxName.getText().length() == 0) {
      etBoxName.setError(getString(R.string.error_cannot_be_empty));
      return false;
    }
    if (etBoxSlots.getText().length() == 0) {
      etBoxSlots.setError(getString(R.string.error_cannot_be_empty));
      return false;
    }
    try {
      Integer.parseInt(etBoxSlots.getText().toString());
    } catch (Exception e) {
      etBoxSlots.setError(getString(R.string.error_must_be_number));
      return false;
    }

    etBoxName.setError(null);
    etBoxSlots.setError(null);
    return true;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (box != null) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.edit_item_menu, menu);
      return true;
    } else {
      return super.onCreateOptionsMenu(menu);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.delete_item:
        deleteItem();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void deleteItem() {
    new AlertDialog.Builder(EditBoxActivity.this)
      .setTitle(getString(R.string.dialog_delete_item_title))
      .setMessage(R.string.dialog_delete_item_content)
      .setCancelable(false)
      .setPositiveButton(R.string.dialog_delete_items_confirm, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
          BoxRepository.deleteBoxWithId(EditBoxActivity.this, boxId);
          dialog.cancel();
          finish();
        }
      })
      .setNegativeButton(R.string.dialog_delete_items_cancel, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
          dialog.cancel();
        }
      }).create().show();
  }

  private void fillViewWithData() {
    if (box != null) {
      etBoxName.setText(box.getName());
      etBoxSlots.setText(box.getSlots().toString());
      etBoxDescription.setText(box.getDescription());
    }
  }
}
