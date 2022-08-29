Vue.component('friendList',{
    data:function (){
        return {
            friends:[]
        }
    },
    template:
        `
         <div class="d-flex flex-column">
             <div v-for="user in friends">
                    <div class="container-fluid d-inline-flex align-items-center border-bottom mt-3 justify-content-start">
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
    mounted:function (){
        axios.get("/user/friends").then((response) => {this.friends = response.data;for(let user of this.friends) {user.birthDate = new Date(user.birthDate['year'],user.birthDate['month'],user.birthDate['day'])}})
    }
})