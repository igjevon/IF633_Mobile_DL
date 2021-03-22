package id.ac.umn.uts_b_26850;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainSongActivity extends AppCompatActivity {
    ArrayAdapter<String> songAdapter;
    ArrayList<File> songArrayList;
    ListView songList;
    String songs[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_song);
        popUpMenu();
        songList = findViewById(R.id.listView);

        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                songArrayList = findMusicFiles(Environment.getExternalStorageDirectory());
                songs = new String[songArrayList.size()];
                for (int i = 0; i <songArrayList.size(); i++) {
                    songs[i] = songArrayList.get(i).getName();
                }

                songAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview_whitetext, R.id.list_content, songs);
                songList.setAdapter(songAdapter);

                songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(MainSongActivity.this, PlaySongActivity.class)
                                .putExtra("songsList", songArrayList)
                                .putExtra("position", position));
                    }
                });
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private void popUpMenu(){
        new AlertDialog.Builder(this).setTitle("Selamat Datang").setMessage("Ignatius Giovanni Jevon - 00000026850")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int click) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.profil:
                Intent intentProfil = new Intent(MainSongActivity.this, ProfilActivity.class);
                if (intentProfil.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentProfil);
                }
                return true;
            case R.id.logOut:
                Intent intentLogout = new Intent(MainSongActivity.this, LoginActivity.class);
                startActivityForResult(intentLogout, 1);
            default: return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<File> findMusicFiles (File file) {
        ArrayList<File> musicfileobject = new ArrayList<>();
        File [] files = file.listFiles();

        for (File currentFiles: files) {
            if (currentFiles.isDirectory() && !currentFiles.isHidden()) {
                musicfileobject.addAll(findMusicFiles(currentFiles));
            } else {
                if (currentFiles.getName().endsWith(".mp3")) {
                    musicfileobject.add(currentFiles);
                }
            }
        }
        return musicfileobject;
    }

}