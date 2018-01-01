package io.github.lavenderming.savefile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import io.github.lavenderming.savefile.util.FileUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FileUtils.isExternalStorageWritable()){
                    Toast.makeText(MainActivity.this,
                            FileUtils.getAlbumStorageDir(
                                    FileUtils.DirectoryName.ALBUM_DIRECTORY_NAME).toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
