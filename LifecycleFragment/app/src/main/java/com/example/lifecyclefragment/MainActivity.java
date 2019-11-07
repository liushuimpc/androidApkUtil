package com.example.lifecyclefragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        BookListFragment.Callbacks, BookDetailFragment
        .OnFragmentInteractionListener {
    private final static String TAG = "MainActivity";
    public final static Integer BOOK_1 = 1;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.book_detail_container) != null) {
            Log.i(TAG, "marcoo--call setActivateOnItemClick");
            ((BookListFragment) getFragmentManager().findFragmentById(R.id
                    .book_list)).setActivateOnItemClick(true);
            onItemSelected(BOOK_1);
        }
    }

    @Override
    public void onItemSelected(Integer id) {
        Log.i(TAG, "onItemSelected, id=" + id);

        Bundle arguments = new Bundle();
        arguments.putInt(BookDetailFragment.ITEM_ID, id);

        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction().replace(R.id
                .book_detail_container, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_setting:
                return false;

            case android.R.id.home:
                Log.i(TAG, "marcoo--onOptionsItemSelected home");
                Toast.makeText(this, "Click home item", Toast.LENGTH_SHORT)
                        .show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_book_detail_menu, menu);
        this.menu = menu;
        this.menu.findItem(R.id.item_setting).setVisible(false);

        return true;
    }

}
