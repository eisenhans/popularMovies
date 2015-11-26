package com.gmail.maloef.popularmovies.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Markus on 25.11.2015.
 */
public interface MovieColumns extends BaseColumns {

    @DataType(DataType.Type.INTEGER) @Unique @NotNull
    String MOVIE_ID = "movieId";

    @DataType(DataType.Type.TEXT) @NotNull
    String TITLE = "title";

    @DataType(DataType.Type.TEXT)
    String ORIGINAL_TITLE = "originalTitle";

    @DataType(DataType.Type.TEXT)
    String OVERVIEW = "overview";

    @DataType(DataType.Type.TEXT)
    String RELEASE_DATE = "releaseDate";

    @DataType(DataType.Type.TEXT)
    String VOTE_AVERAGE = "voteAverage";

    @DataType(DataType.Type.TEXT)
    String POSTER_PATH = "posterPath";

}
