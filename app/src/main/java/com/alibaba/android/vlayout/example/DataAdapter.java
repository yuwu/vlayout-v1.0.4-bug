package com.alibaba.android.vlayout.example;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class DataAdapter extends DelegateAdapter.Adapter {
    private static final int ITEM_TYPE_FIX = 0;
    private static final int ITEM_TYPE_1 = 1;
    private static final int ITEM_TYPE_2 = 2;
    private static final int ITEM_TYPE_3 = 3;
    private static final int ITEM_TYPE_4 = 4;
    private static final int ITEM_TYPE_5 = 5;
    private static final int ITEM_TYPE_6 = 6;

    private final int item_types[] = new int[]{ITEM_TYPE_1, ITEM_TYPE_2, ITEM_TYPE_3, ITEM_TYPE_4, ITEM_TYPE_5, ITEM_TYPE_6};

    private float designWidth = 336f;
    private int designHeight[] = new int[]{336, 280, 500, 400, 276, 450};

    public boolean show_item_type_fix = true;

    public int padding;
    public int lane;

    private List<Integer> datas = new ArrayList<>();

    public DataAdapter(Activity activity) {
        padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, activity.getResources().getDisplayMetrics());
        lane = 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case ITEM_TYPE_FIX: {

                View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                int widthPixels = parent.getWidth();
                int width = (widthPixels-padding*(lane+1))/lane;
                int height = (int)(width * (designHeight[3])/designWidth);

                ViewGroup.LayoutParams params = itemView.getLayoutParams();
                params.width = width;
                params.height = height;
                itemView.setLayoutParams(params);

                holder = new SimpleViewHolder(itemView);
                break;
            }
            case ITEM_TYPE_1:
            case ITEM_TYPE_2:
            case ITEM_TYPE_3:
            case ITEM_TYPE_4:
            case ITEM_TYPE_5:
            case ITEM_TYPE_6: {

                View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

				int widthPixels = parent.getWidth();
				int width = (widthPixels-padding*(lane+1))/lane;
				int height = (int)(width * (designWidth/designHeight[viewType-1]));

				ViewGroup.LayoutParams params = itemView.getLayoutParams();
				params.width = width;
				params.height = height;
				itemView.setLayoutParams(params);

                holder = new SimpleViewHolder(itemView);
                break;
            }
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int type = getItemViewType(position);
        switch (type){
            case ITEM_TYPE_FIX: {

                TextView tv = (TextView) holder.itemView;
                tv.setText("fix");
                break;
            }
            case ITEM_TYPE_1:
            case ITEM_TYPE_2:
            case ITEM_TYPE_3:
            case ITEM_TYPE_4:
            case ITEM_TYPE_5:
            case ITEM_TYPE_6:{

                TextView tv = (TextView) holder.itemView;
                tv.setText(String.valueOf(getItem(position)));
                break;
            }
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return show_item_type_fix ? datas.size() + 1 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(show_item_type_fix){
            if(position == 0){
                return ITEM_TYPE_FIX;
            }
            position = position - 1;
        }
        int viewType = item_types[position % item_types.length];
        return viewType;
    }

    public Integer getItem(int position) {
        if(show_item_type_fix){
            if(position == 0){
                return null;
            }
            position = position - 1;
        }
        return datas.get(position);
    }

    public void addAll(List<Integer> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return getLayoutHelper();
    }

    StaggeredGridLayoutHelper helper;
    public StaggeredGridLayoutHelper getLayoutHelper(){
        if(helper == null){
            StaggeredGridLayoutHelper helper = new StaggeredGridLayoutHelper();
            helper.setLane(lane);
            helper.setBgColor(0xffffffff);
            helper.setGap(padding);
            helper.setPadding(padding, padding*lane, padding, padding);

            this.helper = helper;
        }
        return helper;
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View view) {
            super(view);
        }
    }
}
