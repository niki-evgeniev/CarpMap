//
// window.onload = function() {
//     const cookieConsent = document.getElementById('cookieConsent');
//     const cookieBackdrop = document.getElementById('cookieBackdrop');
//     const acceptCookiesBtn = document.getElementById('acceptCookies');
//
//     // Показване на pop-up ако не е приета политиката за бисквитки
//     if (!localStorage.getItem('cookieAccepted')) {
//         cookieConsent.style.display = 'block';
//         cookieBackdrop.style.display = 'block';  // Показване на тъмния фон зад pop-up
//     }
//
//     // Приемане на бисквитките
//     acceptCookiesBtn.addEventListener('click', function() {
//         localStorage.setItem('cookieAccepted', 'true');
//         cookieConsent.style.display = 'none';
//         cookieBackdrop.style.display = 'none';  // Скриване на тъмния фон
//     });
// };
// last
// window.onload = function () {
//     const cookieConsent = document.getElementById('cookieConsent');
//     const cookieBackdrop = document.getElementById('cookieBackdrop');
//     const acceptCookiesBtn = document.getElementById('acceptCookies');
//
//     if (!localStorage.getItem('cookieAccepted')) {
//         cookieConsent.style.display = 'block';
//         cookieBackdrop.style.display = 'block';
//     } else {
//         loadGoogleAnalytics();
//     }
//
//     acceptCookiesBtn.addEventListener('click', function () {
//         localStorage.setItem('cookieAccepted', 'true');
//         cookieConsent.style.display = 'none';
//         cookieBackdrop.style.display = 'none';
//         loadGoogleAnalytics();
//     });
// };

// function loadGoogleAnalytics() {
//     const script = document.createElement('script');
//     script.src = "https://www.googletagmanager.com/gtag/js?id=G-D0MMQ8NDG3"; // <-- Замени с твоя ID
//     script.async = true;
//     document.head.appendChild(script);
//
//     script.onload = () => {
//         window.dataLayer = window.dataLayer || [];
//         function gtag() { dataLayer.push(arguments); }
//         gtag('js', new Date());
//         gtag('config', 'G-D0MMQ8NDG3');
//     };
// }
// /js/cookie.js

function loadGoogleAnalytics() {
    // Зареждаме GA само при пълно съгласие
    if (window.__gaLoaded) return; // защита от двойно зареждане
    const script = document.createElement('script');
    script.src = "https://www.googletagmanager.com/gtag/js?id=G-D0MMQ8NDG3"; // <-- твоят ID
    script.async = true;
    document.head.appendChild(script);

    script.onload = () => {
        window.dataLayer = window.dataLayer || [];
        function gtag(){ dataLayer.push(arguments); }
        gtag('js', new Date());
        gtag('config', 'G-D0MMQ8NDG3');
        window.__gaLoaded = true;
    };
}

document.addEventListener('DOMContentLoaded', () => {
    const box = document.getElementById('cookieConsent');
    const backdrop = document.getElementById('cookieBackdrop');
    const btnAccept = document.getElementById('acceptCookies');
    const btnReject = document.getElementById('rejectCookies');
    const btnClose  = document.querySelector('.cookie-close');

    const openConsent = () => {
        box?.classList.add('show');
        backdrop?.classList.add('show');
    };
    const closeConsent = () => {
        box?.classList.remove('show');
        backdrop?.classList.remove('show');
    };

    // Прочети записа
    const saved = localStorage.getItem('cookieConsent');

    if (!saved) {
        openConsent();
    } else if (saved === 'all') {
        loadGoogleAnalytics();
    }

    btnAccept?.addEventListener('click', () => {
        localStorage.setItem('cookieConsent', 'all');
        loadGoogleAnalytics();
        closeConsent();
    });

    btnReject?.addEventListener('click', () => {
        localStorage.setItem('cookieConsent', 'essential');
        // НЕ зареждаме GA
        closeConsent();
    });

    btnClose?.addEventListener('click', closeConsent);
    backdrop?.addEventListener('click', closeConsent);
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeConsent();
    });
});
