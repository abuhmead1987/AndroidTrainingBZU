package com.examples.android.androidtrainingbzu

import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.view.View.OnLongClickListener
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MenusActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var drawer: DrawerLayout? = null
    lateinit var mButton: Button

    //region ActionMode Callback implementation
    private var mActionMode: ActionMode? = null
    var mActionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_action_bar, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_share -> {
                    val txt = (findViewById<View>(R.id.textView) as TextView).text.toString()
                    val mimeType = "text/plain"
                    ShareCompat.IntentBuilder(this@MenusActivity)
                        .setType(mimeType)
                        .setChooserTitle(R.string.share_text_with)
                        .setText(txt)
                        .startChooser()
                    mode.finish() // Action picked, so close the action bar
                    true
                }
                R.id.mi_item1 -> {
                    Toast.makeText(applicationContext, "You clicked on ...", Toast.LENGTH_LONG)
                        .show()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            mActionMode = null
        } // Implement action mode callbacks here
    }

    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menus)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        //Context Menu
        registerForContextMenu(findViewById(R.id.btn_contextMenu))

        //Popup Menu
        mButton = findViewById(R.id.btn_popupMenu)
        mButton.setOnClickListener(View.OnClickListener {
            val popup = PopupMenu(this@MenusActivity, mButton)
            popup.menuInflater.inflate(
                R.menu.menu_popup, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                Toast.makeText(applicationContext, "" + item.title, Toast.LENGTH_SHORT).show()
                false
            } // implement click listener

            popup.show()
        })

        //
        findViewById<View>(R.id.textView).setOnLongClickListener(OnLongClickListener { v ->
            if (mActionMode != null) return@OnLongClickListener false
            mActionMode = startActionMode(mActionModeCallback)
            v.isSelected = true
            true
        })
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menus_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Handle item selection
        Toast.makeText(applicationContext, "You selected: " + item.title, Toast.LENGTH_SHORT).show()
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Context Menu
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        Toast.makeText(this, "You selected : " + item.title, Toast.LENGTH_SHORT).show()
//        when (item.getItemId()) {
//            R.id.nav_camera -> {}
//            R.id.nav_gallery -> {}
//            R.id.nav_slideshow -> {}
//        }
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_whatisthis -> {
//                val alertDialog = AlertDialog.Builder(this)
//                alertDialog.setTitle("What is this")
//                alertDialog.setMessage("This is a context menu :)")
//                alertDialog.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
//                alertDialog.setCancelable(true)
//                alertDialog.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}