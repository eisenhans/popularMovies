package com.gmail.maloef.popularmovies.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.References;

public interface ReviewColumns extends BaseColumns {

    @DataType(DataType.Type.TEXT) @NotNull
    String AUTHOR = "author";

    @DataType(DataType.Type.TEXT) @NotNull
    String URL = "url";

    @DataType(DataType.Type.INTEGER) @NotNull @References(table = MovieDatabase.MOVIE, column = MovieColumns._ID)
    String MOVIE = "movie";

}
