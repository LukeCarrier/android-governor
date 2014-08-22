package com.governorapp.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.governorapp.R;

import java.util.List;

/**
 * Governor settings.
 */
public class SettingsActivity extends PreferenceActivity {
    /**
     * Load preference headers into header list.
     *
     * @param target
     */
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences_headers, target);
    }

    /**
     * Developer options fragment,
     */
    public static class DeveloperFragment extends PreferenceFragment {
        /**
         * Load preferences into the fragment.
         *
         * @param savedInstanceState
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences_developer, false);
            addPreferencesFromResource(R.xml.preferences_developer);
        }
    }
}
