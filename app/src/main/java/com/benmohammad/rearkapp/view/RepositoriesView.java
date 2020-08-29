package com.benmohammad.rearkapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;


public class RepositoriesView extends FrameLayout {


    private TextView statusText;
    private Observable<String> searchStringObservable;
    private RecyclerView repositoriesListView;

    public RepositoriesView(Context context) {
        super(context);
    }

    public RepositoriesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
