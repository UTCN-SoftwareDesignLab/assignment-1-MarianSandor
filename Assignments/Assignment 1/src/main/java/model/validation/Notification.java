package model.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Notification<T> {

    private T result;
    private final List<String> errors;

    public Notification() {
        errors = new ArrayList<>();
    }

    public void addError(String message) {
        this.errors.add(message);
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() throws ResultFetchException {
        if (hasErrors()) {
            throw new ResultFetchException(errors);
        }
        return this.result;
    }

    public String getFormattedErrors() {
        return  errors.stream()
                .map(Objects::toString)
                .collect(Collectors.joining("\n"));
    }
}
