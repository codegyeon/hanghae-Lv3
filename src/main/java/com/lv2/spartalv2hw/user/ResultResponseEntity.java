package com.lv2.spartalv2hw.user;

import lombok.Getter;

@Getter
public class ResultResponseEntity {
    private final String msg;

    public ResultResponseEntity(String msg){
        this.msg = msg;
    }
}
