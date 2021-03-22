package model.builder;

import model.Log;

import java.time.LocalDate;

public class LogBuilder {

    private final Log log;

    public LogBuilder() {
        this.log = new Log();
    }

    public LogBuilder setUser(String user) {
        this.log.setUser(user);
        return this;
    }

    public LogBuilder setAction(String action) {
        this.log.setAction(action);
        return this;
    }
    public LogBuilder setDate(LocalDate date) {
        this.log.setDate(date);
        return this;
    }

    public Log build() {
        return this.log;
    }
}
