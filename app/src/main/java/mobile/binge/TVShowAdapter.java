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

public class TVShowAdapter extends BaseAdapter {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w92";

    private List<TVShowModel> tvShows = new ArrayList<>();

    @Override
    public int getCount() {
        return tvShows.size();
    }

    @Override
    public TVShowModel getItem(int position) {
        if (position < 0 || position >= tvShows.size()) {
            return null;
        } else {
            return tvShows.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final TVShowViewHolder viewHolder = (TVShowViewHolder) view.getTag();
        ImageView tvShowPoster = view.findViewById(R.id.tv_show_poster);
        viewHolder.setTVShow(getItem(position));
        Glide.with(view).load(BASE_IMAGE_URL + getItem(position).getPoster_path()).into(tvShowPoster);
        return view;
    }

    public void setTVShows(@Nullable List<TVShowModel> tvShows) {
        if (tvShows == null) {
            return;
        }
        this.tvShows.clear();
        this.tvShows.addAll(tvShows);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_tv_show, parent, false);
        final TVShowViewHolder viewHolder = new TVShowViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class TVShowViewHolder {

        private TextView tvShowName;
        private ImageView tvShowPosterPath;

        public TVShowViewHolder(View view) {
            tvShowName = view.findViewById(R.id.tv_show_name);
            tvShowPosterPath = view.findViewById(R.id.tv_show_poster);
        }

        public void setTVShow(TVShowModel tvShow) {
            tvShowName.setText(tvShow.getName());
            //moviePosterPath.setImage((URI) BASE_IMAGE_URL + movie.getPoster_path());
        }
    }

}
