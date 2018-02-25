package com.example.usama.bakingapp2.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usama.bakingapp2.BakingApp;
import com.example.usama.bakingapp2.R;
import com.example.usama.bakingapp2.model.Step;
import com.squareup.picasso.Picasso;

public class StepDetailsFragment extends Fragment {

    BakingApp bakingApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment, container, false);

        Step currentStep = getArguments().getParcelable("currentStep");

        TextView videoAvailableTextView = view.findViewById(R.id.video_available_text_view);
        TextView thumbNailAvailableTextView = view.findViewById(R.id.step_thumbnail_text_view);
        ImageView thumbNailImageView = view.findViewById(R.id.step_thumbnail_image_view);
        TextView descriptionTextView = view.findViewById(R.id.step_description_text_view);


        assert currentStep != null;
        if (currentStep.getVideoURL().equals("")) {
            videoAvailableTextView.setText(R.string.no_video_available);
        } else {
            videoAvailableTextView.setText("Video is available");
        }
        //currentStep.setThumbnailURL("http://i.imgur.com/DvpvklR.png");
        if (currentStep.getThumbnailURL().equals("")) {
            thumbNailAvailableTextView.setVisibility(View.VISIBLE);
            thumbNailAvailableTextView.setText(R.string.thumnail_not_available);
        } else {

            Picasso.with(getActivity())
                    .load(currentStep.getThumbnailURL())
                    .placeholder(R.drawable.no_image_available)
                    .error(R.drawable.no_image_available)
                    .into(thumbNailImageView);
            thumbNailImageView.setVisibility(View.VISIBLE);
            thumbNailAvailableTextView.setVisibility(View.GONE);
        }
        descriptionTextView.setText(currentStep.getDescription());


        return view;
    }
}
