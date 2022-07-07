package br.com.domain.exception.rules;

public class FieldRequiredException extends RuleException {

    public FieldRequiredException(String field) {
        super(field + " is required");
    }
}
