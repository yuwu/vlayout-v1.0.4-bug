package com.alibaba.android.vlayout.example;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    VirtualLayoutManager layoutManager;

    DataAdapter adapter;

    DelegateAdapter delegateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        layoutManager = new VirtualLayoutManager(this);

        adapter = new DataAdapter(this);

        VirtualLayoutManager.enableDebugging(true);
        recyclerView.setLayoutManager(layoutManager);

        delegateAdapter = new DelegateAdapter(layoutManager);

        // header
        View header = getLayoutInflater().inflate(R.layout.header, recyclerView, false);
        delegateAdapter.addAdapter(DelegateAdapter.simpleAdapter(header, new LinearLayoutHelper(0, 1)));

        //
        adapter.addAll(getDatas());
        delegateAdapter.addAdapter(adapter);

        recyclerView.setAdapter(delegateAdapter);

        // footer
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && !ViewCompat.canScrollVertically(recyclerView, 1)){

                    addFooter();
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            adapter.addAll(getDatas());
                            adapter.notifyDataSetChanged();
                            // removeFooter();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    int count = 0;
    public List<Integer> getDatas() {
        List<Integer> datas = new ArrayList<>();
        for(int i=0; i<10; i++){
            datas.add(count++);
        }
        return datas;
    }

    DelegateAdapter.Adapter footerAdapter;
    public void addFooter(){
        if(footerAdapter == null){
            View footer = getLayoutInflater().inflate(R.layout.footer, recyclerView, false);
            footerAdapter = DelegateAdapter.simpleAdapter(footer, new LinearLayoutHelper(0, 1));
            delegateAdapter.addAdapter(footerAdapter);
        }else{
            // delegateAdapter.addAdapter(footerAdapter);
        }
    }

    public void removeFooter(){
        delegateAdapter.removeAdapter(footerAdapter);
    }
}
