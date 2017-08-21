package com.governorapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by lidongxu on 17/6/9.
 */

public class TempActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent() ;
        Intent tmpIntent = intent.getParcelableExtra("intent");
        this.startActivity(tmpIntent);
        this.finish();
    }
}
