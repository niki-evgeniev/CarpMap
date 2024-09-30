// // Проверка дали вече е приета политиката за бисквитките
// window.onload = function() {
//     const cookieConsent = document.getElementById('cookieConsent');
//     const acceptCookiesBtn = document.getElementById('acceptCookies');
//
//     // Проверка за наличие на "бисквитка" или localStorage
//     if (!localStorage.getItem('cookieAccepted')) {
//         cookieConsent.style.display = 'block';
//     }
//
//     // Приемане на "бисквитките"
//     acceptCookiesBtn.addEventListener('click', function() {
//         localStorage.setItem('cookieAccepted', 'true');
//         cookieConsent.style.display = 'none';
//     });
// };
window.onload = function() {
    const cookieConsent = document.getElementById('cookieConsent');
    const cookieBackdrop = document.getElementById('cookieBackdrop');
    const acceptCookiesBtn = document.getElementById('acceptCookies');

    // Показване на pop-up ако не е приета политиката за бисквитки
    if (!localStorage.getItem('cookieAccepted')) {
        cookieConsent.style.display = 'block';
        cookieBackdrop.style.display = 'block';  // Показване на тъмния фон зад pop-up
    }

    // Приемане на бисквитките
    acceptCookiesBtn.addEventListener('click', function() {
        localStorage.setItem('cookieAccepted', 'true');
        cookieConsent.style.display = 'none';
        cookieBackdrop.style.display = 'none';  // Скриване на тъмния фон
    });
};
