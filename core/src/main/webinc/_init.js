$(function() {
  loadToc($('#toc'), '.toc-link', '.toc-list-h2', 10);
  setupLanguages($('body').data('languages'));
});

window.onpopstate = function() {
  activateLanguage(getLanguageFromQueryString());
};