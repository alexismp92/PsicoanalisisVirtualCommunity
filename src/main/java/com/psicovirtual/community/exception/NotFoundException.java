package com.psicovirtual.community.exception;

import java.util.function.Supplier;

public class NotFoundException extends Exception {

    public NotFoundException(String msg){
        super(msg);
    }
}
