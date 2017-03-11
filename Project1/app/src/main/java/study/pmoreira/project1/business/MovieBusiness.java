package study.pmoreira.project1.business;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import study.pmoreira.project1.BuildConfig;
import study.pmoreira.project1.entity.Movie;
import study.pmoreira.project1.utils.NetworkUtils;

import static android.content.ContentValues.TAG;

public class MovieBusiness extends BaseBusiness {

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

    private static final String PARAM_API_KEY = "api_key";

    public static final String ORDER_BY_MOST_POPULAR = "popular";
    public static final String ORDER_BY_TOP_RATED = "top_rated";

    public MovieBusiness(Context context) {
        super(context);
    }

    public List<Movie> findMovies(String orderBy) {
        String moviesJson = null;
        try {
            moviesJson = NetworkUtils.getResponseFromHttpUrl(buildUrl(orderBy));
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        return extractMoviesFromJson(moviesJson);
    }

    private URL buildUrl(String orderBy) throws MalformedURLException {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();

        switch (orderBy) {
            case ORDER_BY_TOP_RATED:
                builder.appendPath(ORDER_BY_TOP_RATED);
                break;
            case ORDER_BY_MOST_POPULAR:
            default:
                builder.appendPath(ORDER_BY_MOST_POPULAR);
                break;
        }

        Uri uri = builder.appendQueryParameter(PARAM_API_KEY, BuildConfig.MOVIEDB_API_KEY).build();

        return new URL(uri.toString());
    }

    private List<Movie> extractMoviesFromJson(String moviesJson) {
        List<Movie> movies = new ArrayList<>();
        if (TextUtils.isEmpty(moviesJson)) {
            return movies;
        }

        try {
            JSONObject root = new JSONObject(moviesJson);
            JSONArray results = root.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i);

                movies.add(new Movie(
                        movie.getLong("id"),
                        movie.getString("original_title"),
                        movie.getString("poster_path"),
                        movie.getString("overview"),
                        movie.getInt("vote_average"),
                        movie.getString("release_date")));
            }

        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

        return movies;
    }
}
