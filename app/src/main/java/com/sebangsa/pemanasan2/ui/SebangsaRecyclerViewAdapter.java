package com.sebangsa.pemanasan2.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sebangsa.pemanasan2.R;
import com.sebangsa.pemanasan2.model.UserRealm;

import java.util.List;

/**
 * Created by sebangsa on 8/30/16.
 */
public class SebangsaRecyclerViewAdapter extends RecyclerView.Adapter<SebangsaRecyclerViewAdapter.SebangsaRecyclerViewHolder> {

    private List<UserRealm> listUser;
    private LayoutInflater inflater;
    private Context context;
    private String type;

    public SebangsaRecyclerViewAdapter(List list, Context c) {
        inflater = LayoutInflater.from(context);
        listUser = (List<UserRealm>) list;
        this.context = c;
    }

    @Override
    public SebangsaRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new SebangsaRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SebangsaRecyclerViewHolder holder, final int position) {
        final UserRealm user = listUser.get(position);
        holder.username.setText("@" + user.getUsername());
        holder.name.setText(user.getName());
        holder.members.setVisibility(View.INVISIBLE);

        Glide.with(context).load(user.getMedium().trim()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imageAvatar) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.imageAvatar.setImageDrawable(circularBitmapDrawable);
            }
        });

        holder.buttonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isFollow()) {
                    user.setFollow(false);
                } else {
                    user.setFollow(true);
                }
                setImageButtonUser(user, holder);
            }
        });

        setImageButtonUser(user, holder);

    }

    private void setImageButtonUser(UserRealm user, SebangsaRecyclerViewHolder holder) {
        if (user.isFollow()) {
            holder.buttonFollow.setImageResource(R.drawable.i_followed);
            holder.buttonFollow.setBackgroundResource(R.drawable.rounded_corners_imagebutton_green);
        } else {
            holder.buttonFollow.setImageResource(R.drawable.i_follow);
            holder.buttonFollow.setBackgroundResource(R.drawable.rounded_corners_imagebutton_white);
        }
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class SebangsaRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView name;
        private ImageView imageAvatar;
        private ImageButton buttonFollow;
        private TextView members;
        private View container;

        public SebangsaRecyclerViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.textView_username);
            name = (TextView) itemView.findViewById(R.id.textView_name);
            imageAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
            buttonFollow = (ImageButton) itemView.findViewById(R.id.imageButton_follow);
            members = (TextView) itemView.findViewById(R.id.textView_members);
            container = itemView.findViewById(R.id.root_view);
        }
    }
}
