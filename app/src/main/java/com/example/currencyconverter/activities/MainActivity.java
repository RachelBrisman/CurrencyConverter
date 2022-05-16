package com.example.currencyconverter.activities;

import static com.example.currencyconverter.model.CurrencyConverter.getCurrencyConverterObjectFromJSONString;

import android.os.Bundle;

import com.example.currencyconverter.R;
import com.example.currencyconverter.lib.Utils;
import com.example.currencyconverter.model.CurrencyConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.currencyconverter.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private CurrencyConverter mCurrencyConverter;
    private boolean mClearAmountAfterCalculation;
    private String mKeyClearAmountAfterCalculation = "KEY1";
    private String mKEY_SAVED = "SAVE";

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        mCurrencyConverter = new CurrencyConverter();
        setupToolbar();
        setupFAB();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> handleFABClick(fab));
    }

    private void handleFABClick(View view) {

        Snackbar.make(view, "Invalid Equation", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onPrepareOptionsMenu (Menu menu)
    {
        menu.findItem (R.id.action_clear_after_calculate).setChecked(mClearAmountAfterCalculation);
        return super.onPrepareOptionsMenu (menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Utils.showInfoDialog (MainActivity.this,
                    R.string.about, R.string.about_text);
        }
        if (id == R.id.action_info) {
            Utils.showInfoDialog (MainActivity.this,
                    R.string.info, R.string.info_text);
        }
        if (id == R.id.action_clear_after_calculate) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(mKEY_SAVED, mCurrencyConverter.getJSONStringFromThis());
        outState.putBoolean(mKeyClearAmountAfterCalculation, mClearAmountAfterCalculation);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrencyConverter = getCurrencyConverterObjectFromJSONString(savedInstanceState.getString(mKEY_SAVED));
        mClearAmountAfterCalculation = savedInstanceState.getBoolean(mKeyClearAmountAfterCalculation);
    }
}