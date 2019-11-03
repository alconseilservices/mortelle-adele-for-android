package com.bayardpresse.morteleadele.android;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.bayardpresse.morteleadele.android.model.PackStore;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toolbar;

import com.bayardpresse.morteleadele.android.model.Pack;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Pack mItem;
    private GridView mGridView;
    private StickersGridAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = PackStore.getPackById(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
            Toolbar appBarLayout = activity.findViewById(R.id.detail_toolbar);
            if (appBarLayout != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    appBarLayout.setTitle(mItem.name);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        mGridView = rootView.findViewById(R.id.stickers_grid);
        adapter = new StickersGridAdapter(getContext(), mItem);
        mGridView.setAdapter(adapter);
        return rootView;
    }

}
