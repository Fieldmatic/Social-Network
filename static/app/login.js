var login = new Vue({
    el: '#login',
    data: {
        user : {},
        registered : true
    },
    methods: {
        login : function() {
            console.log(this.user)
            console.log(this.registered)
            if (this.registered) {
                axios.post("/authentication/login", this.user)
                    .then(response => {
                            (toast("Successfully logged in!"))
                            console.log(response.data);
                            window.location.href = "/homePage.html";
                        }
                    ).catch(function error(err) {
                    toast(err.response.data);
                });
            } else {
                let userRegister = {
                    username : this.user.username,
                    password : this.user.password,
                    email : this.user.email,
                    name : this.user.name,
                    surname : this.user.surname,
                    birthDate : this.user.birthDate.replace(/-/g, '.'),
                    gender : this.user.gender,
                    privateAccount : true
                }
                axios.post("/authentication/register", JSON.stringify(userRegister))
                    .then(response => {
                            (toast("Successfully registered!"))
                            console.log(response.data);
                            window.location.href = "/homePage.html";
                        }
                    ).catch(function error(err) {
                    toast(err.response.data);
                });
            }
        },
        changeSignUpScreen : function () {
            this.registered = !this.registered
        },
        onChange(event) {
            var optionText = event.target.value;
            console.log(optionText);
            this.user.gender = optionText
        }
    }
})