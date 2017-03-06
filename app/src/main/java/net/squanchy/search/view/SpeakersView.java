package net.squanchy.search.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import net.squanchy.speaker.domain.view.Speaker;

public class SpeakersView extends RecyclerView {

    private SpeakerAdapter adapter;

    private static final int COLUMN_NUMBER = 4;

    public SpeakersView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeakersView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), COLUMN_NUMBER);
        setLayoutManager(gridLayoutManager);
        adapter = new SpeakerAdapter(getContext());
        setAdapter(adapter);
        setClipToPadding(false);
    }

    public void updateWith(List<Speaker> newData, OnSpeakerClickedListener listener) {
        if (getAdapter() == null) {
            super.setAdapter(adapter);
        }

        GridLayoutManager layoutManager = (GridLayoutManager) getLayoutManager();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = adapter.createSpanSizeLookup(layoutManager.getSpanCount());
        layoutManager.setSpanSizeLookup(spanSizeLookup);

        DiffUtil.Callback callback = new SpeakerDiffCallback(adapter.speakers(), newData);       // TODO move off of the UI thread
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback, true);
        adapter.updateWith(newData, listener);
        diffResult.dispatchUpdatesTo(adapter);
    }

    public interface OnSpeakerClickedListener {

        void onSpeakerClicked(Speaker speaker);
    }

    private static class SpeakerDiffCallback extends DiffUtil.Callback {

        private final List<Speaker> oldSpeakers;
        private final List<Speaker> newSpeakers;

        private SpeakerDiffCallback(List<Speaker> oldSpeakers, List<Speaker> newSpeakers) {
            this.oldSpeakers = oldSpeakers;
            this.newSpeakers = newSpeakers;
        }

        @Override
        public int getOldListSize() {
            return oldSpeakers.size();
        }

        @Override
        public int getNewListSize() {
            return newSpeakers.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            Speaker oldSpeaker = oldSpeakers.get(oldItemPosition);
            Speaker newSpeaker = newSpeakers.get(newItemPosition);
            return oldSpeaker.numericId() == newSpeaker.numericId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Speaker oldSpeaker = oldSpeakers.get(oldItemPosition);
            Speaker newSpeaker = newSpeakers.get(newItemPosition);
            return oldSpeaker.equals(newSpeaker);
        }
    }
}
