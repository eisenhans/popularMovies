package com.gmail.maloef.popularmovies.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.References;

public interface TrailerColumns extends BaseColumns {

    @DataType(DataType.Type.TEXT) @NotNull
    String KEY = "key";

    @DataType(DataType.Type.TEXT) @NotNull
    String NAME = "name";

    @DataType(DataType.Type.INTEGER) @NotNull @References(table = MovieDatabase.MOVIE, column = MovieColumns._ID)
    String MOVIE = "movie";

}
