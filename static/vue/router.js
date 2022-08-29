const Bar = {template : '<bar></bar>'}
const HomePage = {template: '<homePage></homePage>'}
const Login = {template : '<login></login>'}
const Registration = {template: '<registration></registration>'}
const Post = {template:'<post></post>'}
const Search = {template :'<search></search>'}
const FriendList = {template:'<friendList></friendList>'}
const FriendRequests = {template:'<friendRequests></friendRequests>'}

const router = new VueRouter({
    mode:'hash',
    routes : [
        {path : "/", component:Post},
        {path :"/search", component:Search},
        {path :"/friendList", component:FriendList},
        {path :"/friendRequests", component:FriendRequests}

    ]
})

var app = new Vue({
    router,
    el:"#app"
})
