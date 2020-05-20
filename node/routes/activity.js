("use strict");
const express = require("express");
const router = express.Router();
const activityService = require("./activity-service");

// Add Activity
router.post("/", function (req, res) {
  let activity = req.body;
  activityService.add(activity, (err, activity) => {
    if (err) {
      res.status(500).send({ msg: err });
    } else if (activity !== null) {
      res.status(200).send({ msg: "Assistant created!" });
    }
  });
});

// Get Activity with id
router.get("/:_id", function (req, res) {
  let _id = req.params._id;
  activityService.get(_id, (err, activity) => {
    if (err) {
      res.status(500).send({ msg: err });
    } else if (activity === null) {
      res.status(500).send({ msg: "Assitant not exist" });
    } else {
      res.status(200).send(activity);
    }
  });
});

// Get all Activities
router.get("/", function (req, res) {
  activityService.getAll((err, activity) => {
    if (err) {
      res.status(500).send({ msg: err });
    } else if (activity === null) {
      res.status(500).send({ msg: "Assistant is empty" });
    } else {
      res.status(200).send(activity);
    }
  });
});

// Delete Acticity with id
router.delete("/:_id", function (req, res) {
  let _id = req.params._id;
  activityService.remove(_id, (err) => {
    if (err) {
      res.status(404).send({ msg: err });
    } else {
      res.status(200).send({ msg: "Assitant deleted!" });
    }
  });
});

// Delete all Activities
router.delete("/", function (req, res) {
  activityService.removeAll((err) => {
    if (err) {
      res.status(500).send({ msg: err });
    } else {
      res.status(200).send({ msg: "Assitant deleted!" });
    }
  });
});

// Update Activity with id
router.put("/:_id", function (req, res) {
  const _id = req.params._id;
  const updatedActivity = req.body;
  activityService.update(_id, updatedActivity, (err, numUpdates) => {
    if (err || numUpdates === 0) {
      res.status(500).send({ msg: err });
    } else {
      res.status(200).send({ msg: "Assitant updated!" });
    }
  });
});

module.exports = router;
