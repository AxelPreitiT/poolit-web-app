
const queryParams = new URLSearchParams(window.location.search);

const timeTabsRowElement = document.getElementById('time-tabs-row');
const timeTabs = timeTabsRowElement.querySelectorAll('.nav-link');
const defaultTimeTabElement = document.getElementById('default-time-tab');

if(queryParams.has('time')) {
    const timeTabsArray = Array.from(timeTabs);
    const timeTabsQueryParamValue = timeTabsArray.map((timeTab) => new URLSearchParams(timeTab.getAttribute('href').split('?')[1]).get('time'));
    const timeTabsQueryParamIndex = timeTabsQueryParamValue.indexOf(queryParams.get('time'));
    if(timeTabsQueryParamIndex === -1) {
        defaultTimeTabElement.classList.add('active');
    } else {
        timeTabs[timeTabsQueryParamIndex].classList.add('active');
    }
} else {
    defaultTimeTabElement.classList.add('active');
}

