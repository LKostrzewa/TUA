package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class ImageEndpoint implements Serializable {

}
