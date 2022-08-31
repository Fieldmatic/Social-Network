Vue.component('editProfile',{
    data:function(){
        return {
            user:"",
            newPassword:"",
            passwordConfirm:"",
            oldPassword:""

        }
        },
    template:`
                   
                    <form id="editProfileForm" class="w-50 mx-auto min-vh-100">
                        <div class="position-absolute mt-2 start-50 translate-middle-x p-3" style="z-index: 11">
                            <div id="liveToast" class="toast hide align-items-center text-white bg-primary border-0" role="alert" aria-live="assertive" aria-atomic="true"></div>
                        </div>
                        <div class="form-floating mb-3 text-dark">
                                <input type="text" v-model="user.username" id="username" placeholder="Username" class="form-control" disabled/>
                                <label for="username" class="form-label">Username</label>
                            </div>
    
                            <div class="row justify-content-center mb-3">
                                <div class="col-md-6 rounded-3">
                                    <div class="form-floating">
                                        <input type="text" v-model="user.name" id="name" placeholder="Name" class="form-control" required/>
                                        <label for="name" class="form-label">Name</label>
                                    </div>
                                </div>
                                <div class="col-md-6 rounded-3">
                                    <div class="form-floating">
                                        <input type="text" v-model="user.surname" id="surname" placeholder="Surname" class="form-control" required/>
                                        <label for="surname" class="form-label">Surname</label>
                                    </div>
                                </div>
                            </div>
    
                            <div class="form-floating mb-3">
                                <input type="password" v-model="newPassword" id="newPassword" placeholder="New password" class="form-control" required/>
                                <label for="password" class="form-label">New Password</label>
                            </div>
                            
                            <div>
                                <div class="form-floating mb-3">
                                    <input type="password" v-model="passwordConfirm" id="passwordConfirm" placeholder="Confirm new password" class="form-control" required/>
                                    <label for="passwordConfirm" class="form-label">Confirm new password</label>
                                </div>
    
                                <div class="form-floating mb-3">
                                    <input type="text" v-model="user.email" id="email" placeholder="Email address" class="form-control" required/>
                                    <label for="email" class="form-label">Email address</label>
                                </div>
    
                                <div class="form-floating mb-3">
                                    <input type="date" v-model="user.birthDate" class="form-control d-inline" id="birthday" name="birthday" required>
                                    <label for="birthday">Date of Birth</label>
                                </div>
    
                                <div class="form-check form-check-inline text-dark mb-4">
                                    <input class="form-check-input" type="radio" name="gender" id="female" @change="onChange($event)" value="FEMALE" required>
                                    <label class="form-check-label" for="female">Female</label>
                                </div>
                                <div class="form-check form-check-inline text-dark">
                                    <input class="form-check-input" type="radio" name="gender" id="male" @change="onChange($event)" value="MALE" required>
                                    <label class="form-check-label" for="male">Male</label>
                                </div>
                                
                                <div class="form-floating mb-3">
                                    <input type="password" v-model="oldPassword" id="oldPassword" placeholder="Old password" class="form-control" required/>
                                    <label for="password" class="form-label">Old Password</label>
                                </div>
                                <button type="button" class="btn btn-success text-white fw-bold me-auto align-self-baseline w-100" @click="saveChanges">Save changes</button>
                        </div>
                    </form>
            `,
    mounted: function () {
        axios.get("/user/loggedUser").then(response =>
        {
            this.user = response.data;
            this.user.birthDate = new Date(this.user.birthDate['year'],this.user.birthDate['month']-1,this.user.birthDate['day']+1);
            this.user.birthDate = this.user.birthDate.toISOString().slice(0,10);
            console.log(this.user.gender)
            if (this.user.gender === "MALE") document.querySelector("#male").checked=true
            else document.querySelector('#female').checked = true;

        })
    },
    methods:{
        onChange(event) {
            this.user.gender = event.target.value;
        },
        saveChanges(){
            if (this.oldPassword !== this.user.password) this.showToast("Your old password isn't correct, you can't save changes without it.")
            else {
                if (this.newPassword !== "")
                    {
                        if (this.newPassword !== this.passwordConfirm) this.showToast("Your new password and password confirmations aren't the same!")
                        else {
                            this.user.password = this.newPassword;
                            axios.put("/user/update",this.user).then((response) => {this.showToast(response.data)})
                        }
                    }
                else axios.put("/user/update",this.user).then((response) => {this.showToast(response.data)})
            }

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