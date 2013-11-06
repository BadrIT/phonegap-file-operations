// window.plugins.FileOperations

function FileOperations() {
}

FileOperations.prototype.copy = function(from, to, success, failure) {
	var args = {};
	args.from = from;
	args.to = to;
	if (device.platform == "Android")
		cordova.exec(success, failure, "FileOperations", "copy", [args]);
	else{
		var fileTransfer = new FileTransfer();
 		fileTransfer.download(args.from, args.to, function (entry) {
 			callback(filePath);
 		}, function (error) {
 			alert("Failed to save photo. Error code: " + error.code);
 		});
	}
}

cordova.addConstructor(function()  {
	if(!window.plugins)
	{
		window.plugins = {};
	}
	
   // shim to work in 1.5 and 1.6
   if (!window.Cordova) {
   	window.Cordova = cordova;
   };
   
   window.plugins.FileOperations = new FileOperations();
});