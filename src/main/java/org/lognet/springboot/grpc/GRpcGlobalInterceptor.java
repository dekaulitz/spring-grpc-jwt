package org.lognet.springboot.grpc;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by jamessmith on 9/7/16.
 */
@Target({ElementType.TYPE,ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GRpcGlobalInterceptor {
}
