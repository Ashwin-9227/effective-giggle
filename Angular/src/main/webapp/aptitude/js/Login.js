var app = angular.module("Aptitude", []);
var method = "";
var url = "";
var data = "";
var aptitudearray = [];
var questionarray = [];
var answerarray = [];
var userid = -1;
app
.controller(
		"AptitudeController",
		function($scope, $http, $timeout) {
			$scope.login = {
					loginName : "",
					password : ""
			};

			$scope.aptitude = [];

			$scope.loginUser = function() {
				method = "POST";
				url = "/loginUser";
				data = $scope.login;

				if ($scope.login != undefined) {
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
				if (response.data["status"] != undefined) {
					if (response.data["status"] == "Login Successfull!!!"
						&& response.data["userdetails"] != undefined) {
						cleardata();
						alert(response.data["status"]);
						method = "GET";
						url = "/getAptitude";
						data = null;
						userid = response.data["userdetails"]["id"];
						backendcall(method, url, data);

					} else if (response.data["status"] == "Success") {
						window
						.open(response.data["path"],
						'_blank');
					} else if (response.data == "Failure") {
						alert("Failue in Export");
					} else {
						alert(response.data["status"]);
					}
				} else if (typeof response.data == "object") {
					if (response.data["questions"] != undefined
							&& response.data["answers"] != undefined) {
						document.getElementById("div_login").style.display = "none";
						document.getElementById("div_aptitude").style.display = "block";
						$scope.aptitude["question"] = response.data["questions"];
						questionarray = response.data["questions"];
						answerarray = response.data["answers"];
						var array = [];
						for (i = 0; i < questionarray.length; i++) {

							aptitudearray["question"] = questionarray[i];
							var index = 0;
							var multiarray = [];
							for (j = 0; j < answerarray.length; j++) {
								if (questionarray[i]["quesid"] == answerarray[j]["quesid"]) {
									multiarray = multiarray
									.concat(answerarray[j]);
								}
							}
							aptitudearray["question"]["answers"] = multiarray;
							console.log("");

							array = array
							.concat(aptitudearray["question"]);
							console.log("");
							time = 18;
							$scope.counter = "Time Remaining: 0h 30m 00s";
						}
					} else if (response.data["Result"] != undefined) {
						if (response.data["Result"] == "Success") {
							alert("Thanks for participating "
									+ response.data["userdetails"]["fullName"]
									+ ". Have a nice day!!!. \n By clicking the OK button will Logout.");
							location.reload();
						} else if (response.data["Result"] == "Failure") {
							alert("Try submiting again!!!");
						}
					} else if (response.data["userdetails"] != undefined) {
						document.getElementById("div_login").style.display = "none";
						document.getElementById("div_aptitude").style.display = "none";
						document.getElementById("div_result").style.display = "block";
						$scope.userdetails = response.data["userdetails"];
					}
				}
			}

			function error(response) {
				console.log(response.statusText);
			}

			function cleardata() {
				$scope.login.loginName = "";
				$scope.login.password = "";
			}

			$scope.validateanswers = function() {
				var radiobindingarray = [];
				var validateanswerarray = [];
				radiobindingarray = $scope.myform.$$success["parse"];
				for (i = 0; i < radiobindingarray.length; i++) {
					console.log(radiobindingarray[i].$viewValue);
					validateanswerarray = validateanswerarray
					.concat(radiobindingarray[i].$viewValue);
				}

				method = "POST";
				url = "/validateAnswer";
				data = {};
				data.validateanswerarray = validateanswerarray;
				data.userid = userid;

				if (radiobindingarray != undefined) {
					backendcall(method, url, data);
				}

			}

			$scope.exportPDF = function() {
				method = "GET";
				url = "/generatePdf";
				data = {};

				backendcall(method, url, data);
			}

			/*$scope.countdown = function() {
				stopped = $timeout(function() {
					console.log($scope.counter);
					$scope.counter--;
					$scope.countdown();
				}, 1000);
			};*/

			$scope.counter = "Time Remaining: 0h 30m 00s";
			var time = 1800;
			var t;
			var days, hours, minutes, seconds;

			t = time;
			days = Math.floor(t / 86400);
			t -= days * 86400;
			hours = Math.floor(t / 3600) % 24;
			t -= hours * 3600;
			minutes = Math.floor(t / 60) % 60;
			t -= minutes * 60;
			seconds = t % 60;
			t = [ hours + 'h', minutes + 'm', seconds + 's' ].join(' ');

			var init = function() {
				stopped = $timeout(function() {
					//console.log(t);
					time--;

					t = time;
					days = Math.floor(t / 86400);
					t -= days * 86400;
					hours = Math.floor(t / 3600) % 24;
					t -= hours * 3600;
					minutes = Math.floor(t / 60) % 60;
					t -= minutes * 60;
					seconds = t % 60;
					t = [ hours + 'h', minutes + 'm', seconds + 's' ].join(' ');

					$scope.counter = "Time Remaining: "+t;

					if(time == 0)
					{
						var radiobindingarray = [];
						var validateanswerarray = [];
						radiobindingarray = $scope.myform.$$success["parse"];
						for (i = 0; i < radiobindingarray.length; i++) {
							console.log(radiobindingarray[i].$viewValue);
							validateanswerarray = validateanswerarray
							.concat(radiobindingarray[i].$viewValue);
						}

						method = "POST";
						url = "/validateAnswer";
						data = {};
						data.validateanswerarray = validateanswerarray;
						data.userid = userid;

						if (radiobindingarray != undefined) {
							backendcall(method, url, data);
						}

					}

					init();
				}, 1000);
			};

			init();

		})

		app.factory('readFile', function($window, $q) {
			'use strict';

			var readFile = function(file) {
				var deferred = $q.defer(), reader = new $window.FileReader();

				reader.onload = function(ev) {
					var content = ev.target.result;
					deferred.resolve(content);
				};

				reader.readAsText(file);
				return deferred.promise;
			};

			return readFile;
		})

		app.directive('fileBrowser', function(readFile, $http) {
			'use strict';

			var filename;

			return {
				template : '<input type="file" style="display: none;" />'
					+ '<ng-transclude></ng-transclude>',
					transclude : true,
					link : function(scope, element) {
						var fileInput = element.children('input[file]');

						fileInput.on('change', function(event) {
							var file = event.target.files[0];
							filename = file;
							console.log(filename.name);
							var fd = new FormData();
							fd.append('file', file);

							$http.post("/uploadAptitude", fd, {
								transformRequest : angular.identity,
								headers : {
									'Content-Type' : undefined
								},
								data : fd
							}).then(success, error);

						});

						element.on('click', function() {
							fileInput[0].click();
						});

						function success(response) {
							alert(response.data);
						}
						function error(response) {
							console.log(response.statusText);
							alert(response.data);
						}
					}
			};
		}),

		// Register the 'myCurrentTime' directive factory method.
		// We inject $interval and dateFilter service since the factory method is DI.
		app.directive('myCurrentTime', [ '$interval', 'dateFilter',
			function($interval, dateFilter) {
			// return the directive link function. (compile function not needed)
			return function(scope, element, attrs) {
				var format, // date format
				stopTime; // so that we can cancel the time updates

				// used to update the UI
				function updateTime() {
					element.text(dateFilter(new Date(), format));
				}

				// watch the expression, and update the UI on change.
				scope.$watch(attrs.myCurrentTime, function(value) {
					format = value;
					updateTime();
				});

				stopTime = $interval(updateTime, 1000);

				// listen on DOM destroy (removal) event, and cancel the next UI update
				// to prevent updating time after the DOM element was removed.
				element.on('$destroy', function() {
					$interval.cancel(stopTime);
				});
			}
		} ]);

$(document).ready(function() {
	$("#login_registerclick").click(login_callRegister);
	$("#login_clearclick").click(login_callClear);
	$("#login_logoutclick").click(login_callLogout);

	function login_callRegister() {
		window.location.href = "Register.html";
	}

	function login_callClear() {
		login_LoginName.value = "";
		login_Password.value = "";
	}

	function login_callLogout() {
		location.reload();
	}
});