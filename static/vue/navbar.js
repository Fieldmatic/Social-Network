Vue.component('navbar', {
    template: `
            <nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-primary">
              <div class="container-fluid">
                <a class="navbar-brand me-5" href="#/">Socialize</a>
                <div class="collapse navbar-collapse" id="navbarText">
                  <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                      <a class="nav-link active" aria-current="page" href="#/">Home</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="#">Your profile</a>
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
                       <a class="nav-link" href="#">Logout</a>
                    </li>
                  </ul>
            
                </div>
              </div>
            </nav>`
});
