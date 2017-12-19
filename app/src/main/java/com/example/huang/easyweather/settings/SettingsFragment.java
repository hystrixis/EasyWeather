package com.example.huang.easyweather.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.huang.easyweather.About;
import com.example.huang.easyweather.R;
import com.example.huang.easyweather.utilities.Utility;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Created by huang on 2017/6/23.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    CheckBoxPreference wifiUpdate;
    ListPreference updateFrequencyListPreference;
    Preference aboutPreference;
    Preference updatePreference;
    public static final String KEY_PREF_WIFI_UPDATE="wifi_update";
    public static final String KEY_PREF_UPDATE_FREQUENCY="update_frequency";
    private static final String TAG="SettingsActivity";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        wifiUpdate=(CheckBoxPreference)findPreference(getString(R.string.pref_key_wifi_update));
        updateFrequencyListPreference=(ListPreference)findPreference(getString(R.string.pref_key_update_frequency));
        aboutPreference=findPreference(getString(R.string.pref_key_about));
        updatePreference=findPreference(getString(R.string.pref_key_update));
        updatePreference.setSummary("当前版本 "+Utility.getVersion(getActivity()));
        updateFrequencyListPreference.setSummary(updateFrequencyListPreference.getEntry());
//        Log.d(TAG,": "+updateFrequencyListPreference.getValue());
//        Log.d(TAG,"值: "+prefs.getString(KEY_PREF_UPDATE_FREQUENCY,""));

        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(getActivity());
        alertDialogBuilder.create();
//

        //PreferenceManager.setDefaultValues(getActivity(),R.xml.settings,false);//设置默认值
//        prefs.getBoolean(KEY_PREF_WIFI_UPDATE,false);
//        prefs.getString(KEY_PREF_UPDATE_FREQUENCY,null);
//        updateFrequencyListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                CharSequence[] entries=updateFrequencyListPreference.getEntries();//获取实体内容
//                int index=updateFrequencyListPreference.findIndexOfValue((String)newValue);//获取实体内容的下标值
//                updateFrequencyListPreference.setSummary(entries[index]);
//                return true;
//            }
//        });

        aboutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent=new Intent(getActivity(), About.class);
                startActivity(intent);
                return true;
            }
        });
        updatePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Beta.checkUpgrade(true,false);
                return true;
            }
        });
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final Preference pref=findPreference(key);//获取已更改的Preference对象
        if(key.equals(KEY_PREF_WIFI_UPDATE)){
            pref.setDefaultValue(sharedPreferences.getBoolean(key,false));
        }else if (key.equals(KEY_PREF_UPDATE_FREQUENCY)){
                pref.setSummary(updateFrequencyListPreference.getEntry());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


}
