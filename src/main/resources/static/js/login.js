const wrapper = document.querySelector('.wrapper-form');
const signInLink = document.querySelector('.login-link');
const signUpLink = document.querySelector('.signup-link');
const signUpForm = document.getElementById('signUpForm');
const alertPopUp = document.getElementById('registration-success');

signUpLink.addEventListener('click', () => {
    wrapper.classList.add('active');
});

signInLink.addEventListener('click', () => {
    wrapper.classList.remove('active');
});

function showSuccessMsg(msg) {
    alertPopUp.appendChild(document.createTextNode(msg));
    alertPopUp.classList.remove('collapse');
    alertPopUp.classList.add('show');
    alertPopUp.style.visibility = 'visible';
}

function hideAlertPopUp() {
    const childNodes = [];
    childNodes.push(...alertPopUp.childNodes);
    childNodes.filter(n => n.nodeType === Node.TEXT_NODE).forEach(n => alertPopUp.removeChild(n));
    alertPopUp.style.visibility = 'hidden';
}

function onLoginSubmit(token) {
    document.getElementById("loginForm").submit();
}

function onSignUpSubmit(token) {
    const hideAllHints = () => {
        hideAlertPopUp();
        const smalls = document.querySelectorAll('small');
        smalls.forEach(s => {
            s.style.visibility = 'hidden';
            s.innerText = 'Error msg';
        });

        function hideLogInHints() {
            const hints = document.getElementsByClassName('hints')[0];
            hints.style.visibility = 'hidden';
            const children = hints.children;
            for (let i = 0; i < children.length; i ++) {
                children[i].innerText = '';
            }
        }

        hideLogInHints();
    };

    const signUp = async (form) => {
        try {
            const res = await fetch("/signup", {
                method: 'POST',
                body: new FormData(form)
            });
            const data = await res.json();
            const errors = data.errors;
            if (errors) {
                fillInErrors(errors);
            } else {
                signUpForm.reset();
                showSuccessMsg(data.result);
                wrapper.classList.remove('active');
            }
        } catch (error) {

        }
    };

    const fillInErrors = (errorObj) => {
        for (const prop in errorObj) {
            if (Object.prototype.hasOwnProperty.call(errorObj, prop)) {
                const elementById = document.getElementById(prop);
                elementById.innerText = errorObj[prop].msg;
                elementById.style.visibility = 'visible';
            }
        }
    };

    hideAllHints();
    signUp(signUpForm);
}