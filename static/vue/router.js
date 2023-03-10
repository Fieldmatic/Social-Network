const Bar = {template : '<bar></bar>'}
const HomePage = {template: '<homePage></homePage>'}
const Login = {template : '<login></login>'}
const Registration = {template: '<registration></registration>'}
const Post = {template:'<post></post>'}
const Search = {template :'<search></search>'}
const FriendList = {template:'<friendList></friendList>'}
const MutualFriends = {template:'<friendList></friendList>'}
const FriendRequests = {template:'<friendRequests></friendRequests>'}
const MainProfile = {template:'<mainProfile></mainProfile>'}
const UserProfile = {template:'<userProfile></userProfile>'}
const EditProfile = {template:'<editProfile></editProfile>'}
const History = {template:'<history></history>'}

const router = new VueRouter({
    mode:'hash',
    routes : [
        {path : "/", component:Post},
        {path :"/search", component:Search},
        {path :"/friendList", component:FriendList},
        {path :"/friendRequests", component:FriendRequests},
        {path: "/messages",component: History},
        {path :"/profile", component:MainProfile,
             children: [
                 {path : 'posts', component : Post},
                 {path : 'edit', component : EditProfile},
                 {path : 'friends', component : FriendList}
             ]},
        {path :"/:username", component: UserProfile,
            children: [
                {path : 'posts', component : Post},
                {path : 'mutualFriends', component : MutualFriends},
                {path : 'friends', component : FriendList}
            ]},

    ]
})

var app = new Vue({
    router,
    el:"#app"
})
