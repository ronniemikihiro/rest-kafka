package br.com.domain.exception.rules;

public class NotFoundException extends RuleException {

    public NotFoundException() {
        super("Not found exception");
    }
}
