package mobile.binge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TopRatedTVFragment extends Fragment {

    private final static String API_KEY = "851e1e4c190266b9132583923c61128d";
    private final static String LANGUAGE = "en-US";
    private final static String PAGE = "1";

    private TVShowAdapter tvShowAdapter = new TVShowAdapter();
    private Subscription subscription;

    private static final String TAG = TopRatedMoviesFragment.class.getSimpleName();

    public TopRatedTVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_rated_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.top_rated_tv_list);
        listView.setAdapter(tvShowAdapter);

        getTopRatedTVShows();
    }

    private void getTopRatedTVShows() {
        subscription = FetchingClient.getInstance()
                .getTopRatedTVShows(TopRatedTVFragment.API_KEY, TopRatedTVFragment.LANGUAGE, TopRatedTVFragment.PAGE)
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
                        tvShowAdapter.setTVShows(tvShows.getResults().subList(0, 10));
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
}
