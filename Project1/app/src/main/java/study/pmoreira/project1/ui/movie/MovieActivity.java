package study.pmoreira.project1.ui.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project1.R;
import study.pmoreira.project1.entity.Movie;

public class MovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @BindView(R.id.movie_poster_imageview)
    ImageView mPosterImageView;

    @BindView(R.id.movie_overview_textview)
    TextView mOverviewTextView;

    @BindView(R.id.movie_release_date_textview)
    TextView mReleaseDateTextView;

    @BindView(R.id.movie_vote_average_textview)
    TextView mVoteAverageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            setTitle(movie.getTitle());

            mOverviewTextView.setText(movie.getOverview());
            mReleaseDateTextView.setText(movie.getReleaseDate());
            mVoteAverageTextView.setText(formatAverage(movie.getVoteAverage()));

            Picasso.with(this)
                    .load(movie.getPosterUrl())
                    .into(mPosterImageView);
        }
    }

    private String formatAverage(Integer voteAverage) {
        return getString(R.string.movie_activity_vote_average, voteAverage);
    }

}
