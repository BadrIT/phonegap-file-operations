FileOperations plugin for Cordova / PhoneGap
======================================================

This plugin is used to copy, move and delete files in android, but why not FileTransfer?

because file transfer is not implemented to handle large size of files,
so if you need to use FileTransfer.download to handle copy elements, your app may crash, so we have to do it nativly

## Usage

Example Usage: 

1. **Copy file**

```js
var from = "/mnt/sdcard/Android/data/com.example.hello/cache/1390911.jpg";
var to = "/mnt/sdcard/test/files/1390911.jpg";
window.plugins.FileOperations.copyFile(from, to, function(){console.log('success')}, function(){console.log('fail')});
```
2. **Delete file**

```js
var path = "/mnt/sdcard/test/files/1390911.jpg";
window.plugins.FileOperations.deleteFile(path, function(){console.log('success')}, function(){console.log('fail')});
});
```

This has been successfully tested on Cordova 3.0 to 3.1.

## MIT Licence

Copyright 2013 Monday Consulting GmbH

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
