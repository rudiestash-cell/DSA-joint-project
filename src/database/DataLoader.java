package database;

import java.io.*;

import model.*;

import structures.DynamicArray;

/** CSV loader. Database access is attempted by the application where an SQLite JDBC driver is present. */
public final class DataLoader  {

    private DataLoader()  {
    }
    public static DynamicArray<Location> locations(String path) throws IOException  {

        DynamicArray<Location> out=new DynamicArray<>();
         BufferedReader r=new BufferedReader(new FileReader(path));
         r.readLine();
         String line;

        while((line=r.readLine())!=null) {
            String[] p=line.split(",",-1);
             if(p.length==6)out.add(new Location(p[0],p[1],p[2],p[3],Double.parseDouble(p[4]),Double.parseDouble(p[5])));
        } r.close();
         return out;
    }
    public static DynamicArray<Road> roads(String path) throws IOException  {

        DynamicArray<Road> out=new DynamicArray<>();
         BufferedReader r=new BufferedReader(new FileReader(path));
         r.readLine();
         String line;

        while((line=r.readLine())!=null) {
            String[] p=line.split(",",-1);
            if(p.length==5)out.add(new Road(p[0],p[1],Double.parseDouble(p[2]),Double.parseDouble(p[3]),Double.parseDouble(p[4])));
        }r.close();
        return out;
    }
    public static DynamicArray<ServiceRequest> requests(String path) throws IOException  {

        DynamicArray<ServiceRequest> out=new DynamicArray<>();
        BufferedReader r=new BufferedReader(new FileReader(path));
        r.readLine();
        String line;

        while((line=r.readLine())!=null) {
            String[] p=line.split(",",-1);
            if(p.length>=9)out.add(new ServiceRequest(p[0],p[1],p[2],p[3],p[4],Integer.parseInt(p[5]),p[6],p[7],p[8]));
        }r.close();
        return out;
    }
    public static DynamicArray<Resource> resources(String path) throws IOException  {

        DynamicArray<Resource> out=new DynamicArray<>();
        BufferedReader r=new BufferedReader(new FileReader(path));
        r.readLine();
        String line;

        while((line=r.readLine())!=null) {
            String[] p=line.split(",",-1);
            if(p.length==5)out.add(new Resource(p[0],p[1],p[2],Integer.parseInt(p[3]),p[4]));
        }r.close();
        return out;
    }
}
