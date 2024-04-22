package com.example.todoapp.Fragment

import android.content.Context
import android.content.SharedPreferences
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
import com.example.todoapp.SharedPreferenceHelper
import java.util.Locale


class SettingFragment:Fragment() {

    private var languages= arrayOf("English","Arabic")
    private var modes= arrayOf("Light","Dark")
    private lateinit var spin_language:Spinner
    private lateinit var spin_mode:Spinner
    private lateinit var adapter_spinner:ArrayAdapter<String>
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
        fillSpinner(spin_language,"language")
        fillSpinner(spin_mode,"mode")


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

                    ///save the mode
                    SharedPreferenceHelper.LanguagePreferenceHelper.saveMode(requireContext(),position)

                    //call this fun again to update the change
                    fillSpinner(spin_mode,"mode")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun fillSpinner(spinner: Spinner,typeOfItem:String){

        if(typeOfItem=="language")
        {
            //first get the last item the user choose
            val selected_language=SharedPreferenceHelper.LanguagePreferenceHelper.getLanguage(requireContext())

            if(selected_language=="ar") {
                 adapter_spinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf(languages[1],languages[0]))
            }
            else{
                 adapter_spinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf(languages[0],languages[1]))
            }
            adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //this line make spaces between the items in spinner
            spinner.adapter=adapter_spinner
        }
       else if(typeOfItem=="mode")
        {
            val selected_mode=SharedPreferenceHelper.LanguagePreferenceHelper.getMode(requireContext())

            if(selected_mode==0) {
                adapter_spinner =ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, arrayOf(modes[0],modes[1]))
            }
            else {
               adapter_spinner =ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,arrayOf(modes[1],modes[0]))
            }
            adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //this line make spaces between the items in spinner
            spinner.adapter=adapter_spinner
        }


    }

    private fun change(language:String) {
        val locale = Locale(language)
        val res:Resources=resources
        val config:Configuration=res.configuration
        Locale.setDefault(locale)
        config.setLocale(locale)
        res.updateConfiguration(config,res.displayMetrics)

        //save the language
        SharedPreferenceHelper.LanguagePreferenceHelper.saveLanguage(requireContext(),language)
        //call this fun again to update the change

        // casting requireActivity of fragment as activity and restart it
        (requireActivity() as HomeActivity).restartFragment()



    }

}