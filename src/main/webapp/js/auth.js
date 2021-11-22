document.getElementById('pw2').addEventListener('input', checkConfirmationPsw, false);
document.getElementById('pw1').addEventListener('input', checkConfirmationPsw, false);

function checkConfirmationPsw(argument) {
    const psw = document.getElementById("pw1").value;
    const confPsw = document.getElementById("pw2").value;
    const errMsg = document.getElementById("error");
    const inputBtn = document.getElementById('inputBtn');
    if (psw !== confPsw) {
        errMsg.style.display = 'block';
        inputBtn.disabled = true;
    } else {
        errMsg.style.display = 'none';
        inputBtn.disabled = false;
    }
}

function validateForm() {
    var x = document.forms["auth"]["fname"].value;
    if (x == "") {
        alert("Name must be filled out");
        return false;
    }
}