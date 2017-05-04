var uk=window.uk||{};
uk.position = (function(){
    var center = function(html){
        var $this = $(html);
        var top,marginTop;
        if($this.css('position') == 'fixed'){
            $(html).css({
                'left':'50%',
                'top': '50%',
                'margin-left': $this.outerWidth()/2*-1,
                'margin-top': $this.outerHeight()/2*-1
            }).show();
        }else{
            $(html).css({
                'left':'50%',
                'top': $(window).scrollTop()+150,
                'margin-left': $this.outerWidth()/2*-1,
                'margin-top': 0
            }).show();
        }
    }
    return {
        center: center
    }
})()
uk.info = (function(){
    var timeout;
    var error = function(data){
        clearTimeout(timeout);
        $('.error-info').remove();
        var $dom =$( '<div class="error-info">'+data+'</div>');
        $('body').append($dom);
        uk.position.center('.error-info');
        timeout = setTimeout(function(){
            $('.error-info').remove();
        },2600);
    }
    return {
        error: error
    }
})()
// 富文本中的字体图片优化
uk.optimize = (function(){
  // 优化文本
  var fontsize = function(){
    var a = document.querySelectorAll('.course-more article *');
      $.each(a,function(){
      var b = this.style.fontSize
      if(b){
        $(this).css('font-size',parseInt(b)*2)
      }
    })
  }
  // 优化图片
  var img = function(){
    var a = document.querySelectorAll('.course-more article img');
    $.each(a,function(){
      var b = this.style
      if(b){
        $(this).removeAttr('style')
      }
    })
  }

  return {
    fontsize: fontsize,
    img: img
  }
})()