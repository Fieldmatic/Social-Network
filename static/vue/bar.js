Vue.component('bar',{
    data: function(){
        return {
            role: window.localStorage.getItem('role'),
            logged:false,
        }
    },
    methods:{
        close: function (event){

            this.$refs.bars.classList.toggle('active');
            this.$refs.button.toggle('active');



        },


    },

    mounted: function(){

    },

  template: `<div><div class="wrapper">
    <!-- Sidebar Holder -->
    <nav id="sidebar" ref="sidebar">
        <div class="sidebar-header">
            <h3>Twitter</h3>
        </div>


        <ul v-if="!logged" class="list-unstyled components">
            <p>Meni</p>
            
            <li>
                <a href="#">Home</a>
                
            </li>
        </ul>

        <ul v-if="role=='User' && logged===true" class="list-unstyled components">
            <p>Meni</p>
            <li>
                <a href="#">Home</a>
                
            </li>
            <li>
                <a href="#">Notifications</a>
            </li>
            
            <li>
                <a href="/#/slike/-2">Moje slike</a>
            </li>
           
        </ul>

        <ul v-if="role=='Admin' && logged===true" class="list-unstyled components">
            <p>Meni</p>
            
           
        </ul>

        <ul v-if="role=='Admin' && logged===true" class="list-unstyled components">
            <p>Meni</p>
            
        </ul>

        
    </nav>

    <!-- Page Content Holder -->
    <div id="content" style="display: block;
    overflow: auto; ">



            <nav v-if="!logged" class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">

                <button type="button" id="sidebarCollapse" ref='button' v-on:click="close" class="navbar-btn">
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
                <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" ref='button' v-on:click="close" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fas fa-align-justify"></i>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="nav navbar-nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#/changeAccount"></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href=""></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#/registration">Registracija</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/#/login">Prijavi se</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <nav v-if="logged===true" class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">

                <button type="button" id="sidebarCollapse" ref='button' v-on:click="close" class="navbar-btn">
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
                <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" ref='button' v-on:click="close" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fas fa-align-justify"></i>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="nav navbar-nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#/changeAccount">Izmjeni informacije profila</a>
                        </li>
                       
                        <li class="nav-item">
                            <a class="nav-link" href="/#/login" v-on:click="logout">Odjavi se</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        
        <router-view></router-view> 
    </div>
</div>
<div id="footer">
<footer class="bg-light text-center text-lg-start" style="position:relative;">
</footer>
</div>
</div>
`



})