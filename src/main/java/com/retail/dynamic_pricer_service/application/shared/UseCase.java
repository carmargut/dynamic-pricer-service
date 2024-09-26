package com.retail.dynamic_pricer_service.application.shared;

public interface UseCase<T, I> {
    I execute(T t);
}
