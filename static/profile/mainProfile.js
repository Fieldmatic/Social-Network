Vue.component('mainProfile',{
    data:function (){
      return {
          user:null
      }
    },
    template:
        `
        <div class="d-flex flex-column">
            <div class="w-75 align-items-center border-bottom mt-3 mx-auto">
                        <div v-if="user!==null" class="row container-fluid align-items-center mb-2">
                            <div class="col-md-2">
                                <img class="img-fluid rounded-circle my-2 float-end" v-bind:src="'user/picture?path=' + user.profilePicture" height="168" width="168"/>
                            </div>
                            <div class="col-md-6">
                                    <div class="container text-dark align-items-center">
                                        <div class="row">
                                            <h2 class="fw-bold">{{user.name}} {{user.surname}}</h2>
                                        </div>
                                        <div class="row mt-2">
                                           <span> Date of birth: {{user.birthDate.toLocaleDateString("en-GB")}}</span>
                                        </div>
                                    </div>
                            </div>
                       </div>
                            
            </div>
            <div class = "w-50 align-items-center justify-content-center mx-auto mt-2 ps-5 border-bottom">
                <div class="row w-100 mx-auto justify-content-center p-1">
                     <div class="col-md-4" @click="$router.push('/profile/posts')">
                        <span>Posts</span>
                     </div>
                     <div class="col-md-4" @click="$router.push('/profile/posts')">
                         <span>Edit profile</span>
                     </div>
                     <div class="col-md-4" @click="$router.push('/profile/friends')">
                        <span>Friends</span>
                     </div>
                </div>              
            </div>
            
            <div class="row w-50 mt-2 mx-auto">
                <router-view></router-view>
            </div>
            
       </div>      
    
    
        `,
    mounted:function () {
        axios.get("/user/loggedUser").then((response) => {this.user = response.data; this.user.birthDate = new Date( this.user.birthDate['year'], this.user.birthDate['month']-1, this.user.birthDate['day'])})

    }

})