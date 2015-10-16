package com.example.qwerty.learn;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class MyInstrumentation extends
        android.test.InstrumentationTestRunner {
    private Writer mWriter;
    private XmlSerializer mTestSuiteSerializer;
    private long mTestStarted;
    private String strTime;
    private String xmlName;

    public void onStart() {
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-kk-mm");
            strTime = sdf.format(d);
            xmlName = "Test" + strTime + ".xml";
            // 如果被测的应用本身有读写sdcard权限的话级可以直接放在sdcard里面，否则机会失败，
            // 有测试应用源码的情况下是可以在AndroidManifest.xml里添加权限，当然所数情况下是没有源码的，
            // 只能放在被测应用的files目录里了，这个是不需要权限的
            // String SDPath = Environment.getExternalStorageDirectory() + "/";
            // String logPath = SDPath + "TestLog/";
            // File file = new File(logPath);
            // if (file.exists()) {
            // } else {
            // file.mkdirs();
            // }
            // startJUnitOutput(new FileWriter(new File(file, xmlName)));
            startJUnitOutput(new FileWriter(new File(getTargetContext()
                    .getFilesDir(), xmlName)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.onStart();
    }

    void startJUnitOutput(Writer writer) {
        try {
            this.mWriter = writer;
            this.mTestSuiteSerializer = newSerializer(this.mWriter);
            this.mTestSuiteSerializer.startDocument(null, null);
            this.mTestSuiteSerializer.startTag(null, "testsuites");
            this.mTestSuiteSerializer.startTag(null, "testsuite");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private XmlSerializer newSerializer(Writer writer) {
        try {
            XmlPullParserFactory pf = XmlPullParserFactory.newInstance();
            XmlSerializer serializer = pf.newSerializer();
            serializer.setOutput(writer);
            return serializer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void sendStatus(int resultCode, Bundle results) {
        super.sendStatus(resultCode, results);
        switch (resultCode) {
            case -2:
            case -1:
            case 0:
                try {
                    recordTestResult(resultCode, results);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            case 1:
                recordTestStart(results);
        }
    }

    void recordTestStart(Bundle results) {
        this.mTestStarted = System.currentTimeMillis();
    }

    void recordTestResult(int resultCode, Bundle results) throws IOException {
        float time = (float) (System.currentTimeMillis() - this.mTestStarted) / 1000.0F;
        String className = results.getString("class");
        String testMethod = results.getString("test");
        String stack = results.getString("stack");
        int current = results.getInt("current");
        int total = results.getInt("numtests");

        this.mTestSuiteSerializer.startTag(null, "testcase");
        this.mTestSuiteSerializer.attribute(null, "ID", current + "");
        this.mTestSuiteSerializer.attribute(null, "classname", className);
        this.mTestSuiteSerializer.attribute(null, "casename", testMethod);
        // Log.v("myInfor", current + "");
        if (resultCode != 0) {
            this.mTestSuiteSerializer
                    .attribute(
                            null,
                            "time",
                            String.format("%.3f",
                                    new Object[] { Float.valueOf(time) }));
            this.mTestSuiteSerializer.startTag(null, "result");
            if (stack != null) {
                String reason = stack.substring(0, stack.indexOf('\n'));
                String message = "";
                int index = reason.indexOf(':');
                if (index > -1) {
                    message = reason.substring(index + 1);
                    reason = reason.substring(0, index);
                }
                this.mTestSuiteSerializer.attribute(null, "message", message);
                // this.mTestSuiteSerializer.attribute(null, "type", reason);
                // this.mTestSuiteSerializer.text(stack);
                this.mTestSuiteSerializer.text("failure");
            }
            this.mTestSuiteSerializer.endTag(null, "result");
        } else {
            this.mTestSuiteSerializer
                    .attribute(
                            null,
                            "time",
                            String.format("%.3f",
                                    new Object[] { Float.valueOf(time) }));
            this.mTestSuiteSerializer.startTag(null, "result");
            this.mTestSuiteSerializer.attribute(null, "message", "pass");
            this.mTestSuiteSerializer.text("success");
            this.mTestSuiteSerializer.endTag(null, "result");
        }
        this.mTestSuiteSerializer.endTag(null, "testcase");
        if (current == total) {
            // this.mTestSuiteSerializer.startTag(null, "system-out");
            // this.mTestSuiteSerializer.endTag(null, "system-out");
            // this.mTestSuiteSerializer.startTag(null, "system-err");
            // this.mTestSuiteSerializer.endTag(null, "system-err");
            this.mTestSuiteSerializer.endTag(null, "testsuite");
            this.mTestSuiteSerializer.flush();
        }
    }

    public void finish(int resultCode, Bundle results) {
        endTestSuites();
        Log.i("testreport", this.getComponentName().getPackageName());
        Log.i("testreport", this.getTargetContext().getFilesDir().toString());
        //报告完成后将xml文件pull到指定文件夹
        try{
            Runtime.getRuntime().exec("pull data/data/com.example.qwerty.learn/files/"+xmlName+" d:/git/androidlearn/learn/testreport/" + "\n");
        }catch (IOException e){
            e.fillInStackTrace();
        }
        super.finish(resultCode, results);
    }

    void endTestSuites() {
        try {
            this.mTestSuiteSerializer.endTag(null, "testsuites");
            this.mTestSuiteSerializer.endDocument();
            this.mTestSuiteSerializer.flush();
            this.mWriter.flush();
            this.mWriter.close();
            Log.i("testreport", this.getComponentName().getPackageName());
            Runtime.getRuntime().exec("cmd /c adb pull data/data/com.example.qwerty.learn/files/"+xmlName+" d:/git/androidlearn/learn/testreport/");
            //报告完成后将xml文件pull到指定文件夹
//            suShell("adb pull " + this.getTargetContext().getFilesDir().toString()+"/"+xmlName+" d:/git/androidlearn/learn/testreport/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void suShell(String cmd) {
        Process ps = null;
        DataOutputStream os;

        try {
            ps = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(ps.getOutputStream());

            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}