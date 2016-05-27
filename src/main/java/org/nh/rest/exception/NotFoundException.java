package org.nh.rest.exception;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = 4793493689207608763L;

    public NotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public NotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public NotFoundException(String arg0) {
        super(arg0);
    }

    public NotFoundException(Throwable arg0) {
        super(arg0);
    }

}
