package com.android.paginationapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private TextView textView;
    private List<POJO> pojoList;
    private int page = 1, pageSize = 50, currentItems, totalItems, scrollOutItems;
    boolean isScrolling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        pojoList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        textView = findViewById(R.id.textView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Data");

        //getting default data from api
        getData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {
                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                        isScrolling = false;
                        page = page + 1;
                        progressBar.setVisibility(View.VISIBLE);
                        getData(page, pageSize); //getting data from api using pagination
                    }
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    //default data
    private void getData() {
        progressDialog.show();
        String url = "https://api.stackexchange.com/2.2/answers?page="+page+"&pagesize="+pageSize+"&site=stackoverflow";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray itemJson = jsonObject.getJSONArray("items");
                for (int i = 0; i < itemJson.length(); i++) {
                    JSONObject jsonObject1 = itemJson.getJSONObject(i);
                    JSONObject ownerJson = jsonObject1.getJSONObject("owner");
                    String reputation = ownerJson.getString("reputation");
                    String user_id = ownerJson.getString("user_id");
                    String profile_image = ownerJson.getString("profile_image");
                    String display_name = ownerJson.getString("display_name");
                    String link = ownerJson.getString("link");
                    pojoList.add(new POJO(reputation, user_id, profile_image, display_name, link));
                    adapter = new Adapter(this, pojoList, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                    textView.setVisibility(View.GONE);
                }
                System.out.println("size of list = " + pojoList.size());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Fetching Data failed", Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //pagination data
    private void getData(int page, int pageSize) {
        System.out.println("page" + page);
        System.out.println("pageSize" + pageSize);
        String url = "https://api.stackexchange.com/2.2/answers?page=" + page + "&pagesize=" + pageSize + "&site=stackoverflow";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray itemJson = jsonObject.getJSONArray("items");
                for (int i = 0; i < itemJson.length(); i++) {
                    JSONObject jsonObject1 = itemJson.getJSONObject(i);
                    JSONObject ownerJson = jsonObject1.getJSONObject("owner");
                    String reputation = ownerJson.getString("reputation");
                    String user_id = ownerJson.getString("user_id");
                    String profile_image = ownerJson.getString("profile_image");
                    String display_name = ownerJson.getString("display_name");
                    String link = ownerJson.getString("link");
                    pojoList.add(new POJO(reputation, user_id, profile_image, display_name, link));
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
                System.out.println("size of list = " + pojoList.size());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            progressDialog.dismiss();
            textView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Fetching Data failed", Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}