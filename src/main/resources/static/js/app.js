if (window.parent) {
  window.parent.postMessage({ 'current-location': window.location.href }, '*');
}

$(document).ready(function() {

$(".header-tabs a").each(function(){
  var target_element_links=$($(this).data("target")).find("a")
  if(target_element_links.length === 1){
    $(this).click(function(){
      window.location.href=target_element_links[0].href
    })
  }
})


})
