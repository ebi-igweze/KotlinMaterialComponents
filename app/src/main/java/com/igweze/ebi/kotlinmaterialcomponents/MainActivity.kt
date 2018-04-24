package com.igweze.ebi.kotlinmaterialcomponents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Basic Components"

        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            Toast.makeText(this, "Fab Clicked", Toast.LENGTH_LONG).show()
        }

        showSnackBarBtn.setOnClickListener {
            val message = "Simple Snack bar"
            Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG).show()
        }

        showSnackBarBtnWithAction.setOnClickListener {
            val message = "Error Loading File"
            val snackBar = Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG)
            snackBar.setAction("Retry") {
                Snackbar.make(coordinator, "File Loaded Successfully", Snackbar.LENGTH_SHORT).show()
            }

            val textColor = ContextCompat.getColor(this, R.color.textColorPrimary)
            val accentColor  = ContextCompat.getColor(this, R.color.colorAccent)
            val primaryColorDark  = ContextCompat.getColor(this, R.color.colorPrimaryDark)

            // set background of snackBar
            snackBar.view.setBackgroundColor(accentColor)

            // set text color
            val textView = snackBar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            textView.setTextColor(textColor)

            // set action btn color
            val actionBtn = snackBar.view.findViewById<Button>(android.support.design.R.id.snackbar_action)
            actionBtn.setTextColor(primaryColorDark)

            snackBar.show()
        }

        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navDrawer.setNavigationItemSelectedListener {
            navText.text = it.title
            // close drawer
            hideDrawer()
            true
        }

        // form submit listener
        submitBtn.setOnClickListener {

            // check if the user name is valid
            val validateUser =  if (userName.text.isEmpty()) {
                userNameLayout.error = "Username cannot be blank"
                false
            } else {
                userNameLayout.isErrorEnabled = false
                true
            }

            // check if the password is valid
            val validatePassword = if (password.text.length < 8) {
                passwordLayout.error = "Password cannot be less than 8 characters"
                false
            } else {
                passwordLayout.isErrorEnabled = false
                true
            }

            // show snackBar if both are valid
            if (validateUser && validatePassword)
                Snackbar.make(it, "Successfully Logged in", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun hideDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId) {
            R.id.showIndividual -> {
                navDrawer.menu.setGroupVisible(R.id.menuItemGroup, false)
                navDrawer.menu.setGroupVisible(R.id.menuItemsOnly, true)
                navDrawer.menu.setGroupVisible(R.id.menuItemHeader, false)
            }
            R.id.showGroup ->{
                navDrawer.menu.setGroupVisible(R.id.menuItemGroup, true)
                navDrawer.menu.setGroupVisible(R.id.menuItemsOnly, false)
                navDrawer.menu.setGroupVisible(R.id.menuItemHeader, false)
            }
            R.id.showGroupHeader -> {
                navDrawer.menu.setGroupVisible(R.id.menuItemGroup, false)
                navDrawer.menu.setGroupVisible(R.id.menuItemsOnly, false)
                navDrawer.menu.setGroupVisible(R.id.menuItemHeader, true)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) hideDrawer()
        else super.onBackPressed()
    }
}
