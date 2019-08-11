package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Commetn_Item> arrList;
    private Context mContext;
    private RecyclerView comment_recycle;

    public ActivityCommentAdapter(Context mContext, ArrayList<Commetn_Item> arrList, RecyclerView comment_recycle) {
        this.arrList = arrList;
        this.mContext = mContext;
        this.comment_recycle = comment_recycle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new RecyclerViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder1 recyclerViewHolder = (RecyclerViewHolder1) holder;
        Commetn_Item item = arrList.get(position);

        recyclerViewHolder.text.setText(item.getCommentText());
        recyclerViewHolder.time.setText(item.getCommetTime());
        recyclerViewHolder.name.setText(item.getCommentName());

        recyclerViewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("WRITER", arrList.get(position).getCommentName());
                    jsonObject.put("TIME", arrList.get(position).getCommetTime());
                    jsonObject.put("COMM", arrList.get(position).getCommentText());
                    jsonObject.put("RECIPE_ID", arrList.get(position).getsRecipeId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpConnection connPost = new HttpConnection(mContext,"DeleteComment", jsonObject);
                connPost.execute();
                ActivityCommentHttpConn httpConn = new ActivityCommentHttpConn(mContext,"GetComment/", arrList.get(position).getsRecipeId(), comment_recycle, new AppCompatDialog(mContext));
                httpConn.execute();
            }
        });

        if (!item.getCommentProfile().equals("NoImg"))
            GlideApp.with(mContext).load(item.getCommentProfile()).into(recyclerViewHolder.profile);

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                if(item.getCommentName().equals(result.getNickname())){
                    recyclerViewHolder.del.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public static class RecyclerViewHolder1 extends RecyclerView.ViewHolder {
       ImageView profile;
       TextView name, time, text, del;

        public RecyclerViewHolder1(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.comment_profile);
            profile.setBackground(new ShapeDrawable(new OvalShape()));
            profile.setClipToOutline(true);
            del =itemView.findViewById(R.id.comment_del);
            name = itemView.findViewById(R.id.comment_name);
            time = itemView.findViewById(R.id.comment_time);
            text = itemView.findViewById(R.id.comment_text);


        }
    }
}
