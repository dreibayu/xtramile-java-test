package com.xtramile.patient.exception;

public class DuplicatePidException extends RuntimeException {

    public DuplicatePidException(String pid) {
        super("Patient PID already exists: " + pid);
    }
}