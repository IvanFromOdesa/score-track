const otpForm = document.getElementById('otpForm');
const otps = document.querySelectorAll('.otp');
const btnSubmit = document.getElementsByClassName('baseBtn')[0];

otps[0].focus();

otps.forEach((otp, idx) => {
    otp.addEventListener('keydown', e => {
        if (e.key >= 0 && e.key <= 9) {
            otps[idx].value = '';
            if (idx !== 5) {
                setTimeout(() => otps[idx + 1].focus(), 10);
            }
        } else if (e.key === 'Backspace' && idx !== 5) {
            setTimeout(() => otps[idx - 1].focus(), 10);
        } else if (["e", "E", "+", "-"].includes(e.key)) {
            e.preventDefault();
        }
    });
});

btnSubmit.addEventListener('mouseover', e => {
    if (Array.from(otps).map(o => o.value).join('').length === 6) {
        btnSubmit.disabled = false;
        btnSubmit.classList.add('enable');
    } else {
        btnSubmit.disabled = true;
        btnSubmit.classList.remove('enable');
    }
});

otpForm.addEventListener('submit', function (e) {
    e.preventDefault();
    document.getElementById('inputOtp').value = Array.from(otps).map(o => o.value).join('');
    otpForm.submit();
});


