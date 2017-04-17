package com.example.fang.myzxingtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.zwe.zxingdemo.R;

public class CustomScanActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener{
    private DecoratedBarcodeView mDecoratedBarcodeView;
    private CaptureManager captureManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
        mDecoratedBarcodeView= (DecoratedBarcodeView) findViewById(R.id.dbv_custom);
        captureManager = new CaptureManager(this,mDecoratedBarcodeView);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDecoratedBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }
}
