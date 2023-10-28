package com.example.searchview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> implements Filterable {

    private List<ExampleItem> mSpeakersData;
    private List<ExampleItem> FullList;
    private Context mContext;
    ExampleAdapter(Context context, List<ExampleItem> speakerData){
        this.mSpeakersData = speakerData;
        FullList = new ArrayList<>(mSpeakersData);
        this.mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.example_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExampleItem currentSpeaker = mSpeakersData.get(position);
        holder.bindTo(currentSpeaker);
    }

    @Override
    public int getItemCount() {
        return mSpeakersData.size();
    }

    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ExampleItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ExampleItem item : FullList) {
                    if (item.getText1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mSpeakersData.clear();
            mSpeakersData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView speakerName, speakerQualification;
        private ImageView speakerImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            speakerName = itemView.findViewById(R.id.textView1);
            speakerQualification = itemView.findViewById(R.id.textView2);
            speakerImage = itemView.findViewById(R.id.image_view);
        }

        void bindTo (ExampleItem currentSpeaker) {
            speakerName.setText(currentSpeaker.getText1());
            speakerQualification.setText(currentSpeaker.getText2());
            speakerImage.setImageResource(currentSpeaker.getImageResource());
        }

    }

}
