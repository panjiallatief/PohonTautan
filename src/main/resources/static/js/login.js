$("#registar").on('click', function(){
    const uRL = window.location.href.substring(0, window.location.href.lastIndexOf("/"));
    // console.log(uRL)
    document.getElementById('registar').classList.add('hidden')

    $("#regbut").append(
        `<div class="my-auto mx-auto animate-spin">
        <svg xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M304 48a48 48 0 1 0 -96 0 48 48 0 1 0 96 0zm0 416a48 48 0 1 0 -96 0 48 48 0 1 0 96 0zM48 304a48 48 0 1 0 0-96 48 48 0 1 0 0 96zm464-48a48 48 0 1 0 -96 0 48 48 0 1 0 96 0zM142.9 437A48 48 0 1 0 75 369.1 48 48 0 1 0 142.9 437zm0-294.2A48 48 0 1 0 75 75a48 48 0 1 0 67.9 67.9zM369.1 437A48 48 0 1 0 437 369.1 48 48 0 1 0 369.1 437z"/></svg>
      </div>`
    )
    $.ajax({
      url: `/inputuser?username=${$("#usernamer").val()}&passwordr=${$("#passwordr").val()}&email=${$("#email").val()}`,
      method: 'POST'
    }).done(function(){
        window.open(`${uRL}`+"/login", "_self")
    })
  })

  $("#forgetpas").on('click', function(){
    $.ajax({
      url: `/forgotpass?email=${$("#emailf").val()}`,
      method: 'POST',
      success: function(){
        location.reload()
      }
    })
  })

$("#regis").on('click', function () {
  document.getElementById('kanan').style.display = 'none';
  document.getElementById('kananB').style.display = 'block'
  document.getElementById('kananC').style.display = 'none'
})

$("#cancel").on('click', function(){
  document.getElementById('kanan').style.display = 'block';
  document.getElementById('kananB').style.display = 'none';
  document.getElementById('kananC').style.display = 'none'

})

$("#cancelf").on('click', function(){
  document.getElementById('kanan').style.display = 'block';
  document.getElementById('kananB').style.display = 'none';
  document.getElementById('kananC').style.display = 'none'

})

$("#forgot").on('click', function(){
  document.getElementById('kanan').style.display = 'none';
  document.getElementById('kananB').style.display = 'none'
  document.getElementById('kananC').style.display = 'block'
})