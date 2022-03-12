package com.morena.netMain.logic.service;

public interface CreateOrUpdate<T,S> {
    public T create(S pojo);
    public T update(S pojo);
}
