package com.governorapp.util;

import android.os.Environment;

/**
 * Created by lidongxu on 17/6/9.
 */

public class Constants {
    public static final String UPLOAD_FILENAME = "/governor_upload/" ;
    public static final String UPLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + UPLOAD_FILENAME ;

}
