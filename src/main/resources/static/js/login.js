const wrapper = document.querySelector('.wrapper-form');
const signInLink = document.querySelector('.login-link');
const signUpLink = document.querySelector('.signup-link');
const openModalBtns = document.querySelectorAll('[data-modal-target]');
const closeModalBtns = document.querySelectorAll('[data-close-button]');
const overlay = document.getElementById('overlay');
const signUpForm = document.getElementById('signUpForm');

signUpLink.addEventListener('click', () => {
    wrapper.classList.add('active');
});

signInLink.addEventListener('click', () => {
    wrapper.classList.remove('active');
});

openModalBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        const modal = document.querySelector(btn.dataset.modalTarget);
        openModal(modal);
    });
});

closeModalBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        const modal = btn.closest('.modal');
        closeModal(modal);
    });
});

overlay.addEventListener('click', () => {
    const modals = document.querySelectorAll('.modal.active');
    modals.forEach(modal => {
        closeModal(modal);
    });
});

const openModal = (modal) => {
    if (modal == null) {
        return;
    }
    modal.classList.add('active');
    overlay.classList.add('active');
};

const closeModal = (modal) => {
    if (modal == null) {
        return;
    }
    modal.classList.remove('active');
    overlay.classList.remove('active');
};

signUpForm.addEventListener('submit', function (e) {
    e.preventDefault();

    const hideAllHints = () => {
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

    hideAllHints();

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
                const regSuccess = document.getElementsByClassName('registration-success')[0];
                regSuccess.innerText = data.result;
                regSuccess.style.visibility = 'visible';
                wrapper.classList.remove('active');
            }
        } catch (error) {
            // TODO: redirect to custom 500 page
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

    signUp(this);
});


