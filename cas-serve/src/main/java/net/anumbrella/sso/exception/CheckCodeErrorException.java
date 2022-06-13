package net.anumbrella.sso.exception;

import org.apereo.cas.authentication.AuthenticationException;

/**
 * @author Anumbrella
 */
public class CheckCodeErrorException extends AuthenticationException {

    public CheckCodeErrorException(){
        super();
    }

    public CheckCodeErrorException(String msg) {
        super(msg);
    }
}

