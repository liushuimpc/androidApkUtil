package com.example.lifecyclefragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lifecyclefragment.model.BookContent;

/**
 * Created by marco on 8/7/18.
 */

public class BookListFragment extends ListFragment {
    private final static String TAG = "BookListFragment";
    private Callbacks mCallbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "marcoo--BookListFragment onCreate");
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setListAdapter(new ArrayAdapter<BookContent.Book>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1, BookContent.ITEMS));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "marcoo--BookListFragment onAttach");

        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("The Activity of BookListFragment" +
                    " should implements Callbacks");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "marcoo--BookListFragment onDetach");

        mCallbacks = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);

        Log.i(TAG, "marcoo--onListItemClick, id=" + BookContent.ITEMS.get
                (position).id);
        mCallbacks.onItemSelected(BookContent.ITEMS.get(position).id);
    }

    public void setActivateOnItemClick(boolean activateOnItemCLick) {
        getListView().setChoiceMode(activateOnItemCLick ? ListView
                .CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_book_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_setting:
                Log.i(TAG, "marcoo--item_setting clicked");
                Toast.makeText(getActivity(), "BookListFragment " +
                        "item_setting", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        return true;
    }

    public interface Callbacks {
        void onItemSelected(Integer id);
    }
}
