package study.pmoreira.project1.ui.catalog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.pmoreira.project1.R;
import study.pmoreira.project1.entity.Movie;
import study.pmoreira.project1.ui.movie.MovieActivity;

class CatalogAdapter extends ArrayAdapter<Movie> {

    CatalogAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    static class ViewHolder {

        @BindView(R.id.poster_imageview)
        ImageView posterImageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Movie movie = (Movie) getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.catalog_item, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.posterImageView);

        holder.posterImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieActivity.class);
                intent.putExtra(MovieActivity.EXTRA_MOVIE, movie);

                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
