Vue.component('postComments',
    {
        data : function() {
            return {
                comment : ""
            }
        },
        props: ['userProfilePicture', 'postId', 'postComments', 'refreshComments', 'loggedUser'],
        template : `
                <div class="d-flex flex-column me-1 mt-1 mb-2 border-top border-2" style="overflow-y: auto; max-height: 40vh">
                    <div class="d-inline-flex mt-2">
                        <img class="img-fluid rounded-circle my-1 ms-2" v-bind:src="'user/picture?path=' + loggedUser.profilePicture" height="30" width="30"/>
                        <div class="d-flex flex-fill ms-2" style="border-radius: 20px; background: #F0F0F0">
                            <input v-model="comment" class="border-0 ms-3" type="text" id="username" placeholder="Enter a comment" style="background: #F0F0F0; width: 80%"/>
                            <button class="btn fw-bold ms-auto" style="color: #3F729B; width: fit-content; border-radius: 20px" v-on:click="publishComment()" :disabled="comment.length === 0">Publish</button>
                        </div>                        
                    </div>
                    <div v-if="postComments">
                    <div class="d-flex" v-for="(postComment, index) in postComments.slice().reverse()">
                        <div v-if="!postComment.deleted" class="d-flex flex-fill mt-3">
                            <img class="img rounded-circle ms-2" v-bind:src="'user/picture?path=' + postComment.user.profilePicture" height="30" width="30"/>
                            <div class="d-flex flex-column ms-2" style="border-radius: 20px; background: #F0F0F0">
                                <span class="border-0 fw-bold mx-3" style="font-size: small; color: #404040">{{postComment.user.name}} {{postComment.user.surname}}</span>
                                <div class="border-0 mx-3" style="color: #404040; max-width: 20vw; word-wrap: break-word;">{{postComment.text}}</div>
                            </div>     
                                <button v-if="postComment.user.username===loggedUser.username" v-on:click="deleteComment(postComment.id)" class="btn d-flex justify-content-end fw-bold text-black-50 align-items-baseline" style="width: fit-content; height: 5vh"> <img class="rounded-circle me-2 mb-1" src="images/redTrash2.png" width="25" height="24"/></button>
                        </div>
                    </div>
                    </div>
                    </div>
                </div>
        `,
        methods : {
            showComments : function () {
                this.showComment = !this.showComment;
            },
            publishComment : function () {
                axios.post('/comment/add', {},{
                        params:{
                            "postId" : this.postId,
                            "commentText" : this.comment
                        }
                    }).then(() => {
                        this.comment = ""
                        this.refreshComments()
                    })
            },
            deleteComment : function (postCommentId) {
                axios.post("/comment/deleteComment", {}, {
                    params: {
                        "commentId": postCommentId
                    }
                    })
                    .then(() => {
                            this.refreshComments()
                        }
                    ).catch(function error(err) {
                    that.showToast(err.response.data);
                });
            }

        }
    }
)