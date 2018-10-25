package mobile.binge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Objects;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TVShowSearchResultsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private final static String API_KEY = "851e1e4c190266b9132583923c61128d";
    private final static String LANGUAGE = "en-US";

    private TVShowAdapter tvShowAdapter = new TVShowAdapter();
    private Subscription subscription;

    private static final String TAG = TVShowSearchResultsFragment.class.getSimpleName();
    //private OnFragmentInteractionListener mListener;

    public TVShowSearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_search_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.tv_show_search_results_list);
        listView.setAdapter(tvShowAdapter);

        listView.setOnItemClickListener(this);

        searchTVShows(Objects.requireNonNull(this.getArguments()).getString("QUERY"));
    }

    private void searchTVShows(String query) {
        subscription = FetchingClient.getInstance()
                .searchTVShows(TVShowSearchResultsFragment.API_KEY, TVShowSearchResultsFragment.LANGUAGE, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TVShowModelResults>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override
                    public void onNext(TVShowModelResults tvShows) {
                        Log.d(TAG, "In onNext()");
                        tvShowAdapter.setTVShows(tvShows.getResults());
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getContext(), TVShowDetailsActivity.class);
        intent.putExtra("DETAILS", (TVShowModel) parent.getItemAtPosition(position));
        startActivity(intent);
    }
}
