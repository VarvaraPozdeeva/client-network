package com.unn;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Blocking {
    BLOCK(1),
    UNBLOCK(2),
    LOCK_LOCK(3),
    UNBLOCK_FOR_LINK(4);

    private Integer value;

    Blocking(Integer val) {
        value = val;
    }

    public static Blocking of(Integer val){
        return Stream.of(Blocking.values())
                .filter(x->x.getValue().equals(val))
                .findFirst().orElse(null);
    }
}
