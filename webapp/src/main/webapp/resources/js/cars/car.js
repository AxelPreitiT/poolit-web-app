const imageFileElement = document.getElementById('image-file');
const imageLabelElement = document.getElementById('image-label');

imageFileElement.addEventListener('change', updateImageDisplay);

function updateImageDisplay() {
  while(imageLabelElement.firstChild) {
    imageLabelElement.removeChild(imageLabelElement.firstChild);
  }

  const curFiles = imageFileElement.files;
  if (curFiles.length === 0) {
    const icon = document.createElement('i');
    icon.classList.add("bi", "bi-car-front-fill", "light-text", "h1");
    imageLabelElement.appendChild(icon);
  } else {
    const imageFile = curFiles[0];
    const image = document.createElement('img');
    image.src = URL.createObjectURL(imageFile);
    imageLabelElement.appendChild(image);

    const iconOverlay = document.createElement('div');
    iconOverlay.classList.add("overlay");
    const icon = document.createElement('i');
    icon.classList.add("icon", "bi", "bi-pencil-fill", "secondary-color", "h1");
    iconOverlay.appendChild(icon);
    imageLabelElement.appendChild(iconOverlay);
  }
}

const carFeatureOptionClass = 'feature-option';
const carFeatureLabel = 'label';
const carFeatureInput = 'input';

const carFeatureLabelElements = document.querySelectorAll('.' + carFeatureOptionClass);
const carFeaturesAvailable = {};
carFeatureLabelElements.forEach((carFeatureLabelElement) => {
  carFeaturesAvailable[carFeatureLabelElement.id] = {
    id: carFeatureLabelElement.id,
    feature: carFeatureLabelElement.id.slice(carFeatureLabel.length + 1),
  };
  carFeaturesAvailable[carFeatureLabelElement.id].inputElement = document.getElementById(carFeatureInput + '-' + carFeaturesAvailable[carFeatureLabelElement.id].feature);

  carFeatureLabelElement.addEventListener('click', () => {
    if (carFeatureLabelElement.classList.contains('active')) {
      carFeatureLabelElement.classList.remove('active');
      carFeaturesAvailable[carFeatureLabelElement.id].inputElement.setAttribute('disabled', 'disabled');
    } else {
      carFeatureLabelElement.classList.add('active');
      carFeaturesAvailable[carFeatureLabelElement.id].inputElement.removeAttribute('disabled');
    }
  });
})
