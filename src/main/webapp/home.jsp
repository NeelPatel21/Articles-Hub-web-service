<%-- 
    Document   : home
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>
<!DOCTYPE html>

<%
    String appLink = System.getenv("articleshub.app");    
    String userName1 = System.getenv("articleshub.userName.1");        
    String userName2 = System.getenv("articleshub.userName.2");        
    String userInfo1 = System.getenv("articleshub.userinfo.1");        
    String userInfo2 = System.getenv("articleshub.userinfo.2");        
    String userImg1 = System.getenv("articleshub.userimg.1");        
    String userImg2 = System.getenv("articleshub.userimg.2");        
    String email1 = System.getenv("articleshub.email.1");        
    String email2 = System.getenv("articleshub.email.2");        
    String linkedin1 = System.getenv("articleshub.linkedin.1");     
    String linkedin2 = System.getenv("articleshub.linkedin.2");       
    String linkedin_link1 = System.getenv("articleshub.linkedin.link.1");     
    String linkedin_link2 = System.getenv("articleshub.linkedin.link.2");       
%>
<html>
    <head>
        <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <title>Articles Hub</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
    body,h1,h2,h3,h4,h5 {font-family: "Poppins", sans-serif}
    body {font-size: 16px;}
    img {margin-bottom: -8px;}
    .mySlides {display: none;}
    </style>
    </head>
<body class="w3-content w3-black" style="max-width:1500px;">

<!-- Header with Slideshow -->
<header class="w3-display-container w3-center">
  <a class="w3-button w3-block w3-green w3-hide-large w3-hide-medium" href="<%=appLink%>">Download <i class="fa fa-android"></i></a>
  <div class="mySlides w3-animate-opacity">
    <img class="w3-image" src="/main-background.jpg" alt="Image 1" style="" width="100%" height="100%">
<!--    <div class="w3-display-middle w3-padding" style="width: calc(100%-40);
                height: calc(100% - 20px);">
        <img class="w3-image" src="/articles hub logo.png" alt="Image 1" style="min-width:500px; padding-top: 50px; padding-left: 5px; padding-right: 5px" width="100%" height="100%">
    </div>-->
    <div class="w3-display-left w3-padding w3-hide-small" style="width:30%">
      <div class="w3-black w3-opacity w3-hover-opacity-off w3-padding-large w3-round-large">
        <h1 class="w3-xlarge">Write whatever you want</h1>
        <hr class="w3-opacity">
        <p>Articles Hub is web application in which users can easily read or write articles on any topic.</p>
        <p><a class="w3-button w3-block w3-green w3-round" href="<%=appLink%>">Download <i class="fa fa-android"></i></a></p>
      </div>
    </div>
  </div>
<!--  <a class="w3-button w3-black w3-display-right w3-margin-right w3-round w3-hide-small w3-hover-light-grey" href="plusDivs(1)">Take Tour <i class="fa fa-angle-right"></i></a>
  <a class="w3-button w3-block w3-black w3-hide-large w3-hide-medium" href="plusDivs(1)">Take Tour <i class="fa fa-angle-right"></i></a>-->
</header>

<!-- The App Section -->
<div class="w3-padding-64 w3-white">
  <div class="w3-row-padding">
    <div class="w3-col l8 m6">
      <h1 class="w3-jumbo"><b>The App</b></h1>
      <h1 class="w3-xxlarge w3-text-green"><b>Why should I use it?</b></h1>
      <p class="w3-large">'Articles Hub' is a simple community based web application 
      where you can find large number of articles on different topics written by users.
      You can also write your own articles by simply registering your self as a user.
      It is <span class="w3-xlarge">100% free</span>. You can spend
      your free time with the app by reading articles of your choice. You can also
      share your knowledge or thoughts with others by writing articles.</p>
      <a class="w3-button w3-light-grey w3-padding-large w3-section w3-hide-small" href="<%=appLink%>">
        <i class="fa fa-download"></i> Download Application
      </a>
      <p>Available for <i class="fa fa-android w3-xlarge w3-text-green"></i></p>
    </div>
    <div class="w3-col l4 m6">
      <img src="/screen1.jpg" class="w3-image w3-right w3-hide-small" width="335" height="471">
      <div class="w3-center w3-hide-large w3-hide-medium">
        <a class="w3-button w3-block w3-padding-large" href="<%=appLink%>">
          <i class="fa fa-download"></i> Download Application
        </a>
        <img src="/screen1.jpg" class="w3-image w3-margin-top" width="335" height="471">
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<!--<div id="download" class="w3-modal w3-animate-opacity">
  <div class="w3-modal-content" style="padding:32px">
    <div class="w3-container w3-white">
      <i href="document.getElementById('download').style.display='none'" class="fa fa-remove w3-xlarge w3-button w3-transparent w3-right w3-xlarge"></i>
      <h2 class="w3-wide">DOWNLOAD</h2>
      <p>Download the app in AppStore, Google Play or Microsoft Store.</p>
      <i class="fa fa-android w3-large"></i> <i class="fa fa-apple w3-large"></i> <i class="fa fa-windows w3-large"></i>
      <p><input class="w3-input w3-border" type="text" placeholder="Enter e-mail"></p>
      <a type="button" class="w3-button w3-block w3-padding-large w3-red w3-margin-bottom" href="document.getElementById('download').style.display='none'">Fake Download</a>
    </div>
  </div>
</div>-->

<!-- Clarity Section -->
<div class="w3-padding-64 w3-light-grey">
  <div class="w3-row-padding">
    <div class="w3-col l4 m6">
      <img class="w3-image w3-round-large w3-hide-small w3-grayscale" src="/screen2.jpg" alt="App" width="335" height="471">
    </div>
    <div class="w3-col l8 m6">
      <h1 class="w3-jumbo"><b>How to write an article?</b></h1>
      <h1 class="w3-xxlarge w3-text-red"><b>I'm not a professional writer!</b></h1>
      <p class="w3-large">You don't have to be a professional writer to write
          an article. You can write article in <span class="w3-xlarge">any formate</span>.
          Any registered user can simply write an article by picking up appropriate
          title & related tags.
          Articles' <span class="w3-xlarge">length doesn't matter</span>. You
          can write an article in few words to more then 1000 words. 
      </p>
      <a class="w3-button w3-grey w3-padding-large w3-section w3-hide-small" href="<%=appLink%>">
        <i class="fa fa-download"></i> Download Application
      </a>
      <p>Available for <i class="fa fa-android w3-xlarge w3-text-green"></i></p>
    </div>
    <div class="w3-col l4 m6">
      <div class="w3-center w3-hide-large w3-hide-medium">
        <a class="w3-button w3-block w3-padding-large" href="<%=appLink%>">
          <i class="fa fa-download"></i> Download Application
        </a>
        <img src="/screen2.jpg" class="w3-image w3-margin-top" width="335" height="471">
      </div>
    </div>
  </div>
</div>

<!-- The App Section 2-->
<div class="w3-padding-64 w3-white">
  <div class="w3-row-padding">
    <div class="w3-col l8 m6">
      <h1 class="w3-jumbo"><b>Features</b></h1>
      <h1 class="w3-xxlarge w3-text-blue"><b>What else I can do with this app?</b></h1>
      <p class="w3-large">Application introduce number of features. You can 
          <span class="w3-xlarge">like</span> & <span class="w3-xlarge">comment</span>
          on articles. You can search articles by tag. You can also select
          <span class="w3-xlarge">favorite tags</span> for personalized experience.
          You can request a new tag. You can easily manage your articles.
      </p>
      <a class="w3-button w3-light-grey w3-padding-large w3-section w3-hide-small" href="<%=appLink%>">
        <i class="fa fa-download"></i> Download Application
      </a>
      <p>Available for <i class="fa fa-android w3-xlarge w3-text-green"></i></p>
    </div>
    <div class="w3-col l4 m6">
      <img src="/screen3.jpg" class="w3-image w3-right w3-hide-small" width="335" height="471">
      <div class="w3-center w3-hide-large w3-hide-medium">
        <a class="w3-button w3-block w3-padding-large" href="<%=appLink%>">
          <i class="fa fa-download"></i> Download Application
        </a>
        <img src="/screen3.jpg" class="w3-image w3-margin-top" width="335" height="471">
      </div>
    </div>
  </div>
</div>

<!-- Team Section -->
<div class="w3-padding-64 w3-center w3-white">
  <h1 class="w3-jumbo"><b>Team</b></h1>
  <!--<p class="w3-large">Developers</p>-->
<!--  <div class="w3-col l3 m6 w3-margin-bottom">
      <img src="screen3.jpg" alt="Mike" style="width:100%">
      <h3>Mike Ross</h3>
      <p class="w3-opacity">Architect</p>
      <p>Phasellus eget enim eu lectus faucibus vestibulum. Suspendisse sodales pellentesque elementum.</p>
      <p><a class="w3-button w3-light-grey w3-block">Contact</a></p>
    </div>-->
  <div class="w3-row-padding" style="margin-top:64px">
    <div class="w3-half w3-section">
        <ul class="w3-ul w3-card w3-hover-shadow">
          <li class="w3-indigo w3-xlarge w3-padding-32"><%=userName1%></li>
        <div class="w3-row-padding">
          <div class="w3-col l4 m6">
              <object data="<%=userImg1%>"
                      style="width:100%; margin-bottom: 0px;" class="w3-circle w3-hover-opacity"
                      type="image/png">
                <img src="/person.jpg" alt="" style="width:100%; margin-bottom: 0px;" class="w3-circle w3-hover-opacity"/>
              </object>
            <!--<img src="/neelpatel.jpeg" alt="" style="width:100%; margin-bottom: 0px;" class="w3-circle w3-hover-opacity">-->
          </div>
          <div class="w3-col l8 m6">
              <li class="w3-padding-16 w3-left-align w3-large">
                  <%=userInfo1!=null?userInfo1:""%>
               </li>
          </div>
        </div>
          <!--<li class="w3-padding-16 w3-light-grey">Contact</li>-->
          <li class="w3-padding-16 w3-large"><i class="fa fa-envelope"></i> <%=email1!=null?email1:""%></li>
          <li class="w3-padding-16 w3-large"><i class="fa fa-linkedin-square">
              <a href="<%=linkedin_link1!=null?linkedin_link1:""%>" style='font-family: "Poppins"'><%=linkedin1!=null?linkedin1:""%></a></i></li>
        </ul>
    </div>
    <div class="w3-half w3-section">
      <ul class="w3-ul w3-card w3-hover-shadow">
          <li class="w3-brown w3-xlarge w3-padding-32"><%=userName2%></li>
        <div class="w3-row-padding">
          <div class="w3-col l4 m6">
              <object data="<%=userImg2%>"
                      style="width:100%; margin-bottom: 0px;" class="w3-circle w3-hover-opacity"
                      type="image/png">
                <img src="/person.jpg" alt="" style="width:100%; margin-bottom: 0px;" class="w3-circle w3-hover-opacity"/>
              </object>
            <!--<img src="/neelpatel.jpeg" alt="" style="width:100%; margin-bottom: 0px;" class="w3-circle w3-hover-opacity">-->
          </div>
          <div class="w3-col l8 m6">
            <li class="w3-padding-16 w3-left-align w3-large">
              <%=userInfo2!=null?userInfo2:""%>
            </li>
          </div>
        </div>
          <!--<li class="w3-padding-16 w3-light-grey">Contact</li>-->
          <li class="w3-padding-16 w3-large"><i class="fa fa-envelope"></i> <%=email2!=null?email2:""%></li>
          <li class="w3-padding-16 w3-large"><i class="fa fa-linkedin-square">
            <a href="<%=linkedin_link2!=null?linkedin_link2:""%>" style='font-family: "Poppins"'><%=linkedin2!=null?linkedin2:""%></a></i></li>
        </ul>
    </div>
  </div>
  <br>
</div>
<!-- Footer -->
<footer class="w3-container w3-padding-32 w3-light-grey w3-center w3-xlarge">
  <p class="w3-large">Contact us :- articleshub.app@gmail.com</p>
</footer>

<script>
// Slideshow
var slideIndex = 1;
showDivs(slideIndex);

function plusDivs(n) {
  showDivs(slideIndex += n);
}

function showDivs(n) {
  var i;
  var x = document.getElementsByClassName("mySlides");
  if (n > x.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = x.length}
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";  
  }
  x[slideIndex-1].style.display = "block";  
}
</script>

</body>
</html>
