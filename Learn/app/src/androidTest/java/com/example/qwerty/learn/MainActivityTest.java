package com.example.qwerty.learn;

import android.test.ActivityInstrumentationTestCase2;

import com.example.qwerty.learn.Activity.MainActivity;
import com.robotium.solo.Solo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception{
        try{
            this.solo.finishOpenedActivities();
        }catch (Exception e){
            e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();
    }

    public void test_click_lv(){
        solo.clickOnView(solo.getView(R.id.open_my_lv_btn));
        solo.sleep(2000);
        solo.goBack();
    }

    public void test_click_chat(){
        solo.clickOnView(solo.getView(R.id.open_chat_btn));
        solo.sleep(2000);
        solo.goBack();
    }

    public void test_click_file(){
        solo.clickOnView(solo.getView(R.id.open_save_file_btn));
        solo.sleep(2000);
        solo.goBack();
    }

    public void test_click_database(){
        solo.clickOnView(solo.getView(R.id.open_database_btn));
        solo.sleep(2000);
        solo.goBack();
    }



}