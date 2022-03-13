package com.morena.netMain.logic.service;

public interface CreateOrUpdateEntityMaker<T,S> {
    public T creatable(S pojo);
    public T updatable(S pojo);
}
