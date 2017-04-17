package com.example.fang.myzxingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zwe.zxingdemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEditText;
    private Button btnGetCode,btnScanning,btnGetLogoCode;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mEditText= (EditText) findViewById(R.id.content);
        btnGetCode= (Button) findViewById(R.id.getCode);
        btnScanning= (Button) findViewById(R.id.scanning);
        mImageView= (ImageView) findViewById(R.id.code);
        btnGetLogoCode= (Button) findViewById(R.id.getLogoCode);
        btnGetLogoCode.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
        btnScanning.setOnClickListener(this);
    }

    private Bitmap encodeAsBitmap(String str){
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getCode:
                showImage();
                break;
            case R.id.getLogoCode:
                showLogoImage();
                break;

            case R.id.scanning:
                customScan();
                break;
        }
    }



    public void customScan(){
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this,"扫描成功",Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                Toast.makeText(this,ScanResult,Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void showLogoImage() {
        String textContent = mEditText.getText().toString();
        if (TextUtils.isEmpty(textContent)) {
            Toast.makeText(MainActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        mEditText.setText("");
        Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mImageView.setImageBitmap(mBitmap);
    }

    private void showImage() {
        String textContent = mEditText.getText().toString();
        if (TextUtils.isEmpty(textContent)) {
            Toast.makeText(MainActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        mEditText.setText("");
        Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, null);
        mImageView.setImageBitmap(mBitmap);
    }
}

