

document.querySelectorAll('.show-more-btn').forEach(button => {
  // showNum-agregado por click/cant inicial
  const showNum = 3;
  let index = showNum;
  const container = button.closest('.reviews-container');
  const reviews = container.querySelectorAll('.revs');
  for (let i = 0; i < reviews.length; i++) {
    if (i < showNum) {
      reviews[i].style.display = 'block';
    } else {
      reviews[i].style.display = 'none';
    }
  }

  if (reviews.length <= showNum) {
    button.style.display = 'none';
  }

  button.addEventListener('click', function() {
    index += showNum;
    for (let i = 0; i < reviews.length; i++) {
      if (i < index) {
        reviews[i].style.display = 'block';
      } else {
        reviews[i].style.display = 'none';
      }
    }
    if (index >= reviews.length) {
      button.style.display = 'none';
    }
  });
});
