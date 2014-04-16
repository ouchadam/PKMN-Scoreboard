package com.ouchadam.psr.read.validate;

public interface Validator<T> {
    boolean isValid(T what);
}
