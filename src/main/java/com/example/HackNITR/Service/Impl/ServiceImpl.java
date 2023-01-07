package com.example.HackNITR.Service.Impl;

import com.example.HackNITR.Entity.Site;
import com.example.HackNITR.Repository.SiteRepository;
import com.example.HackNITR.Service.Service;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    private SiteRepository siteRepository;
    public ServiceImpl(SiteRepository siteRepository){
        this.siteRepository=siteRepository;
    }
    @Override
    public void createSite(String la, String lo, Site site) {
        site.setDistance(distanceCalculator(la,lo, site.getLatitude(),site.getLongitude()));
        siteRepository.save(site);
    }

    @Override
    public List<Site> getAllSites() {
        List<Site> list= siteRepository.findAll();
        List<Site> dList = new ArrayList<>();
        for(Site site:list){
            if(site.getDistance()<2000){
                dList.add(site);
            }
        }
        return dList;
    }

    @Override
    public List<Site> refresh(String la,String lo) {
        List<Site> list = siteRepository.findAll();
        for(Site site:list){
            site.setDistance(distanceCalculator(la,lo,site.getLatitude(),site.getLongitude()));
            siteRepository.save(site);
        }
        return getAllSites();
    }


    private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    @Override
    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if(!StringUtils.isEmpty(ipAddress)
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    @Override
    public String[] getLocation(String ip) throws IOException, GeoIp2Exception {
        File database = new File("/Users/aman/Downloads/HackNITR/src/main/resources/GeoLite2-City_20230106/GeoLite2-City.mmdb");
        DatabaseReader dbReader  = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);
        String latitude = response.getLocation().getLatitude().toString();
        String longitude = response.getLocation().getLongitude().toString();
        return new String[]{latitude,longitude};
    }

    @Override
    public void deleteById(long id) {
        siteRepository.deleteById(id);
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
        return(c * r)*1000;
    }
}
