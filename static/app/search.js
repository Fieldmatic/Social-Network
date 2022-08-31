Vue.component('search', {
    data:function (){
        return {
            users : [],
        }
    },
    template:`
         <div class="d-flex flex-column">
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
                <select id="sortSelect" @change="onChange($event)">
                    <option value="name" selected >Name</option>
                    <option value="surname">Surname</option>
                    <option value="dateAsc">Birthday Ascending</option>
                    <option value="dateDesc">Birthday Descending</option>
                </select>
             </div>
             
             <div v-for="user in users">
                <div class="container-fluid d-inline-flex align-items-center border-bottom mt-3 justify-content-start" @click="$router.push('/' + user.username)">
                <div class="row align-items-center">
                    <div class="col-md-3">
                        <img class="img-fluid rounded-circle my-2" v-bind:src="'user/picture?path=' + user.profilePicture" height="80" width="80"/>
                    </div>
                    <div class="col-md-9">
                        <div class="container text-dark">
                            <div class="row">
                                <span class="fw-bold">{{user.name}} {{user.surname}}</span>
                            </div>
                            <div class="row">
                               <span> Date of Birth: {{user.birthDate.toLocaleDateString("en-GB")}} </span>
                            </div>
                        </div>
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
        }).then((response) => { this.users = response.data; for(let user of this.users) {user.birthDate = new Date(user.birthDate['year'],user.birthDate['month']-1,user.birthDate['day'])}})

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
    }

});