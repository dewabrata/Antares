/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dewabrata.antares;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


import java.util.ArrayList;



public class CustomRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>  {
    public static final int TYPE_TEMPERATURE = 0;
    public static final int TYPE_HUMIDITY = 1;
    public static final int TYPE_PH = 2;
    public static final int TYPE_SWITCH = 3;
    private static final String TAG = "CustomRecyclerAdapter";

    private ArrayList<ModelData> mDataSet;
    private  MainActivity main;

    public CustomRecyclerAdapter(MainActivity main ,ArrayList<ModelData> dataSet) {
        this.main = main;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType);

        ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_TEMPERATURE:
                viewHolder =
                        new TemperatureViewHolder(
                                LayoutInflater.from(viewGroup.getContext())
                                        .inflate(
                                                R.layout.temperature_row,
                                                viewGroup,
                                                false));
                break;

            case TYPE_HUMIDITY:
                viewHolder =
                        new HumidityViewHolder(
                                LayoutInflater.from(viewGroup.getContext())
                                        .inflate(
                                                R.layout.humidity_row,
                                                viewGroup,
                                                false));
                break;

            case TYPE_PH:
                viewHolder =
                        new PHViewHolder(
                                LayoutInflater.from(viewGroup.getContext())
                                        .inflate(
                                                R.layout.ph_row,
                                                viewGroup,
                                                false));
                break;

            case TYPE_SWITCH:
                viewHolder =
                        new SwitchViewHolder(
                                LayoutInflater.from(viewGroup.getContext())
                                        .inflate(
                                                R.layout.switch_row,
                                                viewGroup,
                                                false));
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        switch (viewHolder.getItemViewType()) {
            case TYPE_TEMPERATURE:

                ((TemperatureViewHolder)viewHolder).txtTemperature.setText(mDataSet.get(position).getTemperature());

                break;

            case TYPE_HUMIDITY:
                ((HumidityViewHolder)viewHolder).txtHumidity.setText(mDataSet.get(position).getHumidity());
                break;
            case TYPE_PH:
                ((PHViewHolder)viewHolder).txtPH.setText(mDataSet.get(position).getPh());
                break;

            case TYPE_SWITCH:
                if(mDataSet.get(position).getSaklar().equalsIgnoreCase("1")){
                    ((SwitchViewHolder)viewHolder).switch1.setChecked(true);
                }else{
                    ((SwitchViewHolder)viewHolder).switch1.setChecked(false);
                }
                ((SwitchViewHolder)viewHolder).switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            main.postAntaresData(mDataSet.get(position).getTemperature(),mDataSet.get(position).getHumidity(),mDataSet.get(position).getPh(),"1");
                        }else{
                            main.postAntaresData(mDataSet.get(position).getTemperature(),mDataSet.get(position).getHumidity(),mDataSet.get(position).getPh(),"0");
                        }
                    }
                });

                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        ModelData data = mDataSet.get(position);
        return data.getType();
    }




    /** ***** Classes representing custom {@link ViewHolder}. ****** */

    /**
     * Displays {@link Bitmap} passed from other devices via the {@link
     * com.google.android.gms.wearable.Asset} API.
     */
    public static class TemperatureViewHolder extends ViewHolder {

        TextView txtTemperature;
        public TemperatureViewHolder(View view) {
            super(view);
            txtTemperature = view.findViewById(R.id.txtTemperature);

        }

    }

    public static class HumidityViewHolder extends ViewHolder {
        TextView txtHumidity;
        public HumidityViewHolder(View view) {
            super(view);
            txtHumidity = view.findViewById(R.id.txtHumidity);
        }

    }

    public static class PHViewHolder extends ViewHolder {
        TextView txtPH;
        public PHViewHolder(View view) {
            super(view);
            txtPH = view.findViewById(R.id.txtPH);
        }

    }

    public static class SwitchViewHolder extends ViewHolder {

        Switch switch1;
        public SwitchViewHolder(View view) {
            super(view);
            switch1 = view.findViewById(R.id.switch1);

        }

    }




}
