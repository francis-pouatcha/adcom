angular.module('AdCatal').factory('CatalArticleResource', function($resource) {
	var resource = $resource('rest/catalarticles/:CatalArticleId', {
		CatalArticleId : '@id'
	}, {
		'queryAll' : {
			method : 'GET',
			isArray : true
		},
		'query' : {
			method : 'GET',
			isArray : false
		},
		'update' : {
			method : 'PUT'
		}
	});
	return resource;
});