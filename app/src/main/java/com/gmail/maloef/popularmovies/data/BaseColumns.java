package com.gmail.maloef.popularmovies.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

public interface BaseColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
    String _ID = "_id";
}
