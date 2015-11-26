package com.gmail.maloef.popularmovies.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;

public interface ReviewColumns extends BaseColumns {

    @DataType(DataType.Type.TEXT) @NotNull
    String AUTHOR = "author";

    @DataType(DataType.Type.TEXT) @NotNull
    String URL = "url";

}
