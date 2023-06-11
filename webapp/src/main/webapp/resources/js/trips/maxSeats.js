document.getElementById('car-select').addEventListener('change', function() {

    var selectedOption = this.options[this.selectedIndex];
    var seats = selectedOption.getAttribute('itemattributes');


    document.getElementById('seats').value = seats;
});