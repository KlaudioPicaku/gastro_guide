var searchCache = {};
var searchResults = $("#searchResults");

$(document).ready(function() {
  $("#searchInput").on("input", function() {
    var searchTerm = $(this).val();
    if (searchTerm.length > 0) {
      if (searchCache.hasOwnProperty(searchTerm)) {
        showSearchResults(searchCache[searchTerm]);
      } else {
        var url = "/api/searchbar/autocomplete?term=" + searchTerm;
        $.get(url, function(results) {
          searchCache[searchTerm] = results;
          showSearchResults(results);
        });
      }
    } else {
      hideSearchResults();
    }
  });

 // Nascondi i risultati quando fai click fuori dal campo di ricerca
  $(document).click(function(event) {
    var target = $(event.target);
    if (!target.is("#searchInput") && !target.is("#searchResults")) {
      hideSearchResults();
    }
  });

  function showSearchResults(results) {
    searchResults.empty();

    if (results.length > 0) {
      $.each(results, function(index, result) {
        var link = $("<a>").text(result.name).attr("href", result.absoluteUrl).attr("class","modern-link").attr("style","color:black");
        searchResults.append(link);
      });
      searchResults.show();
    } else {
      hideSearchResults();
    }
  }

  function hideSearchResults() {
    searchResults.empty().hide();
  }
});