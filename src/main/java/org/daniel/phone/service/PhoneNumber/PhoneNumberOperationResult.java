package org.daniel.phone.service.PhoneNumber;

import org.daniel.phone.service.OperationResult;

import java.util.LinkedList;
import java.util.List;

public class PhoneNumberOperationResult<T> extends OperationResult<T> {

    public static enum Error {
        PHONE_NUMBER_NOT_FOUND,
        PHONE_NUMBER_NOT_VALID,
        COUNTRY_NOT_FOUND

    }

    private PhoneNumberOperationResult() {
        super();
    }

    private PhoneNumberOperationResult(Builder builder) {
        super((T) builder.result, builder.error);

    }


    public void addError(Error error) {
        super.getErrors().add(error);
    }

    public static Builder empty() {
        return new Builder();
    }

    public static class Builder<T> {
        private List<Enum> error = new LinkedList<>();
        private T result;

        private Builder() {

        }

        public Builder addError(Error error) {
            this.error.add(error);
            return this;
        }

        public Builder result(T result) {
            this.result = result;
            return this;
        }

        public PhoneNumberOperationResult<T> build() {
            return new PhoneNumberOperationResult(this);
        }


    }

}
