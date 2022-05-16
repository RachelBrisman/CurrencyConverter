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
import com.google.android.material.textfield.TextInputEditText;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private CurrencyConverter mCurrencyConverter;
    private boolean mClearAmountAfterCalculation;
    private String mKeyClearAmountAfterCalculation = "KEY1";
    private String mKEY_SAVED = "SAVE";
    private double currencyAmount;
    private double currencyRate;
    private double total;
    private String result;

    private TextInputEditText name1;
    private TextInputEditText name2;
    private TextInputEditText amount;
    private TextInputEditText rate;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        mCurrencyConverter = new CurrencyConverter();
        setupToolbar();
        setupFAB();
        initializeViews();
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
        //try to do equation
        try {
            currencyAmount = Double.parseDouble(amount.getText().toString());
            currencyRate = Double.parseDouble(rate.getText().toString());
        } catch (NumberFormatException e) {
            //else
            Snackbar.make(view, "Only type in valid doubles for the amount and rate", Snackbar.LENGTH_LONG).show();
            return;
        }

        mCurrencyConverter.setCurrencyFromAmount(currencyAmount);
        mCurrencyConverter.setConversionPercentage(currencyRate);

        try{
            total = mCurrencyConverter.getConvertedCurrencyAmount();

            result = getSnackbarText();

            Snackbar.make(view, result,
                    Snackbar.LENGTH_LONG).show();
            if(mClearAmountAfterCalculation)
            {
                name1.setText("");
                name2.setText("");
                rate.setText("");
                amount.setText("");
            }
        }catch (IllegalStateException e){
            //else
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private String getSnackbarText() {
        return name1 + " " + currencyAmount + " = " + name2 + " " + total;
    }

    private void initializeViews() {
        name1 = findViewById(R.id.currency1Name);
        name2 = findViewById(R.id.currency2Name);
        rate = findViewById(R.id.currencyRate);
        amount = findViewById(R.id.currencyAmount);
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
        }else if (id == R.id.action_info) {
            Utils.showInfoDialog (MainActivity.this,
                    R.string.info, R.string.info_text);
        }else if (id == R.id.action_clear_after_calculate) {
            toggleMenuItem(item);
            mClearAmountAfterCalculation = item.isChecked();
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleMenuItem(MenuItem item)
    {
        item.setChecked(!item.isChecked());
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