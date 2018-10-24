package mobile.binge;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w92";

    private List<MovieModel> movies = new ArrayList<>();

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public MovieModel getItem(int position) {
        if (position < 0 || position >= movies.size()) {
            return null;
        } else {
            return movies.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final MovieViewHolder viewHolder = (MovieViewHolder) view.getTag();
        ImageView moviePoster = view.findViewById(R.id.movie_poster);
        viewHolder.setMovie(getItem(position));
        Glide.with(view).load(BASE_IMAGE_URL + getItem(position).getPoster_path()).into(moviePoster);
        return view;
    }

    public void setMovies(@Nullable List<MovieModel> movies) {
        if (movies == null) {
            return;
        }
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_movie, parent, false);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class MovieViewHolder {

        private TextView movieTitle;
        private ImageView moviePosterPath;

        public MovieViewHolder(View view) {
            movieTitle = view.findViewById(R.id.movie_title);
            moviePosterPath = view.findViewById(R.id.movie_poster);
        }

        public void setMovie(MovieModel movie) {
            movieTitle.setText(movie.getTitle());
            //moviePosterPath.setImage((URI) BASE_IMAGE_URL + movie.getPoster_path());
        }
    }
}
