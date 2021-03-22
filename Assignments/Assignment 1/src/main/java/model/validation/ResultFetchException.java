package model.validation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResultFetchException extends RuntimeException {

    private  final List<String> errors;

    public ResultFetchException(List<String> errors) {
        super("Failed to fetch the result.");
        this.errors = errors;
    }

    @Override
    public String toString() {
        return  errors.stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(","))
                    + super.toString();
    }
}
