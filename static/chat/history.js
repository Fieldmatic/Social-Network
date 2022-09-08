Vue.component('history',{
    data:function (){
        return {
            chats:[],
            loggedUser:""
        }
    },
    template:
        `
         <div class="d-flex flex-column">
             <div v-if="chats.length==0" class="w-auto mx-auto mt-3">
                <h4> No chats to show yet.</h4>
             </div>
             <div v-for="chat in chats">
                    <div class="w-100 d-inline-flex align-items-center border-bottom mt-3" @click="openChat(chat.user)" style="cursor: pointer;">
                        <div class="row w-100 mx-auto align-items-center">
                            
                            <div class="col-md-2">
                                <img class="img-fluid rounded-circle my-2 float-end" v-bind:src="'user/picture?path=' + chat.user.profilePicture" height="80" width="80"/>
                            </div>
                            <div class="col-md-8">
                                <div class="container text-dark d-flex flex-column">
                                    <div class="row">
                                        <span v-if="chat.user.role ==='ADMIN'" class="fw-bold" style="color:#FFD700">{{chat.user.name}} {{chat.user.surname}}</span>
                                        <span v-else class="fw-bold">{{chat.user.name}} {{chat.user.surname}}</span>
                                    </div>
                                    <div class="row d-inline-block">
                                        <span v-if="chat.message.sender === loggedUser.username">You: <b>{{chat.message.messageContent}}</b></span>
                                        <span v-else>They: <b>{{chat.message.messageContent}}</b></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>      
             </div>
         </div>
        
        `,
    mounted:function (){
        axios.get("/message/history").then((response) => {this.chats = response.data})
        axios.get("/user/loggedUser").then((response) => {this.loggedUser = response.data})
    },
    methods:{
        openChat(user){
            this.$root.$emit('openChat',user);
        }
    }
})