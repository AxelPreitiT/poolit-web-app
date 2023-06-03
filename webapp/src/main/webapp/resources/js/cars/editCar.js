function toggleEdit() {
  var noEditElements = document.querySelectorAll(".no-edit");
  var editElements = document.querySelectorAll(".edit");

  // Ocultar elementos con id="no-edit"
  for (var i = 0; i < noEditElements.length; i++) {
    noEditElements[i].classList.add("hidden");
  }

  // Mostrar elementos con id="edit"
  for (var i = 0; i < editElements.length; i++) {
    editElements[i].classList.remove("hidden");
  }
}
