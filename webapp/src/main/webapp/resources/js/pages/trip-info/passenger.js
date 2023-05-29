
const modalReviewComponent = document.getElementById("modal-review");

if(modalReviewComponent.classList.contains("show-on-load")) {
    new bootstrap.Modal(modalReviewComponent).show();
}
