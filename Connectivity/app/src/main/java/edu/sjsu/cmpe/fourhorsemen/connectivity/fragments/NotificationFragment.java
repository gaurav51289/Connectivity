package edu.sjsu.cmpe.fourhorsemen.connectivity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.sjsu.cmpe.fourhorsemen.connectivity.R;
import edu.sjsu.cmpe.fourhorsemen.connectivity.beans.Notification;
import edu.sjsu.cmpe.fourhorsemen.connectivity.utilities.PreferenceHandler;
import edu.sjsu.cmpe.fourhorsemen.connectivity.utilities.ProjectProperties;
import edu.sjsu.cmpe.fourhorsemen.connectivity.utilities.RequestHandler;
import edu.sjsu.cmpe.fourhorsemen.connectivity.utilities.ResponseHandler;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NotificationFragment extends Fragment{

    private final static String TAG = NotificationFragment.class.getSimpleName();
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotificationFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NotificationFragment newInstance(int columnCount) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyNotificationRecyclerViewAdapter(getNotifications(getContext()), mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    private List<Notification> getNotifications(Context context) {
        final List<Notification> notificationsList = new ArrayList<Notification>();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("unique_id", PreferenceHandler.getAccessKey());


//        Calling get notification method
        RequestHandler.HTTPRequest(getContext(), ProjectProperties.METHOD_GET_NOTIFICATIONS, params, new ResponseHandler() {
            @Override
            public void handleSuccess(JSONObject response) throws Exception {
                if(response.getInt("status_code") == 200) {
                    JSONArray notifications = response.getJSONArray("message");
                    for(int i  = 0; i < notifications.length(); i++) {
                        JSONObject currentObj = notifications.getJSONObject(i);
                        notificationsList.add(new Notification(currentObj.getInt("notification_id"), currentObj.getInt("profile"), currentObj.getInt("friend"), currentObj.getString("text"), currentObj.getString("timestamp")));
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Internal Error. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void handleError(Exception e) throws Exception {
                e.printStackTrace();
            }
        });
        return notificationsList;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction();

    }



}
