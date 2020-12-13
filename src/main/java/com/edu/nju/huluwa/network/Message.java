package com.edu.nju.huluwa.network;

import java.io.Serializable;
import java.util.ArrayList;

public interface Message extends Serializable {
    enum Kind{
        RANNUM,  // [a random number], to decide the first mover
        MOVE,    // move the fighter on [x, y] to [x1, y1]
        ATTACK,  // the fighter on [x, y] attacks another fighter on [x1, y1]
        END;     // it is your turn now
    }
    Kind getKind();
}
