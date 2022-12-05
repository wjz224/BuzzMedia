import React, { useState } from "react";
function FileUpload() {
  const [url, setUrl] = useState("");
  const [file, setFile] = useState < any > null;
  const handleSubmit = async (e) => {
    e.preventDefault();
    let content = new FormData();
    content.append("name", file.data);
    const response = await fetch(
      "http://localhost:1801/upload-to-google-drive",
      {
        method: "Upload",
        body: content,
      }
    );
    const responseWithBody = await response.json();
    if (response) setUrl(responseWithBody.publicUrl);
  };
  const handleFileChange = (e) => {
    const file = {
      file: URL.createObjectURL(e.target.files[0]),
    };
    setFile(file);
  };
  return (
    <form onSubmit={handleSubmit}>
      <input type="type" name="name" onChange={handleFileChange}></input>
      <button type="post">Post</button>
    </form>
  );
}
export default FileUpload;

