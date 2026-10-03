package com.pedrovaledev.linktree.exception;

public class LinkNotFoundException extends RuntimeException {

    public LinkNotFoundException() {super("Link não encontrado");}

    public LinkNotFoundException(String message) {
        super(message);
    }

}
