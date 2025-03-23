//package com.example.carpmap.Controller;
//
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//public class RedirectController {
//
//    //    @GetMapping("*/**")
////    public ResponseEntity<Void> redirect(HttpServletRequest request) {
////        String host = request.getHeader("Host");
////        System.out.println(host);
////        if (host.startsWith("/css") || host.startsWith("/js") ||
////                host.startsWith("/images") || host.startsWith("/static")) {
////            return ResponseEntity.ok().build(); // Оставя ги да се зареждат нормално
////        }
////        return ResponseEntity.ok().build();
////    }
//
//    @GetMapping("/**")
//    public ResponseEntity<Void> redirect(HttpServletRequest request) {
//        String requestUri = request.getRequestURI();
//
//        // Оставяме статичните файлове да се зареждат нормално
//        if (requestUri.matches(".*(\\.css|\\.js|\\.lib|\\.jpg|\\.jpeg|\\.png|\\.gif|\\.ico|\\.svg|\\.woff|\\.woff2|\\.ttf|\\.eot|\\.mp4|\\.webp)$")) {
//            return ResponseEntity.ok().build();
//        }
//
//        String host = request.getHeader("Host");
//        if (host != null && host.contains("carpmap.bg")) {
//            String newUrl = "https://carpmap.bg" + requestUri;
//            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
//                    .header("Location", newUrl)
//                    .build();
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//}
//
