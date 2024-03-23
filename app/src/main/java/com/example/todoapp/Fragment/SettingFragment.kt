package com.example.todoapp.Fragment

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todoapp.HomeActivity
import com.example.todoapp.R
import java.util.Locale


class SettingFragment:Fragment() {

    private var modes=arrayOf("Light","Dark")
    private var languages=arrayOf("English","Arabic")
    private lateinit var spin_language:Spinner
    private lateinit var spin_mode:Spinner
    private var change_mode:Boolean=true
    private var change_language:Boolean=true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spin_language=view.findViewById(R.id.sp_language)
        spin_mode=view.findViewById(R.id.sp_mode)

        //fill spinners
        fillSpinner(spin_language,languages)
        
        fillSpinner(spin_mode,modes)


         //change language

        spin_language.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (change_language == true) { //this condition to not check when fill the spinner for the first time
                    change_language = false
                }
                else {
                    if (position == 1) {
                        change("ar")
                    } else {
                        change("en")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        //change mode
        spin_mode.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (change_mode == true){ //this condition to prevent the spinner to do the action which i need when fill it , only fill the spinner
                    change_mode = false
                }
                else {
                    if (position == 0) {//light
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    } else if (position == 1) {//dark

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun fillSpinner(spinner: Spinner,item:Array<String>){
       val  adapter_spin =ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,item)
       adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //this line make spaces between the items in spinner
       spinner.adapter=adapter_spin
    }

    private fun change(language:String) {
        val locale = Locale(language)
        val res:Resources=resources
        val config:Configuration=res.configuration
        Locale.setDefault(locale)
        config.setLocale(locale)
        res.updateConfiguration(config,res.displayMetrics)

        // casting requireActivity of fragment as activity and restart it
        (requireActivity() as HomeActivity).restartFragment()
    }
}