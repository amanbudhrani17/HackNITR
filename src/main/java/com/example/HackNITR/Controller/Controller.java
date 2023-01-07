package com.example.HackNITR.Controller;

import com.example.HackNITR.Entity.Site;
import com.example.HackNITR.Service.Service;
import com.example.HackNITR.payload.SiteDto;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/sites")
public class Controller {
    public String la;
    public String lo;
    private Service service;
    public Controller(Service service){
        this.service=service;
    }
    @PostMapping("/create")
    public void createSite(@RequestBody Site site){
        System.out.println(site);
        service.createSite(la,lo,site);
    }
    @GetMapping("/{id}")
    public List<Site> getAllSites(@PathVariable(name = "id") long id){
        Comparator<Site> comparator = new Comparator<Site>() {
            @Override
            public int compare(Site o1, Site o2) {
                if(id==1){
                    return Double.compare(o1.getDistance(),o2.getDistance());
                }
                else{
                    return Double.compare(o1.getPercentage(),o2.getPercentage());
                }
            }
        };
        List<Site> list = service.getAllSites();
        list.sort(comparator);
        return list;
    }
    @PostMapping("/refresh")
    public List<Site> refreshSites(){
        return service.refresh(la,lo);
    }
    public double distanceCalculator(String latitude1, String longitude1, String latitude2, String longitude2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        double lat1 = Double.parseDouble(latitude1);
        double lon1 = Double.parseDouble(longitude1);
        double lat2 = Double.parseDouble(latitude2);
        double lon2 = Double.parseDouble(longitude2);

        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }
    @GetMapping("/location")
    public void index(HttpServletRequest request) throws IOException, GeoIp2Exception {
        String clientIp = service.getClientIp(request);
        System.out.println(clientIp);
        String[] strings = service.getLocation("14.139.197.66");
        System.out.println(strings[0]+" "+strings[1]);
        la=strings[0];
        lo=strings[1];
    }
}
