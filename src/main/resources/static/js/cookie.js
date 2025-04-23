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
window.onload = function () {
    const cookieConsent = document.getElementById('cookieConsent');
    const cookieBackdrop = document.getElementById('cookieBackdrop');
    const acceptCookiesBtn = document.getElementById('acceptCookies');

    if (!localStorage.getItem('cookieAccepted')) {
        cookieConsent.style.display = 'block';
        cookieBackdrop.style.display = 'block';
    } else {
        loadGoogleAnalytics();
    }

    acceptCookiesBtn.addEventListener('click', function () {
        localStorage.setItem('cookieAccepted', 'true');
        cookieConsent.style.display = 'none';
        cookieBackdrop.style.display = 'none';
        loadGoogleAnalytics();
    });
};

function loadGoogleAnalytics() {
    const script = document.createElement('script');
    script.src = "https://www.googletagmanager.com/gtag/js?id=G-D0MMQ8NDG3"; // <-- Замени с твоя ID
    script.async = true;
    document.head.appendChild(script);

    script.onload = () => {
        window.dataLayer = window.dataLayer || [];
        function gtag() { dataLayer.push(arguments); }
        gtag('js', new Date());
        gtag('config', 'G-D0MMQ8NDG3');
    };
}

