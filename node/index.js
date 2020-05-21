const express = require("express");
const app = express();
const logger = require("morgan");
const http = require("http");
const path = require("path");
const PORT = process.env.PORT || 8080;
const bodyParser = require("body-parser");
const baseAPI = "/api/v1";
const cors = require("cors");
const activity = require("./routes/activity");
const activityService = require("./routes/activity-service");
const server = http.createServer(app);

app.use(cors());
app.use(bodyParser.json());
app.use(logger("dev"));
app.use(bodyParser.urlencoded({ extended: true }));
app.get("/", function (req, res) {
  res.send("Hello World!");
});
app.use("/assistants", activity);
activityService.connectDb(function (err) {
  if (err) {
    console.log("Could not connect with MongoDB–moviesService");
    process.exit(1);
  }
  server.listen(PORT, function () {
    console.log("Server up and running on localhost:" + PORT);
  });
});
