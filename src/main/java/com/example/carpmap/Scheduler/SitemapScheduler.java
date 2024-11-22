package com.example.carpmap.Scheduler;

import com.example.carpmap.Models.Entity.FishList;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Repository.FishListRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
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

    public SitemapScheduler(ReservoirRepository reservoirRepository, FishListRepository fishListRepository) {
        this.reservoirRepository = reservoirRepository;
        this.fishListRepository = fishListRepository;
    }

    @Scheduled(cron = "0 */1 * * * *")
//    @Scheduled(cron = "0 0 0 1 * ?")
    public void createSitemap() throws MalformedURLException, ParseException {

        WebSitemapGenerator webSitemapGenerator = WebSitemapGenerator.
                builder("https://carpmap.online/", new File("src/main/resources/static")).build();
        String day = LocalDate.now().toString();

        WebSitemapUrl webSitemapUrlCarpMap = new WebSitemapUrl.Options("https://carpmap.online/")
//                .lastMod(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlCarpMap);

        WebSitemapUrl webSitemapUrlAnnounced = new WebSitemapUrl.Options("https://carpmap.online/announced")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlAnnounced);

        WebSitemapUrl webSitemapUrlReservoirs = new WebSitemapUrl.Options("https://carpmap.online/reservoirs/reservoirsByType/reservoirs")
//                .lastMod(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlReservoirs);

        WebSitemapUrl webSitemapUrlBlog = new WebSitemapUrl.Options("https://carpmap.online/blog")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlBlog);


        WebSitemapUrl webSitemapUrlAbout = new WebSitemapUrl.Options("https://carpmap.online/about")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlAbout);

        WebSitemapUrl webSitemapUrlContact = new WebSitemapUrl.Options("https://carpmap.online/contact")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(1.0)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlContact);

        WebSitemapUrl webSitemapUrlCountVisitors = new WebSitemapUrl.Options("https://carpmap.online/reservoirs/reservoirsByType/countVisitors")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(0.8)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlCountVisitors);

        WebSitemapUrl webSitemapUrlCountry = new WebSitemapUrl.Options("https://carpmap.online/reservoirs/reservoirsByType/private_reservoir")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(0.8)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlCountry);

        WebSitemapUrl webSitemapUrlFree = new WebSitemapUrl.Options("https://carpmap.online/reservoirs/reservoirsByType/free_reservoir")
                .lastMod(day)
                .changeFreq(ChangeFreq.DAILY)
                .priority(0.8)
                .build();
        webSitemapGenerator.addUrl(webSitemapUrlFree);

        List<Reservoir> all = reservoirRepository.findAll();
        for (Reservoir reservoir : all) {
            WebSitemapUrl webSitemapUrlReservoir = new WebSitemapUrl.Options("https://carpmap.online/reservoirs/" + reservoir.getUrlName())
                    .lastMod(day)
                    .changeFreq(ChangeFreq.DAILY)
                    .priority(1.0)
                    .build();
            webSitemapGenerator.addUrl(webSitemapUrlReservoir);
        }

        List<FishList> allFishList = fishListRepository.findAll();
        for (FishList fishList : allFishList) {
            WebSitemapUrl webSitemapUrlFishList = new WebSitemapUrl.Options("https://carpmap.online/fish-list-type/" + fishList.getUrlName())
                    .lastMod(day)
                    .changeFreq(ChangeFreq.DAILY)
                    .priority(1.0)
                    .build();
            webSitemapGenerator.addUrl(webSitemapUrlFishList);
        }


        webSitemapGenerator.write();

        System.out.println("Sitemap updated");
    }

}
