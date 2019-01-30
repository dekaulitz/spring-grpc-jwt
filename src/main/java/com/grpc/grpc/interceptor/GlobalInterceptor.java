package com.grpc.grpc.interceptor;

import io.grpc.*;
import lombok.extern.log4j.Log4j2;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.core.annotation.Order;

@GRpcGlobalInterceptor
@Log4j2
@Order(20)
public class GlobalInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> delegate = next.startCall(call, headers);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {
            @Override
            protected ServerCall.Listener<ReqT> delegate() {
                log.info("delegate");
                return super.delegate();
            }

            @Override
            public void onHalfClose() {
                log.info(call.getAttributes().toString());
                log.info("onHalfclose");
                try {
                    super.onHalfClose();
                } catch (RuntimeException e) {
                    call.close(Status.INTERNAL.withDescription(e.getMessage()).withCause(e)
                            , headers);
                }
            }

            @Override
            public void onCancel() {
                log.info("onCancel");
                super.onCancel();
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
                super.onComplete();
            }

            @Override
            public void onReady() {
                log.info("onReady");
                super.onReady();
            }
        };
    }
}
