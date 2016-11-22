package com.csun_sunlink.csuncareercenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by olgak on 11/21/16.
 * used to populated side menu adapter
 */

public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuDrawerAdapter.ViewHolder> {

        //Variables:
        private static final int TYPE_HEADER = 0;
        // IF the view under inflation and population is header or Item
        private static final int TYPE_ITEM = 1;
        private String mNavTitles[];
        private int mIcons[];

        private String name;
        private int profile;        //int Resource for header view profile picture
        private String email;


        public static class ViewHolder extends RecyclerView.ViewHolder {
            int Holderid;
            TextView textView;
            ImageView imageView;
            ImageView profile;
            TextView Name;
            TextView email;


            public ViewHolder(View itemView,int ViewType) {
                super(itemView);

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


        }

        // Constructor:
        MenuDrawerAdapter(String Titles[],int Icons[],String newname,String newemail, int newprofile){
            this.mNavTitles = Titles;
            this.mIcons = Icons;
            this.name = newname;
            this.email = newemail;
            this.profile = newprofile;

        }

        @Override
        public MenuDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
                holder.Name.setText(name);
                holder.email.setText(email);
            }
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