package mobile.binge;

import org.json.JSONObject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import rx.Observable;

public interface FetchingService {

    @GET("movie/top_rated")
    Observable<MovieModelResults> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") String page);

    @GET("tv/top_rated")
    Observable<TVShowModelResults> getTopRatedTVShows(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") String page);

    @GET("search/movie")
    Observable<MovieModelResults> searchMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);

    @GET("search/tv")
    Observable<TVShowModelResults> searchTVShows(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);

}
