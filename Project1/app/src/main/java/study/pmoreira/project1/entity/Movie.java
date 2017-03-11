package study.pmoreira.project1.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE_185 = "w185";

    private Long id;
    private String title;
    private String posterPath;
    private String overview;
    private Integer voteAverage;
    private String releaseDate;

    public Movie(Long id, String title, String posterPath, String overview, Integer voteAverage, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readInt();
        releaseDate = in.readString();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public Integer getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeInt(voteAverage);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterUrl() {
        String posterUrl = "";
        if (posterPath != null) {
            posterUrl = IMAGE_BASE_URL + IMAGE_SIZE_185 + getPosterPath();
        }
        return posterUrl;
    }

}
