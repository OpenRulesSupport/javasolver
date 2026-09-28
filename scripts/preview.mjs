import http from "node:http";
import fs from "node:fs";
import path from "node:path";

const root = path.resolve(import.meta.dirname, "..", "docs");
const types = { ".css": "text/css", ".html": "text/html", ".js": "text/javascript", ".json": "application/json", ".png": "image/png", ".jpg": "image/jpeg", ".jpeg": "image/jpeg", ".svg": "image/svg+xml", ".xml": "application/xml" };

http.createServer((request, response) => {
  const pathname = decodeURIComponent(new URL(request.url, "http://localhost").pathname);
  let filename = path.join(root, pathname);
  if (!path.extname(filename)) filename = path.join(filename, "index.html");
  if (!filename.startsWith(root)) {
    response.writeHead(403).end("Forbidden");
    return;
  }
  if (!fs.existsSync(filename)) filename = path.join(root, "404.html");
  response.writeHead(filename.endsWith("404.html") ? 404 : 200, { "Content-Type": types[path.extname(filename)] || "application/octet-stream" });
  fs.createReadStream(filename).pipe(response);
}).listen(4173, "127.0.0.1", () => console.log("Java Solver preview: http://127.0.0.1:4173"));
