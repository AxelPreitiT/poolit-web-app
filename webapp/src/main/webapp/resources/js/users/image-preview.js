const inputElement = document.getElementById('image-file');
const labelElement = document.getElementById("image-label");
const preview = document.querySelector('.preview');

inputElement.addEventListener('change', updateImageDisplay);

function updateImageDisplay() {
  while (labelElement.firstChild) {
    labelElement.removeChild(labelElement.firstChild);
  }

  const curFiles = inputElement.files;
  if (curFiles.length === 0) {
    const icon = document.createElement('i');
    icon.classList.add("bi", "bi-image", "light-text", "h1");
    labelElement.appendChild(icon);
  } else {
    const imageFile = curFiles[0];
    const image = document.createElement('img');
    image.src = URL.createObjectURL(imageFile);
    labelElement.appendChild(image);

    const iconOverlay = document.createElement('div');
    iconOverlay.classList.add("overlay");
    const icon = document.createElement('i');
    icon.classList.add("icon", "bi", "bi-pencil-fill", "secondary-color", "h1");
    iconOverlay.appendChild(icon);
    labelElement.appendChild(iconOverlay);
  }
}
