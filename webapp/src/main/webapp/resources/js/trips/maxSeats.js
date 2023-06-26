document.getElementById('car-select').addEventListener('change', function() {

    var selectedOption = this.options[this.selectedIndex];
    var seats = selectedOption.getAttribute('itemattributes');
    var seatsInputDiv = document.getElementById('seatsInput');

    var seatsValue = document.getElementById('seats').value;



    seatsInputDiv.classList.remove("hidden");
    document.getElementById('seats').value = seats;

});