
/*
*ShareIt
* Date: 14.06.2017
* java version 1.8.0_73
* Windows 10 (1607)
* Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz 2.30 GHz
* @Author Sebastian Heunke, heunke@hm.edu
*/
package edu.hm;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import edu.hm.shareit.resources.BookService;
import edu.hm.shareit.resources.BookServiceImpl;
import edu.hm.shareit.resources.DiscService;
import edu.hm.shareit.resources.DiscServiceImpl;

/**
 * Context Listener to enable usage of google guice together with jersey.
 */
public class ShareitServletContextListener extends GuiceServletContextListener {

    private static final Injector injector
            = Guice.createInjector(new ServletModule() {
        @Override
        protected void configureServlets() {
            bind(BookService.class).to(BookServiceImpl.class);
            bind(DiscService.class).to(DiscServiceImpl.class);
        }
    });

    @Override
    protected Injector getInjector() {
        return injector;
    }

    /**
     * 30 * This method is only required for the HK2-Guice-Bridge in the
     * Application class.
     *
     * @return Injector instance.
     */
    static Injector getInjectorInstance() {
        return injector;
    }
}