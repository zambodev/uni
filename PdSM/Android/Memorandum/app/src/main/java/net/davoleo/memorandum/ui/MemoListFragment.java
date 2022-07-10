package net.davoleo.memorandum.ui;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import net.davoleo.memorandum.R;
import net.davoleo.memorandum.model.Memo;
import net.davoleo.memorandum.model.MemoStatus;
import net.davoleo.memorandum.util.SortedMemosCallback;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MemoListFragment extends Fragment {

    private static final String TAG = "MemoListFragment";
    protected RecyclerView recyclerView;

    private SortedList<Memo> processedList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MemoListFragment()
    {
    }

    protected void processMemoList(final List<Memo> original, final @Nullable MemoStatus filtered)
    {
        processedList.beginBatchedUpdates();
        processedList.replaceAll(original);

        if (filtered != null) {
            for (Memo memo : original)
                if (memo.status != filtered)
                    processedList.remove(memo);
        }

        processedList.endBatchedUpdates();
    }

    protected void addMemoToProcessedList(final Memo memo, final @Nullable MemoStatus currentFilter) {
        if (memo.status == currentFilter)
            processedList.add(memo);
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

            processedList = new SortedList<>(Memo.class, new SortedMemosCallback(this.getContext(), () -> (MemoRecycleAdapter) recyclerView.getAdapter()));

            MemoRecycleAdapter adapter = new MemoRecycleAdapter(this, processedList);
            recyclerView.setAdapter(adapter);

            processedList.addAll(activity.memos);

            ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.START | ItemTouchHelper.END,
                    ItemTouchHelper.START | ItemTouchHelper.END)
            {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
                {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
                {
                    if (direction == ItemTouchHelper.START) {
                        adapter.onSwipedLeft(viewHolder.getBindingAdapterPosition());
                    }
                    else if (direction == ItemTouchHelper.END) {
                        adapter.onSwipedRight(viewHolder.getBindingAdapterPosition());
                    }

                    adapter.notifyItemChanged(viewHolder.getBindingAdapterPosition());
                }

                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
                {
                    View itemView = viewHolder.itemView;

                    Paint paint = new Paint();
                    if (dX > 0) {
                        //Color for swipe right
                        //paint.setColor();
                        //https://stackoverflow.com/questions/30820806/adding-a-colored-background-with-text-icon-under-swiped-row-when-using-androids
                    }
                    else {
                        //Color for swipe left
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            });
            touchHelper.attachToRecyclerView(recyclerView);


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