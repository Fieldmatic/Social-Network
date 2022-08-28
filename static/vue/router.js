const Bar = {template : '<bar></bar>'}
const HomePage = {template: '<homePage></homePage>'}
const Login = {template : '<login></login>'}
const Registration = {template: '<registration></registration>'}
const Post = {template:'<post></post>'}

const router = new VueRouter({
    mode:'hash',
    routes : [
        {path : "/", component:Post}

    ]
})

var app = new Vue({
    router,
    el:"#app"
})
