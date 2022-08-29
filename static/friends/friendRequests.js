Vue.component('friendRequests',{
    data:function (){
        return {
            requests:[]
        }
    },
    template:
        `
         <div class="d-flex flex-column">
             <div class="position-absolute mt-2 start-50 translate-middle-x p-3" style="z-index: 11">
                  <div id="liveToast" class="toast hide align-items-center text-white bg-primary border-0" role="alert" aria-live="assertive" aria-atomic="true"></div>
             </div>
             <div v-for="user in requests">
                    <div class="container-fluid d-inline-flex align-items-center border-bottom mt-3 justify-content-between">
                        <div class="row align-items-center d-inline-flex justify-content-between">
                            <div class="col-md-3">
                                <img class="img-fluid rounded-circle my-2" v-bind:src="'user/picture?path=' + user.profilePicture" height="80" width="80"/>
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
                            <div class="col-md-3 d-inline-flex justify-content-between">
                                <button type="button" class="btn btn-success me-3" @click="acceptRequest(user)">Accept</button>
                                <button type="button" class="btn btn-danger" @click="rejectRequest(user)">Reject</button>
                            </div>
                        </div>
                    </div>      
             </div>
         </div>
        
        `,
    mounted:function (){
        axios.get("/user/friendRequests").then((response) => {this.requests = response.data;for(let user of this.requests) {user.birthDate = new Date(user.birthDate['year'],user.birthDate['month'],user.birthDate['day'])}})
    },
    methods:{
        acceptRequest(user){
            axios.post("/user/acceptRequest",{},{
                params:{
                    "sender":user.username
                }
            }).then(async (response) => {
                this.showToast("You are now friends with " + user.name + " " + user.surname)
                await new Promise(resolve => setTimeout(resolve, 2000));
                window.location.reload();
            })

        },
        rejectRequest(user){
            axios.post("/user/rejectRequest",{},{
                params:{
                    "sender":user.username
                }
            }).then(async (response) => {
                this.showToast("You declined friend request from " + user.name + " " + user.surname)
                await new Promise(resolve => setTimeout(resolve, 2000));
                window.location.reload();
            })

        },
        showToast : function(message) {
            let toastExample = document.getElementById('liveToast');
            toastExample.innerHTML =
                '                      <div class="d-flex"><div class="toast-body">' + message + '</div><button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button></div>'
            let toast = new bootstrap.Toast(toastExample);
            toast.show();
        }

    }
})