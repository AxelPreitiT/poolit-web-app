
const reportListModalElement = document.getElementById("report-list-modal");
const reportPublishedContainerElement = document.getElementById("report-published-container");

if(reportListModalElement.classList.contains("show-on-load")) {
    new bootstrap.Modal(reportListModalElement).show();
}

if(reportPublishedContainerElement.classList.contains("show")) {
    setTimeout(() => {
        new bootstrap.Collapse(reportPublishedContainerElement).hide();
    }, 2000);
}
