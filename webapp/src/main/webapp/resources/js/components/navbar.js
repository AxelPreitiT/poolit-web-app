const navItems = document.querySelectorAll('.nav-item');
const createTripButton = document.getElementById('create-trip');

for (const navItem of navItems) {
    const navItemAnchor = navItem.getElementsByTagName('a').item(0);
    const navItemPathRoutes = navItemAnchor.getAttribute('href').split('/').slice(1);
    const pathRoutes = window.location.pathname.split('/').slice(1);
    if(navItemPathRoutes.every((value) => pathRoutes.includes(value))) {
        navItemAnchor.classList.add('active');
    }
}
