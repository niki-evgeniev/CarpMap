package com.example.carpmap.Scheduler;


import com.example.carpmap.Repository.IpAddressRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CleanIpScheduler {

    public final IpAddressRepository ipAddressRepository;

    public CleanIpScheduler(IpAddressRepository ipAddressRepository) {
        this.ipAddressRepository = ipAddressRepository;
    }

//    @Scheduled(cron = "0 0 3 1 * ?")
//        @Scheduled(cron = "0 */1 * * * *")
//    public void cleanOldIpsByLastSeen() {
//        LocalDateTime cutoffDate = LocalDateTime.now().minusYears(10);
//        ipAddressRepository.deleteOldIps(cutoffDate);
//        System.out.println("method cleanOldIpsByLastSeen : Old ip is cleaned " + cutoffDate);
    }
// IN TESTING
//    @Scheduled(cron = "0 0 23 31 12 ?")
//    public void cleanIpsWithNullLastSeenOrOldTimeToAdd() {
//        LocalDateTime cutoffDate = LocalDateTime.now().minusYears(10);
//        ipAddressRepository.deleteOldIpsOrIpIsNull(cutoffDate);
//        System.out.println("method cleanIpsWithNullLastSeenOrOldTimeToAdd: Old ip or null is cleaned " + cutoffDate);
//    }
//}
