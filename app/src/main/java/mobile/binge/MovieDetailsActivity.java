package mobile.binge;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w780";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.app_name));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView title = findViewById(R.id.title);
        ImageView backdrop = findViewById(R.id.backdrop);
        TextView overview = findViewById(R.id.overview);

        MovieModel movieModel = (MovieModel) Objects.requireNonNull(getIntent().getExtras()).getSerializable("DETAILS");

        Glide.with(this).load(BASE_IMAGE_URL + Objects.requireNonNull(movieModel).getBackdrop_path()).into(backdrop);

        title.setText(movieModel.getTitle());
        overview.setText(movieModel.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
