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


public class MovieSearchResultsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private final static String API_KEY = "851e1e4c190266b9132583923c61128d";
    private final static String LANGUAGE = "en-US";

    private MovieAdapter movieAdapter = new MovieAdapter();
    private Subscription subscription;

    private static final String TAG = MovieSearchResultsFragment.class.getSimpleName();
    //private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_search_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.movie_search_results_list);
        listView.setAdapter(movieAdapter);

        listView.setOnItemClickListener(this);

        searchMovies(Objects.requireNonNull(this.getArguments()).getString("QUERY"));
    }

    private void searchMovies(String query) {
        subscription = FetchingClient.getInstance()
                .searchMovies(MovieSearchResultsFragment.API_KEY, MovieSearchResultsFragment.LANGUAGE, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieModelResults>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override
                    public void onNext(MovieModelResults movies) {
                        Log.d(TAG, "In onNext()");
                        movieAdapter.setMovies(movies.getResults());
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
        Intent intent = new Intent(this.getContext(), MovieDetailsActivity.class);
        intent.putExtra("DETAILS", (MovieModel) parent.getItemAtPosition(position));
        startActivity(intent);
    }
}
