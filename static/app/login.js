var login = new Vue({
    el: '#login',
    data: {
        user : {
            gender : 'FEMALE',
            privateAccount: false
        },
        registered : true
    },
    methods: {
        login : function() {
            if (this.registered) {
                const that = this;
                console.log(this.user.username)
                if (this.user.username === "" || this.user.password === "") {
                    that.showToast("Username and password fields are required.");
                    return;
                }

                axios.post("/authentication/login", this.user)
                    .then(() => {
                            window.location.href = "/homePage.html";
                        }
                    ).catch(function error(err) {
                        that.showToast(err.response.data);
                });
            } else {
                //birthDate-undefined
                if (this.user.username === "" || this.user.password === "" || !this.user.passwordConfirm || !this.user.name || !this.user.surname || !this.user.email) {
                    this.showToast("All fields are required.");
                    return;
                }
                if (this.user.birthDate === undefined) {
                    this.showToast("You must enter your date of birth.");
                    return;
                }

                if (this.user.password !== this.user.passwordConfirm) {
                    this.showToast('Passwords are not equal.');
                    return;
                }
                if (!this.checkIfAllLetters(this.user.name)) {
                    this.showToast('Name can only contain letters.');
                    return;
                } else if (!this.checkIfAllLetters(this.user.surname)) {
                    this.showToast('Surname can only contain letters.');
                    return;
                } else if (!this.checkIfAllLetters(this.user.surname)) {
                    this.showToast('Surname can only contain letters.');
                    return;
                } else if (!this.checkEmail()) {
                    this.showToast('You have entered an invalid email address!');
                    return;
                }
                let userRegister = {
                    username : this.user.username,
                    password : this.user.password,
                    email : this.user.email,
                    name : this.user.name,
                    surname : this.user.surname,
                    birthDate : this.user.birthDate.replace(/-/g, '.'),
                    gender : this.user.gender,
                    privateAccount : this.user.privateAccount
                }
                const that = this;
                axios.post("/authentication/register", JSON.stringify(userRegister))
                    .then(() => {
                            this.showToast('You have successfully registered.');
                            this.registered = true;
                        }
                    ).catch(function error(err) {
                    that.showToast(err.response.data);
                });
            }
        },
        changeSignUpScreen : function () {
            this.registered = !this.registered
        },
        onChange(event) {
            this.user.gender = event.target.value;
        },
        checkIfAllLetters(field) {
            return /^[a-zA-Z]+$/.test(field)
        },
        checkEmail() {
            return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.user.email)
        },
        showToast : function(message) {
            var toastExample = document.getElementById('liveToast');
            toastExample.innerHTML =
                '                      <div class="d-flex"><div class="toast-body">' + message + '</div><button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button></div>'
            var toast = new bootstrap.Toast(toastExample);
            toast.show();
        }
    }
})