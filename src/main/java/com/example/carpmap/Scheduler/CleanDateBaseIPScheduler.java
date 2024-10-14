package com.example.carpmap.Scheduler;

import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Repository.IpAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.carpmap.Cammon.ErrorMessages.*;
import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_CHECK_FOR_DUPLICATE_IP_LOGGER;
import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_SAVE_NEW_IP_LOGGER;

@Component
public class CleanDateBaseIPScheduler {

    private final Logger LOGGER = LoggerFactory.getLogger(CleanDateBaseIPScheduler.class);
    private final IpAddressRepository ipAddressRepository ;

    public CleanDateBaseIPScheduler(IpAddressRepository ipAddressRepository) {
        this.ipAddressRepository = ipAddressRepository;
    }

//    @Scheduled(cron = "0 */1 * * * *")    // executing every 10 min
    @Scheduled(cron = "0 0 * * * *")    // executing every 1 h
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
            LOGGER.error(ERROR_DUPLICATE_IP_LOGGER);
            ipAddressRepository.deleteAll();
            ipAddressRepository.saveAll(allNewIpAddressInDB);
            LOGGER.error(SUCCESSFUL_SAVE_NEW_IP_LOGGER);
        } else {
            LOGGER.info(SUCCESSFUL_CHECK_FOR_DUPLICATE_IP_LOGGER);
        }
    }
}
