package edu.hm;

import com.google.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;

/*
*ShareIt
* Date: 14.06.2017
* java version 1.8.0_73
* Windows 10 (1607)
* Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz 2.30 GHz
* @Author Sebastian Heunke, heunke@hm.edu
*/
public class ShareItApplication extends ResourceConfig {

    @Inject
    public ShareItApplication(ServiceLocator serviceLocator){
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2BRidge guiceBridge =
    }
}
