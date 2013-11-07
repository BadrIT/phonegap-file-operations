
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
 			callback(filePath);
 		}, function (error) {
 			alert("Failed to save photo. Error code: " + error.code);
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
			fileSystem.root.getFile(path,
 			function(file){
 				file.remove(function(){
 					console.log("remove "+fileName);
 					success(true);
 				}, 
 				function(error){
 					alert(error.code);
 					success(false);
 				});
 			},
 			function(error){
 				alert(error.code);
 				console.log("Error creating directory " + error.code);
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
   
   window.plugins.FileOperations = new FileOperations();
});

