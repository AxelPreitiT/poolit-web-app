let editing = false;

function toggleEdit() {
  const noEditElements = document.querySelectorAll(".no-edit");
  const editElements = document.querySelectorAll(".edit");

  // Ocultar elementos con id="no-edit"
  for (let i = 0; i < noEditElements.length; i++) {
    if (!editing) {
      noEditElements[i].classList.add("hidden");
    } else {
      noEditElements[i].classList.remove("hidden");
    }
  }

  // Mostrar elementos con id="edit"
  for (let i = 0; i < editElements.length; i++) {
    if (!editing) {
      editElements[i].classList.remove("hidden");
    } else {
      editElements[i].classList.add("hidden");
    }
  }
  editing = !editing;
}

const imageFileElement = document.getElementById('image-file');
const imageLabelElement = document.getElementById('image-label');

imageFileElement.addEventListener('change', updateImageDisplay);

function updateImageDisplay() {
  while(imageLabelElement.firstChild) {
    imageLabelElement.removeChild(imageLabelElement.firstChild);
  }

  const curFiles = imageFileElement.files;
  if (curFiles.length === 0) {
    const iconOverlay = document.createElement('div');
    iconOverlay.classList.add("overlay");
    const icon = document.createElement('i');
    icon.classList.add("icon","bi", "bi-pencil-fill", "secondary-color", "h1");
    imageLabelElement.appendChild(icon);
    imageLabelElement.appendChild(iconOverlay);
  } else {
    const imageFile = curFiles[0];
    const imageDiv = document.createElement('div');
    imageDiv.classList.add("circular--landscape");
    const image = document.createElement('img');
    image.classList.add("circular--square")
    image.src = URL.createObjectURL(imageFile);
    imageDiv.appendChild(image);
    imageLabelElement.appendChild(imageDiv);

    const iconOverlay = document.createElement('div');
    iconOverlay.classList.add("overlay");
    const icon = document.createElement('i');
    icon.classList.add("icon", "bi", "bi-pencil-fill", "secondary-color", "h1");
    iconOverlay.appendChild(icon);
    imageLabelElement.appendChild(iconOverlay);
  }
}

const featureOptions = document.querySelectorAll('.feature-option');
const features = {};

featureOptions.forEach(featureOption => {
  features[featureOption.id] = {
    id: featureOption.id,
    feature: featureOption.id.split('-')[1],
  };
  features[featureOption.id].inputElement = document.getElementById('input-' + features[featureOption.id].feature);


  featureOption.addEventListener('click', () => {
    if(featureOption.classList.contains('active')) {
        featureOption.classList.remove('active');
        features[featureOption.id].inputElement.setAttribute('disabled', 'disabled');
    } else {
        featureOption.classList.add('active');
        features[featureOption.id].inputElement.removeAttribute('disabled');
    }
  });
});
