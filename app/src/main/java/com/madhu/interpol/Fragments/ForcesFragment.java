package com.madhu.interpol.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madhu.interpol.DataClasses.ForcesData;

import com.madhu.interpol.R;
import com.madhu.interpol.RecyclerViewAdapters.ForcesAdapter;
import com.madhu.interpol.Retrofit.ClientInstance;
import com.madhu.interpol.Retrofit.PoliceGetDataService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ForcesFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private ProgressDialog progressDialog;
    private List<ForcesData> mForces;
    private ForcesAdapter forcesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.forces_list_fragment, container,false);

        recyclerView=view.findViewById(R.id.recycler_view);
        searchView=view.findViewById(R.id.search_view);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Interpol");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait, Loading...");
        progressDialog.show();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryFilter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryFilter(newText);
                return false;
            }
        });

        PoliceGetDataService service= ClientInstance.getClientInstance().create(PoliceGetDataService.class);
        Call<List<ForcesData>> call=service.getForces();

        call.enqueue(new Callback<List<ForcesData>>() {
            @Override
            public void onResponse(Call<List<ForcesData>> call, Response<List<ForcesData>> response) {
                progressDialog.dismiss();
                mForces=response.body();

                setForcesAdapter();

            }

            @Override
            public void onFailure(Call<List<ForcesData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Sorry, Something went wrong! Check your Internet Connection and try again.\n\n"+t.getCause()+"\n\n"+t.getMessage(), Toast.LENGTH_LONG).show();
                StringWriter sw=new StringWriter();
                PrintWriter pw=new PrintWriter(sw);
                t.getStackTrace();
            }
        });

        return view;



    }

    private void queryFilter(String queryText)
    {
        ArrayList<ForcesData> list=new ArrayList<>();
        for (ForcesData forcesData: mForces)
        {
            if (forcesData.getName().toLowerCase().contains(queryText.toLowerCase()))
                list.add(forcesData);
        }
        forcesAdapter.queryDataChanged(list);


    }

    private void setForcesAdapter()
    {
        forcesAdapter=new ForcesAdapter(getContext(), mForces, this.getFragmentManager());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(forcesAdapter);

    }
}
