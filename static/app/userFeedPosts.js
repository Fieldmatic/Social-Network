Vue.component('userFeedPosts',
    {
        data : function() {
            return {
                posts : [],
                indexOfCommentToShow : -1
            }
        }, template : `
                <div class="d-flex flex-column mt-4">
                    <div class="card mb-3" v-for="(post, index) in posts" style="border-radius: 15px">
                        <div class="card-header">
                             <div class="d-flex align-items-center justify-content-start">
                                        <img class="img-fluid rounded-circle" v-bind:src="'user/picture?path=' + post.ownerProfilePicture" height="40" width="40"/>
                                        <h5 class="ms-1 mt-2" style="color: #282828">{{post.ownerName}} {{post.ownerSurname}}</h5>
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
                        <postComments :refreshComments="refreshComments" :userProfilePicture="post.ownerProfilePicture" :postId="post.id" :postComments="post.comments" v-if="indexOfCommentToShow===index"></postComments>
                    </div>
                </div>
        `,
        methods : {
            showComments : function (index) {
                //da se zatvore komentari posta, ako su vec otvoreni
                if (index === this.indexOfCommentToShow) this.indexOfCommentToShow = -1;
                else this.indexOfCommentToShow = index;
            },

            refreshComments : function() {
                axios
                    .get('post/getUserFeedPosts')
                    .then(response => (this.posts = response.data))
            }

        },
        mounted () {
            axios
                .get('post/getUserFeedPosts')
                .then(response => (this.posts = response.data))
        }
    }
)