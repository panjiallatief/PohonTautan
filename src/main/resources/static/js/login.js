$("#registar").on('click', function(){
    const uRL = window.location.href.substring(0, window.location.href.lastIndexOf("/"));
    // console.log(uRL)

    $.ajax({
      url: `/inputuser?username=${$("#usernamer").val()}&passwordr=${$("#passwordr").val()}&email=${$("#email").val()}`,
      method: 'POST',
      success: function(){
        window.open(`${uRL}`+"/login", "_self")
      }
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