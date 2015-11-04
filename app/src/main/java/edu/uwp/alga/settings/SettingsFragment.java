package edu.uwp.alga.settings;

/**
 * Copyright 2015 UW-Parkside, Harleen Kaur, Hanh Le, Francisco Mateo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import edu.uwp.alga.R;

public class SettingsFragment extends PreferenceFragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    /**
     * Called when a shared preference is changed, added, or removed. This
     * may be called even if a preference is set to its existing value.
     * <p/>
     * <p>This callback will be run on your main thread.
     *
     * @param sharedPreferences The {@link SharedPreferences} that receive the change.
     * @param key The key of the preference that was changed, added, or
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch(key) {
            default:
                Log.d("PREFS", key);
                break;
        }
    }

    /**
     * To ensure proper lifecycle management in the activity, Google recommends that you register
     * and unregister your {@link android.content.SharedPreferences.OnSharedPreferenceChangeListener}
     * during the {@link #onResume()} and {@link #onPause()} callbacks.
     *
     * <b>Caution:</b>
     * When you call {@link android.content.SharedPreferences.OnSharedPreferenceChangeListener},
     * the preference manager does not currently store a strong reference to the listener. You must
     * store a strong reference to the listener, or it will be susceptible to garbage collection.
     * Google recommends you keep a reference to the listener in the instance data of an object
     * that will exist as long as you need the listener. In other words, use an instance variable,
     * not an anonymous inner class.
     */
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * See {@link #onResume()}
     */
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
