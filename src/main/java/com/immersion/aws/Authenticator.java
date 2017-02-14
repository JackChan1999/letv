package com.immersion.aws;

import com.immersion.aws.tvm.Token;
import org.apache.http.auth.AuthenticationException;

public interface Authenticator {

    public interface Listener {
        void onAuthorizationFailure();

        void onAuthorizationSuccess(Token token);

        void onAuthorizationTimeout();

        void onAuthorizing(Token token);
    }

    Token authenticate() throws AuthenticationException;
}
