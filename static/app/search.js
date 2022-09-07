Vue.component('search', {
    data:function (){
        return {
            users : [],
            loggedUser : ""
        }
    },
    template:`
         <div class="d-flex flex-column">
             <div class="position-absolute mt-2 start-50 translate-middle-x p-3" style="z-index: 11">
                  <div id="liveToast" class="toast hide align-items-center text-white bg-primary border-0" role="alert" aria-live="assertive" aria-atomic="true"></div>
             </div>
             <div class='container-fluid ms-2'>
                <form id="search" class="mt-3" v-on:submit="searchUsers()">
                    <a>Name:</a> 
                    <input type="text" id="name"/>
                    <a>Surname:</a> 
                    <input type="text" id="surname"/>
                    <a>Birthday start:</a> 
                    <input type="date" id="startDate"/>
                    <a>Birthday end:</a> 
                    <input type="date" id="endDate"/>
                    <input type="submit" value="Search" class="bg-primary text-white border-white ms-3">
                    
                </form>
             </div>
             
             <div class='container-fluid ms-2 mt-3'>
                <a>Sort by:</a>
                <select v-if="users.length" id="sortSelect" @change="onChange($event)">
                    <option value="name" selected>Name</option>
                    <option value="surname">Surname</option>
                    <option value="dateAsc">Birthday Ascending</option>
                    <option value="dateDesc">Birthday Descending</option>
                </select>
             </div>
             
             <div v-for="user in users" :key="user.username">
                <div class="container-fluid d-inline-flex align-items-center border-bottom mt-3 justify-content-start" @click="$router.push('/' + user.username + '/posts')"  style="cursor: pointer;">
                <div class="row w-75 align-items-center">
                    <div class="col-md-1">
                        <img class="img-fluid rounded-circle my-2" v-bind:src="'user/picture?path=' + user.profilePicture" height="80" width="80"/>
                    </div>
                    <div class="col-md-3">
                        <div class="container text-dark">
                            <div class="row">
                                <span class="fw-bold">{{user.name}} {{user.surname}}</span>
                            </div>
                            <div class="row">
                               <span> Date of Birth: {{user.birthDate.toLocaleDateString("en-GB")}} </span>
                            </div>
                        </div>
                    </div>
                    <div v-if="loggedUser.role=='ADMIN'" class="col-md-2">
                        <button v-if="user.blocked" class="btn w-75 btn-success align-items-center" @click="unblock(user)">
                            <img class= "" src = "https://static.xx.fbcdn.net/rsrc.php/v3/yK/r/r2FA830xjtI.png?_nc_eui2=AeFEBkghhyzhzWa4KtTd_MlRLvJBHXhZHNwu8kEdeFkc3HT-xARCHyLzi1RzkbIyTXnTxi2poERtBPH94M3jWMwX" width="16" height="16"/>
                            <span>Unblock</span>
                            </button>
                        <button v-else class="btn w-75 btn-danger align-items-center" @click="block(user)">
                            <img class= "" src = "https://static.xx.fbcdn.net/rsrc.php/v3/yK/r/r2FA830xjtI.png?_nc_eui2=AeFEBkghhyzhzWa4KtTd_MlRLvJBHXhZHNwu8kEdeFkc3HT-xARCHyLzi1RzkbIyTXnTxi2poERtBPH94M3jWMwX" width="16" height="16"/>
                            <span>Block</span>
                        </button>
                    </div>
                    
                </div>
                </div>      
             </div>
         </div>
             
             
    `,
    mounted:function () {
        axios.get("/user/search",{
            params:{
                'name':"",
                'surname':"",
                'startDate':"",
                'endDate':""
            }
        }).then((response) => { this.users = response.data;
                                for(let user of this.users)
                                 {
                                    user.birthDate = new Date(user.birthDate['year'],user.birthDate['month']-1,user.birthDate['day'])
                                 }
                                 let event = {target:{value:"name"}};
                                 this.onChange(event);
                             })
        axios.get("/user/loggedUser").then((response) => {
                                                          this.loggedUser = response.data;
                                                         })
    },
    methods: {
        searchUsers() {
            axios.get("/user/search", {
                params: {
                    'name': document.querySelector('#name').value,
                    'surname': document.querySelector('#surname').value,
                    'startDate': document.querySelector('#startDate').value,
                    'endDate': document.querySelector('#endDate').value
                }
            }).then((response) => {
                this.users = response.data;
                for (let user of this.users) {
                    user.birthDate = new Date(user.birthDate['year'], user.birthDate['month']-1, user.birthDate['day'],)
                }
            })
        },
        onChange(event) {
            if (event.target.value === 'name') this.users.sort((s1,s2) => s1.name.localeCompare(s2.name))
            else if (event.target.value === 'surname') this.users.sort((s1,s2) => s1.surname.localeCompare(s2.surname))
            else if (event.target.value === 'dateAsc') this.users.sort((s1,s2) => Number(s1.birthDate) -Number(s2.birthDate))
            else this.users.sort((s1,s2) => Number(s2.birthDate) -Number(s1.birthDate))
        },
        block(user) {
            event.stopPropagation();
            axios.post("user/block",{},{params:{"username":user.username}}).then((response) => {this.showToast(response.data); user.blocked = true})
        },
        unblock(user){
            event.stopPropagation();
            axios.post("user/unblock",{},{params:{"username":user.username}}).then((response) => {this.showToast(response.data); user.blocked = false})
        },
        showToast : function(message) {
            let toastExample = document.getElementById('liveToast');
            toastExample.innerHTML =
                '                      <div class="d-flex"><div class="toast-body">' + message + '</div><button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button></div>'
            let toast = new bootstrap.Toast(toastExample);
            toast.show();
        }
    }

});