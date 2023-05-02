const input = document.querySelector('input');
const preview = document.querySelector('.preview');

document.getElementById('imageFile').style.opacity=0;


document.getElementById('imageFile').addEventListener('change', updateImageDisplay);

function updateImageDisplay() {
  while(preview.firstChild) {
    preview.removeChild(preview.firstChild);
  }

  const curFiles = input.files;
  if (curFiles.length === 0) {
    const para = document.createElement('p');
    para.textContent = 'No files currently selected for upload';
    preview.appendChild(para);
  } else {
    const list = document.createElement('div');
    preview.appendChild(list);

    for (const file of curFiles) {
      const listItem = document.createElement('div');
      const para = document.createElement('p');

      const image = document.createElement('img');
      image.src = URL.createObjectURL(file);
      listItem.appendChild(image);
      listItem.appendChild(para);


      list.appendChild(listItem);
    }
  }
}
