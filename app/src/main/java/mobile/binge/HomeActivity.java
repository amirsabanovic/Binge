package mobile.binge;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    MovieAdapter movieAdapter = new MovieAdapter();
    TopRatedMoviesFragment topRatedMoviesFragment = new TopRatedMoviesFragment();
    TopRatedTVFragment topRatedTVFragment = new TopRatedTVFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(topRatedMoviesFragment, "MOVIES");
        adapter.addFragment(topRatedTVFragment, "TV SHOWS");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
    }

    protected void launchNewSearchActivity(int selectedTab) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("SELECTED_TAB", selectedTab);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                tabLayout = findViewById(R.id.tab_layout);

                FragmentManager manager = getSupportFragmentManager();
                Fragment fragment;

                if (newText.length() >= 3) {
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("QUERY", newText);

                    if (tabLayout.getSelectedTabPosition() == 0) {
                        fragment = new MovieSearchResultsFragment();
                        fragment.setArguments(bundle);
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction
                                .replace(R.id.top_rated_movies_layout, fragment)
                                .commit();
                    }
                    else {
                        fragment = new TVShowSearchResultsFragment();
                        fragment.setArguments(bundle);
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction
                                .replace(R.id.top_rated_tv_shows_layout, fragment)
                                .commit();
                    }

                } else {
                    Log.i("HomeActivity", "Not enough.");
                    if (tabLayout.getSelectedTabPosition() == 0) {
                        manager
                                .beginTransaction()
                                .replace(R.id.top_rated_movies_layout, topRatedMoviesFragment)
                                .commit();
                    } else {
                        manager
                                .beginTransaction()
                                .replace(R.id.viewpager, topRatedTVFragment)
                                .commit();
                    }

                }
                return false;
            }
        });

        return true;
    }

    /*@Override
    public boolean onSearchRequested() {
        Log.d("HomeActivity", "Search requested.");
        return super.onSearchRequested();
    }*/
}
