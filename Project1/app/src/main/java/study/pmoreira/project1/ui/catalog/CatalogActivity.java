package study.pmoreira.project1.ui.catalog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project1.R;
import study.pmoreira.project1.business.MovieBusiness;
import study.pmoreira.project1.entity.Movie;
import study.pmoreira.project1.utils.NetworkUtils;

public class CatalogActivity extends AppCompatActivity {

    private static final String STATE_MOVIES = "STATE_MOVIES";
    private static final String STATE_TITLE = "STATE_TITLE";

    @BindView(R.id.catalog_gridview)
    GridView mCatalogGridView;

    @BindView(R.id.error_textview)
    TextView mErrorTextView;

    private MovieBusiness mMovieBusiness;

    private List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        mMovieBusiness = new MovieBusiness(this);

        if (NetworkUtils.isNetworkAvailable(this)) {
            initCatalog(savedInstanceState);
        } else {
            showError(true);
        }
    }

    private void initCatalog(Bundle savedInstanceState) {
        if (savedInstanceState != null && (mMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIES)) != null) {
            mCatalogGridView.setAdapter(new CatalogAdapter(this, mMovies));
            setTitle(savedInstanceState.getCharSequence(STATE_TITLE));
        } else {
            new CatalogAsyncTask().execute();
        }
    }

    private class CatalogAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            String orderBy = MovieBusiness.ORDER_BY_MOST_POPULAR;
            if (params.length > 0) {
                orderBy = params[0];
            }

            return mMovieBusiness.findMovies(orderBy);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mMovies = movies;
            mCatalogGridView.setAdapter(new CatalogAdapter(CatalogActivity.this, movies));
            showError(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(STATE_TITLE, getTitle());
        outState.putParcelableArrayList(STATE_MOVIES, new ArrayList<>(mMovies));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalog_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.catalog_menu_most_popular:
                new CatalogAsyncTask().execute(MovieBusiness.ORDER_BY_MOST_POPULAR);
                setTitle(getString(R.string.popular_movies));
                return true;
            case R.id.catalog_menu_top_rated:
                new CatalogAsyncTask().execute(MovieBusiness.ORDER_BY_TOP_RATED);
                setTitle(getString(R.string.top_rated_movies));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showError(boolean showError) {
        mErrorTextView.setVisibility(showError ? View.VISIBLE : View.GONE);
        mCatalogGridView.setVisibility(showError ? View.GONE : View.VISIBLE);
    }

}
