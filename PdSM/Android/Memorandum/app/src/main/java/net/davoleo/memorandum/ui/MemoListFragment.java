package net.davoleo.memorandum.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import net.davoleo.memorandum.R;
import net.davoleo.memorandum.model.Memo;
import net.davoleo.memorandum.model.MemoStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A fragment representing a list of Items.
 */
public class MemoListFragment extends Fragment {

    private static final String TAG = "MemoListFragment";
    protected RecyclerView recyclerView;

    private List<Memo> processedMemos = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MemoListFragment()
    {
    }

    protected void processMemoList(final List<Memo> memos, final @Nullable MemoStatus filtered)
    {
        //Clear Previous Configuration
        processedMemos.clear();

        for (Memo memo : memos)
            if (filtered == null || memo.status.equals(filtered))
                processedMemos.add(memo);
        //noinspection ComparatorCombinators
        Collections.sort(processedMemos, (memo1, memo2) -> memo1.getTimestamp().compareTo(memo2.getTimestamp()));
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_memo_list, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        // Set the adapter
        if (view instanceof RecyclerView && this.getActivity() instanceof MainActivity)
        {
            MainActivity activity = (MainActivity) this.getActivity();

            recyclerView = (RecyclerView) view;
            //Set Recycler View Linear Layout Manager
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            //Add Divider Decoration
            DividerItemDecoration dividers = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation());
            recyclerView.addItemDecoration(dividers);
            //Set Memo Recycler Adapter

            processMemoList(activity.memos, activity.filteredStatus);

            MemoRecycleAdapter adapter = new MemoRecycleAdapter(this, processedMemos);
            recyclerView.setAdapter(adapter);

            //FAB Hiding when scrolling
            FloatingActionButton fab = this.getActivity().findViewById(R.id.fab);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
                {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        fab.show();
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
                {
                    if (dy > 0 || dy < 0 && fab.isShown())
                        fab.hide();
                }
            });
        }
    }
}