const navItems = document.querySelectorAll('.nav-item');
const createTripButton = document.getElementById('create-trip');

for (const navItem of navItems) {
    const navItemAnchor = navItem.getElementsByTagName('a').item(0);
    const pathRoutes = window.location.pathname.split('/').slice(1);
    if(pathRoutes.includes(navItemAnchor.getAttribute('href').slice(1))) {
        navItemAnchor.classList.add('active');
    }
}
