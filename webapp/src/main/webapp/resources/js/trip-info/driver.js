const showPassengersListButtonElement = document.getElementById('show-passengers-list-button');
const passengersListContainerElement = document.getElementById('passengers-list-container');
const passengersListTitleElement = document.getElementById('passengers-list-title');

const isInViewport = (elem) => {
    const distance = elem.getBoundingClientRect();
    return (
        distance.top >= 0 &&
        distance.left >= 0 &&
        distance.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        distance.right <= (window.innerWidth || document.documentElement.clientWidth)
    );
};

const showPassengersList = () => {
    if (isInViewport(passengersListTitleElement)) {
        showPassengersListButtonElement.classList.add('hidden');
    }
}

document.onreadystatechange = function () {
    if (document.readyState === "complete") {
        setTimeout(() => {
            if (!isInViewport(passengersListTitleElement)) {
                showPassengersListButtonElement.classList.remove('hidden');
            }
        }, 1000);
    }
}

showPassengersListButtonElement.addEventListener('click', () => {
    passengersListTitleElement.scrollIntoView({behavior: "smooth"});
});

window.addEventListener('scroll', showPassengersList);


