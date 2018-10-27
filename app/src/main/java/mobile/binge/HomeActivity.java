package mobile.binge;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeTabLayout();
    }

    private void initializeTabLayout() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(TopRatedMoviesFragment.newInstance(),"MOVIES");
        viewPagerAdapter.addFragment(TopRatedTVFragment.newInstance(), "TV SHOWS");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        /* Default to "TV SHOWS" tab, per requirement */
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        if (tab != null) {
            tab.select();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            Fragment fragment;
            boolean threshold = false;

            @Override
            public boolean onQueryTextSubmit(String query) {
                return query.length() < 3;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                tabLayout = findViewById(R.id.tab_layout);

                FragmentManager manager = getSupportFragmentManager();

                if (threshold && newText.length() < 3) {
                    threshold = false;

                    manager
                            .beginTransaction()
                            .remove(fragment)
                            .commit();

                } else if (newText.length() >= 3) {
                    threshold = true;

                    if (tabLayout.getSelectedTabPosition() == 0) {
                        fragment = MovieSearchResultsFragment.newInstance(newText);

                        manager
                                .beginTransaction()
                                .replace(R.id.top_rated_movies_layout, fragment)
                                .commit();
                    } else {
                        fragment = TVShowSearchResultsFragment.newInstance(newText);

                        manager
                                .beginTransaction()
                                .replace(R.id.top_rated_tv_shows_layout, fragment)
                                .commit();
                    }

                }
                return false;
            }
        });

        return true;
    }
}
