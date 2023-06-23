
const rejectReportModalElement = document.getElementById("reject-modal");
const approveReportModalElement = document.getElementById("approve-modal");

if(rejectReportModalElement.classList.contains("show-on-load")) {
    new bootstrap.Modal(rejectReportModalElement).show();
}

if(approveReportModalElement.classList.contains("show-on-load")) {
    new bootstrap.Modal(approveReportModalElement).show();
}
