package com.csun_sunlink.csuncareercenter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csun_sunlink.csuncareercenter.Profile.ProfileActivity;
import com.csun_sunlink.csuncareercenter.Search.SearchStart;

/**
 * Created by olgak on 11/21/16.
 * used to populated side menu adapter
 */

public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuDrawerAdapter.ViewHolder>  {

        //Variables:
        private static final int TYPE_HEADER = 0;
        // IF the view under inflation and population is header or Item
        private static final int TYPE_ITEM = 1;
        private String mNavTitles[];
        private int mIcons[];

        private String name;
        private int profile;        //int Resource for header view profile picture
        private String email;
        Context context;




    String titles[] = {"Home","Profile","Search","My Career Center","Resources","Settings"};
    //Testing Purposes:
        int icons[] = {R.drawable.ic_home_white_48dp,R.drawable.ic_person_white_48dp,R.drawable.ic_search_white_48dp,R.drawable.ic_business_center_white_48dp,R.drawable.ic_library_books_white_48dp, R.drawable.ic_settings_white_48dp};
    String newName;
    String newEmail;
    int newProfile = R.drawable.defaultpicture;


        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            int Holderid;
            TextView textView;
            ImageView imageView;
            ImageView profile;
            TextView Name;
            TextView email;

            ItemClickListener clickListener;


            public ViewHolder(View itemView,int ViewType) {
                super(itemView);
                itemView.setOnClickListener(this);
                if(ViewType == TYPE_ITEM) {
                    textView = (TextView) itemView.findViewById(R.id.rowText);
                    imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                    Holderid = 1;
                }
                else {
                    //Populate header
                    Name = (TextView) itemView.findViewById(R.id.header_name);
                    email = (TextView) itemView.findViewById(R.id.header_email);
                    profile = (ImageView) itemView.findViewById(R.id.circleView);
                    Holderid = 0;
                }
            }

            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }
            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getPosition(), false);
            }

        }

        // Constructor:
       public MenuDrawerAdapter(){
            this.mNavTitles = titles;
            this.mIcons = icons;
            this.profile = newProfile;
        }

        @Override
        public MenuDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
            String fName = pref.getString("first_name","");
            String lName = pref.getString("family_name","");
            String name = fName + " " +lName;
            newName = name;
            newEmail = pref.getString("user_email","");
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item,parent,false);
                ViewHolder vhItem = new ViewHolder(v,viewType);
                return vhItem;

            } else if (viewType == TYPE_HEADER) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
                ViewHolder vhHeader = new ViewHolder(v,viewType);
                return vhHeader;
            }
            return null;

        }

        @Override
        public void onBindViewHolder(MenuDrawerAdapter.ViewHolder holder, int position) {
            if(holder.Holderid == 1) {
                // position by 1 and pass it to the holder while setting the text and image
                holder.textView.setText(mNavTitles[position - 1]);
                holder.imageView.setImageResource(mIcons[position -1]);
            }
            else{

                holder.profile.setImageResource(profile);
                holder.Name.setText(newName);
                holder.email.setText(newEmail);
            }
            holder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                      switch (position) {
                          case 1:
                              Intent intent = new Intent(view.getContext(),HomePage.class);
                              view.getContext().startActivity(intent);
                              break;
                          case 2:
                              Intent intent2 = new Intent(view.getContext(),ProfileActivity.class);
                              view.getContext().startActivity(intent2);
                              break;
                          case 3:
                              Intent intent3 = new Intent(view.getContext(),SearchStart.class);
                              view.getContext().startActivity(intent3);
                              break;

                          default:
                              break;
                      }

                }
            });
        }

        @Override
        public int getItemCount() {
            return mNavTitles.length+1;
        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;

            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }



    }