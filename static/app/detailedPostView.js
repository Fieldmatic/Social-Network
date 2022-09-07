Vue.component('detailedPostView',
    {
        data : function() {
            return {
                post : {},
                loggedUser : {},
            }
        }, props: ['refreshComments']
        , template : `
                <div v-if="post" class="modal fade" id="detailedViewModal" tabindex="-1" role="dialog" aria-labelledby="detailedViewModalLabel" aria-hidden="true">
                  <div id="detailedViewModalDialog" class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content" style="min-height: 60vh">
                      <div class="modal-body">
                      <div class="row" id="row">
                     
                     
                      <div id="modalInfoColumn">
                          <div class="d-flex align-items-center mb-3">
                            <img id="postOwnerPicture" class="img-fluid rounded-circle" v-bind:src="'user/picture?path=' + post.ownerProfilePicture" height="40" width="40"/>
                            <h5 id="postOwner" class="ms-1 mt-2" style="color: #282828">{{post.ownerName}} {{post.ownerSurname}}</h5>
                          </div>  
                          <span id="postText" class="card-text flex-fill" style="max-width: 100%; word-wrap: break-word; display: block">{{post.text}}</span>
                          <postComments class="mt-4" :loggedUser="loggedUser" :refreshComments="refreshPage" :userProfilePicture="post.ownerProfilePicture" :postId="post.id" :postComments="post.comments"></postComments>
                        </div>
                        </div>
                      </div>
                      
                    </div>
                  </div>
                </div>
        `,
        methods : {
            sendPostForDetailedView : function (postToShow, loggedUser) {
                this.loggedUser = loggedUser;
                this.post = postToShow;
            },
            refreshPage : function () {
                this.refreshComments();
            }
        },
    }
)