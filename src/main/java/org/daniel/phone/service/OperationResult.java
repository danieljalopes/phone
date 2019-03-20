package org.daniel.phone.service;


import java.util.LinkedList;
import java.util.List;


public abstract class OperationResult<T> {
    private T result;

    private List<Enum> errors = new LinkedList<>();

    public OperationResult() {

    }

   protected OperationResult(T result, List<Enum> errors) {
        this.result = result;
        this.errors = errors;
    }

    public boolean hasErrors() {
        return errors.size() != 0;
    }


    public List<Enum> getErrors() {
        return this.errors;
    }


    public T getResult() {
        return result;
    }


}
