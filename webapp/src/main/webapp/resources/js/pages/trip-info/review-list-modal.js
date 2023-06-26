
const reviewListModalElement = document.getElementById("review-list-modal");
const reviewPublishedContainerElement = document.getElementById("review-published-container");
const errorReviewContainerElement = document.getElementById("review-published-container-error");

if(reviewListModalElement.classList.contains("show-on-load")) {
    new bootstrap.Modal(reviewListModalElement).show();
}

if(reviewPublishedContainerElement.classList.contains("show")) {
    setTimeout(() => {
        new bootstrap.Collapse(reviewPublishedContainerElement).hide();
    }, 2000);
}

if(errorReviewContainerElement.classList.contains("show")) {
    setTimeout(() => {
        new bootstrap.Collapse(errorReviewContainerElement).hide();
    }, 2000);
}