package com.example.mypreference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by marco on 2/6/17.
 */

public class MyPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        addPreferencesFromResource(R.xml.fragment_preference);
    }
}
