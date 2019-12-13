package com.unn;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Status {
    LOCK(1),
    UNLOCK(2),
    LOCK_FOR_LINK(3),
    UNLOCK_FOR_LINK(4),
    UPDATE(5);

    private Integer value;

    Status(Integer val) {
        value = val;
    }

    public static Status of(Integer val){
        return Stream.of(Status.values())
                .filter(x->x.getValue().equals(val))
                .findFirst().orElse(null);
    }
}
