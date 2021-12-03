Date.prototype.toDateInputValue = (function () {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0, 10);
});

$(document).ready(function () {
    $('#datePicker').val(new Date().toDateInputValue());
});

document.getElementById('datePicker').value = new Date().toDateInputValue();
