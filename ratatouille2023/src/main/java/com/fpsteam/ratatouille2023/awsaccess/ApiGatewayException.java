package com.fpsteam.ratatouille2023.awsaccess;

import com.amazonaws.AmazonServiceException;

public class ApiGatewayException extends AmazonServiceException {
    public ApiGatewayException(String errorMessage) {
        super(errorMessage);
    }

    public ApiGatewayException(String errorMessage, Exception cause) {
        super(errorMessage, cause);
    }
}
