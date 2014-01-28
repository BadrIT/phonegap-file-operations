
function FileOperations() {
}

FileOperations.prototype.copyFile = function(from, to, success, failure) {
	var args = {};
	args.from = from;
	args.to = to;
	if (device.platform == "Android")
		cordova.exec(success, failure, "FileOperations", "copy", [args]);
	else{
		var fileTransfer = new FileTransfer();
 		fileTransfer.download(args.from, args.to, function (entry) {
 			success(args.to);
 		}, function (error) {
 			alert("Failed to save photo. Error code: " + error.code);
            failure(error.code);
 		});
	}
}

FileOperations.prototype.deleteFile = function(path, success, failure) {
	var args = {};
	args.path = path;
	if (device.platform == "Android")
		cordova.exec(success, failure, "FileOperations", "delete", [args]);
	else{

		var onRequestFileSystemSuccess = function (fileSystem) { 
			fileSystem.root.getFile(args.path,
 			function(file){
 				file.remove(function(){
 					console.log("remove: " + args.path);
 					success(true);
 				}, 
 				function(error){
 					alert(error.code);
 					failure(error.code);
 				});
 			},
 			function(error){
 				alert(error.code);
 				console.log("Error creating directory " + error.code);
                failure(error.code);
 			});
		} 
		window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, onRequestFileSystemSuccess, null); 
		
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
   
   if (device.platform == "Android")
   window.plugins.FileOperations = new FileOperations();
});

