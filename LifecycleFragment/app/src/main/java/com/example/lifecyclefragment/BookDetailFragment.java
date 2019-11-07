package com.example.lifecyclefragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifecyclefragment.model.BookContent;

import static com.example.lifecyclefragment.MainActivity.BOOK_1;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailFragment extends Fragment {
    public final static String ITEM_ID = "item_id";
    private final static String TAG = "BookDetailFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private BookContent.Book book;

    public BookDetailFragment() {
        // Required empty public constructor
        Log.i(TAG, "marco--BookDetailFragment()");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetailFragment newInstance(String param1, String param2) {
        Log.i(TAG, "marcoo--BookDetailFragment newInstance");
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "marcoo--BookDetailFragment-onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ITEM_ID)) {
            book = BookContent.ITEM_MAP.get(getArguments().get(ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "marcoo--BookDetailFragment-onCreateView");

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book_detail,
                container, false);
        if (book != null) {
            ((TextView) rootView.findViewById(R.id.book_title)).setText(book
                    .title);
            ((TextView) rootView.findViewById(R.id.book_desc)).setText(book
                    .desc);
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "marcoo--BookDetailFragment-onAttach");

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "marcoo--BookDetailFragment-onDetach");

        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating
     * .html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
