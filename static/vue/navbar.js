Vue.component('navbar', {
    template: `
            <nav class="navbar sticky-top navbar-expand-lg navbar-dark" style="background: #3F729B">
              <div class="container-fluid">
                <a class="navbar-brand me-5 fw-bold" href="#/">Socialize</a>
                <div class="ms-auto w-100 collapse navbar-collapse d-inline-flex" id="navbarText">
                  <ul class="navbar-nav w-100 me-auto mb-2 mb-lg-0 d-inline-flex">
                    <div class="w-75 d-inline-flex">
                         <li class="nav-item">
                           <a class="nav-link active" aria-current="page" href="#/">Home</a>
                         </li>
                         <li class="nav-item">
                           <a class="nav-link" href="#/profile/posts">Your profile</a>
                         </li>
                         <li class="nav-item">
                           <a class="nav-link" href="#/friendList">Friends</a>
                         </li>
                         <li class="nav-item">
                           <a class="nav-link" href="#/friendRequests">Friend requests</a>
                         </li>
                         <li class="nav-item">
                           <a class="nav-link" href="#/search">Search</a>
                         </li>
                         <li class="nav-item">
                           <a class="nav-link" href="#/messages">Messages</a>
                         </li>
                    </div>
                    <div class="w-25 me-2">
                        <li class="nav-item float-end">
                            <a class="nav-link" href="/" v-on:click="logout()">Logout</a>
                        </li>
                    </div>
                    
                  </ul>
                </div>
              </div>
            </nav>`,
    methods : {
        logout : function() {
            axios.get("/authentication/logout");
        }
    }
});
