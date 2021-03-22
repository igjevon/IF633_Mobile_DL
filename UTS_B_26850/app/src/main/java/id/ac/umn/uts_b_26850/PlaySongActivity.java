package id.ac.umn.uts_b_26850;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlaySongActivity extends AppCompatActivity {
    static MediaPlayer MediaPlayer;
    ArrayList<File> musicList;
    TextView songName;
    SeekBar mSeekBarTime;
    ImageView prev,play, next;
    Bundle songExtraData;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        prev = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        mSeekBarTime = findViewById(R.id.mSeekBarTime);
        songName  = findViewById(R.id.songName);

        if (MediaPlayer!=null) {
            MediaPlayer.stop();
        }

        Intent intent = getIntent();
        songExtraData = intent.getExtras();
        musicList = (ArrayList)songExtraData.getParcelableArrayList("songsList");
        position = songExtraData.getInt("position", 0);
        initializeMusicPlayer(position);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position < musicList.size() -1) {
                    position++;
                } else {
                    position = 0;
                }
                initializeMusicPlayer(position);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position<=0) {
                    position = musicList.size();
                } else {
                    position++;
                }
                initializeMusicPlayer(position);
            }
        });
    }

    private void initializeMusicPlayer(int position) {
        if (MediaPlayer!=null && MediaPlayer.isPlaying()) {
            MediaPlayer.reset();
        }
        String name = musicList.get(position).getName();
        songName.setText(name);
        Uri uri = Uri.parse(musicList.get(position).toString());
        MediaPlayer = MediaPlayer.create(this, uri);
        MediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBarTime.setMax(MediaPlayer.getDuration());
                play.setImageResource(R.drawable.ic_baseline_pause_24);
                MediaPlayer.start();
            }
        });

        MediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            }
        });

        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if (fromUser) {
                    mSeekBarTime.setProgress(progress);
                    MediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(MediaPlayer!=null) {
                    try {
                        if (MediaPlayer.isPlaying()) {
                            Message message = new Message();
                            message.what = MediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mSeekBarTime.setProgress(msg.what);
        }
    };

    private void play() {
        if (MediaPlayer!=null && MediaPlayer.isPlaying()) {
            MediaPlayer.pause();
            play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        } else {
            MediaPlayer.start();
            play.setImageResource(R.drawable.ic_baseline_pause_24);

        }
    }

}