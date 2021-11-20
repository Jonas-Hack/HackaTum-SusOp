package Testing;

import Data.Device;
import Networking.NetworkDevices;
import UI.HomeScreen;
import UI.ListRenderer;

import java.util.List;

public class UILoop extends Thread
{
    List<Device> devices;
    ListRenderer list;
    HomeScreen homeScreen;

    public UILoop(List<Device> devices, ListRenderer list, HomeScreen homeScreen)
    {
        this.devices = devices;
        this.list = list;
        this.homeScreen = homeScreen;
    }

    public void run()
    {
        while (true)
        {
            List<Device> currentDevices = NetworkDevices.getInstance().getDevices();

            for (Device d : currentDevices)
            {
                if (!devices.contains(d))
                {
                    devices.add(d);

                    list.addElement(d);
                }
            }

            for (Device d : devices)
            {
                if (!currentDevices.contains(d))
                {
                    devices.remove(d);

                    list.removeElement(d);
                }
            }

            try
            {
                sleep(1000);
            }
            catch (Exception e)
            {
                System.out.println("Crashed Ping Loop");
            }

            NetworkDevices nd = NetworkDevices.getInstance();
            homeScreen.setStatistics
                    (
                        Math.round(nd.getAvgOfWatt()),     Math.round(nd.getSumOfWatt()),
                        Math.round(nd.getAvgOfWattHour()), Math.round(nd.getSumOfWattHour()),
                        Math.round(nd.getAvgOfCo2()),      Math.round(nd.getSumOfCo2())
                    );

            homeScreen.repaint();
            homeScreen.revalidate();

            list.repaint();
            list.revalidate();
        }
    }
}

