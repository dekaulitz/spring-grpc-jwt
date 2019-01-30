package com.grpc.grpc.interceptor;

import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;


public class JwtInterceptor implements ServerInterceptor {
    private static final ServerCall.Listener NOOP_LISTENER = new ServerCall.Listener() {
    };


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        return null;
    }
}
