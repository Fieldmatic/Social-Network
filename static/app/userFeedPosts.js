Vue.component('userFeedPosts',
    {
        data : function() {
            return {
                posts : [],
                indexOfCommentToShow : -1,
                loggedUser : {},
                postForModal : {},
                postToDelete : {},
                deletionReason:""
            }
        }, template : `
                <div class="d-flex flex-column mt-4">
                    <detailedPostView ref="details" :refreshComments="refreshFeed"></detailedPostView>
                    <div class="card mb-3" v-for="(post, index) in posts" v-if="!post.deleted" :key = "post.id" style="border-radius: 15px">
                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                          <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                              <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Post deleting</h5>
                                <button type="button" class="btn-close" id="modal-close" data-bs-dismiss="modal" aria-label="Close"></button>
                              </div>
                              <div class="modal-body">
                                 <span>Jeste li sigurni da zelite da obrisete?</span>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="button" data-bs-dismiss="modal" v-on:click="deletePost()" class="btn" style="background: red; color: white">Delete</button>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="card-header" style="cursor:pointer;">
                             <div class="d-flex align-items-center">
                                        <img class="img-fluid rounded-circle" v-bind:src="'user/picture?path=' + post.ownerProfilePicture" height="40" width="40"/>
                                        <h5 class="ms-1 mt-2" style="color: #282828">{{post.ownerName}} {{post.ownerSurname}}</h5>
                                        <button v-if="(post.ownerUsername===loggedUser.username && loggedUser.role==='USER') || (loggedUser.role==='ADMIN')" v-on:click="savePostForDeletion(post)" data-bs-toggle="modal" data-bs-target="#exampleModal" class="btn ms-auto float-end fw-bold text-black-50 fw-bolder" style="width: fit-content; font-size: xx-large">...</button>
                             </div>   
                        </div>
                        <div class="card-body d-flex">
                            <span style="word-wrap: break-word; max-width: 100%" class="card-text flex-fill" v-on:click="createPostLayout(post)" data-toggle="modal" data-target="#detailedViewModal">{{post.text}}</span>
                        </div>
                        <img v-if="post.picture" v-on:click="createPostLayout(post)" v-bind:src="'user/picture?path=' + post.picture" class="card-img-bottom">    
                        <div class="d-flex">
                            <button class="btn flex-fill fw-bold text-black-50" v-on:click="handleLike(post)"> <img id="likeDislikeImage" v-bind:src="'images/' + setLikeOrDislike(post) + '.png'" class="me-2 mb-1" width="19" height="19" />{{setLikeOrDislike(post)}}</button>
                            <button class="btn flex-fill fw-bold text-black-50" v-on:click="showComments(index)"><img class="me-2" src="images/comment.png" width="17" height="17" />Comment</button>
                        </div>  
                        <postComments :loggedUser="loggedUser" :refreshComments="refreshFeed" :userProfilePicture="post.ownerProfilePicture" :postId="post.id" :postComments="post.comments" v-if="indexOfCommentToShow===index"></postComments>
                        
                    </div>
                </div>
        `,
        methods : {
            showComments : function (index) {
                //da se zatvore komentari posta, ako su vec otvoreni
                if (index === this.indexOfCommentToShow) this.indexOfCommentToShow = -1;
                else this.indexOfCommentToShow = index;
            },
            setLikeOrDislike : function (post) {
                if (post.likes.includes(this.loggedUser.username)) return "Dislike";
                else return "Like";
            },
            handleLike : function (post) {
                let likeOrDislike;
                if (post.likes.includes(this.loggedUser.username)) likeOrDislike = "dislike";
                else likeOrDislike = "like";
                axios.put("/post/leaveLikeOrDislike", {}, {
                    params: {
                        "postId": post.id,
                        "likeOrDislike": likeOrDislike
                    }
                }).then(() => {this.refreshFeed()})
                    .catch(function error(err) {console.log("error")});
            },
            refreshFeed : function() {
                if ((this.$route.matched.some(route => route.path.includes('profile/posts')))) axios.get("/post/getUserPosts",
                    {
                        params : {"username": this.loggedUser.username}
                    }).then(response =>
                {
                    this.posts = response.data;
                    if ($('#detailedViewModal').is(':visible')) {
                        for (let i = 0; i < this.posts.length; i++) {
                            if (this.posts[i].id === this.postForModal.id)
                                this.showScreen(this.posts[i]);
                        }
                    }
                })
                else axios.get('post/getUserFeedPosts').then(response => {
                    this.posts = response.data; document.getElementById("posts").style.width = "40%";
                    if ($('#detailedViewModal').is(':visible')) {
                        for (let i = 0; i < this.posts.length; i++) {
                            if (this.posts[i].id === this.postForModal.id)
                                this.showScreen(this.posts[i]);
                        }
                    }
                })

            },
            savePostForDeletion : function (post) {
                this.postToDelete = post;
            },
            deletePost : function () {
                axios.post("/post/deletePost", {}, {
                    params: {
                        "postId": this.postToDelete.id
                    }
                }).then(() => {
                        if (this.loggedUser.role === "ADMIN")
                        {
                            let message = '{sender:"' + this.loggedUser.username + '",receiver:"' + this.postToDelete.ownerUsername + '",messageContent:"Admin deleted your post. Deletion reason: ' + this.deletionReason +'",timeStamp:"gg"}'
                            this.$root.$emit('adminMessage',message);
                        }
                        this.postToDelete.deleted = true
                        $('#modal-close').click();
                        }).catch(function error(err) {console.log("error")});
            },
            showScreen : function(post) {
                this.postForModal = post;
                this.$refs.details.sendPostForDetailedView(post, this.loggedUser);
                $('#detailedViewModal').modal('show')
            },
            createPostLayout : function (post) {
                const row = document.getElementById("row");
                if (row.children.length > 1) {
                    row.removeChild(row.children[0])
                }
                document.getElementById('detailedViewModalDialog').classList.remove('modal-xl');
                document.getElementById('modalInfoColumn').classList.remove('col-6');
                document.getElementById('modalInfoColumn').classList.remove('col-5');


                if (post.picture) {
                    document.getElementById('detailedViewModalDialog').classList.add('modal-xl');

                    const pictureDiv = document.createElement("div");
                    const pictureTag = document.createElement("img");
                    pictureDiv.classList.add('col-7');
                    pictureTag.src = 'user/picture?path=' + post.picture;
                    pictureTag.width = 1000;
                    pictureTag.height = 530;
                    pictureTag.classList.add('img-responsive');
                    pictureTag.classList.add('w-100');
                    pictureDiv.appendChild(pictureTag);

                    row.insertBefore(pictureDiv, row.children[0]);

                    document.getElementById('modalInfoColumn').classList.add('col-5');
                } else {
                    document.getElementById('detailedViewModalDialog').classList.add('modal-lg');
                    document.getElementById('modalInfoColumn').classList.add('col');
                }
                this.showScreen(post);
            }
        },
        mounted () {
            axios.get('user/loggedUser').then(response => (this.loggedUser = response.data)).then (() => {
            if ((this.$route.matched.some(route => route.path.includes('profile/posts')))) axios.get("/post/getUserPosts",
                {
                    params : {"username": this.loggedUser.username}
                }).then(response => {this.posts = response.data;})
            else if((this.$route.matched.some(route => route.path.includes('/posts')))) axios.get("/post/getUserPosts",
            {
                params : {"username": this.$route.params.username}
            }).then(response => {this.posts = response.data; document.getElementById("posts").style.width = "100%";})
            else axios.get('post/getUserFeedPosts').then(response => {this.posts = response.data; document.getElementById("posts").style.width = "40%";})
        })
        }
    }
)