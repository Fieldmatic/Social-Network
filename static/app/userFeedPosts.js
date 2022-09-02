Vue.component('userFeedPosts',
    {
        data : function() {
            return {
                posts : [],
                indexOfCommentToShow : -1,
                loggedUser : "",
                componentKey: 0
            }
        }, template : `
                <div class="d-flex flex-column mt-4">
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
                                Are you sure you want to delete your post?
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="button" data-bs-dismiss="modal" v-on:click="deletePost(post)" class="btn" style="background: red; color: white">Delete</button>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="card-header">
                             <div class="d-flex align-items-center">
                                        <img class="img-fluid rounded-circle" v-bind:src="'user/picture?path=' + post.ownerProfilePicture" height="40" width="40"/>
                                        <h5 class="ms-1 mt-2" style="color: #282828">{{post.ownerName}} {{post.ownerSurname}}</h5>
                                        <button v-if="post.ownerUsername===loggedUser.username" data-bs-toggle="modal" data-bs-target="#exampleModal" class="btn ms-auto float-end fw-bold text-black-50 fw-bolder" style="width: fit-content; font-size: xx-large">...</button>

                             </div>   
                        </div>
                        <div class="card-body">
                            <span class="card-text">{{post.text}}</span>
                        </div>
                        <img v-if="post.picture" v-bind:src="'user/picture?path=' + post.picture" class="card-img-bottom">    
                        <div class="d-flex">
                            <button class="btn flex-fill fw-bold text-black-50" > <img class="me-2 mb-1" src="images/like.png" width="19" height="19" />Like</button>
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

            refreshFeed : function() {
                if ((this.$route.matched.some(route => route.path.includes('profile/posts')))) axios.get("/post/getUserPosts").then(response => {this.posts = response.data;})
                else axios.get('post/getUserFeedPosts').then(response => {this.posts = response.data; document.getElementById("posts").style.width = "40%";})
            },
            deletePost : function (post) {
                axios.post("/post/deletePost", {}, {
                    params: {
                        "postId": post.id
                    }
                })
                    .then(() => {
                        post.deleted = true
                        console.log("vriiska")
                        $('#modal-close').click();
                        }
                    ).catch(function error(err) {
                    console.log("error")
                });
            }

        },
        mounted () {
            if ((this.$route.matched.some(route => route.path.includes('profile/posts')))) axios.get("/post/getUserPosts").then(response => {this.posts = response.data;})
            else axios.get('post/getUserFeedPosts').then(response => {this.posts = response.data; document.getElementById("posts").style.width = "40%";})

            axios.get('user/loggedUser').then(response => (this.loggedUser = response.data))
        }
    }
)