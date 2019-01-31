package com.vino.xmonitor.ds;

/**
 * @author phantom
 */
public class CircularLinkedElement <T> {
    T value = null;
    CircularLinkedElement <T> next = null;
    public CircularLinkedElement <T> next() {return next;}
    public T value() { return value; }
}
