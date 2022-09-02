Vue.component('post',
    {
        data: function() {
          return {
              text:"",
              file:""
          }
        },
        template: `
            <div class="mx-auto" id="posts">
                <div class="mx-auto mt-2 "  style="border-color:lightgray; border-style:solid; border-radius: 15px;border-width: 1px;"">        
                    <div class="row w-100 mx-auto pt-4">
                        <div class="col-12"> 
                            <input id="text" v-model="text" class="form-control form-rounded" placeholder="What's up?"/>
                        </div>
                    </div>      
                    <div class="row w-100  d-inline-flex mx-auto mt-2 ">  
                        <img id="picture" src="" alt="">          
                    </div>          
                    <div class="row w-100 mx-auto pt-4 d-flex align-items-center mb-2">
                        <div class="col-7 d-inline-flex"> 
                            <div class="mx-auto me-auto">
                                <div class="image-upload" >
                                 <input type="file" id="fileUpload" style="font-size:0.9em" v-on:change="showPicture();" accept="image/png, image/gif, image/jpeg" />
                                </div>
                            </div>	
                        </div>
    
                        <div class="col-5 border-0 justify-content-end" style="font-size:0.9em "> 
                            <button id="postButton" type="button" class="btn btn-sm float-end" style="width: 80%; background: #0762ab; color: white" v-bind:disabled='!isDisabled' v-on:click="createPost();"> Post </button>
                        </div>
                    </div>           
                </div> 
                 <userFeedPosts></userFeedPosts>
            </div>       
        `,
        computed:{
            isDisabled() {
                if ((this.text.length > 0) || (this.file)) return true;
            }
        },
        methods:{
            showPicture:function () {
                let picture = document.querySelector('#picture');
                let fileReader = new FileReader();
                let file = document.querySelector('#fileUpload').files[0];
                this.file = file;
                fileReader.onloadend = () => {picture.src = fileReader.result;}
                fileReader.readAsDataURL(file);

            },
            createPost:function () {
                let fileReader = new FileReader();
                let newPost = {
                    "picture" : "",
                    "pictureName" : "",
                    "text" : this.text
                }
                if (this.file)
                {
                    let file = document.querySelector('#fileUpload').files[0];
                    fileReader.readAsDataURL(file);
                    fileReader.onloadend = () =>
                    {
                        newPost.picture = fileReader.result
                        newPost.pictureName = this.file.name
                        axios.post("/post/add", JSON.stringify(newPost)).then((response) => { console.log(response.data)})
                    }
                }
                else axios.post("/post/add", JSON.stringify(newPost)).then((response) => { console.log(response.data)})}
            }

    }
)