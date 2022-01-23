const Bar = {template : '<bar></bar>'}
const HomePage = {template: '<homePage></homePage>'}
const Login = {template : '<login></login>'}
const Registration = {template: '<registration></registration>'}

const router = new VueRouter({
    mode:'hash',
    routes : [
	 	{path: '/login', component: Login},
        {path: '/registration/:id?', component: Registration},
        {path: '/', component:Bar,
            children:[
	           {
	            path: 'homePage',
	            component: HomePage
	        	}
            ]
          },
    ]
})

var app = new Vue({
    router,
    el:"#socialNetwork"
})