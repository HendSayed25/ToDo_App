package com.example.todoapp.Fragment

import android.app.Activity
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
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import java.util.Locale


class SettingFragment:Fragment() {

    private var modes=arrayOf("Light","Dark")
    private var languages=arrayOf("English","Arabic")
    private lateinit var spin_language:Spinner
    private lateinit var spin_mode:Spinner

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
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position==0){
                    change("en");
                }
                else if(position==1){
                    change("ar")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //change mode
       /* spin_mode.onItemSelectedListener=object :OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position==0){//light
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
                else if(position==1){//dark

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }*/
    }

    private fun fillSpinner(spinner: Spinner,item:Array<String>){
       val  adapter_spin =ArrayAdapter<Any?>(requireContext(),android.R.layout.simple_spinner_item,item)
       adapter_spin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line) //this line make spaces between the items in spinner
       spinner.adapter=adapter_spin
    }

    private fun change(language:String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res:Resources=requireContext().resources
        val config:Configuration=res.configuration
        config.setLocale(locale)
        res.updateConfiguration(config,res.displayMetrics)

        // casting requireActivity of fragment as activity and restart it
        (requireActivity() as Activity).recreate()
    }
}