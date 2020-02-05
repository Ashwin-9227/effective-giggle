var app = angular.module("Aptitude", []);
var method = "";
var url = "";
var data = "";

app.controller("AptitudeController", function($scope, $http) {
	$scope.register = {
			id : null,
			loginName : "",
			fullName : "",
			password : "",
			email : "",
			dob : ""
	};

	$scope.registerUser = function() {
		method = "POST";
		url = "/registerUser";
		data = $scope.register;

		if ($scope.register != undefined) {
			backendcall(method, url, data);
		}

	}

	function backendcall(method, url, data) {
		$http({
			method : method,
			url : url,
			data : angular.toJson(data),
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(success, error);
	}

	function success(response) {
		if (angular.isObject(response.data)) {
			cleardata();
			alert("Registration Successfull!!!");
			window.location.href = "Login.html";
		} else
			alert(response.data);
	}

	function error(response) {
		console.log(response.statusText);
	}

	function cleardata() {
		$scope.register.fullName = "";
		$scope.register.loginName = "";
		$scope.register.password = "";
		$scope.register.email = "";
		$scope.register.dob = "";
	}

});

$(document).ready(function() {
	$("#register_loginclick").click(register_callLogin);
	$("#register_clearclick").click(register_callClear);

	function register_callLogin() {
		window.location.href = "Login.html";
	}

	function register_callClear() {
		register_FullName.value = "";
		register_LoginName.value = "";
		register_Password.value = "";
		register_EmailID.value = "";
		register_DOB.value = "";
	}
});