package com.example.qwerty.learn;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
        super.finish(resultCode, results);
    }

    void endTestSuites() {
        try {
            this.mTestSuiteSerializer.endTag(null, "testsuites");
            this.mTestSuiteSerializer.endDocument();
            this.mTestSuiteSerializer.flush();
            this.mWriter.flush();
            this.mWriter.close();
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