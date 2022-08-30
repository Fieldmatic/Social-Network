Vue.component('friendList',{
    data:function (){
        return {
            friends:[]
        }
    },
    template:
        `
         <div class="d-flex flex-column">
             <div v-if="friends.length==0" class="w-auto mx-auto mt-3">
                <h2> No friends to show yet.</h2>
             </div>
             <div v-for="user in friends">
                    <div class="w-100 d-inline-flex align-items-center border-bottom mt-3" @click="$router.push('/' + user.username)">
                        <div class="row w-100 mx-auto align-items-center">
                            
                            <div class="col-md-2">
                                <img class="img-fluid rounded-circle my-2 float-end" v-bind:src="'user/picture?path=' + user.profilePicture" height="80" width="80"/>
                            </div>
                            <div class="col-md-6">
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
    mounted:function (){
        let username = this.$route.params.username;
        if (username) axios.get("/user/friends",{params:{'username':username}}).then((response) => {this.friends = response.data;for(let user of this.friends) {user.birthDate = new Date(user.birthDate['year'],user.birthDate['month']-1,user.birthDate['day'])}})
        else axios.get("/user/loggedUserFriends").then((response) => {this.friends = response.data;for(let user of this.friends) {user.birthDate = new Date(user.birthDate['year'],user.birthDate['month']-1,user.birthDate['day'])}})
    }
})