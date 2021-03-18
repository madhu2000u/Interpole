package com.madhu.interpol.RecyclerViewAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.madhu.interpol.DataClasses.ForcesData;
import com.madhu.interpol.DataClasses.SpecificForceData;
import com.madhu.interpol.Fragments.SpecificForcesFragment;
import com.madhu.interpol.R;
import com.madhu.interpol.Retrofit.ClientInstance;
import com.madhu.interpol.Retrofit.PoliceGetDataService;
import com.madhu.interpol.Utils.Constants;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForcesAdapter extends RecyclerView.Adapter<ForcesAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";



    private List<ForcesData> mForces=new ArrayList<>();
    private Context context;
    FragmentManager fm;

    public ForcesAdapter(Context context, List<ForcesData> mForces, FragmentManager fm) {
        this.context=context;
        this.mForces=mForces;
        this.fm=fm;

    }

    public void queryDataChanged(ArrayList<ForcesData> list)
    {
        mForces=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forces_item_list, viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Log.d("msg", "I am here");

        viewHolder.forceName.setText(mForces.get(i).getName());

        viewHolder.forceId.setText(mForces.get(i).getId());

        viewHolder.recyclerViewListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setMessage("Loading Force...");
                progressDialog.setIndeterminate(true);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String text=viewHolder.forceId.getText().toString();

                openNewFragment(text);
                Log.d("msg","Clicked View "+ text);


                PoliceGetDataService policeGetDataService = ClientInstance.getClientInstance().create(PoliceGetDataService.class);
                Call<SpecificForceData> call = policeGetDataService.getSpecificForce(text);

                call.enqueue(new Callback<SpecificForceData>() {


                    @Override
                    public void onResponse(Call<SpecificForceData> call, Response<SpecificForceData> response) {
                        progressDialog.dismiss();
                        fm.beginTransaction().addToBackStack("fm").add(R.id.fragment_container, new SpecificForcesFragment()).commit();
                        







                        Log.d("msg", "onResponse: recieved"+response.body().getDescription());

                    }

                    @Override
                    public void onFailure(Call<SpecificForceData> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG);

                        Log.d("msg", "onFailure in adapter: Some Error occured with code"+ t.getMessage());

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mForces.size();
    }

    private void openNewFragment(String forceId)
    {


    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView forceName;
        TextView forceId;
        LinearLayout recyclerViewListItem;     //forces_item_list

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            forceName =itemView.findViewById(R.id.force_name);
            forceId =itemView.findViewById(R.id.force_id);
            recyclerViewListItem=itemView.findViewById(R.id.forces_item_list);



        }
    }
}
