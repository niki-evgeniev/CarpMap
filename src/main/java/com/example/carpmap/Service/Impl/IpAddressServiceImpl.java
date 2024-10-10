package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Ip.AllIpDTO;
import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.IpAddressRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.IpAddressService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.carpmap.Cammon.ErrorMessages.ERROR_CHANGE_IS_BANNED_CANT_FIND;
import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_CHANGE_IS_BANNED;


@Service
public class IpAddressServiceImpl implements IpAddressService {

    private final IpAddressRepository ipAddressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(IpAddressServiceImpl.class);

    public IpAddressServiceImpl(IpAddressRepository ipAddressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.ipAddressRepository = ipAddressRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String getIp() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getDetails() instanceof WebAuthenticationDetails details) {
            return details.getRemoteAddress();
        }
        return "Cant detect ip";
    }

    @Override
    public Long findAllVisits() {
        return ipAddressRepository.sumAllCounts();
    }

    @Override
    @Transactional
    public void checkIpAddressLogin(String username, String ipAddress) {
        Optional<IpAddress> findExistingIpAddress = ipAddressRepository.findByAddress(ipAddress);
        Optional<User> findUser = userRepository.findByUsername(username);

        if (findExistingIpAddress.isEmpty()) {
            IpAddress newAddress = new IpAddress();
            newAddress.setAddress(ipAddress);
            newAddress.setTimeToAdd(LocalDateTime.now());
            newAddress.setCountVisits(1L);
            findUser.ifPresent(newAddress::setUser);
            ipAddressRepository.save(newAddress);
        } else if (findExistingIpAddress.get().getUser() == null) {
            findUser.ifPresent(user -> findExistingIpAddress.get().setUser(user));
            addView(findExistingIpAddress);
            ipAddressRepository.save(findExistingIpAddress.get());
        } else {
            findUser.ifPresent(user -> findExistingIpAddress.get().setUser(user));
            addView(findExistingIpAddress);
            ipAddressRepository.save(findExistingIpAddress.get());
        }
    }

    private static void addView(Optional<IpAddress> findExistingIpAddress) {

        if (findExistingIpAddress.isPresent()) {
            findExistingIpAddress.get().setCountVisits(findExistingIpAddress.get().getCountVisits() + 1L);
            findExistingIpAddress.get().setLastSeen(LocalDateTime.now());
        }
    }

    @Override
    @Transactional
    public void getIpVisitor(String ipAddress) {
        Optional<IpAddress> byAddress = ipAddressRepository.findByAddress(ipAddress);
        if (byAddress.isEmpty()) {
            IpAddress addNewIpVisitor = new IpAddress();
            addNewIpVisitor.setAddress(ipAddress);
            addNewIpVisitor.setTimeToAdd(LocalDateTime.now());
            addNewIpVisitor.setCountVisits(1L);
            ipAddressRepository.save(addNewIpVisitor);
        } else {
            IpAddress newIpAdd = byAddress.get();
            newIpAdd.setCountVisits(newIpAdd.getCountVisits() + 1L);
            newIpAdd.setLastSeen(LocalDateTime.now());
            ipAddressRepository.save(newIpAdd);
        }
    }

    @Override
    @Transactional
    public boolean banIp(Long id) {
        return banOrUnbanIp(id, true);
    }

    @Override
    @Transactional
    public boolean unbanIp(Long id) {
        return banOrUnbanIp(id, false);
    }

    @Override
    public Page<AllIpDTO> getAllIpsAddress(Pageable pageable) {
        Page<IpAddress> all = ipAddressRepository.findAll(pageable);
        return getAllIpDTOS(all);
    }

    @Override
    public Page<AllIpDTO> findOnlyUsedByUser(Pageable pageable, String type) {
        Page<IpAddress> allByUserIsNotNull = ipAddressRepository.findAllByUserIsNotNull(pageable);

        return getAllIpDTOS(allByUserIsNotNull);
    }

    @Override
    public Page<AllIpDTO> findLastDay(Pageable pageable, String type) {
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        Page<IpAddress> findAllLastDay = ipAddressRepository.findAllIpAddressesFromLastDay(lastDay, pageable);

        return getAllIpDTOS(findAllLastDay);
    }

    @Override
    public Page<AllIpDTO> findThirtyDaysAgo(Pageable pageable, String type) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Page<IpAddress> allUserJoin30DaysAgo = ipAddressRepository.findAllIpAddressesFromLast30Days(thirtyDaysAgo, pageable);

        return getAllIpDTOS(allUserJoin30DaysAgo);
    }


    private Page<AllIpDTO> getAllIpDTOS(Page<IpAddress> allByUserIsNotNull) {
        Page<AllIpDTO> findUserIsNotNull = allByUserIsNotNull.map(
                ip -> {
                    AllIpDTO map = modelMapper.map(ip, AllIpDTO.class);
                    map.setIsBanned(ip.getBanned().toString());
                    if (ip.getUser() != null) {
                        map.setUserId(ip.getUser().getUsername());
                    }
                    return map;
                }
        );
        return findUserIsNotNull;
    }

    private boolean banOrUnbanIp(Long id, boolean banned) {
        Optional<IpAddress> findIpToBan = ipAddressRepository.findById(id);

        if (findIpToBan.isPresent()) {
            IpAddress ipAddress = findIpToBan.get();
            ipAddress.setBanned(banned);
            ipAddressRepository.save(ipAddress);
            String errMsg = String.format(SUCCESSFUL_CHANGE_IS_BANNED, banned, ipAddress.getAddress());
            LOGGER.error(errMsg);
            return true;
        }
        String errMsg = String.format(ERROR_CHANGE_IS_BANNED_CANT_FIND, id);
        LOGGER.error(errMsg);
        return false;
    }
}
