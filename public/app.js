var app = angular.module('App', []);
var todoModel = { descricao: "", pronto: false }

app.service('todoRestApiService', ['$http', function($http){
    var urlBase = "/api/itens";

    this.getAll = function(){
        return $http.get(urlBase);
    }

    this.getById = function(id) {
        return $http.get(urlBase+"/"+id);
    }

    this.insert = function(todo) {
        return $http.post(urlBase, todo);
    }

    this.update = function(todo) {
        return $http.put(urlBase, todo);
    }

    this.delete = function(id) {
        return $http.delete(urlBase+"/"+id);
    }
}])

app.controller('padrao', function ($scope, $http, todoRestApiService) {
    $scope.todo = Object.assign({}, todoModel);
    $scope.lista = [];
    $scope.loading = false;
    $scope.editingItem = null;

    $scope.loadTodoItems = function() {
        $scope.loading = true;
        todoRestApiService.getAll()
            .then(function (response) {
                $scope.lista = response.data;
                $scope.loading = false;
            }).catch(function(err) {
                $scope.loading = false;
            });
    }

    this.$onInit = function () {
        $scope.loadTodoItems();
    };
    $scope.adicionar = function () {
        $scope.loading = true;
        todoRestApiService.insert($scope.todo)
            .then(function(response) {
                $scope.loading = false;
                $scope.lista.push(response.data)
                $scope.todo = Object.assign({}, todoModel);
            }).catch(function(err) {
                $scope.loading = false;
            });
    };
    $scope.remover = function (id) {
        $scope.loading = true;
        todoRestApiService.delete(id)
            .then(function(response) {
                $scope.loadTodoItems();
            }).catch(function() {
                $scope.loading = false;
            });
    };
    $scope.editarItem = function (index) {
        $scope.editingItem = index;
    };

    $scope.update = function(item) {
        $scope.loading = true;
        todoRestApiService.update(item)
            .then(function(response) {
                $scope.editingItem = null;
                $scope.loadTodoItems();
            }).catch(function(err){
                $scope.loading = false;
            })
    }

    $scope.done = function(item) {
        var doneTodoItem = Object.assign(item, {"pronto": true});
        $scope.loading = true;
        todoRestApiService.update(doneTodoItem)
            .then(function(response){
                $scope.loadTodoItems();
            }).catch(function(err) {
                $scope.loading = false;
            });
    }

});