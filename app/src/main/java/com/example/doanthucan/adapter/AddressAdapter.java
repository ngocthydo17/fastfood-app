package com.example.doanthucan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.AdressModel;
import com.example.doanthucan.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    List<AdressModel> adressModels;


   private  RadioButton selectedRadiobtn;
    SelectedAddress selectedAddress;
    public AddressAdapter(List<AdressModel> adressModels, SelectedAddress selectedAddress, Context context) {
        this.adressModels = adressModels;
        this.selectedAddress = selectedAddress;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
    holder.address1.setText(adressModels.get(position).getUserAdress());
     holder.radioButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             for(AdressModel adressModel: adressModels){
                 adressModel.setSelected(false);
             }
             adressModels.get(position).setSelected(true);
             if(selectedRadiobtn!= null){
                 selectedRadiobtn.setChecked(false);
             }
             selectedRadiobtn = (RadioButton) v;
             selectedRadiobtn.setChecked(true);
             selectedAddress.setAdress(adressModels.get(position).getUserAdress());
         }
     });
    }

    @Override
    public int getItemCount() {
        return adressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address1;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address1 = itemView.findViewById(R.id.address_add);
           radioButton = itemView.findViewById(R.id.select_address);
        }
    }
    public  interface  SelectedAddress {
        void setAdress(String address);
    }
}
