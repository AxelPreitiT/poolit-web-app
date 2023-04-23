const navItems = document.querySelectorAll('.nav-item');
const createTripButton = document.getElementById('create-trip');

for (const navItem of navItems) {
    const navItemAnchor = navItem.getElementsByTagName('a').item(0);
    if(window.location.pathname === navItemAnchor.getAttribute('href')) {
        navItemAnchor.classList.add('active');
    }
}