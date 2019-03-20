package org.daniel.phone.service.PhoneValidator;

import org.daniel.phone.service.OperationResult;

import java.util.LinkedList;
import java.util.List;

public class PhoneValidatorOperationResult<T> extends OperationResult<T> {

    public static enum Error {
        PHONE_VALIDATOR_NOT_FOUND

    }

    private PhoneValidatorOperationResult() {
        super();
    }

    private PhoneValidatorOperationResult(Builder builder) {
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

        public PhoneValidatorOperationResult<T> build() {
            return new PhoneValidatorOperationResult(this);
        }


    }

}
