<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Save Image Test</title>
    <script>
        function setFullPath(input) {
            var fullPath = input.value;
            document.getElementById('fullPath').value = fullPath;
        }

        function previewImage(input) {
            setFullPath(input);
            var reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById("preview").setAttribute('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    </script>
</head>
<body>
<h2>Input path</h2>
<form method="post" action="test-save-image" enctype="multipart/form-data">
    <input type="file" name="image" accept="image/*" onchange="previewImage(this)" />
    <br />
    <img id="preview" style="max-width: 300px; max-height: 300px;" />
    <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>