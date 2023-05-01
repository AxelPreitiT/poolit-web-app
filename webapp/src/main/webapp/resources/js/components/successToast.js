
const successToastElement = document.getElementById('success-toast');
const successToast = bootstrap.Toast.getOrCreateInstance(successToastElement);

const hideToast = () => {
    setTimeout(() => {
        successToast.hide();
    }, 3000);
}

const showToast = () => {
    setTimeout(() => {
        successToast.show();
        hideToast();
    }, 200);
}

showToast();
