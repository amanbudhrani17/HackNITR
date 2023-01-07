package com.example.HackNITR.Service;

import com.example.HackNITR.Entity.Site;
import com.example.HackNITR.payload.SiteDto;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface Service {
    public void createSite(String la, String lo, Site site);
    public List<Site> getAllSites();
    public List<Site> refresh(String la,String lo);
    String getClientIp(HttpServletRequest request);
    public String[] getLocation(String ip) throws IOException, GeoIp2Exception;
}
