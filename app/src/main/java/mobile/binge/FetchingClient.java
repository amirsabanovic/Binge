package mobile.binge;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class FetchingClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "851e1e4c190266b9132583923c61128d";
    private static final String LANGUAGE = "en-US";
    private static final String PAGE = "1";

    private static FetchingClient instance;
    private FetchingService fetchingService;

    private FetchingClient() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        fetchingService = retrofit.create(FetchingService.class);
    }

    public static FetchingClient getInstance() {
        if (instance == null) {
            instance = new FetchingClient();
        }
        return instance;
    }

    public Observable<MovieModelResults> getTopRatedMovies(@NonNull String apiKey, String language, String page) {
        return fetchingService.getTopRatedMovies(apiKey, language, page);
    }

    public Observable<TVShowModelResults> getTopRatedTVShows(@NonNull String apiKey, String language, String page) {
        return fetchingService.getTopRatedTVShows(apiKey, language, page);
    }

    public Observable<MovieModelResults> searchMovies(@NonNull String apiKey, String language, @NonNull String query) {
        return fetchingService.searchMovies(apiKey, language, query);
    }

    public Observable<TVShowModelResults> searchTVShows(@NonNull String apiKey, String language, @NonNull String query) {
        return fetchingService.searchTVShows(apiKey, language, query);
    }
}
