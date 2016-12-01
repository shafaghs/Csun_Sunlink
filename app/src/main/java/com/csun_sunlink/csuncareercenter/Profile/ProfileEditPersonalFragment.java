package com.csun_sunlink.csuncareercenter.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.csun_sunlink.csuncareercenter.R;

/**
 * Created by olgak on 11/30/16.
 */

public class ProfileEditPersonalFragment extends Fragment {

    private View rootView;
    private Context ctx;
    protected UserPersonal currUser;

    Button save;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_edit_profile, container, false);
        // Inflate the layout for this fragment
        ListView listView =(ListView) view.findViewById(R.id.edit_list);
        ctx = getActivity().getApplicationContext();
        //save = (Button)findViewById(R.id.save_edit_button);
        //set on click listener
        ProfileBgTask bgTask = new ProfileBgTask(ctx, listView);
        bgTask.execute("editPersonalFragment");

        return view;
    }

    public void setUser(UserPersonal newu) {
        this.currUser=newu;
    }

}
