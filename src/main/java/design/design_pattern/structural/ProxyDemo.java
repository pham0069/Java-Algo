package design.design_pattern.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * 4 types of proxies:
 * 1. Virtual proxy: placeholder of 'expensive to create' objects
 * Proxy is only created when a client first request/access the object
 * 2. Remote proxy: provides local representative for an object that resides in different address space
 * This is stub code in RPC and COBRA
 * 3. Protective proxy: controls access to sensitive master object
 * The surrogate object checks if caller has permission required prior forwarding the request
 * 4. Smart proxy: interposes additional actions when an object is accessed
 *
 * In general, proxy is used when we need to create a wrapper to cover the main object's complexity from the client
 *
 * Adv
 * 1. Security
 * 2. Avoid duplication of objects that might be expensive
 *
 * Disadv
 * Another layer of abstraction is introduced. It could be a problem if some objects access the RealSubject directly,
 * some access through proxy.
 * This might cause disparate behavior.
 *
 * Note that
 * 1. Adapter provides a different interface to its subject
 * 2. Proxy provides the same interface
 * 3. Decorator provides an enhanced interface
 * 4. Facade provides a simpler interface
 *
 */
public class ProxyDemo {
    interface Internet {
        void connectTo(String serverhost) throws Exception;
    }

    static class RealInternet implements Internet {
        @Override
        public void connectTo(String serverhost) {
            System.out.println("Connecting to " + serverhost);
        }
    }

    static class ProxyInternet implements Internet {
        private Internet internet = new RealInternet();
        private static List<String> bannedSites;

        static {
            bannedSites = new ArrayList<>();
            bannedSites.add("abc.com");
            bannedSites.add("def.com");
            bannedSites.add("ijk.com");
            bannedSites.add("lnm.com");
        }

        @Override
        public void connectTo(String serverhost) throws Exception {
            if (bannedSites.contains(serverhost.toLowerCase())) {
                throw new Exception("Access Denied");
            }

            internet.connectTo(serverhost);
        }

    }

    public static void main (String[] args) {
        Internet internet = new ProxyInternet();
        try {
            internet.connectTo("geeksforgeeks.org");
            internet.connectTo("abc.com");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
