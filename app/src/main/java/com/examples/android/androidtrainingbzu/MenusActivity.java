package com.examples.android.androidtrainingbzu;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MenusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Button mButton;
    //region ActionMode Callback implementation
    private ActionMode mActionMode;
    public ActionMode.Callback mActionModeCallback =
            new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.menu_action_bar, menu);
                    return true;

                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_share:
                            String txt = ((TextView) findViewById(R.id.textView)).getText().toString();
                            String mimeType = "text/plain";
                            ShareCompat.IntentBuilder
                                    .from(MenusActivity.this)
                                    .setType(mimeType)
                                    .setChooserTitle(R.string.share_text_with)
                                    .setText(txt)
                                    .startChooser();
                            mode.finish(); // Action picked, so close the action bar
                            return true;
                        case R.id.mi_item1:
                            Toast.makeText(getApplicationContext(), "You clicked on ...", Toast.LENGTH_LONG).show();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    mActionMode = null;
                }
                // Implement action mode callbacks here
            };
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Context Menu
        registerForContextMenu(findViewById(R.id.btn_contextMenu));

        //Popup Menu
        mButton = findViewById(R.id.btn_popupMenu);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MenusActivity.this, mButton);
                popup.getMenuInflater().inflate(
                        R.menu.menu_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    // implement click listener
                });
                popup.show();
            }
        });

        //
        findViewById(R.id.textView).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mActionMode != null) return false;
                mActionMode =
                        MenusActivity.this.startActionMode(mActionModeCallback);
                v.setSelected(true);
                return true;

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        Toast.makeText(getApplicationContext(), "You selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Context Menu

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Toast.makeText(this,"You selected : "+item.getTitle(),Toast.LENGTH_SHORT).show();
//        switch (item.getItemId()) {
//            case R.id.nav_camera:
//                break;
//            case R.id.nav_gallery:
//                break;
//            case R.id.nav_slideshow:
//                break;
//        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_whatisthis:
                androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("What is this");
                alertDialog.setMessage("This is a context menu :)");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setCancelable(true);
                alertDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

}
