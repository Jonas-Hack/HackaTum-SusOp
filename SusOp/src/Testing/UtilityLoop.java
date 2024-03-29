package Testing;

import Data.Device;
import Networking.NetworkDevices;
import Networking.NetworkPing;
import UI.ListRenderer;

import java.net.UnknownHostException;
import java.util.List;

public class UtilityLoop extends Thread
{
    NetworkDevices instance;

    public UtilityLoop(){
        this.instance = NetworkDevices.getInstance();
    }

    public void run()
    {
        while (true)
        {
            //Refresh Devices Ping
            try {
                NetworkPing.getAllDevices();
                //NetworkPing.dummyDevices();
            } catch (UnknownHostException e) {
                System.out.println("Unkown Host Exception " + e);
            }

            try
            {
                sleep(10000);
            }
            catch (Exception e)
            {
                System.out.println("Crashed Ping Loop");
            }

            //Remove old Devices that have been inactive for 60 seconds at least from List
            this.instance.removeOldDevices();
            this.instance.addGraphDataPoint();

            try
            {
                sleep(10000);
            }
            catch (Exception e)
            {
                System.out.println("Crashed Ping Loop");
            }
        }
    }
}

