package com.hcp.aradish;

import android.test.ActivityInstrumentationTestCase2;

import com.hcp.aradish.activity.Main_Activity;

/**
 * Created by hcp on 15/6/17.
 */
public class MainTest extends ActivityInstrumentationTestCase2<Main_Activity>{
    public MainTest(){
        super(Main_Activity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

   public void testBtn() throws Throwable{
//        fail("不应该通过测试");
       System.out.println("测试数据");
    }

}
