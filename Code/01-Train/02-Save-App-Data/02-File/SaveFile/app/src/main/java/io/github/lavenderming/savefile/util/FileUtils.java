package io.github.lavenderming.savefile.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by 阿懂 on 2017/12/31.
 */

public class FileUtils {
    private static final String LOG_TAG = ".util.FileUtils";

    public class DirectoryName {
        public static final String ALBUM_DIRECTORY_NAME = "相册";
    }

    /* 检测外部存储是否可读也可写 */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* 检测外部存储至少可写 */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getAlbumStorageDir(String albumName) {
        // 在用户公共图片目录下创建一个目录
        File file = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),
                albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }
}
