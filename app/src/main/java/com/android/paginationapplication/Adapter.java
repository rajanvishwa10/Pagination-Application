package com.android.paginationapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Context context;
    private final List<POJO> pojoList;
    private final Activity activity;

    public Adapter(Context context, List<POJO> pojoList, Activity activity) {
        this.context = context;
        this.pojoList = pojoList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_data, parent, false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        POJO pojo = pojoList.get(position);
        String reputation = pojo.getReputation();
        String display_name = pojo.getDisplay_name();
        String profile_link = pojo.getLink();
        String user_id = pojo.getUser_id();
        String profile_image = pojo.getProfile_image();

        Glide.with(context).load(profile_image).transform(new CenterCrop(), new RoundedCorners(10))
                .into(holder.imageView);
        holder.textView.setText(reputation);
        holder.textView2.setText(user_id);
        holder.textView3.setText(display_name);
        holder.textView4.setOnClickListener(v -> {
            //View profile with chrome custom tabs
//            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//            CustomTabsIntent customTabsIntent = builder.build();
//            customTabsIntent.launchUrl(context, Uri.parse(profile_link));

            //Through intent
            Intent intent = new Intent(context, MainActivity2.class);
            intent.putExtra("url", profile_link);
            activity.overridePendingTransition(0, 0);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return pojoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2, textView3, textView4;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.reputation);
            textView2 = itemView.findViewById(R.id.user_id);
            textView3 = itemView.findViewById(R.id.display_name);
            textView4 = itemView.findViewById(R.id.profile_link);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
