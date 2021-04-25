package id.ac.umn.week11_26850;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.telecom.Call;
import android.widget.Toast;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvPostList;
    PostAdapter adapter;

    ArrayList<PostModel> posts;

    netInterface netInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPostList = findViewById(R.id.rvPostList);
        rvPostList.setLayoutManager(new LinearLayoutManager(this));

        netInterface = retrofitConfig.getClient().create(netInterface.class);
        getPosts();
    }

    public void getPosts() {
        retrofit2.Call<ArrayList<PostModel>> postModelCall = netInterface.getPosts();
        postModelCall.enqueue(new retrofit2.Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                posts = response.body();
                adapter = new PostAdapter(posts);
                rvPostList.setAdapter(adapter);
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<PostModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Internet not available", Toast.LENGTH_LONG).show();

            }
        });
    }
}