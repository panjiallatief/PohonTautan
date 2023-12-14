$("#forgetpas").on('click', function(){
    var ema =  window.location.href;
    var mm = ema.substring(ema.lastIndexOf("/")+1, ema.length)
    var ma = ema.substring(0, ema.lastIndexOf("/"))
    console.log(ema)
    console.log(mm)
    console.log(ma)
    $.ajax({
      url: `/resetpass?token=${mm}&password=${$("#password").val()}`,
      method: 'PUT',
      success: function(){
        window.open(`${ma}`+"/login", "_self")
      }
    })
  })