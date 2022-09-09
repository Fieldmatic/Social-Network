Vue.component('userProfile',{
    data:function (){
        return {
            user:null,
            loggedUser:null,
            isFriend:false,
            sentRequestExists:false,
            receivedRequestExists:false,
            statusChanged:false
        }
    },
    template:
        `
        <div class="d-flex flex-column">
            <div class="w-75 align-items-center border-bottom mt-3 mx-auto">
                        <div v-if="user!==null" :key="user.username" class="row container-fluid align-items-center mb-2">
                            <div class="col-md-2">
                                <img id = "profilePic" class="img-fluid rounded-circle my-2 float-end" v-bind:src="'user/picture?path=' + user.profilePicture" height="168" width="168" alt="profilePicture"/>
                            </div>       
                            <div class="col-md-6">
                                    <div class="container text-dark align-items-center">
                                        <div class="row">
                                            <h2 class="fw-bold">{{user.name}} {{user.surname}}</h2>
                                        </div>
                                        <div class="row">
                                           <span> Date of birth: {{user.birthDate.toLocaleDateString("en-GB")}}</span>
                                        </div>
                                        <div class="row w-50 mt-2 ms-1 d-flex align-items-center" :key="statusChanged">
                                            <button v-if="sentRequestExists" class="btn w-auto btn-secondary align-items-center">
                                                <img class= "" src = "https://static.xx.fbcdn.net/rsrc.php/v3/yK/r/r2FA830xjtI.png?_nc_eui2=AeFEBkghhyzhzWa4KtTd_MlRLvJBHXhZHNwu8kEdeFkc3HT-xARCHyLzi1RzkbIyTXnTxi2poERtBPH94M3jWMwX" width="16" height="16"/>
                                                <span>Request sent</span>
                                            </button>
                                            <button v-else-if="receivedRequestExists" class="btn w-auto btn-warning align-items-center">
                                                <img class= "" src = "https://static.xx.fbcdn.net/rsrc.php/v3/yK/r/r2FA830xjtI.png?_nc_eui2=AeFEBkghhyzhzWa4KtTd_MlRLvJBHXhZHNwu8kEdeFkc3HT-xARCHyLzi1RzkbIyTXnTxi2poERtBPH94M3jWMwX" width="16" height="16"/>
                                                <span>Waiting for your response</span>
                                            </button>
                                            <button v-else-if="!isFriend" class="btn w-auto btn-primary align-items-center" @click="sendRequest()">
                                                <img class= "" src = "https://static.xx.fbcdn.net/rsrc.php/v3/yK/r/r2FA830xjtI.png?_nc_eui2=AeFEBkghhyzhzWa4KtTd_MlRLvJBHXhZHNwu8kEdeFkc3HT-xARCHyLzi1RzkbIyTXnTxi2poERtBPH94M3jWMwX" width="16" height="16"/>
                                                <span>Add Friend</span>
                                            </button>
                                            <button v-else class="btn w-auto btn-danger align-items-center" @click="stopFriendship()">
                                                <img class= "" src = "https://static.xx.fbcdn.net/rsrc.php/v3/yK/r/r2FA830xjtI.png?_nc_eui2=AeFEBkghhyzhzWa4KtTd_MlRLvJBHXhZHNwu8kEdeFkc3HT-xARCHyLzi1RzkbIyTXnTxi2poERtBPH94M3jWMwX" width="16" height="16"/>
                                                <span>Remove Friend</span>
                                            </button>
                                        </div>
                                        <div v-if="isFriend || !user.privateAccount" class="row w-50 mt-2 ms-1 d-flex align-items-center">
                                            <button class="btn w-auto btn-secondary align-items-center" @click="openChat()">
                                               <span>Open chat</span>
                                            </button>
                                        </div>
                                        
                                    </div>
                            </div>
                       </div>
                            
            </div>
            <div class = "w-50 align-items-center justify-content-center mx-auto mt-2 ps-5 border-bottom">
                <div class="row w-100 mx-auto justify-content-center p-1">
                     <div class="col-md-4" @click="$router.push('/' + user.username + '/posts')" style="cursor: pointer;">
                        <span>Posts</span>
                     </div>
                     <div class="col-md-4" @click="$router.push('/' + user.username + '/mutualFriends')" style="cursor: pointer;">
                         <span>Mutual friends</span>
                     </div>
                     <div class="col-md-4" @click="$router.push('/' + user.username + '/friends')" style="cursor: pointer;">
                        <span>Friends</span>
                     </div>
                </div>              
            </div>
            
            <div v-if = "user" class="row w-50 mt-2 mx-auto d-inline-flex">
                <router-view v-if = "this.$route.matched.some(route => route.path.includes('/friends')) && ((isFriend) || (!user.privateAccount))"></router-view>
                <router-view v-else-if = "this.$route.matched.some(route => route.path.includes('/mutualFriends'))"></router-view>
                <router-view v-else-if = "this.$route.matched.some(route => route.path.includes('/posts')) && ((isFriend) || (!user.privateAccount))"></router-view>
                <div v-else class="mx-auto w-50 d-inline-flex justify-content-center mt-3">
                    <h4> This account is private.</h4>
                </div>
                
            </div>
            
       </div>      
    
    
        `,
    mounted:function () {
        this.load()
    },
    methods:{
        sendRequest(){
            axios.post("/user/createFriendRequest",{},{params:{"receiver":this.user.username}}).then(() => {this.load()})

        },

        stopFriendship(){
            axios.post("/user/stopFriendship",{},{params:{"friend":this.user.username}}).then((response) => {this.isFriend = false; this.load()})
        },
        openChat(){
            this.$root.$emit('openChat',this.user);
        },
        load(){
            let username = this.$route.params.username;
            axios.get("/user/loggedUser").then((response) => {this.loggedUser = response.data}).then (() => {
                axios.get("/user/data",
                    {
                        params:{"username":username}
                    }).then((response) => {this.user = response.data; this.user.birthDate = new Date( this.user.birthDate['year'], this.user.birthDate['month']-1, this.user.birthDate['day'])}).then (() =>
                {
                    axios.get("/user/requestExists",{params:{"sender":this.loggedUser.username,"receiver":this.user.username}}).then((response) => {this.sentRequestExists = response.data;})
                    axios.get("/user/requestExists",{params:{"sender":this.user.username,"receiver":this.loggedUser.username}}).then((response) => {this.receivedRequestExists = response.data;})
                    axios.get("/user/isFriend",{params:{"username":this.user.username}}).then((response) => {this.isFriend = response.data;})

                })
            })
        }
    }

})