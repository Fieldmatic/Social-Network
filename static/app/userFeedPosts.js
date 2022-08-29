Vue.component('userFeedPosts',
    {
        data : function() {
            return {
                posts : []
            }
        }, template : `
            <div class="card-group">
            <div class="card" v-for="post in posts"><h1>{{post.text}}</h1></div>
            </div>
        `,
        methods : {
        },
        mounted () {
            axios
                .get('post/getUserFeedPosts')
                .then(response => (this.posts = response.data))
        }
    }
)