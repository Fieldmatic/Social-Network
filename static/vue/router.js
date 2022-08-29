const Bar = {template : '<bar></bar>'}
const HomePage = {template: '<homePage></homePage>'}
const Login = {template : '<login></login>'}
const Registration = {template: '<registration></registration>'}
const Post = {template:'<post></post>'}
const Search = {template :'<search></search>'}

const router = new VueRouter({
    mode:'hash',
    routes : [
        {path : "/", component:Post},
        {path :"/search", component:Search}

    ]
})

var app = new Vue({
    router,
    el:"#app"
})
