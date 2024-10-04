package com.example.carpmap.Scheduler;

import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Repository.IpAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CleanDateBaseIPScheduler {

    private final Logger LOGGER = LoggerFactory.getLogger(CleanDateBaseIPScheduler.class);
    private final IpAddressRepository ipAddressRepository ;

    public CleanDateBaseIPScheduler(IpAddressRepository ipAddressRepository) {
        this.ipAddressRepository = ipAddressRepository;
    }

    @Scheduled(cron = "0 0 * * * *")    // executing every 1h
//    @Scheduled(cron = "0 0 */2 * * *")    // executing every 2h
    public void cleanDbIp(){
        List<IpAddress> allIpAddressInDB = ipAddressRepository.findAll();
        List<IpAddress> allNewIpAddressInDB = new ArrayList<>();

        for (IpAddress ipAddress : allIpAddressInDB) {
            if (!allNewIpAddressInDB.contains(ipAddress)) {
                allNewIpAddressInDB.add(ipAddress);
            }
        }
        if (allIpAddressInDB.size() != allNewIpAddressInDB.size()) {
            LOGGER.error("ERROR : DUPLICATE IP ADDRESS IN DB - DELETED DUPLICATE");
            ipAddressRepository.saveAll(allNewIpAddressInDB);
        } else {
            LOGGER.info("Successful check for duplicate - none duplicate ipAddress found");
        }
    }
}
