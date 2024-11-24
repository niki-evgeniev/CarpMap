package com.example.carpmap.Service.Impl;

import com.example.carpmap.Repository.FishListRepository;
import com.example.carpmap.Repository.PictureRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.ServerInfoService;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;


@Service
public class ServerInfoServiceImpl implements ServerInfoService {

    private final ReservoirRepository reservoirRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final FishListRepository fishListRepository;

    public ServerInfoServiceImpl(ReservoirRepository reservoirRepository, UserRepository userRepository,
                                 PictureRepository pictureRepository, FishListRepository fishListRepository) {
        this.reservoirRepository = reservoirRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.fishListRepository = fishListRepository;
    }

    @Override
    public String getUptime() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptimeMachine = runtimeMXBean.getUptime();
        String formatedUptime = formatUptime(uptimeMachine);
        return formatedUptime;
    }

    @Override
    public Long countAllReservoir() {
        return reservoirRepository.count();
    }

    @Override
    public Long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public Long countAllPictureCloudinary() {
        return pictureRepository.count();
    }

    @Override
    public Long countFishList() {
        return fishListRepository.count();
    }

    private String formatUptime(long uptimeMachine) {
        long allSeconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMachine);
        long days = allSeconds / 86400;
        long hours = (allSeconds % 86400) / 3600;
        long minutes = (allSeconds % 3600) / 60;
        long seconds = allSeconds % 60;
        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
    }
}
