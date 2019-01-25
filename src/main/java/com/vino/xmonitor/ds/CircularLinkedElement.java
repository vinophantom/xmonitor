package com.vino.xmonitor.ds;

/**
 * @author phantom
 */
public class CircularLinkedElement <T> {
    T value = null;
    CircularLinkedElement next = null;
    public CircularLinkedElement next() {return next;}
    public T value() { return value; }
}
