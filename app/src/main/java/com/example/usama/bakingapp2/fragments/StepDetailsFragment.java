package com.example.usama.bakingapp2.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usama.bakingapp2.R;
import com.example.usama.bakingapp2.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

public class StepDetailsFragment extends Fragment implements VideoRendererEventListener {

    SimpleExoPlayerView playerView;
    boolean videoAvailable = false;
    int currentWindow;
    long playbackPosition;
    boolean playWhenReady = true;
    SimpleExoPlayer player;
    Step currentStep;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment, container, false);

        currentStep = getArguments().getParcelable("currentStep");

        playerView = view.findViewById(R.id.player_view);

        TextView videoAvailableTextView = view.findViewById(R.id.video_available_text_view);
        TextView thumbNailAvailableTextView = view.findViewById(R.id.step_thumbnail_text_view);
        ImageView thumbNailImageView = view.findViewById(R.id.step_thumbnail_image_view);
        TextView descriptionTextView = view.findViewById(R.id.step_description_text_view);


        assert currentStep != null;
        if (currentStep.getVideoURL().equals("")) {
            videoAvailableTextView.setText(R.string.no_video_available);
        } else {
            videoAvailableTextView.setVisibility(View.GONE);
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

    private void initializePlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        playerView.setUseController(true);
        playerView.requestFocus();
        playerView.setPlayer(player);
        Uri mp4VideoUri = Uri.parse(currentStep.getVideoURL());

        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "exoplayer2example"), bandwidthMeterA);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);
        final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        player.prepare(loopingSource);

        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.v(TAG, "Listener-onTimelineChanged...");
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.v(TAG, "Listener-onTracksChanged...");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.v(TAG, "Listener-onLoadingChanged...isLoading:" + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState);
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                Log.v(TAG, "Listener-onRepeatModeChanged...");
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG, "Listener-onPlayerError...");
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity() {
                Log.v(TAG, "Listener-onPositionDiscontinuity...");
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Log.v(TAG, "Listener-onPlaybackParametersChanged...");
            }
        });

        player.setPlayWhenReady(playWhenReady);
        player.setVideoDebugListener(this);
        player.seekTo(playbackPosition);

    }


    @Override
    public void onResume() {
        super.onResume();

        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();

        }
    }



    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {

    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!currentStep.getVideoURL().equals("")) {
            if (savedInstanceState != null) {

                playbackPosition = savedInstanceState.getLong("playerPosition");
                playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            }
            playerView.setVisibility(View.VISIBLE);
            initializePlayer();
            videoAvailable = true;
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (videoAvailable && player != null) {
            outState.putLong("playerPosition", player.getCurrentPosition());
            outState.putBoolean("playWhenReady", player.getPlayWhenReady());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
