package com.gmail.maloef.popularmovies.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

public interface ReviewColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    String AUTHOR = "author";

    @DataType(DataType.Type.TEXT) @NotNull
    String URL = "url";

    @DataType(DataType.Type.INTEGER) @NotNull @References(table = MovieDatabase.MOVIE, column = MovieColumns._ID)
    String MOVIE = "movie";

}
