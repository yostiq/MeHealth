package com.mehealth.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.mehealth.R;
import com.mehealth.utilities.SharedPref;
import com.mehealth.user.User;

import java.util.Objects;

/**
 * Settings activity containing the preferences fragment.
 * User can set basic values such as their name and reset the values collected by the app thus far.
 * @author Amin Karaoui
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default settings for creating a settings activity
        super.onCreate(savedInstanceState);

        //Setup the settings fragment
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Sets the toolbar for the activity with back button on the left of the title
        Toolbar toolbar = findViewById(R.id.toolbarTop);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Asetukset");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            EditTextPreference age = getPreferenceManager().findPreference("age");
            age.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
            });

            EditTextPreference height = getPreferenceManager().findPreference("height");
            height.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
            });
        }

        /**
         * Listens for clicks on the preferences.
         * If a preference given preference based on their id is clicked, executes the code below.
         * @param preference Name of the preference clicked
         */
        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            if (preference.getKey().equals("buttonResetEverything")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Nollaa kaikki")
                        .setMessage("Oletko varma?")
                        .setNegativeButton("Peruuta", null)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Gets the user class from shared preferences and calls the method to reset everything.
                                SharedPref pref = new SharedPref(Objects.requireNonNull(getContext()));
                                User user = pref.getUser();
                                user.clearEverything();
                                pref.saveUser(user);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            return super.onPreferenceTreeClick(preference);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_settings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
        return super.onOptionsItemSelected(item);
    }
}