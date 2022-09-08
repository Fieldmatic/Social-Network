Vue.component("chat",{
    data:function() {
        return {
            loggedUser:undefined,
           user:undefined,
           chat:[],
           websocket:undefined,
            message:""
        }
    },
    template:`
               <div class="container justify-content-center fixed-bottom">
                <div class="row justify-content-center">
                        <div v-if = "user" class="col-4 card border-0 border-start-1 mb-1" style="min-height: 50vh; max-height: 50vh; min-width: 20vw">
                            <div class="card-header d-flex align-items-center sticky-top flex-fill" style="max-height: 10vh">
                                 <div class="d-flex flex-fill align-items-center w-100 justify-content-between">
                                        <div class="d-flex flex-fill align-items-center">
                                            <img class="img-fluid rounded-circle me-1" v-bind:src="'user/picture?path=' + user.profilePicture" height="40" width="40"/>
                                            <h5 class="ms-1 mt-2" style="color: #282828">{{user.name}} {{user.surname}}</h5>
                                        </div>
                                        <button type="button" class="btn-close float-end" aria-label="Close" @click="close()"></button>
                                 </div>
                             </div>
                             <div class="card-body overflow-auto d-flex flex-column border">
                                <div v-for="message in chat" class="mb-2">
                                   <div v-if="message.receiver===user.username" class="d-flex flex-fill float-end p-2 text-light" style="max-width:13vw;border-radius: 20px; background: #4169E1"> {{message.messageContent}}</div>
                                   <div v-else class="float-start d-flex flex-fill p-2 text-light" style="max-width:13vw; border-radius: 20px; background: #808080"">{{message.messageContent}}</div>
                                </div>
                                <div v-if="this.user.role==='ADMIN'" class="d-flex p-2 text-dark" style="border-radius: 20px;font-size: 0.6rem; background: #FFD700"> This user is admin, please do not respond to this chat. </div>
                             </div>    
                             <div class="border">
                                 <div class="d-flex flex-fill ms-2 mb-2 mt-2" style="border-radius: 20px; background: #F0F0F0">
                                    <input class="flex-fill border-0 ms-3" v-model="message" type="text" id="messageContent" placeholder="Write a message" style="background: #F0F0F0"/>
                                    <button class="btn fw-bold ms-auto flex-fill" style="color: #3F729B; width: fit-content; border-radius: 20px" @click="sendMessage()">Send</button>
                                 </div>   
                             </div>
                             </div>
                            
                        </div>
             
                        </div>
                </div>
                 
               
               </div>
              
              
    
                `,
    mounted:function(){
        axios.get("/user/loggedUser").then((response) => {
                                                            this.loggedUser = response.data
                                                            this.websocket = new WebSocket("ws://localhost:8000/chat?username=" + this.loggedUser.username);
                                                         })
        this.$root.$on("openChat", (user) => {this.user = user; this.loadChat()})
        this.$root.$on("adminMessage",(message) => {this.websocket.send(message);})

    },
    methods:{
        close(){
          this.user = undefined
        },
        loadChat() {
            axios.get("/message/chat",{params:{'username':this.user.username}}).then((res) => {this.chat = res.data;for(let message of this.chat) {message.timeStamp = new Date(message.timeStamp)} this.setupOnMessage()})
        },
        setupOnMessage(){
            var context = this;
            this.websocket.onmessage = function (event) {
                let message = JSON.parse(event.data)
                context.chat.push(message);
            };
        },
       async sendMessage() {
           if (this.message !== "") {
               this.websocket.send('{sender:"' + this.loggedUser.username + '",receiver:"' + this.user.username + '",messageContent:"' + this.message + '",timeStamp:"gg"}');
               this.message = ""
               await new Promise(resolve => setTimeout(resolve, 100));
               this.loadChat()
           }
       },
    }

})