package com.example.carpmap.Scheduler;

import com.example.carpmap.Models.Entity.FishList;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Repository.FishListRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Component
public class SitemapScheduler {

    private final ReservoirRepository reservoirRepository;
    private final FishListRepository fishListRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SitemapScheduler.class);

    public SitemapScheduler(ReservoirRepository reservoirRepository, FishListRepository fishListRepository) {
        this.reservoirRepository = reservoirRepository;
        this.fishListRepository = fishListRepository;
    }

//    @Scheduled(cron = "0 */1 * * * *")
    @Scheduled(cron = "0 0 */4 * * *")
//    @Scheduled(cron = "0 0 0 1 * ?")
    public void createSitemap() throws MalformedURLException, ParseException {

        createFolderForSitemap();

        WebSitemapGenerator webSitemapGenerator = WebSitemapGenerator.
                builder("https://carpmap.online/", new File("./sitemap")).build();
        String day = LocalDate.now().toString();

        WebSitemapUrl webSitemapUrlCarpMap = new WebSitemapUrl
                .Options("https://carpmap.online/")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlCarpMap);

        WebSitemapUrl webSitemapUrlAnnounced = new WebSitemapUrl
                .Options("https://carpmap.online/announced")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlAnnounced);

        WebSitemapUrl webSitemapUrlReservoirs = new WebSitemapUrl
                .Options("https://carpmap.online/reservoirs/reservoirsByType/reservoirs")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlReservoirs);
        WebSitemapUrl webSitemapUrlInformation = new WebSitemapUrl
                .Options("https://carpmap.online/info")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlInformation);

        WebSitemapUrl webSitemapUrlBlog = new WebSitemapUrl
                .Options("https://carpmap.online/blog")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlBlog);


        WebSitemapUrl webSitemapUrlAbout = new WebSitemapUrl
                .Options("https://carpmap.online/about")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlAbout);

        WebSitemapUrl webSitemapUrlContact = new WebSitemapUrl
                .Options("https://carpmap.online/contact")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlContact);

        WebSitemapUrl webSitemapUrlCountVisitors = new WebSitemapUrl
                .Options("https://carpmap.online/reservoirs/reservoirsByType/countVisitors")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(0.8)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlCountVisitors);

        WebSitemapUrl webSitemapUrlCountry = new WebSitemapUrl
                .Options("https://carpmap.online/reservoirs/reservoirsByType/private_reservoir")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(0.8)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlCountry);

        WebSitemapUrl webSitemapUrlFree = new WebSitemapUrl
                .Options("https://carpmap.online/reservoirs/reservoirsByType/free_reservoir")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(0.8)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlFree);

        List<Reservoir> all = reservoirRepository.findAll();
        for (Reservoir reservoir : all) {
            WebSitemapUrl webSitemapUrlReservoir = new WebSitemapUrl
                    .Options("https://carpmap.online/reservoirs/" + reservoir.getUrlName())
                    .lastMod(day)
                    .changeFreq(ChangeFreq.DAILY)
                    .priority(1.0)
                    .build();
            webSitemapGenerator.addUrl(webSitemapUrlReservoir);
        }

        List<FishList> allFishList = fishListRepository.findAll();
        for (FishList fishList : allFishList) {
            WebSitemapUrl webSitemapUrlFishList = new WebSitemapUrl
                    .Options("https://carpmap.online/fish-list-type/" + fishList.getUrlName())
                    .lastMod(day)
                    .changeFreq(ChangeFreq.DAILY)
                    .priority(1.0)
                    .build();
            webSitemapGenerator.addUrl(webSitemapUrlFishList);
        }
        webSitemapGenerator.write();
        LOGGER.info("UPDATE SITEMAP.XML SUCCESSFUL");
    }

    private static void createFolderForSitemap() {
        String folder = "./sitemap";
        File sitemapDir = new File(folder);
        if (!sitemapDir.exists()) {
            boolean created = sitemapDir.mkdirs();
            if (created) {
                LOGGER.info("Sitemap folder created: {}", folder);
            } else {
                LOGGER.info("Failed to create sitemap folder: {}", folder);
            }
        }
    }

}
