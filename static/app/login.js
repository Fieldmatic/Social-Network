var login = new Vue({
    el: '#login',
    data: {
        user : {}
    },
    methods: {
        login : function() {
            console.log(this.user.username)
            console.log(this.user.password)

            axios.post("/authentication/login", this.user)
                .then(response => {
                    (toast("Successfully logged in!"))
                    console.log(response.data);
                    window.location.href = "/homePage.html";
                }
            ).catch(function error(err) {
                toast(err.response.data);
            });


        }
    }
})