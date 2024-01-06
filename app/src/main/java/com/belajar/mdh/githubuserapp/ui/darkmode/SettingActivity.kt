package com.belajar.mdh.githubuserapp.ui.darkmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.belajar.mdh.githubuserapp.ui.ViewModelFactory
import com.belajar.mdhgithubuserapp.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {

    private val settingViewModel by viewModels<DarkModeViewModel>{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            themeUpdate(isDarkModeActive)
            switchTheme.isChecked = isDarkModeActive
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

    }

    private fun themeUpdate(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }