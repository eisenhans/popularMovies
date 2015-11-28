package com.gmail.maloef.popularmovies.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

public interface TrailerColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    String KEY = "key";

    @DataType(DataType.Type.TEXT) @NotNull
    String NAME = "name";

    @DataType(DataType.Type.INTEGER) @NotNull @References(table = MovieDatabase.MOVIE, column = MovieColumns._ID)
    String MOVIE = "movie";

}
