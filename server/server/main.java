package server;

import server.PathFindingClasses.ReallyBadExampleOfFakeDataAndRequestProviderInOneClass;
import server.PathFindingClasses.PathFinder;

public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ReallyBadExampleOfFakeDataAndRequestProviderInOneClass noob = new ReallyBadExampleOfFakeDataAndRequestProviderInOneClass();
       
        PathFinder pf = new PathFinder();
        pf.setDataProvider(noob);
        pf.setRequestProvider(noob);
        pf.init();
        pf.run();
    }
    
}
