package com.zespolowe.server.dataFormats;

import java.time.LocalDateTime;

/**
 * Created by lizzard on 23.01.17.
 */
public class AdjustedTime {
    public LocalDateTime planned;
    public LocalDateTime predicted;

    AdjustedTime(LocalDateTime planned, LocalDateTime predicted){
        this.planned = planned;
        this.predicted = predicted;
    }
}
